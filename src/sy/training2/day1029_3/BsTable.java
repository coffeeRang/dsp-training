package sy.training2.day1029_3;

import java.io.*;
import java.util.*;

import org.apache.catalina.connector.Request;
import org.apache.catalina.ha.context.ReplicatedContext;
import org.json.simple.*;
import org.json.simple.parser.*;

import sy.training2.day1029_3.TestJson;

public class BsTable {
	/**
	 * 2018/10/25
	 * 
	 * @author NamSangYuop
	 */
	public static void main(String[] args) {
//		 public Map<String,Object> get() {
		JSONParser parser = new JSONParser();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// 현재 class의 상대경로를 조회
			String path = BsTable.class.getResource("").getPath();

			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser
					.parse(new FileReader(path + "bs_table_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("bsData");

			// TestJson에 있는 roundd()을 쓰기위해불러옴
			TestJson tj = new TestJson();

			for (int i = 0; i < jsonArr.size(); i++) {
				// Map을 선언
				Map<String, Object> stdMap = null; // 자산,부채,유동,비유동의 이름과 std_cd가
													// 들어있음
				Map<String, Object> prevMap = null; // std_name 과 prev_current가
													// 들어있음
				Map<String, Object> seriesMap = null; // 값들이 들어있음
				// double val1 = 0;
				// double val2 = 0;

				JSONObject jsonO = (JSONObject) jsonArr.get(i);

				String account_sbjt_cd = (String) jsonO.get("account_sbjt_cd"); // 구분코드
				String account_sbjt_name = (String) jsonO.get("account_sbjt_name");// 자산 유동 비유동 부채 유동 비유동
				String std_cd = (String) jsonO.get("std_cd");// F211000 F212000
				String std_name = (String) jsonO.get("std_name");// 유동자산 , 비유동자산
				String prev_current = (String) jsonO.get("prev_current");// 02:당기 01:전기 16:전년동기
				String seriesdata = (String) jsonO.get("seriesdata");// 돈
				double data = 0;

				// 뒤에 네자리가 0000이면 부모
				if (account_sbjt_cd.substring(3, 7).equals("0000")) {
					stdMap = (Map<String, Object>) map.get(account_sbjt_cd);
					// stdMap이 널일때
					if (stdMap == null) {
						seriesMap = new HashMap<String, Object>();
						seriesMap.put("seriesdata", seriesdata);

						prevMap = new HashMap<String, Object>();
						prevMap.put("std_name", std_name);
						prevMap.put(prev_current, seriesMap);

						stdMap = new HashMap<String, Object>();
						stdMap.put("account_sbjt_name", account_sbjt_name);
						stdMap.put(prev_current, seriesdata);
						stdMap.put(std_cd, prevMap);
						map.put(account_sbjt_cd, stdMap);
					} else {
						prevMap = (Map<String, Object>) stdMap.get(std_cd);
						// prevMap이 널일때
						if (prevMap == null) {
							seriesMap = new HashMap<String, Object>();
							seriesMap.put("seriesdata", seriesdata);

							prevMap = new HashMap<String, Object>();
							prevMap.put("std_name", std_name);
							prevMap.put(prev_current, seriesMap);
							stdMap.put(std_cd, prevMap);

						} else {
							seriesMap = (Map<String, Object>) prevMap.get(prev_current);
							// seriesMap이 널일경우
							
							if(prevMap.get(prev_current) == null) {
								prevMap.put(prev_current, seriesMap);
							}
							
							if (seriesMap == null) {
								seriesMap = new HashMap<String, Object>();
								seriesMap.put("seriesdata", seriesdata);
							}
						}
					}
					prevMap.put(prev_current, seriesMap);

					// 자식일때
				} else{
					// 부모대행 == 최상위코드의 자식
					stdMap = (Map<String, Object>) map.get(account_sbjt_cd.substring(0, 3) + "0000");
					
					prevMap = (Map<String, Object>) stdMap.get(account_sbjt_cd);
					//자식이 없을때
					if (prevMap == null) {
						seriesMap = new HashMap<String, Object>();
						seriesMap.put(prev_current, seriesdata);
						seriesMap.put("std_name", std_name);

						prevMap = new HashMap<String, Object>();
						prevMap.put("std_name", account_sbjt_name);
						prevMap.put(std_cd, seriesMap);
						
						
						stdMap.put(account_sbjt_cd.substring(0, 4) + "000",prevMap);
						stdMap.put(prev_current, seriesdata);
						
						map.put(account_sbjt_cd.substring(0, 3) + "0000",stdMap);
					}else {
						seriesMap = (Map<String, Object>) prevMap.get(std_cd);
						
						if(prevMap.get(prev_current) == null) {
							prevMap.put(prev_current, seriesMap);
						}
						
						//자식의 자식이 없을때
						if(seriesMap == null || !prev_current.equals(seriesMap.get("seriesdata")) ){
							seriesMap = new HashMap<String, Object>();
							seriesMap.put(prev_current, seriesdata);
							seriesMap.put("std_name", std_name);
							
							prevMap.put(std_cd, seriesMap);
						//자식의 자식이 있을때
						}else {
							if(seriesMap.get(prev_current) == null) {
								seriesMap.put(prev_current, seriesdata);
								prevMap.put(prev_current, seriesMap);
							}else {
								prevMap.put(prev_current, seriesMap);
							}
						}
					}
				}
			}

			System.out.println(map + "\n");

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
//		 return map;
	}
}
