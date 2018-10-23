package gun.training2.day1022;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** @author kimgun
 * @date 2018.10.18 JsonSimple 기능 구현 클래스 */
public class JsonSimple {
	/** json에 내용들을 map에 추가 후 반환함.
	 * @return */
	public Map<String, Object> getJson() {
		JSONParser parser = new JSONParser();
		Map<String, Object> companyMap = null;
		try {
			//현재 class의 상대경로를 조회
			String path = JsonSimple.class.getResource("").getPath();

			//해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "indicat_db.json"));

			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");

			//회사 정보가 들어갈 Map
			companyMap = new HashMap<String, Object>();

			//jsonArr에 담긴 Row만큼 돌면서 처리
			for (int i = 0; i < jsonArr.size(); i++) {
				//companyName, postionCd를 key로 갖는 map
				Map<String, Object> comNumMap = null;
				//positionName, evalFieldCd를 key로 갖는 map
				Map<String, Object> posCdMap = null;
				//evalName, list를 key로 갖는 map
				Map<String, Object> evalCdMap = null;

				//evalCdMap에 들어갈 list로 성과지표 Row가 들어감
				List<String> kpieList = null;

				JSONObject jsonO = (JSONObject) jsonArr.get(i);

				String companyNum = (String) jsonO.get("companyNum"); //i번째 companyNum 정보를 할당
				String companyName = (String) jsonO.get("companyName"); //i번째 companyName 정보를 할당
				String positionCd = (String) jsonO.get("positionCd"); //i번째 positionCd 정보를 할당
				String positionName = (String) jsonO.get("positionName"); //i번째 positionName 정보를 할당
				String evalFieldCd = (String) jsonO.get("evalFieldCd"); //i번째 evalFieldCd 정보를 할당
				String evalFieldName = (String) jsonO.get("evalFieldName"); //i번째 evalFieldName 정보를 할당
				String kpiEvalIndicatName = (String) jsonO.get("kpiEvalIndicatName"); //i번째 kpiEvalIndicatName 정보를 할당

				comNumMap = (Map<String, Object>) companyMap.get(companyNum);

				//comNumMap이 없을 경우
				if (comNumMap == null) {
					evalCdMap = addEvalMap(evalFieldName, kpiEvalIndicatName, kpieList);

					posCdMap = addPosMap(positionName, evalFieldCd, kpieList, evalCdMap);

					comNumMap = new HashMap<String, Object>();
					comNumMap.put("companyName", companyName);
					comNumMap.put(positionCd, posCdMap);

					companyMap.put(companyNum, comNumMap);

					//comNumMap이 있을 경우
				} else {
					posCdMap = (Map<String, Object>) comNumMap.get(positionCd);

					//posCdMap이 없을 경우
					if (posCdMap == null) {
						evalCdMap = addEvalMap(evalFieldName, kpiEvalIndicatName, kpieList);

						posCdMap = addPosMap(positionName, evalFieldCd, kpieList, evalCdMap);

						comNumMap.put(positionCd, posCdMap);

						//posCdMap이 있을 경우
					} else {
						evalCdMap = (Map<String, Object>) posCdMap.get(evalFieldCd);

						//evalCdMap이 없을 경우
						if (evalCdMap == null) {
							evalCdMap = addEvalMap(evalFieldName, kpiEvalIndicatName, kpieList);

							posCdMap.put(evalFieldCd, evalCdMap);

							//evalCdMap이 있을 경우
						} else {
							kpieList = (ArrayList<String>) evalCdMap.get("list");

							//kpieList이 없을 경우
							if (kpieList == null) {
								kpieList = new ArrayList<String>();
								kpieList.add(kpiEvalIndicatName);
								evalCdMap.put("list", kpieList);

								//kpieList이 있을 경우
							} else {
								kpieList.add(kpiEvalIndicatName);
							}
						}
					}
				}
			}
			//			System.out.println("==========companyMap==========================");
			//			System.out.println(companyMap);
			//			System.out.println("==============================================");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return companyMap;
	}

	/** 테스트용 메소드
	 * @param jsonArr */
	private void printTest(JSONArray jsonArr) {
		System.out.println("=========출력 테스트=========");
		for (int i = 0; i < jsonArr.size(); i++) {

			JSONObject jsonO = (JSONObject) jsonArr.get(i);

			System.out.println("회사코드 : " + jsonO.get("companyNum"));
			System.out.println("회사명 : " + jsonO.get("companyName"));
			System.out.println("직급코드 : " + jsonO.get("positionCd"));
			System.out.println("직급명 : " + jsonO.get("positionName"));
			System.out.println("구분코드 : " + jsonO.get("evalFieldCd"));
			System.out.println("구분명 : " + jsonO.get("evalFieldName"));
			System.out.println("표시코드 : " + jsonO.get("indicatInsideNum"));
			System.out.println("구분표시코드 : " + jsonO.get("kpiEvalIndicatNum"));
			System.out.println("구분표시명 : " + jsonO.get("kpiEvalIndicatName"));
			System.out.println("------------------------");
		}
	}

	/** Map을 새로 생성해서 들어온 파라미터를 추가 후 반환함.
	 * @param evalFieldName
	 * @param kpiEvalIndicatName
	 * @param kpieList
	 * @return */
	private Map<String, Object> addEvalMap(String evalFieldName, String kpiEvalIndicatName, List<String> kpieList) {
		Map<String, Object> evalCdMap = new HashMap<String, Object>();
		evalCdMap.put("evalFieldName", evalFieldName);

		kpieList = new ArrayList<String>();
		kpieList.add(kpiEvalIndicatName);
		evalCdMap.put("list", kpieList);

		return evalCdMap;
	}

	/** Map을 새로 생성해서 들어온 파라미터를 추가 후 반환함.
	 * @param positionName
	 * @param evalFieldCd
	 * @param kpieList
	 * @param evalCdMap
	 * @return */
	private Map<String, Object> addPosMap(String positionName, String evalFieldCd, List<String> kpieList, Map<String, Object> evalCdMap) {
		Map<String, Object> posCdMap = new HashMap<String, Object>();
		posCdMap.put("positionName", positionName);
		posCdMap.put(evalFieldCd, evalCdMap);

		return posCdMap;
	}
}
