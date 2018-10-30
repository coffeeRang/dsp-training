package sy.training2.day1030_3;

import java.io.*;
import java.util.*;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.json.simple.*;
import org.json.simple.parser.*;

public class TestJson {
	/**
	 * 2018/10/23
	 * @author NamSangYuop
	 * @param args
	 */
//	public static void main(String[] args) {
	public Map<String,Object> get() {
		JSONParser parser = new JSONParser();
		HashMap<String,Object> map = new HashMap<String, Object>();
	try {
		// 현재 class의 상대경로를 조회
		String path = TestJson.class.getResource("").getPath();

		// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
		Object obj = parser.parse(new FileReader(path + "pl_table_db.json"));
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray jsonArr = (JSONArray) jsonObj.get("seriesName");
		for (int i = 0; i < jsonArr.size(); i++) {
			//Map을 선언
			Map<String, Object> stdMap= null;
			Map<String, Object> prevMap= null;
			Map<String, Object> seriesMap= null;
			double val1 = 0;
			double val2 = 0;
			
			JSONObject jsonO = (JSONObject) jsonArr.get(i);

			String account_sbjt_cd = (String) jsonO.get("account_sbjt_cd");  // 구분코드
			String account_sbjt_name = (String) jsonO.get("account_sbjt_name");// 매출액 매출원가 매출총이익
			String std_cd = (String) jsonO.get("std_cd");// 물품코드
			String std_name = (String) jsonO.get("std_name");// 물품이름
			String prev_current = (String) jsonO.get("prev_current");// 16:당기 01:전기 02:전년동기
			String seriesdata =  (String) jsonO.get("seriesdata");// 가격   이걸 더해야하는데....
			double data = 0;
			
			stdMap = (Map<String, Object>) map.get(account_sbjt_cd);
			// stdMap이 널일경우
			if(stdMap == null) {
				
				seriesMap = new HashMap<String,Object>();
				data = roundd(seriesdata);
				seriesMap.put("seriesdata", data);
				
				prevMap = new HashMap<String,Object>();
				prevMap.put("std_name", std_name);
				prevMap.put(prev_current, seriesMap);
				
				stdMap = new HashMap<String,Object>();
				stdMap.put("account_sbjt_name", account_sbjt_name);
				val2 = roundd(seriesdata);
				stdMap.put(prev_current, val2);
				stdMap.put(std_cd, prevMap);
				
				map.put(account_sbjt_cd, stdMap);
				
			}else {
				prevMap = (Map<String,Object>) stdMap.get(std_cd);
				// prevMap이 널일경우
				if(prevMap == null) {
					seriesMap = new HashMap<String,Object>();
					data = roundd(seriesdata);
					seriesMap.put("seriesdata", data);
					
					prevMap = new HashMap<String,Object>();
					prevMap.put("std_name", std_name);
					prevMap.put(prev_current, seriesMap);
					//매출액 , 매출원가, 매출총이익.전기 당기 전년동기의 값을 널일경우 넣어줌 
					
					if(stdMap.get(prev_current) == null) {
						val2 = roundd(seriesdata);
						stdMap.put(prev_current, val2);
					//있을경우 ++
						
					}else {
						val1 = (double) stdMap.get(prev_current);
						val2 = roundd(seriesdata);
						stdMap.put(prev_current, val1 + val2);
					}
						stdMap.put(std_cd, prevMap);
				}else {
					seriesMap = (Map<String,Object>) prevMap.get(prev_current);
					// seriesMap이 널일경우
					if(seriesMap == null) {
						seriesMap = new HashMap<String,Object>();
						data = roundd(seriesdata);
						seriesMap.put("seriesdata", data);
						//매출액 , 매출원가, 매출총이익.전기 당기 전년동기의 값을 널일경우 넣어줌 
						if(stdMap.get(prev_current) == null) {
							val2 = roundd(seriesdata);
							stdMap.put(prev_current, val2);
							//있을경우 ++
						}else {
							val1 = (double) stdMap.get(prev_current);
							val2 = roundd(seriesdata);
							stdMap.put(prev_current, val1 + val2);
						}
					}
				}
			}
			prevMap.put(prev_current, seriesMap);
		}
		
		System.out.println(map+"\n");
		
	} catch (IOException | ParseException e) {
		e.printStackTrace();
	}
		return map;
	}
	
	/**
	 *string형태로 받아오는 값을 형변환후 roundd에서 처리하게해줌
	 * @param target
	 * @return result
	 */
	public double roundd(String target) {
		
		double result = Double.parseDouble(target);
		return roundd(result, 1);
	}
	/**
	 * Math.round를 사용해서 소수점 1번째 자리까지 잘라주는 역할
	 * @param target
	 * @param count
	 * @return result
	 */
	public double roundd(double target, int count) {
//		System.out.println(target + " : " + count);
		double value = 0;
		double result = 0;
		for(int i=0; i<=count; i++) {
			value= i * 10 ;
		}
		result = Math.round(target * value) / value;
//		System.out.println("result :: "+result);
		return result;
	}
}
