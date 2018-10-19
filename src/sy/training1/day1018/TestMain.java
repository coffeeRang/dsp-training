package sy.training1.day1018;

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

public class TestMain {

	public static void main(String[] args) {

	}
	/**
	 * 리턴형을 맵으로 하는 method를 만들어 자료를 Map에 넣어준다. 
	 * 2018/10/16 ~ 2018/10/17
	 * @author NamSangYuop
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> getJson() {
		JSONParser parser = new JSONParser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			// 현재 class의 상대경로를 조회
			String path = TestMain.class.getResource("").getPath();
			
			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "indicat_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");
			
			/**
			 * jsonArr 사이즈 만큼 돌려서 Map에 값을 넣어준다.
			 * @author NamSangYuop
			 */
			for (int i = 0; i < jsonArr.size(); i++) {
				//Map들을 선언.
				Map<String, Object> posMap = null;
				Map<String, Object> evalMap = null;
				Map<String, Object> kpieMap = null;
				List<String> kpieList = null;
				
				JSONObject jsonO = (JSONObject) jsonArr.get(i);
				
				String companyNum = (String) jsonO.get("companyNum");
				String companyName = (String) jsonO.get("companyName");
				String positionCd = (String) jsonO.get("positionCd");
				String positionName = (String) jsonO.get("positionName");
				String evalFieldCd = (String) jsonO.get("evalFieldCd");
				String evalFieldName = (String) jsonO.get("evalFieldName");
				String kpiEvalIndicatNum = (String) jsonO.get("kpiEvalIndicatNum");
				String kpiEvalIndicatName = (String) jsonO.get("kpiEvalIndicatName");
				
				posMap = (Map<String, Object>) map.get(companyNum);
				
				// posMap이 null일 경우. posMap에 값을 넣어주고 companyNum과 이어준다.
				if (posMap == null) {
					
					kpieList = new ArrayList<String>();
					kpieList.add(kpiEvalIndicatName);
					
					kpieMap = new HashMap<String, Object>();
					kpieMap.put("evalFieldName", evalFieldName);
					kpieMap.put("list", kpieList);
					
					evalMap = new HashMap<String, Object>();
					evalMap.put("positionName", positionName);
					evalMap.put(evalFieldCd, kpieMap);
					
					posMap = new HashMap<String, Object>();
					posMap.put("companyName", companyName);
					posMap.put(positionCd, evalMap);
					map.put(companyNum, posMap);
					
				} else {
					evalMap = (Map<String, Object>) posMap.get(positionCd);
					//evalMap이 null일 경우. evalMap에 값을 넣어주고 posMap과 이어준다.
					if (evalMap == null) {
						
						kpieList = new ArrayList<String>();
						kpieList.add(kpiEvalIndicatName);
						kpieMap = new HashMap<String, Object>();
						kpieMap.put("evalFieldName", evalFieldName);
						kpieMap.put("list", kpieList);
						evalMap = new HashMap<String, Object>();
						evalMap.put("positionName", positionName);
						evalMap.put(evalFieldCd, kpieMap);
						posMap.put(positionCd, evalMap);
						
					} else {
						kpieMap = (Map<String, Object>) evalMap.get(evalFieldCd);
						//kpieMap값이 null일 경우. kpieMap에 값을 넣어주고 evalMap과 이어준다.
						if (kpieMap == null) {
							
							kpieList = new ArrayList<String>();
							kpieList.add(kpiEvalIndicatName);
							kpieMap = new HashMap<String, Object>();
							kpieMap.put("evalFieldName", evalFieldName);
							kpieMap.put("list", kpieList);
							evalMap.put(evalFieldCd, kpieMap);
							
						} else {
							kpieList = (ArrayList<String>) kpieMap.get("list");
							//kpieList가 null일 경우. list를 새로만들어주고 원래 list주소값에 넣어준다.
							if (kpieList == null) {
								kpieList = new ArrayList<String>();
								map.put("list", kpieList);
							}
							kpieList.add(kpiEvalIndicatName);
						}
						
					}
					
				}
			}
			Map<String, Object> map1 = (Map<String, Object>) map.get("100");
			Map<String, Object> map2 = (Map<String, Object>) map1.get("HP01");
			Map<String, Object> map3 = (Map<String, Object>) map2.get("HE01");
			List<String> list1 = (List<String>) map3.get("list");
			
//			for (int j = 0; j < list1.size(); j++) {
//				
//				System.out.println(list1.get(j));
//			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return map;
	}
}