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

public class AccountSumrBak {
	private Set<String> accountSbjtPathSet = new LinkedHashSet<String>();
	
	/**
	 * TestMain에서 호출되는 메서드 - json 파일 읽어들어 구조 변경
	 * @author dhkim
	 * @param jsonArr
	 * @return
	 */
//	public List<LinkedHashMap<String, Object>> testReplaceFormat(JSONArray jsonArr) {
	public String testReplaceFormat(JSONArray jsonArr) {
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			recursiveFunction(list, jsonObj, sb);
		}

		printArrayListToJSONArrayFormat(list);
		System.out.println(">> end");
//		return list;
		return sb.toString();
	}
	
	
	/**
	 * recursive 돌면서 list에 데이터 넣는 메서드
	 * 현재 list에 값이 없을경우 list의 subArr까지 찾아들어감
	 * @author dhkim
	 * @param list
	 * @param jsonObj
	 * @return
	 */
//	public List<LinkedHashMap<String, Object>> recursiveFunction(List<LinkedHashMap<String, Object>> list, JSONObject jsonObj) {
	public void recursiveFunction(List<LinkedHashMap<String, Object>> list, JSONObject jsonObj, StringBuffer sb) {
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");	// 상위계정코드
//		String accountSbjtName = (String) jsonObj.get("accountSbjtName");			// 계정명
		
		if (upperAccountSbjtCd == null) {
			LinkedHashMap<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj, null);
			list.add(newLinkedHashMap);
			
		} else {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<LinkedHashMap<String, Object>> subArr = (ArrayList<LinkedHashMap<String, Object>>) list.get(i).get("subArr");
				if (upperAccountSbjtCd.equals(list.get(i).get("accountSbjtCd"))) {	// 비교하려는 upperAccountSbjtCd 값이 현재 list의 accountSbjtCd와 같이 같을 경우
					String upperName = (String) list.get(i).get("accountSbjtName");
					LinkedHashMap<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj, upperName);
					subArr.add(newLinkedHashMap);
					
					sb.append("<tr>");
					sb.append("<td>" + list.get(i).get("accountSbjtName") + " > " + jsonObj.get("accountSbjtName") + "</td>");
					sb.append("<td>" + jsonObj.get("organizationName") + "</td>");
					sb.append("<td>" + jsonObj.get("bizSecName") + "</td>");
					sb.append("<td></td>");
					sb.append("<td></td>");
					sb.append("<td></td>");
					sb.append("<td></td>");
					sb.append("<td>" + jsonObj.get("accountSbjtUnitName") + "</td>");
					sb.append("</tr>");
					
					break;
					
				} else {
					// 테스트 중
					if (subArr != null) {
						recursiveFunction(subArr, jsonObj, sb);
					}
					
					// 기존 분기처리 로직
//					if (i == list.size() - 1) {
//						recursiveFunction(subArr, jsonObj, sb);
//					}
				}

			}
		}
//		return sb.toString();
//		return list;
	}
	
	// 테이블 생성
	public void makeTable(List<LinkedHashMap<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + " : " + list.get(i).get("subArr"));;
			
		}
		
		
	}
	
	/**
	 * table row 생성
	 * @param jsonObj
	 */
	public String makeTableRow(JSONObject jsonObj) {
		String accountSbjtName = (String) jsonObj.get("accountSbjtName");			// 계정명
		String accountSbjtUnitName = (String) jsonObj.get("accountSbjtUnitName");	// 금액 단위 
		String organizationName = (String) jsonObj.get("organizationName");			// 조직명
		String bizSecName = (String) jsonObj.get("bizSecName");						// 사업부문명
		
		StringBuffer tableBuffer = new StringBuffer();

//		StringBuffer accountSbjtPath = new StringBuffer();
//
//		for (String key: accountSbjtPathSet) {
//			accountSbjtPath.append(key + " > ");
//		}
//		accountSbjtPath.deleteCharAt(accountSbjtPath.lastIndexOf(">"));
//		System.out.println(accountSbjtPath.toString().trim());
		

		tableBuffer.append("<tr>");
		tableBuffer.append("<td>" + accountSbjtName + "</td>");
		tableBuffer.append("<td>" + organizationName + "</td>");
		tableBuffer.append("<td>" + bizSecName + "</td>");
		tableBuffer.append("<td></td>");
		tableBuffer.append("<td></td>");
		tableBuffer.append("<td></td>");
		tableBuffer.append("<td></td>");
		tableBuffer.append("<td>" + accountSbjtUnitName + "</td>");
		tableBuffer.append("</tr>");
		
		
		return tableBuffer.toString();
		
	}
	
	
	/**
	 * 새로운 LinkedHashMap 생성해주는 메서드
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
	public LinkedHashMap<String, Object> makeNewLinkedHashMap(JSONObject jsonObj, String upperName) {
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
		
		LinkedHashMap<String, Object> newLinkedHashMap = new LinkedHashMap<String, Object>();
		newLinkedHashMap.put("accountSbjtPathName", upperName + " > " + accountSbjtName);
		newLinkedHashMap.put("accountSbjtName", accountSbjtName);
		newLinkedHashMap.put("accountSbjtLvOrder", accountSbjtLvOrder);
		newLinkedHashMap.put("accountSbjtSortOrder", accountSbjtSortOrder);
		newLinkedHashMap.put("upperAccountSbjtCd", upperAccountSbjtCd);
		newLinkedHashMap.put("accountSbjtCd", accountSbjtCd);
		newLinkedHashMap.put("accountSbjtUnitName", accountSbjtUnitName);
		newLinkedHashMap.put("accountSbjtUnitCd", accountSbjtUnitCd);
		newLinkedHashMap.put("organizationName", organizationName);
		newLinkedHashMap.put("organizationNum", organizationNum);
		newLinkedHashMap.put("bizSecName", bizSecName);
		newLinkedHashMap.put("bizSecNum", bizSecNum);
		
		newLinkedHashMap.put("subArr", new ArrayList<LinkedHashMap<String, Object>>());
		return newLinkedHashMap;
	}
	
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
