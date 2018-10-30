package gun.training2.day1030;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonSimpleAssets {

	/**json파일 안에 데이터들을 Map에 담아서 반환함.
	 * @return
	 */
	public Map<String, Object> getJson() {
		Map<String, Object> assetsMap = null;

		JSONParser parser = new JSONParser();
		try {
			//현재 class의 상대경로를 조회
			String path = JsonSimpleSale.class.getResource("").getPath();

			//해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = null;

			try {
				obj = parser.parse(new FileReader(path + "bs_table_db.json"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			JSONArray jsonArr = (JSONArray) obj;

			//최상위 부모 코드를 key로 가짐
			assetsMap = new TreeMap<String, Object>();

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jsonO = (JSONObject) jsonArr.get(i);

				//최상위 부모를 의미
				//부모이름, 자식코드, 구분코드를 key로 가짐
				Map<String, Object> parentsMap = null;

				//최상위 부모의 자식을 의미
				//자식이름, 구분코드, 자손 코드를 key로 가짐
				Map<String, Object> childMap = null;

				//자식의 자식, 즉 자손을 의미
				//자손 이름, 구분코드를 key로 가짐
				Map<String, Object> grandsonMap = null;

				//현재 Row의 부모 코드
				String parentsCd = (String) jsonO.get("account_sbjt_cd");
				//현재 Row의 부모 이름
				String parentsName = (String) jsonO.get("account_sbjt_name");
				//현재 Row의 자식 코드
				String childCd = (String) jsonO.get("std_cd");
				//현재 Row의 자식 이름
				String childName = (String) jsonO.get("std_name");
				//현재 Row의 구분 코드
				String prevCurrent = (String) jsonO.get("prev_current");
				//현재 Row의 값
				String seriesData = (String) jsonO.get("seriesdata");

				//parentsCd가 최상위 코드인지 확인하기 위한 변수.
				boolean isParents = true;
				
				//뒷 자리 0이 4개가 아닐 경우, 최상위 코드의 하위 코드라는 것이므로
				//false로 변경한다.
				if(!parentsCd.substring(parentsCd.length() - 4).equals("0000")) {
					isParents = false;
				}
				
				//들어온 parentsCd에 자산, 부채 등 1번째 계층인지 검사.
				if (isParents) {

					parentsMap = (Map<String, Object>) assetsMap.get(parentsCd);

					//parentsMap이 없는 경우.
					if (parentsMap == null) {

						childMap = childMap = addChildMap(childMap, childName, prevCurrent, seriesData);

						parentsMap = new TreeMap<String, Object>();
						parentsMap.put("account_sbjt_name", parentsName);
						parentsMap.put(childCd, childMap);
						addSeriesData(parentsMap, prevCurrent, seriesData);

						assetsMap.put(parentsCd, parentsMap);

						//parentsMap이 있는 경우.
					} else {
						childMap = (Map<String, Object>) parentsMap.get(childCd);

						//합계
						addSeriesData(parentsMap, prevCurrent, seriesData);

						//childMap이 없는 경우.
						if (childMap == null) {
							childMap = childMap = addChildMap(childMap, childName, prevCurrent, seriesData);

							parentsMap.put(childCd, childMap);
							addSeriesData(parentsMap, prevCurrent, seriesData);

							//childMap이 있는 경우.
						} else {
							//합계
							addSeriesData(childMap, prevCurrent, seriesData);
						}
					}

					//들어온 parentsCd가 비유동자산, 유동자산 등 2번째 계층일 경우.
				} else {
					//최상위 계층의 코드
					String superCd = parentsCd.substring(0, 3) + "0000";

					parentsMap = (Map<String, Object>) assetsMap.get(superCd);

					//parentsMap이 없는 경우.
					if (parentsMap == null) {

						grandsonMap = addGrandsonMap(grandsonMap, childName, prevCurrent, seriesData);

						childMap = addChildMap(childMap, parentsName, prevCurrent, seriesData);
						childMap.put(childCd, grandsonMap);

						parentsMap = new TreeMap<String, Object>();
						parentsMap.put(parentsCd, childMap);
						addSeriesData(parentsMap, prevCurrent, seriesData);

						assetsMap.put(superCd, parentsMap);

						//parentsMap이 있는 경우.
					} else {
						childMap = (Map<String, Object>) parentsMap.get(parentsCd);

						//합계
						addSeriesData(parentsMap, prevCurrent, seriesData);

						//childMap이 없는 경우.
						if (childMap == null) {
							grandsonMap = addGrandsonMap(grandsonMap, childName, prevCurrent, seriesData);

							childMap = childMap = addChildMap(childMap, childName, prevCurrent, seriesData);
							childMap.put(childCd, grandsonMap);

							parentsMap.put(parentsCd, childMap);

							//childMap이 있는 경우. 
						} else {
							grandsonMap = (Map<String, Object>) childMap.get(childCd);

							//합계
							addSeriesData(childMap, prevCurrent, seriesData);

							//grandsonMap이 없는 경우.
							if (grandsonMap == null) {
								grandsonMap = addGrandsonMap(grandsonMap, childName, prevCurrent, seriesData);

								childMap.put(childCd, grandsonMap);

								//grandsonMap이 있는 경우.
							} else {
								//grandsonMap 안에 동기, 전기, 전년동기가 없는 경우.
								if (grandsonMap.get(prevCurrent) == null) {
									addSeriesData(grandsonMap, prevCurrent, seriesData);
								}
							}
						}
					}
				}
			}
			System.out.println(assetsMap.toString());

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return assetsMap;
	}

	/** 들어온 map에 prevCurrent를 key로 갖는 value가 있는지 판단한 후 있으면 더하고, 없으면 넣어준다. map에 넣는 모든
	 * 숫자형 데이터의 처리는 이곳에서 한다.
	 * @param map
	 * @param prevCurrent
	 * @param seriesData */
	public void addSeriesData(Map<String, Object> map, String prevCurrent, String seriesData) {
		JsonSimpleSale jsonSimple = new JsonSimpleSale();
		//전기, 동기, 전년동기가 없는 경우.
		//없는 경우, 생성
		if (map.get(prevCurrent) == null) {

			map.put(prevCurrent, addComma(jsonSimple.getRound(seriesData)));

			//전기, 동기, 전년동기가 있는 경우.
			//있는 경우, 더해줌
		} else {
			double oldValue = removeComma((String) map.get(prevCurrent));
			double newValue = jsonSimple.getRound(seriesData);

			double result = jsonSimple.getRound(oldValue + newValue);

			map.put(prevCurrent, addComma(result));
		}
	}

	/** 천단위로 콤마(,)를 찍은 후 반환함.
	 * @param value
	 * @return */
	public String addComma(double value) {
		return new java.text.DecimalFormat("#,###.#").format(value);
	}

	/** 넘어온 문자열의 콤마(,)를 제거 후 double형으로 반환함.
	 * @param value
	 * @return */
	public double removeComma(String value) {
		String parseValue = value.replaceAll("\\,", "");

		return Double.parseDouble(parseValue);
	}

	/** Map에 파라미터 값들을 추가 후 반환함.
	 * @param grandsonMap
	 * @param childName
	 * @param prevCurrent
	 * @param seriesData
	 * @return */
	public Map<String, Object> addGrandsonMap(Map<String, Object> grandsonMap, String childName, String prevCurrent, String seriesData) {
		grandsonMap = new TreeMap<String, Object>();
		grandsonMap.put("std_name", childName);
		addSeriesData(grandsonMap, prevCurrent, seriesData);

		return grandsonMap;
	}

	/** Map에 파라미터 값들을 추가 후 반환함.
	 * @param childMap
	 * @param parentsName
	 * @param prevCurrent
	 * @param seriesData
	 * @return */
	public Map<String, Object> addChildMap(Map<String, Object> childMap, String childName, String prevCurrent, String seriesData) {
		childMap = new TreeMap<String, Object>();
		childMap.put("std_name", childName);
		addSeriesData(childMap, prevCurrent, seriesData);

		return childMap;
	}
}
