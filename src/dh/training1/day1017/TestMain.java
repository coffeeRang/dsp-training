package dh.training1.day1017;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TestMain {
	
	public static void main(String[] args) {
		UseJson useJson = new UseJson();
		
		JSONObject jsonObj = useJson.getJsonFile();
		JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");
		
		Map<String, Object> resultMap = useJson.replaceFormat(jsonArr);

		Set<String> companyKeySet = resultMap.keySet();
		
		for (String companyKey: companyKeySet) {
			Map<String, Object> companyMap = (Map<String, Object>)resultMap.get(companyKey);
			Map<String, Object> positionMap = null;
			Map<String, Object> evalMap = null;
//			Map<String, Object> indicatMap = null;
			ArrayList<String> indicatInfoList = null;
			System.out.println(">> 회사명 : " + companyMap.get("companyName"));

			Set<String> positionKeySet = companyMap.keySet();
			Set<String> evalKeySet = null;
			Set<String> indicatKeySet = null;
			for (String positionKey: positionKeySet) {
				if (positionKey.equals("companyName")) {
				} else {
					positionMap = (Map<String, Object>)companyMap.get(positionKey);
					System.out.println("\t직책명 : " + positionMap.get("positionName"));
					evalKeySet = positionMap.keySet();

					for (String evalKey: evalKeySet) {
						if (evalKey.equals("positionName")) {
						} else {
							evalMap = (Map<String, Object>)positionMap.get(evalKey);
							indicatKeySet = evalMap.keySet();
							
							for(String indicatKey: indicatKeySet) {
								if (!indicatKey.equals("list")) {
									System.out.println("\t\t지표구분 : " + evalMap.get("evalFieldName"));
								} else {
									indicatInfoList = (ArrayList<String>)evalMap.get(indicatKey);
									for (int i = 0; i < indicatInfoList.size(); i++) {
										System.out.println("\t\t\t" + i + " : " + indicatInfoList.get(i));
									}
								}
								
							}
							
						}
					}
					
				}
				
			}
			// break; // 한 계열사만 테스트시 임시 break 문
			
		}
		
	}

	
}
