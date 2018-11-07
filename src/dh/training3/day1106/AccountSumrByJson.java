package dh.training3.day1106;

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
	public List<Map<String, Object>> replaceFormat(JSONArray jsonArr) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			recursiveFunction(list, jsonObj);
		}

		printArrayListToJSONArrayFormat(list);
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
	public void recursiveFunction(List<Map<String, Object>> list, JSONObject jsonObj) {
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");	// 상위계정코드
		long accountSbjtLvOrder = (long) jsonObj.get("accountSbjtLvOrder");		// 계정레벨순서
		
		if (accountSbjtLvOrder == 1) {
			Map<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
			list.add(newLinkedHashMap);
			

			
		} else {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<Map<String, Object>> subArr = (ArrayList<Map<String, Object>>) list.get(i).get("subArr");

				if (upperAccountSbjtCd.equals(list.get(i).get("accountSbjtCd"))) {	// 비교하려는 upperAccountSbjtCd 값이 현재 list의 accountSbjtCd와 같이 같을 경우
					Map<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);

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
//					System.out.println(upperAccountSbjtCd + ", accountSbjtCd : " + list.get(i).get("accountSbjtCd"));

					// 상위게정콛, 계정코드 간에 연관관계가 존재할경우만 recursiveFunction 실행
					if (upperAccountSbjtCdCdPrefix.contains(accountSbjtCdPrefix)) {
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
	public String makeTable(List<Map<String, Object>> list) {
		StringBuffer tableStringBuffer = new StringBuffer();
		List<String[]> pathArrList = new ArrayList<String[]>();
		int maxTdCount = 0;	// 생성해야 할 td 갯수
		
		for(int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) list.get(i).get("subArr");
			checkTotalPathNameCount(tempList, pathArrList);
			
		}
		
		for (int i = 0; i < pathArrList.size(); i++) {
			int pathArrLength = pathArrList.get(i).length;
			if (maxTdCount < pathArrLength) {
				maxTdCount = pathArrLength;
			}
		}
		
		tableStringBuffer.append("<tr class=\"head\">");
		for (int i = 0; i < maxTdCount; i++) {
			String title = "계정구분 depth"+i;
			tableStringBuffer.append("<td style=\"text-align: center;\">" + title + "</td>");
		}
		tableStringBuffer.append("<td style=\"text-align: center;\">조직</td>");
		tableStringBuffer.append("<td style=\"text-align: center;\">사업부문</td>");
		tableStringBuffer.append("<td style=\"text-align: center;\">계획</td>");
		tableStringBuffer.append("<td style=\"text-align: center;\">추정</td>");
		tableStringBuffer.append("<td style=\"text-align: center;\">현황</td>");
		tableStringBuffer.append("<td style=\"text-align: center;\">특이사항</td>");
		tableStringBuffer.append("<td style=\"text-align: center;\">단위</td>");
//		tableStringBuffer.append("");
		

		for(int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) list.get(i).get("subArr");
			
			makeTableRow(tempList, tableStringBuffer, maxTdCount);
			
		}
		return tableStringBuffer.toString();
	}
	

	/**
	 * pathName 분석해 생성할 총 td 확인하는 메서드
	 * @param list
	 * @param tableStringBuffer
	 */
	public void checkTotalPathNameCount(List<Map<String, Object>> list, List<String[]> pathArrList) {
		for(Map<String,Object> map : list) {
			List<Map<String,Object>> tempArr = (List<Map<String,Object>>) map.get("subArr");
			
			if(tempArr.size() == 0) {
				String pathName = map.get("pathName").toString();
				pathArrList.add(pathName.split(" > "));
				
				
			}else {
				checkTotalPathNameCount(tempArr, pathArrList);
				
			}
		}
		
	}
	

	/**
	 * table row 생성
	 * @param list
	 * @param tableStringBuffer
	 */
	public void makeTableRow(List<Map<String, Object>> list, StringBuffer tableStringBuffer, int maxTdCount) {
		for(Map<String,Object> map : list) {
			List<Map<String,Object>> tempArr = (List<Map<String,Object>>) map.get("subArr");
			
			if(tempArr.size() == 0) {
				String[] pathArr = {};
				String pathName = map.get("pathName").toString();
				pathArr = pathName.split(" > ");

				tableStringBuffer.append("<tr>");		// tr 시작

				// depth 갯수만큼 계정구분 추가
				for (int j = 0; j < maxTdCount; j++) {
					if (pathArr.length < maxTdCount && j > pathArr.length -1) {
						tableStringBuffer.append("<td></td>");
					} else {
						tableStringBuffer.append("<td>"+ pathArr[j] +"</td>");
					}
				}

				tableStringBuffer.append(checkTdValueNull(map.get("organizationName")));
				tableStringBuffer.append(checkTdValueNull(map.get("bizSecName")));
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append("<td></td>");
				tableStringBuffer.append(checkTdValueNull(map.get("accountSbjtUnitName")));

				tableStringBuffer.append("</tr>");		// tr 종료
				
			}else {
				makeTableRow(tempArr, tableStringBuffer, maxTdCount);

			}
		}
		
	}
	

	/**
	 * 입력받은 데이터가 null인지 아닌지 검사해 <td></td>형태로 리턴하는 메서드
	 * @author dhkim
	 * @param data
	 * @return
	 */
	public String checkTdValueNull(Object data) {
		return data == null ? "<td></td>" : "<td>"+data+"</td>" ;
	}
	
	
	/**
	 * 새로운 LinkedHashMap 생성해주는 메서드
	 * @param jsonObj
	 * @return
	 */
	public Map<String, Object> makeNewLinkedHashMap(JSONObject jsonObj) {
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
		
		Map<String, Object> newLinkedHashMap = new LinkedHashMap<String, Object>();
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
	public void printArrayListToJSONArrayFormat(List<Map<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}



}
