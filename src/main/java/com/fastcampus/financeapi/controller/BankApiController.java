package com.fastcampus.financeapi.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.financeapi.dto.DepositDto;
import com.fastcampus.financeapi.dto.DepositOptionDto;
import com.fastcampus.financeapi.dto.SavingDto;
import com.fastcampus.financeapi.dto.SavingOptionDto;
import com.fastcampus.financeapi.service.DepositService;
import com.fastcampus.financeapi.service.SavingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BankApiController {
	private final DepositService depositService;
	private final SavingService savingService;
	private final String auth = "{api key 넣어야함}";


	@GetMapping("/deposit")
	public String getAllDeposit() throws Exception {
		String[] topFinGrpNoList = {"020000", "030200", "030300", "050000", "060000"};
		// String[] topFinGrpNoList = { "030300"};
		int pageNo = 1;
		for (String topFinGrpNo : topFinGrpNoList) {
			Long maxPageNo = getDeposit(auth, topFinGrpNo, 1);
			while (maxPageNo > pageNo) {
				pageNo += 1;
				getDeposit(auth, topFinGrpNo, pageNo);
			}
		}

		return "success";
	}

	@GetMapping("/saving")
	public String getAllSaving() throws Exception {
		String[] topFinGrpNoList = {"020000", "030200", "030300", "050000", "060000"};
		int pageNo = 1;
		for (String topFinGrpNo : topFinGrpNoList) {
			Long maxPageNo = getSaving(auth, topFinGrpNo, 1);
			while (maxPageNo > pageNo) {
				pageNo += 1;
				getSaving(auth, topFinGrpNo, pageNo);
			}
		}

		return "success";
	}

	public Long getDeposit(String auth, String topFinGrpNo, int pageNo) throws Exception {

		String result = "";
		Long maxPageNo = 0L;

		try {
			String urlStr =
				"https://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json?auth=" + auth + "&topFinGrpNo="
					+ topFinGrpNo
					+ "&pageNo=" + pageNo;
			URL url = new URL(urlStr);
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

			result = br.readLine();

			//JSON 파싱 객체를 생성
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
			//total_count 0이면 데이터가 없거나 오류임
			JSONObject resultJsonObj = (JSONObject)jsonObject.get("result");
			String totalCount = resultJsonObj.get("total_count").toString();

			log.info("total count is {}", totalCount);
			if (totalCount.equals("0")) {
				return 0L;
			}
			maxPageNo = getLong(resultJsonObj, "max_page_no", 0l);

			//baseList 받아옴
			JSONArray baseList = (JSONArray)resultJsonObj.get("baseList");
			log.info("baseList.size: {}", baseList.size());
			for (var base : baseList) {
				JSONObject baseJsonObj = (JSONObject)base;
				// log.info("baseJsonObj: {}", baseJsonObj.toString());
				String dcls_month = getString(baseJsonObj, "dcls_month", "").trim();
				String fin_co_no = getString(baseJsonObj, "fin_co_no", "").trim();
				String fin_prdt_cd = getString(baseJsonObj, "fin_prdt_cd", "").trim();
				String id = dcls_month + "_" + fin_co_no + "_" + fin_prdt_cd;

				DepositDto depositDto = DepositDto.builder()
					.id(id)
					.dcls_month(dcls_month)
					.fin_co_no(fin_co_no)
					.fin_prdt_cd(fin_prdt_cd)
					.dcls_strt_day(getString(baseJsonObj, "dcls_strt_day", ""))
					.etc_note(getString(baseJsonObj, "etc_note", ""))
					.fin_co_subm_day(getString(baseJsonObj, "fin_co_subm_day", ""))
					.join_deny(getString(baseJsonObj, "join_deny", ""))
					.join_member(getString(baseJsonObj, "join_member", ""))
					.join_way(getString(baseJsonObj, "join_way", ""))
					.kor_co_nm(getString(baseJsonObj, "kor_co_nm", ""))
					.max_limit(getLong(baseJsonObj, "max_limit", 0L))
					.mtrt_int(getString(baseJsonObj, "mtrt_int", ""))
					.spcl_cnd(getString(baseJsonObj, "spcl_cnd", ""))
					.fin_prdt_nm(getString(baseJsonObj, "fin_prdt_nm", ""))
					.dcls_end_day(getString(baseJsonObj, "dcls_end_day", ""))
					.top_fin_grp_no(topFinGrpNo)
					.build();
				// log.info("depositDto: {}", depositDto);
				depositService.saveDeposit(depositDto);
			}


			//optionList를 받아옴
			JSONArray optionList = (JSONArray)resultJsonObj.get("optionList");
			for (var option : optionList) {
				JSONObject optionJsonObj = (JSONObject)option;

				String dcls_month = getString(optionJsonObj, "dcls_month", "").trim();
				String fin_co_no = getString(optionJsonObj, "fin_co_no", "").trim();
				String fin_prdt_cd = getString(optionJsonObj, "fin_prdt_cd", "").trim();
				String id = dcls_month + "_" + fin_co_no + "_" + fin_prdt_cd;

				DepositOptionDto depositOptionDto = DepositOptionDto.builder()
					.id(id)
					.dcls_month(dcls_month)
					.fin_co_no(fin_co_no)
					.fin_prdt_cd(fin_prdt_cd)
					.intr_rate_type(getString(optionJsonObj, "intr_rate_type", ""))
					.intr_rate_type_nm(getString(optionJsonObj, "intr_rate_type_nm", ""))
					.save_trm(getString(optionJsonObj, "save_trm", ""))
					.intr_rate(getDouble(optionJsonObj, "intr_rate", 0.0))
					.intr_rate2(getDouble(optionJsonObj, "intr_rate2", 0.0))
					.build();
				depositService.saveDepositOption(depositOptionDto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxPageNo;
	}

	public Long getSaving(String auth, String topFinGrpNo, int pageNo) throws Exception {

		String result = "";
		Long maxPageNo = 0L;

		try {
			String urlStr =
				"https://finlife.fss.or.kr/finlifeapi/savingProductsSearch.json?auth=" + auth + "&topFinGrpNo="
					+ topFinGrpNo
					+ "&pageNo=" + pageNo;
			URL url = new URL(urlStr);
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

			result = br.readLine();

			//JSON 파싱 객체를 생성
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
			//total_count 0이면 데이터가 없거나 오류임
			JSONObject resultJsonObj = (JSONObject)jsonObject.get("result");
			String totalCount = resultJsonObj.get("total_count").toString();

			log.info("total count is {}", totalCount);
			if (totalCount.equals("0")) {
				return 0L;
			}
			maxPageNo = getLong(resultJsonObj, "max_page_no", 0l);

			//baseList 받아옴
			JSONArray baseList = (JSONArray)resultJsonObj.get("baseList");
			log.info("baseList.size: {}", baseList.size());
			for (var base : baseList) {
				JSONObject baseJsonObj = (JSONObject)base;
				// log.info("baseJsonObj: {}", baseJsonObj.toString());
				String dcls_month = getString(baseJsonObj, "dcls_month", "").trim();
				String fin_co_no = getString(baseJsonObj, "fin_co_no", "").trim();
				String fin_prdt_cd = getString(baseJsonObj, "fin_prdt_cd", "").trim();
				String id = dcls_month + "_" + fin_co_no + "_" + fin_prdt_cd;

				SavingDto savingDto = SavingDto.builder()
					.id(id)
					.dcls_month(dcls_month)
					.fin_co_no(fin_co_no)
					.fin_prdt_cd(fin_prdt_cd)
					.dcls_strt_day(getString(baseJsonObj, "dcls_strt_day", ""))
					.etc_note(getString(baseJsonObj, "etc_note", ""))
					.fin_co_subm_day(getString(baseJsonObj, "fin_co_subm_day", ""))
					.join_deny(getString(baseJsonObj, "join_deny", ""))
					.join_member(getString(baseJsonObj, "join_member", ""))
					.join_way(getString(baseJsonObj, "join_way", ""))
					.kor_co_nm(getString(baseJsonObj, "kor_co_nm", ""))
					.max_limit(getLong(baseJsonObj, "max_limit", 0L))
					.mtrt_int(getString(baseJsonObj, "mtrt_int", ""))
					.spcl_cnd(getString(baseJsonObj, "spcl_cnd", ""))
					.fin_prdt_nm(getString(baseJsonObj, "fin_prdt_nm", ""))
					.dcls_end_day(getString(baseJsonObj, "dcls_end_day", ""))
					.top_fin_grp_no(topFinGrpNo)
					.build();

				savingService.saveSaving(savingDto);
			}
			//optionList를 받아옴
			JSONArray optionList = (JSONArray)resultJsonObj.get("optionList");
			for (var option : optionList) {
				JSONObject optionJsonObj = (JSONObject)option;
				String dcls_month = getString(optionJsonObj, "dcls_month", "").trim();
				String fin_co_no = getString(optionJsonObj, "fin_co_no", "").trim();
				String fin_prdt_cd = getString(optionJsonObj, "fin_prdt_cd", "").trim();
				String id = dcls_month + "_" + fin_co_no + "_" + fin_prdt_cd;

				SavingOptionDto savingOptionDto = SavingOptionDto.builder()
					.id(id)
					.dcls_month(dcls_month)
					.fin_co_no(fin_co_no)
					.fin_prdt_cd(fin_prdt_cd)
					.intr_rate_type(getString(optionJsonObj, "intr_rate_type", ""))
					.intr_rate_type_nm(getString(optionJsonObj, "intr_rate_type_nm", ""))
					.rsrv_type(getString(optionJsonObj, "rsrv_type", ""))
					.rsrv_type_nm(getString(optionJsonObj, "rsrv_type_nm", ""))
					.save_trm(getString(optionJsonObj, "save_trm", ""))
					.intr_rate(getDouble(optionJsonObj, "intr_rate", 0.0))
					.intr_rate2(getDouble(optionJsonObj, "intr_rate2", 0.0))
					.build();
				savingService.saveSavingOption(savingOptionDto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxPageNo;
	}

	private static String getString(JSONObject jsonObject, String key, String defaultValue) {
		String value = (String)jsonObject.get(key);
		return value != null ? value : defaultValue;
	}

	private static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
		Long value = (Long)jsonObject.get(key);
		return value != null ? value : defaultValue;
	}

	private static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
		if (jsonObject.get(key) instanceof Double) {
			Double value = (Double)jsonObject.get(key);
			return value != null ? value : defaultValue;
		} else if (jsonObject.get(key) instanceof Long) {
			Long value = (Long)jsonObject.get(key);
			return value != null ? value : defaultValue;
		}
		return defaultValue;
	}
}
