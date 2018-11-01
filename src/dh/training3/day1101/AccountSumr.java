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
			list.add(newLinkedHashMap);
		}
		
		List<LinkedHashMap<String, Object>> mainList = new ArrayList<LinkedHashMap<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, Object> map =  list.get(i);
			HashMap<String, Object> pathMap = new HashMap<String, Object>();

			if ((long) map.get("accountSbjtLvOrder") == 1) {
				map.put("subArr", new ArrayList<LinkedHashMap<String, Object>>());
				mainList.add(list.get(i));

			} else {
				recursiveFunction(mainList, map, pathMap);

			}
		}
		
		System.out.println(">>> end");
		return mainList;
	}
	
	public void recursiveFunction(List<LinkedHashMap<String, Object>> mainList, LinkedHashMap<String, Object> map, HashMap<String, Object> pathMap) {
		for (int i = 0; i < mainList.size(); i++) {
			List<LinkedHashMap<String, Object>> subArr = (List<LinkedHashMap<String, Object>>) mainList.get(i).get("subArr");
			
			System.out.println("mainList lv order : " + mainList.get(i).get("accountSbjtLvOrder") + " | map lv order : " + map.get("accountSbjtLvOrder"));
			if (mainList.get(i).get("accountSbjtLvOrder") != map.get("accountSbjtLvOrder")) {
				pathMap.remove("accountSbjtPath");
			}
			pathMap.put("accountSbjtPath", pathMap.get("accountSbjtPath") + " > " + map.get("accountSbjtName"));
//			System.out.println(pathMap.get("accountSbjtPath") + ">" + map.get("accountSbjtName"));
			
			
			map.put("accountSbjtPath", pathMap.get("accountSbjtPath"));

			if (mainList.get(i).get("accountSbjtCd").equals(map.get("upperAccountSbjtCd"))) {
				map.put("subArr", new ArrayList<LinkedHashMap<String, Object>>());
				subArr.add(map);
				break;

			} else {
				if (subArr != null) {
					recursiveFunction(subArr, map, pathMap);
				}
				
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
