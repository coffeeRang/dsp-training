package dh.training3.day1101;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dh.util.UseJson;

public class AccountSumr {
	private Set<String> accountSbjtPathSet = new LinkedHashSet<String>();
	
	/**
	 * TestMain에서 호출되는 메서드 - json 파일 읽어들어 구조 변경
	 * @author dhkim
	 * @param jsonArr
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> replaceFormat(JSONArray jsonArr) {
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
		
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject)jsonArr.get(i);
			
			String accountSbjtName = (String) jsonObj.get("accountSbjtName");			// 계정명
			long accountSbjtLvOrder = (Long) jsonObj.get("accountSbjtLvOrder");		// 계정depth 레벨
			long accountSbjtSortOrder = (long) jsonObj.get("accountSbjtSortOrder");	// 계정정렬 순서
			String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");	// 상위계정코드
			String accountSbjtCd = (String) jsonObj.get("accountSbjtCd");				// 계정코드
			String accountSbjtUnitName = (String) jsonObj.get("accountSbjtUnitName");	// 금액 단위 
			String organizationName = (String) jsonObj.get("organizationName");			// 조직명
			String bizSecName = (String) jsonObj.get("bizSecName");						// 사업부문명
			
			LinkedHashMap<String, Object> newLinkedHashMap = new LinkedHashMap<String, Object>();
			newLinkedHashMap.put("accountSbjtName", accountSbjtName);
			newLinkedHashMap.put("accountSbjtLvOrder", accountSbjtLvOrder);
			newLinkedHashMap.put("accountSbjtSortOrder", accountSbjtSortOrder);
			newLinkedHashMap.put("upperAccountSbjtCd", upperAccountSbjtCd);
			newLinkedHashMap.put("accountSbjtCd", accountSbjtCd);
			newLinkedHashMap.put("accountSbjtUnitName", accountSbjtUnitName);
			newLinkedHashMap.put("organizationName", organizationName);
			newLinkedHashMap.put("bizSecName", bizSecName);
			newLinkedHashMap.put("pathName", "");
			newLinkedHashMap.put("subArr", new ArrayList<LinkedHashMap<String, Object>>());
			list.add(newLinkedHashMap);
		}
		
		List<LinkedHashMap<String, Object>> mainList = new ArrayList<LinkedHashMap<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, Object> map = list.get(i);
			LinkedHashMap<String, Object> pathMap = new LinkedHashMap<String, Object>();
			
			if ((long) map.get("accountSbjtLvOrder") == 1) {
//				list.get(i).put("pathName", list.get(i).get("accountSbjtName"));
//				pathMap.put("totalMap", list.get(i).get("accountSbjtName"));
				mainList.add(list.get(i));
				
//				pathMap.put("totalMap", value)
				

			} else {
				recursiveFunction(mainList, map, (String)list.get(i).get("pathName"), (long)list.get(i).get("accountSbjtLvOrder"), pathMap );

			}
		}
		
		return mainList;
	}
	
	public void recursiveFunction(List<LinkedHashMap<String, Object>> mainList, LinkedHashMap<String, Object> map, String pathName, long accountSbjtLvOrder, LinkedHashMap<String, Object> pathMap) {
		for (int i = 0; i < mainList.size(); i++) {
			List<LinkedHashMap<String, Object>> subArr = (List<LinkedHashMap<String, Object>>) mainList.get(i).get("subArr");
			
			if (mainList.get(i).get("accountSbjtCd").equals(map.get("upperAccountSbjtCd"))) {
				map.put("pathName", map.get("accountSbjtName"));
				pathMap.put("totalPathName", mainList.get(i).get("pathName") + " > " + map.get("pathName"));
				subArr.add(map);
				break;

			} else {
				
				// 조건들
				/**
				 * 조건들 : 
				 * 1. pathName 값이 없을 경우 
				 * 2. 기준이 되는 리스트의 현재 pathName과 map으로 들어온 pathName이 다를 경우
				 * 3. 기준이 되는 리스트의 현재 accountSbjtLvOrder 값과 map으로 들어온 accountSbjtLvOrder 값이 동일할 경우 
				 *    기준이 되는 리스트의 현재 accountSbjtLvOrder 값과 map으로 들어온 accountSbjtLvOrder 값이 다를경우
				 * ** 그밖에 다른 항목들 확인 및 점거해서 [계정명] > [계정명] 구조 만들기
				 */
				if (pathName.equals("")) {
					map.put("pathName", map.get("accountSbjtName"));
					
					if (mainList.get(i).get("accountSbjtCd").equals(map.get("accountSbjtCd")) && accountSbjtLvOrder != (long)mainList.get(i).get("accountSbjtLvOrder")) {
//						pathMap.put("totalPathName", mainList.get(i).get("pathName") + " > " + map.get("pathName"));
						System.out.println(mainList.get(i).get("pathName") + " | " + map.get("pathName"));
						System.out.println(pathMap.get("totalPathName"));
						
					}
					
				}
				
//				if (accountSbjtLvOrder != (long)mainList.get(i).get("accountSbjtLvOrder")) {
//					pathMap.put("totalPathName", mainList.get(i).get("pathName") + " > " + map.get("pathName"));
////					map.put("pathName", mainList.get(i).get("pathMap") + " > " + map.get("pathMap"));
////					pathMap.put("", value)
//				}
//				System.out.println("현재 pathMap : " + pathMap.get("totalPathName"));
				
				recursiveFunction(subArr, map, pathName, accountSbjtLvOrder, pathMap);
				
			}

		}
		
	};
	
	
	/**
	 * ArrayList 항목 JSONArray 포맷으로 변경해 출력하는 메서드
	 * @author dhkim
	 * @param list
	 */
	public void printArrayListToJSONArrayFormat(List<LinkedHashMap<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}
	
	
	


}
