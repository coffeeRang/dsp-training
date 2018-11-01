package dh.training3.day1031;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dh.util.UseJson;

public class AccountSumr {
	
	
	public List<TreeMap<String, Object>> testReplaceFormat(JSONArray jsonArr) {
		List<TreeMap<String, Object>> list = new ArrayList<>();	// 리커시브 돌때마다 list 항목 변경됨
		int count = 0;
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			list = recursiveFunction(list, jsonObj);
			count++;
//			break;
		} 
		
//		System.out.println(">> count : " + count + " | resultList size : " + list.size());
		return list;
	}
	
	
	public List<TreeMap<String, Object>> recursiveFunction(List<TreeMap<String, Object>> list, JSONObject jsonObj) {
		List<TreeMap<String, Object>> masterList = list;
		
		String accountSbjtName = (String) jsonObj.get("accountSbjtName");			// 계정명
		long accountSbjtLvOrder = (Long) jsonObj.get("accountSbjtLvOrder");		// 계정depth 레벨
		long accountSbjtSortOrder = (long) jsonObj.get("accountSbjtSortOrder");	// 계정정렬 순서
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");	// 상위계정코드
		String accountSbjtCd = (String) jsonObj.get("accountSbjtCd");				// 계정코드
		String accountSbjtUnitName = (String) jsonObj.get("accountSbjtUnitName");	// 금액 단위 
		String accountSbjtUnitCd = (String) jsonObj.get("accountSbjtUnitCd");		// 금액 단위 코드
		String organizationName = (String) jsonObj.get("organizationName");			// 조직명
		String organizationNum = (String) jsonObj.get("organizationNum");			// 조직번호
		String bizSecName = (String) jsonObj.get("bizSecName");						// 사업부문명
		String bizSecNum = (String) jsonObj.get("bizSecNum");						// 사업부문번호
		
		StringBuffer mainSb = new StringBuffer();
		
		
		if (upperAccountSbjtCd == null) {
			masterList = new ArrayList<>();
			TreeMap<String, Object> newTreeMap = makeNewTreeMap(accountSbjtName, accountSbjtLvOrder, accountSbjtSortOrder, upperAccountSbjtCd, accountSbjtCd
					, accountSbjtUnitName, accountSbjtUnitCd, organizationName, organizationNum, bizSecName, bizSecNum);
			masterList.add(newTreeMap);
			return masterList;
			
		} else {
			for (int i = 0; i < masterList.size(); i++) {
				ArrayList<TreeMap<String, Object>> subArr = (ArrayList<TreeMap<String, Object>>) masterList.get(i).get("subArr");
				if (upperAccountSbjtCd.equals(masterList.get(i).get("accountSbjtCd"))) {	// 비교하려는 upperAccountSbjtCd 값이 현재 list의 accountSbjtCd와 같이 같을 경우
					TreeMap<String, Object> newTreeMap = makeNewTreeMap(accountSbjtName, accountSbjtLvOrder, accountSbjtSortOrder, upperAccountSbjtCd, accountSbjtCd
							, accountSbjtUnitName, accountSbjtUnitCd, organizationName, organizationNum, bizSecName, bizSecNum);
					subArr.add(newTreeMap);
//					mainSb.append(makeStringBuffer(accountSbjtCd, accountSbjtName, organizationNum, organizationName, bizSecNum, bizSecName, accountSbjtUnitName));
					break;
					
				} else {
					if (i == masterList.size() - 1) {
						recursiveFunction(subArr, jsonObj);
						
					}
				}
				
			}
		}
//		return mainSb;
		return masterList;
	}
	
	
	public void makeTable(List<TreeMap<String, Object>> list) {
		System.out.println(list);
		
	}
	
//	// StringBuffer로 테이블 생성
//	public StringBuffer makeStringBuffer(String accountSbjtCd, String accountSbjtName, String organizationNum, String organizationName, String bizSecNum, String bizSecName, String accountSbjtUnitName) {
//		StringBuffer returnSb = new StringBuffer();
//		returnSb.append("<tr>");
//		returnSb.append("<td>"+accountSbjtName+"</td>");
//		returnSb.append("<td>"+organizationNum+"</td>");
//		returnSb.append("<td>"+bizSecName+"</td>");
//		returnSb.append("<td></td>");
//		returnSb.append("<td></td>");
//		returnSb.append("<td></td>");
//		returnSb.append("<td>"+accountSbjtUnitName+"</td>");
//		returnSb.append("</tr>");
//		return returnSb;
//	}
	
	
	/**
	 * 새로운 TreeMap 생성해주는 메서드
	 * @author dhkim
	 * @param accountSbjtName
	 * @param accountSbjtLvOrder
	 * @param accountSbjtSortOrder
	 * @param upperAccountSbjtCd
	 * @param accountSbjtCd
	 * @param accountSbjtUnitName
	 * @param accountSbjtUnitCd
	 * @param organizationName
	 * @param organizationNum
	 * @param bizSecName
	 * @param bizSecNum
	 * @return
	 */
	public TreeMap<String, Object> makeNewTreeMap(String accountSbjtName, long accountSbjtLvOrder, long accountSbjtSortOrder, String upperAccountSbjtCd, String accountSbjtCd
			, String accountSbjtUnitName, String accountSbjtUnitCd, String organizationName, String organizationNum, String bizSecName, String bizSecNum) {
		TreeMap<String, Object> newTreeMap = new TreeMap<>();
		newTreeMap.put("accountSbjtName", accountSbjtName);
		newTreeMap.put("accountSbjtLvOrder", accountSbjtLvOrder);
		newTreeMap.put("accountSbjtSortOrder", accountSbjtSortOrder);
		newTreeMap.put("upperAccountSbjtCd", upperAccountSbjtCd);
		newTreeMap.put("accountSbjtCd", accountSbjtCd);
		newTreeMap.put("accountSbjtUnitName", accountSbjtUnitName);
		newTreeMap.put("accountSbjtUnitCd", accountSbjtUnitCd);
		newTreeMap.put("organizationName", organizationName);
		newTreeMap.put("organizationNum", organizationNum);
		newTreeMap.put("bizSecName", bizSecName);
		newTreeMap.put("bizSecNum", bizSecNum);
		
		newTreeMap.put("subArr", new ArrayList<TreeMap<String, Object>>());
		return newTreeMap;
	}
	
	/**
	 * ArrayList 항목 JSONArray 포맷으로 변경해 출력하는 메서드
	 * @author dhkim
	 * @param list
	 */
	public void printArrayListToJSONArrayFormat(List<TreeMap<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}
	
	
	

}
