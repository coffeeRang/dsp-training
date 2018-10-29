package sy.training1.day1025;

import java.io.*;
import java.util.*;

import org.apache.catalina.connector.Request;
import org.apache.catalina.ha.context.ReplicatedContext;
import org.json.simple.*;
import org.json.simple.parser.*;

import sy.training1.day1025.TestJson;

public class BsTable{
	/**
	 * 2018/10/25
	 * @author NamSangYuop
	 */
//	public static void main(String[] args) {
	public Map<String,Object> get() {
		JSONParser parser = new JSONParser();
		HashMap<String,Object> map = new HashMap<String, Object>();
	try {
		// 현재 class의 상대경로를 조회
		String path = BsTable.class.getResource("").getPath();

		// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
		Object obj = parser.parse(new FileReader(path + "bs_table_db.json"));
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray jsonArr = (JSONArray) jsonObj.get("bsData");
		
		// TestJson에 있는 roundd()을 쓰기위해불러옴
		TestJson tj = new TestJson();
		
		for (int i = 0; i < jsonArr.size(); i++) {
			//Map을 선언
			Map<String, Object> stdMap= null;
			Map<String, Object> prevMap= null;
			Map<String, Object> seriesMap= null;
			double val1 = 0;
			double val2 = 0;
			
			JSONObject jsonO = (JSONObject) jsonArr.get(i);

			String account_sbjt_cd = (String) jsonO.get("account_sbjt_cd");  // 구분코드  F210000 , F211000, F212000
			String account_sbjt_name = (String) jsonO.get("account_sbjt_name");// 자산 유동 비유동 부채 유동 비유동
			String std_cd = (String) jsonO.get("std_cd");//  F211000  F212000
			String std_name = (String) jsonO.get("std_name");// 유동자산 , 비유동자산
			String prev_current = (String) jsonO.get("prev_current");// 16:당기 01:전기 02:전년동기
			String seriesdata =  (String) jsonO.get("seriesdata");// 가격
			double data = 0;
			
			stdMap = (Map<String, Object>) map.get(account_sbjt_cd);
			//stdMap이 널일경우
			if(stdMap == null) {
				seriesMap = new HashMap<String,Object>();
				data = tj.roundd(seriesdata);
				seriesMap.put("seriesdata", data);
				
				prevMap = new HashMap<String,Object>();
				prevMap.put("std_name", std_name);
				prevMap.put(prev_current, seriesMap);
				stdMap = new HashMap<String,Object>();
				stdMap.put("account_sbjt_name", account_sbjt_name);
				val2 = tj.roundd(seriesdata);
				stdMap.put(prev_current, val2);
				stdMap.put(std_cd, prevMap);
				map.put(account_sbjt_cd, stdMap);
				
			}else {
				prevMap = (Map<String,Object>) stdMap.get(std_cd);
				// prevMap이 널일경우
				if(prevMap == null) {
					seriesMap = new HashMap<String,Object>();
					data = tj.roundd(seriesdata);
					seriesMap.put("seriesdata", data);
					
					prevMap = new HashMap<String,Object>();
					prevMap.put("std_name", std_name);
					prevMap.put(prev_current, seriesMap);
					//매출액 , 매출원가, 매출총이익.전기 당기 전년동기의 값을 널일경우 넣어줌 
					if(stdMap.get(prev_current) == null) {
						val2 = tj.roundd(seriesdata);
						stdMap.put(prev_current, val2);
					//있을경우 +
					}else {
						val1 = (double) stdMap.get(prev_current);
						val2 = tj.roundd(seriesdata);
						stdMap.put(prev_current, val1 + val2);
					}
						stdMap.put(std_cd, prevMap);
				}else {
					seriesMap = (Map<String,Object>) prevMap.get(prev_current);
					// seriesMap이 널일경우
					if(seriesMap == null) {
						seriesMap = new HashMap<String,Object>();
						data = tj.roundd(seriesdata);
						seriesMap.put("seriesdata", data);
						//매출액 , 매출원가, 매출총이익.전기 당기 전년동기의 값을 널일경우 넣어줌 
						if(stdMap.get(prev_current) == null) {
							val2 = tj.roundd(seriesdata);
							stdMap.put(prev_current, val2);
							//있을경우 ++
						}else {
							val1 = (double) stdMap.get(prev_current);
							val2 = tj.roundd(seriesdata);
							stdMap.put(prev_current, val1 + val2);
						}
					}
				}
				prevMap.put(prev_current, seriesMap);
			}
		}
		
		System.out.println(map+"\n");
		
	} catch (IOException | ParseException e) {
		e.printStackTrace();
	}
		return map;
	}
	
}


