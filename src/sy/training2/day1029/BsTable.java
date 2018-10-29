package sy.training2.day1029;

import java.io.*;
import java.util.*;

import org.apache.catalina.connector.Request;
import org.apache.catalina.ha.context.ReplicatedContext;
import org.json.simple.*;
import org.json.simple.parser.*;

import sy.training2.day1029.TestJson;

public class BsTable {
	/**
	 * 2018/10/25
	 * 
	 * @author NamSangYuop
	 */
	public static void main(String[] args) {
		// public Map<String,Object> get() {
		JSONParser parser = new JSONParser();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// 현재 class의 상대경로를 조회
			String path = BsTable.class.getResource("").getPath();

			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "bs_table_db.json"));
			JSONObject jsonObj = (JSONObject)obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("bsData");

			// TestJson에 있는 roundd()을 쓰기위해불러옴
			TestJson tj = new TestJson();
			Set<String> keySet = new LinkedHashSet<String>();

			for (int i = 0; i < jsonArr.size(); i++) {
				// Map을 선언
				TreeMap<String, Object> stdMap = null; // 자산,부채,유동,비유동의 이름과 std_cd가
													// 들어있음
				TreeMap<String, Object> prevMap = null; // std_name 과 prev_current가
													// 들어있음
				TreeMap<String, Object> seriesMap = null; // 값들이 들어있음
				// double val1 = 0;
				// double val2 = 0;

				JSONObject jsonO = (JSONObject) jsonArr.get(i);


				// F210000
					// F211000
					// F212000

				// F220000
					// F221000
					// F222000

				// F240000
				String account_sbjt_cd = (String) jsonO.get("account_sbjt_cd"); // 구분코드
																				// F210000
																				// ,
																				// F211000,
																				// F212000
				keySet.add(account_sbjt_cd);
				

				String account_sbjt_name = (String) jsonO.get("account_sbjt_name");// 자산 유동 비유동 부채 유동 비유동
				String std_cd = (String) jsonO.get("std_cd");// F211000 F212000
				String std_name = (String) jsonO.get("std_name");// 유동자산 , 비유동자산
				String prev_current = (String) jsonO.get("prev_current");// 02:당기
																			// 01:전기
																			// 16:전년동기
				String seriesdata = (String) jsonO.get("seriesdata");// 돈
				double data = 0;

				// 뒤에 네자리가 0000이면 부모
				if (account_sbjt_cd.substring(3, 7).equals("0000")) {
				} else {
					
				}
			}
			
			for(String key: keySet) {
				System.out.println(key);
				
			}

//			System.out.println(map + "\n");

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		// return map;
	}
}
