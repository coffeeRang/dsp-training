package training1;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.print.StreamPrintService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TestMainBak {
	
//	public static void main(String[] args) {
//		UseJson useJson = new UseJson();
//		
//		JSONObject jsonObj = useJson.getJsonFile();
//		
////		System.out.println(jsonObj.toString());
////		System.out.println(jsonObj.get("dataArray").toString());
//		
//		JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");
//		
//		System.out.println(jsonArr);
//		
////		List<Object> list = new ArrayList<Object>();
////		String[] companyNumArr = {"100", "200", "300", "400", "500", "600", "700"};
//
//		HashMap<String, Object> hMap = new HashMap<String, Object>();
//		Set<String> companyNumSet = new LinkedHashSet<String>();
//		Set<String> positionCdSet = new LinkedHashSet<String>();
//		Set<String> evalFieldCdSet = new LinkedHashSet<String>();
//		for (int i = 0; i < jsonArr.size(); i++) {
//			JSONObject dataObj = (JSONObject)jsonArr.get(i);
//			companyNumSet.add((String)dataObj.get("companyNum"));
//			positionCdSet.add((String)dataObj.get("positionCd"));
//			evalFieldCdSet.add((String)dataObj.get("evalFieldCd"));
//		}
//		
//		for (String keys: companyNumSet) {
//			System.out.println(">> set key : " + keys);
//		}
//		
//		// 계열사에 따라서 리스트로 묶어서 map에 넣기
//		for (String companyKey: companyNumSet) {
//			List<Object> list = new ArrayList<Object>();
//
//			for (int j = 0; j < jsonArr.size(); j++) {
//				JSONObject dataObj = (JSONObject)jsonArr.get(j);
//				String companyNum = (String)dataObj.get("companyNum");
//				
//				if (companyKey.equals(companyNum)) {
//					HashMap<String, Object> positionMap = new HashMap<String, Object>();
//					list.add(dataObj);
//				}
//				positionCdSet.add( (String)dataObj.get("positionCd") );
//				evalFieldCdSet.add( (String)dataObj.get("evalFieldCd") );
//			}
//
//			hMap.put(companyKey, list);
//		}
//		
////		HashMap<String, Object> companyMap = null;
////		HashMap<String, Object> positionMap = null;
////		HashMap<String, Object> evalFieldMap = null;
////		HashMap<String, Object> indicatMap = null;
//		
////		HashMap<String, Object> companyMap = null;
//		TreeMap<String, Object> positionMap = new TreeMap<String, Object>();
////		HashMap<String, Object> evalFieldMap = null;
//		TreeMap<String, Object> indicatMap = new TreeMap<String, Object>();
//		HashMap<String, Object> totalMap = new HashMap<String, Object>();
//		
//		List<Object> indicatInfoList = null;
//		for (int i = 0; i < jsonArr.size(); i++ ) {
//			JSONObject dataObj = (JSONObject)jsonArr.get(i);
//			String companyNum = (String)dataObj.get("companyNum");
//			String positionCd = (String)dataObj.get("positionCd");
//			String evalFieldCd = (String)dataObj.get("evalFieldCd");
//
//			for (String companyKey: companyNumSet) {
//				HashMap<String, Object> companyMap = new HashMap<String, Object>();
//				if (companyKey.equals(companyNum)) {
//					companyMap.put("companyName", dataObj.get("companyName"));
//					companyMap.put("positionCd", positionMap);
//
//					totalMap.put(companyKey, companyMap);
//					
//					for (String positionKey: positionCdSet) {
//						if (positionKey.equals(positionCd)) {
//							HashMap<String, Object> evalFieldMap = new HashMap<String, Object>();
//							positionMap.put(positionKey, evalFieldMap);
//							evalFieldMap.put("positionName", dataObj.get("positionName"));
//							
//							for (String evalFieldKey: evalFieldCdSet ) {
//								if (evalFieldKey.equals(evalFieldCd)) {
//									HashMap<String, Object> tempMap = new HashMap<String, Object>();
//									indicatInfoList = new ArrayList<Object>();
//									indicatInfoList.add(dataObj);
//									tempMap.put("evalFieldName", dataObj.get("evalFieldName"));
////									tempMap.put("indicatInfoList", indicatInfoList);
//									indicatMap.put(evalFieldKey, tempMap);
////									indicatMap.put("list", indicatInfoList);
//									
//									evalFieldMap.put("evalFieldCd", indicatMap);
//									
//									
//								}
//								
//							}
//							
//							
//						}
//						
//						
//					}
////					totalMap.put(companyKey, companyMap);
//					
//				}
//				
//				
//			}
//
//			
//		}
//		
//		for (int i = 0; i < indicatInfoList.size(); i++) {
//			System.out.println(i + " 번째 값 : " + indicatInfoList.get(i));
//			
//		}
//		
////		indicatMap.put("list", indicatInfoList);
////		System.out.println(totalMap);
//		
//
//	}

	
	

	
}
