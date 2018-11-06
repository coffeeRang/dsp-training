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

public class AccountSumrByJson {
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
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			recursiveFunction(list, jsonObj);
		}

		printArrayListToJSONArrayFormat(list);
		System.out.println(">> data total row count : " + jsonArr.size());
		return list;
	}
	
	
	/**
	 * recursive 돌면서 list에 데이터 넣는 메서드
	 * 현재 list에 값이 없을경우 list의 subArr까지 찾아들어감
	 * @author dhkim
	 * @param list
	 * @param jsonObj
	 * @return
	 */
	public void recursiveFunction(List<LinkedHashMap<String, Object>> list, JSONObject jsonObj) {
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");	// 상위계정코드
		long accountSbjtLvOrder = (long) jsonObj.get("accountSbjtLvOrder");		// 계정레벨순서
		
		if (accountSbjtLvOrder == 1) {
			LinkedHashMap<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
			list.add(newLinkedHashMap);
			
		} else {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<LinkedHashMap<String, Object>> subArr = (ArrayList<LinkedHashMap<String, Object>>) list.get(i).get("subArr");

				if (upperAccountSbjtCd.equals(list.get(i).get("accountSbjtCd"))) {	// 비교하려는 upperAccountSbjtCd 값이 현재 list의 accountSbjtCd와 같이 같을 경우
					LinkedHashMap<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);

					if (list.get(i).get("pathName").equals("")) {
						newLinkedHashMap.put("pathName", newLinkedHashMap.get("accountSbjtName"));
						subArr.add(newLinkedHashMap);
					} else {
						newLinkedHashMap.put("pathName", list.get(i).get("pathName") + " > " + newLinkedHashMap.get("accountSbjtName"));
						subArr.add(newLinkedHashMap);
					}
					
					break;
					
				} else {
					String upperAccountSbjtCdCdPrefix = upperAccountSbjtCd.replaceAll("0", "");
					String accountSbjtCdPrefix = list.get(i).get("accountSbjtCd").toString().replaceAll("0", "");

					// 상위게정콛, 계정코드 간에 연관관계가 존재할경우만 recursiveFunction 실행
					if (upperAccountSbjtCdCdPrefix.contains(accountSbjtCdPrefix)) {
						System.out.println(">> 부모계층 가능 : upperAccountSbjtCdCdPrefix : " + upperAccountSbjtCdCdPrefix + ", accountSbjtCdPrefix : " + accountSbjtCdPrefix);
						recursiveFunction(subArr, jsonObj);
					}
					
				}

			}
		}

	}
	
	/**
	 * table 생성하는 메서드
	 * @author dhkim
	 * @param list
	 * @return
	 */
	public String makeTable(List<LinkedHashMap<String, Object>> list) {
		StringBuffer tableStringBuffer = new StringBuffer();
		
		for(int i = 0; i < list.size(); i++) {
			List<LinkedHashMap<String, Object>> tempList = (List<LinkedHashMap<String, Object>>) list.get(i).get("subArr");
			
			makeTableRow(tempList, tableStringBuffer);
			
		}
		return tableStringBuffer.toString();
	}
	

	/**
	 * table row 생성
	 * @param list
	 * @param tableStringBuffer
	 */
	public void makeTableRow(List<LinkedHashMap<String, Object>> list, StringBuffer tableStringBuffer) {
		for(LinkedHashMap<String,Object> map : list) {
			List<LinkedHashMap<String,Object>> tempArr = (List<LinkedHashMap<String,Object>>) map.get("subArr");
			
			if(tempArr.size() == 0) {
				tableStringBuffer.append("<tr>");
				tableStringBuffer.append("<td>"+ map.get("pathName") +"</td>");
				tableStringBuffer.append("<td>"+ map.get("organizationName") +"</td>");
				tableStringBuffer.append("<td>"+ map.get("bizSecName") +"</td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td>"+ map.get("accountSbjtUnitName") +"</td>");
				tableStringBuffer.append("</tr>");
				
			}else {
				makeTableRow(tempArr, tableStringBuffer);

			}
		}
		
	}
	
	
	/**
	 * 새로운 LinkedHashMap 생성해주는 메서드
	 * @param jsonObj
	 * @return
	 */
	public LinkedHashMap<String, Object> makeNewLinkedHashMap(JSONObject jsonObj) {
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
		newLinkedHashMap.put("pathName", "");
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
