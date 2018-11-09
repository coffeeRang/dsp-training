package dh.training3.day1108;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Menu {
	
	private int totalDepthCount = -1;
	private int rowspanCount = 0;

	
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
		String upperMenuId = (String) jsonObj.get("upperMenuId");	// 상위계정코드
		int menuLv = Integer.parseInt((String) jsonObj.get("menuLv"));		// 계정레벨순서
		
		if (menuLv == 1) {
			Map<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
			newLinkedHashMap.put("menuTotalDepthName", jsonObj.get("menuName"));
			
			list.add(newLinkedHashMap);

		} else {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<Map<String, Object>> subArr = (ArrayList<Map<String, Object>>) list.get(i).get("subArr");

				if ( totalDepthCount < menuLv ) {	// 현재 json 파일 내부 가장 높은 menuLv 찾아 totalDepthCount에 할당
					totalDepthCount = menuLv;
				}
				
				if (upperMenuId.equals(list.get(i).get("menuId"))) {	// 비교하려는 upperMenuId 값이 현재 list의 menuId와 같이 같을 경우
					Map<String, Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);

					if (list.get(i).get("menuTotalDepthName").equals("")) {
						newLinkedHashMap.put("menuTotalDepthName", newLinkedHashMap.get("menuName"));
						subArr.add(newLinkedHashMap);
					} else {
						newLinkedHashMap.put("menuTotalDepthName", list.get(i).get("menuTotalDepthName") + " > " + newLinkedHashMap.get("menuName"));
						subArr.add(newLinkedHashMap);
					}
					
					break;
					
				} else {
					String upperMenuIdCdPrefix = upperMenuId.replaceAll("0", "");
					String menuIdPrefix = list.get(i).get("menuId").toString().replaceAll("0", "");
					
					if (upperMenuIdCdPrefix.contains(menuIdPrefix)) {
						recursiveFunction(subArr, jsonObj);
					}
					
				}

			}
		}

	}
	
	
	/**
	 * 새로운 LinkedHashMap 생성해주는 메서드
	 * @author dhkim
	 * @param jsonObj
	 * @return
	 */
	public Map<String, Object> makeNewLinkedHashMap(JSONObject jsonObj) {
		String menuId = (String) jsonObj.get("menuId");			// 계정명
		String menuName = (String) jsonObj.get("menuName");
		String menuLv = (String) jsonObj.get("menuLv");
		String menuOutputOrder = (String) jsonObj.get("menuOutputOrder");
		String upperMenuId = (String) jsonObj.get("upperMenuId");
		String companyNum = (String) jsonObj.get("companyNum");
		
		Map<String, Object> newLinkedHashMap = new LinkedHashMap<String, Object>();
		
		newLinkedHashMap.put("menuId", menuId);
		newLinkedHashMap.put("menuName", menuName);
		newLinkedHashMap.put("menuLv", menuLv);
		newLinkedHashMap.put("menuOutputOrder", menuOutputOrder);
		newLinkedHashMap.put("upperMenuId", upperMenuId);
		newLinkedHashMap.put("companyNum", companyNum);
		newLinkedHashMap.put("menuTotalDepthName", "");
		
		newLinkedHashMap.put("subArr", new ArrayList<Map<String, Object>>());

		return newLinkedHashMap;
	}
	
	
	
	/**
	 * table 생성하는 메서드
	 * @author dhkim
	 * @param list
	 * @return
	 */
	public String makeTable(List<Map<String, Object>> list) {
		StringBuffer tableStringBuffer = new StringBuffer();
		
		tableStringBuffer.append("<tr class=\"head\">");
		System.out.println(">> " + totalDepthCount);
		for (int i = 0; i < totalDepthCount; i++) {
			String title = "메뉴 depth "+ (i + 1);
			tableStringBuffer.append("<td style=\"text-align: center;\">" + title + "</td>");
		}
		
		Map<String, String> defaultNameMap = new HashMap<String, String>();

		for(int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) list.get(i).get("subArr");
			
			makeTableRow(tempList, tableStringBuffer, totalDepthCount, defaultNameMap);
			
		}
		return tableStringBuffer.toString();
	}
	
	
	
	/**
	 * table row 생성
	 * @author dhkim
	 * @param list
	 * @param tableStringBuffer
	 */
	public void makeTableRow(List<Map<String, Object>> list, StringBuffer tableStringBuffer, int totalDepthCount, Map<String, String> defaultNameMap) {
		
		for(Map<String,Object> map : list) {
			List<Map<String,Object>> tempArr = (List<Map<String,Object>>) map.get("subArr");
			
			if(tempArr.size() == 0) {
				String[] pathArr = {};
				String pathName = map.get("menuTotalDepthName").toString();
				pathArr = pathName.split(" > ");

				tableStringBuffer.append("<tr>");		// tr 시작

				// depth 갯수만큼 계정구분 추가
				for (int j = 0; j < totalDepthCount; j++) {
					if (pathArr.length < totalDepthCount && j > pathArr.length -1) {
						tableStringBuffer.append("<td></td>");
					} else {
						String depthKey = "depth" + (j + 1);
						String depthName = defaultNameMap.get(depthKey); 
						
						if (pathArr[j].equals(depthName) ) {
							tableStringBuffer.append("<td></td>");

						} else {
							defaultNameMap.put(depthKey, pathArr[j]);
//							tableStringBuffer.append("<td rowspan='" + rowspanCount + "'>"+ pathArr[j] +"</td>");
//							System.out.println(">> 입력되는 값 : " + pathArr[j]);

							tableStringBuffer.append("<td>"+ pathArr[j] +"</td>");
						}
						
					}

				}

				
				tableStringBuffer.append("</tr>");		// tr 종료
				
			}else {
//				rowspanCount++;
				System.out.println(">> recursive 돌기 전 rowspanCount : "+ rowspanCount);
				makeTableRow(tempArr, tableStringBuffer, totalDepthCount, defaultNameMap);

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
	 * pathName 분석해 생성할 총 td 확인하는 메서드
	 * @param list
	 * @param tableStringBuffer
	 */
	public void checkTotalPathNameCount(List<Map<String, Object>> list, List<String[]> pathArrList) {
		for(Map<String,Object> map : list) {
			List<Map<String,Object>> tempArr = (List<Map<String,Object>>) map.get("subArr");
			
			if(tempArr.size() == 0) {
				String pathName = map.get("menuTotalDepthName").toString();
				pathArrList.add(pathName.split(" > "));
				
				
			}else {
				checkTotalPathNameCount(tempArr, pathArrList);
				
			}
		}
		
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
