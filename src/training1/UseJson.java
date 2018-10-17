package training1;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UseJson {
	
	/**
	 * JSONObject 파일로 return 하는 메서드
	 * @author dhkim
	 * @return
	 */
	public JSONObject getJsonFile() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		
		try {
			// 현재 class의 상대경로를 조회
			String path = TestMain.class.getResource("").getPath();

			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "indicat_db.json"));

			jsonObj = (JSONObject)obj;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj;
		
	}
	
	/**
	 * format 변경하는 메서드
	 * @author dhkim
	 * @param jsonArr
	 * @return
	 */
	public Map<String, Object> replaceFormat(JSONArray jsonArr) {
		Map<String, Object> masterMap = new HashMap<String, Object>();
		for (int i = 0; i < jsonArr.size(); i++) {
			Map<String, Object> companyMap = null;
			Map<String, Object> positionMap = null;
			Map<String, Object> evalMap = null;
			List<String> evalIndicatList = null;
			
			JSONObject dataObj = (JSONObject)jsonArr.get(i);
			
			String companyNum = (String)dataObj.get("companyNum");
			String companyName = (String)dataObj.get("companyName");
			String positionCd = (String)dataObj.get("positionCd");
			String positionName = (String)dataObj.get("positionName");
			String evalFieldCd = (String)dataObj.get("evalFieldCd");
			String evalFieldName = (String)dataObj.get("evalFieldName");
			String kpiEvalIndicatName = (String)dataObj.get("kpiEvalIndicatName");
			
			 companyMap = (Map<String, Object>)masterMap.get(companyNum);
			 if (companyMap == null) {
				 evalIndicatList = new ArrayList<String>();
				 evalIndicatList.add(kpiEvalIndicatName);
				 evalMap = new HashMap<String, Object>();
				 evalMap.put("evalFieldName", evalFieldName);
				 evalMap.put("list", evalIndicatList);
				 positionMap = new HashMap<String, Object>();
				 positionMap.put("positionName", positionName);
				 positionMap.put(evalFieldCd, evalMap);
				 companyMap = new HashMap<String, Object>();
				 companyMap.put("companyName", companyName);
				 companyMap.put(positionCd, positionMap);
				 
				 masterMap.put(companyNum, companyMap);

			 } else {
				 positionMap = (Map<String, Object>)companyMap.get(positionCd);
				 if (positionMap == null) {
					 evalIndicatList = new ArrayList<String>();
					 evalIndicatList.add(kpiEvalIndicatName);
					 evalMap = new HashMap<String, Object>();
					 evalMap.put("evalFieldName", evalFieldName);
					 evalMap.put("list", evalIndicatList);
					 positionMap = new HashMap<String, Object>();
					 positionMap.put("positionName", positionName);
					 positionMap.put(evalFieldCd, evalMap);
					 
					 companyMap.put(positionCd, positionMap);
					 
				 } else {
					 evalMap = (Map<String, Object>)positionMap.get(evalFieldCd);
					 if (evalMap == null) {
						 evalIndicatList = new ArrayList<String>();
						 evalIndicatList.add(kpiEvalIndicatName);
						 evalMap = new HashMap<String, Object>();
						 evalMap.put("evalFieldName", evalFieldName);
						 evalMap.put("list", evalIndicatList);
						 
						 positionMap.put(evalFieldCd, evalMap);
						 
					 } else {
						 evalIndicatList = (ArrayList<String>)evalMap.get("list");
						 if (evalIndicatList == null) {
							 evalIndicatList = new ArrayList<String>();
						 }
						 evalIndicatList.add(kpiEvalIndicatName);
//						 evalMap.put("evalFieldName", evalFieldName);
						 evalMap.put("list", evalIndicatList);
					 }
					 
				 }
			 }
			 
		}
		
		return masterMap;
	}

}
