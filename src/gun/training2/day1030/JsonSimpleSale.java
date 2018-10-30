package gun.training2.day1030;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** @author kimgun
 * @date 2018.10.18 JsonSimple 기능 구현 클래스 */
public class JsonSimpleSale {

	/** json에 내용들을 map에 추가 후 반환함.
	 * @return */
	public Map<String, Object> getJson() {
		//			public static void main(String[] args) {
		//account_sbjt_cd를 key로 가짐
		//예)F110000, F120000, F130000
		Map<String, Object> saleMap = null;

		JSONParser parser = new JSONParser();
		Map<String, Object> companyMap = null;
		try {
			//현재 class의 상대경로를 조회
			String path = JsonSimpleSale.class.getResource("").getPath();

			//해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "pl_table_db.json"));

			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("seriesName");

			//printTest(jsonArr);

			saleMap = new TreeMap<String, Object>();

			//jsonArr의 size만큼
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jsonO = (JSONObject) jsonArr.get(i);

				//account_name, std_cd를 key로 가짐
				//예)account_name, std_cd
				Map<String, Object> stdMap = null;
				//std_cd를 key로 가짐
				//예)S-100_01, S-100-02, S-100-03
				Map<String, Object> preMap = null;

				//현재 Row의 계정과목코드
				String accountSbjtCd = (String) jsonO.get("account_sbjt_cd");
				//현재 Row의 계정과목이름
				String accountSbjtName = (String) jsonO.get("account_sbjt_name");
				//현재 Row의 항목코드
				String stdCd = (String) jsonO.get("std_cd");
				//현재 Row의 항목이름
				String stdName = (String) jsonO.get("std_name");
				//현재 Row의 당기, 전기, 전년동기 여부
				String prevCurrent = (String) jsonO.get("prev_current");
				//현재 Row의 값
				String seriesData = (String) jsonO.get("seriesdata");

				stdMap = (Map<String, Object>) saleMap.get(accountSbjtCd);

				//stdMap이 없을 경우.
				//stdMap이 없다는 것은 account_sbjt_cd를 key로 갖는 값이 존재하지 않는 것을 의미
				if (stdMap == null) {

					preMap = addStdName(stdName);

					double addValue = getRound(seriesData);

					preMap.put(prevCurrent, addValue);

					stdMap = new TreeMap<String, Object>();
					stdMap.put("account_sbjt_name", accountSbjtName);

					stdMap.put(prevCurrent, addValue);

					stdMap.put(stdCd, preMap);

					saleMap.put(accountSbjtCd, stdMap);

					//stdMap이 있을 경우.
					//stdMap이 있다는 것은 account_sbjt_cd를 key로 갖는 값이 존재한다는 것을 의미
				} else {

					preMap = (Map<String, Object>) stdMap.get(stdCd);

					//preMap이 없을 경우.
					//preMap이 없다는 것은 std_cd를 key로 갖는 값이 존재하지 않는 것을 의미
					if (preMap == null) {

						preMap = addStdName(stdName);

						double addValue = getRound(seriesData);
						preMap.put(prevCurrent, addValue);

						addSum(stdMap, prevCurrent, seriesData);

						stdMap.put(stdCd, preMap);

						//preMap이 있을 경우.
						//preMap이 있다는 것은 std_cd를 key로 갖는 값이 존재한다는 것을 의미
					} else {

						addSum(stdMap, prevCurrent, seriesData);

						double addValue = getRound(seriesData);
						preMap.put(prevCurrent, addValue);
					}

				}
			}
			System.out.println(saleMap);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return saleMap;
	}

	/** 테스트용 메소드
	 * @param jsonArr */
	private static void printTest(JSONArray jsonArr) {
		System.out.println("=========출력 테스트=========");
		for (int i = 0; i < jsonArr.size(); i++) {

			JSONObject jsonO = (JSONObject) jsonArr.get(i);

			System.out.println("부모코드 : " + jsonO.get("account_sbjt_cd"));
			System.out.println("부모명 : " + jsonO.get("account_sbjt_name"));
			System.out.println("자식코드 : " + jsonO.get("std_cd"));
			System.out.println("자식명 : " + jsonO.get("std_name"));
			if (jsonO.get("prev_current").equals("16")) {
				System.out.println("전 흐름 : 전년동기");
			} else if (jsonO.get("prev_current").equals("01")) {
				System.out.println("전 흐름 : 전기");
			} else {
				System.out.println("전 흐름 : 당기");
			}
			System.out.println("데이터 : " + jsonO.get("seriesdata"));
			System.out.println("------------------------");
		}
	}

	/** PreMap을 의미하는 Map을 생성하여 stdName을 추가 후 반환함.
	 * @param stdName
	 * @return */
	private Map<String, Object> addStdName(String stdName) {
		Map<String, Object> returnMap = new TreeMap<String, Object>();
		returnMap.put("std_name", stdName);

		return returnMap;
	}

	/** Map 안에 prevCurrent인 key값이 있는 경우. 더해주고, 없는 경우. 넣어준다.
	 * @param stdMap
	 * @param prevCurrent
	 * @param seriesData */
	private void addSum(Map<String, Object> stdMap, String prevCurrent, String seriesData) {
		//stdMap 안에 prevCurrent를 key로 갖는 값이 존재하지 않는 경우.
		//존재하지 않는 경우 합계를 들어있는 key가 없는 것이므로
		//prevCurrent를 key로 현재 seriesData 데이터를 넣어준다.
		if (stdMap.get(prevCurrent) == null) {
			double addValue = getRound(seriesData);

			stdMap.put(prevCurrent, addValue);

			//stdMap 안에 prevCurrent를 key로 갖는 값이 존재하는 경우.
			//존재하는 경우 기존에 값이 이미 들어있는 것이므로 현재 seriesData의 값을 더해준다.
		} else {
			double sumVal1 = getRound(seriesData);
			double sumVal2 = getRound((double) stdMap.get(prevCurrent));

			double sumResult = sumVal1 + sumVal2;

			stdMap.put(prevCurrent, sumResult);
		}
	}

	/** Double형 문자열의 소수 점 자리를 첫 번째 자리까지 반 올림하여 반환함.
	 * @param value
	 * @return */
	public double getRound(String value) {
		double castValue = Double.parseDouble(value);
		return getRound(castValue, 1);
	}

	/** Double형 변수의 소수 점 자리를 첫 번째 자리까지 반 올림하여 반환함.
	 * @param value
	 * @return */
	public double getRound(double value) {
		return getRound(value, 1);
	}

	/** Doube형 변수의 지정한 소수 점 자리까지 반 올림하여 반환함.
	 * @param value
	 * @param dotPoint
	 * @return */
	public double getRound(double value, double dotPoint) {
		double dot = 1d;
		
		for (int i = 0; i < dotPoint; i++) {	
			dot = dot * 10;
		}

		return (double) Math.round(value * dot) / dot;
	}
}
