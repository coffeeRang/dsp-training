package sy.training3.day1108;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
* 2018/11/07
* @author NamSangYuop
*/
public class Menu {
	
	private int totalCnt = -1;
	
	/**
	 * json파일을 읽어서, JSONArray 형태로 리턴하는 메서드
	 * @author NamSangYuop
	 * @return
	 */
	public JSONArray getJSONFile() {
		JSONParser parser = new JSONParser();
		// 현재 class의 상대경로를 조회
		String path = Menu.class.getResource("").getPath();
		// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
		Object obj;
		JSONArray returnJsonArr = null;
		try {
			obj = parser.parse(new FileReader(path + "menu_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			returnJsonArr = (JSONArray) jsonObj.get("dataMenu");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnJsonArr;
	}

	/**
	 * 읽어들인 jsonData 기반으로 포맷변경해서 list에 담아서 리턴해주는 메서드
	 * 
	 * @author NamSangYuop
	 * @param jsonArr
	 * @return List
	 */
	public List<Map<String, Object>> replaceFormat(JSONArray jsonArr) {
		List<Map<String, Object>> returnList = new ArrayList<>();
		
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			recursiveFunction(returnList, jsonObj);
		}
		printArrayListToJSONArrayFormat(returnList);
		return returnList;
	}
	
	
	/**
	 * recursiveFunction이라는 메서드를 만들어서 재귀함수를 구현
	 * 
	 * @author NamSangYuop
	 * @param list
	 * @param jsonObj
	 * @return List
	 */
	public List<Map<String,Object>> recursiveFunction(List<Map<String,Object>> list, JSONObject jsonObj){
		//계층레벨 (몇번째 뎁스인지)
		String menuLv = (String) jsonObj.get("menuLv");
		//jsonObj의 부모코드
		String upperMenuId = (String) jsonObj.get("upperMenuId");
		//회사코드
		String comapnyNum = (String) jsonObj.get("comapnyNum");
		
		// 부모코드가 null일때
		if(menuLv.equals("1")) {
			//메서드화 시킨LinkedHashMap을 넣어줄 LinkedHashMap을 만듬
				Map<String,Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
				newLinkedHashMap.put("menuTotalDepth", jsonObj.get("menuName"));
				list.add(newLinkedHashMap);
				return list;
				
			// 부모코드가 null이 아닐때
		} else {
			for(int i = 0; i < list.size(); i++) {
				// 재무안에 들어있는 subArr들을 꺼내어 옴.
				ArrayList<Map<String,Object>> subArr = (ArrayList<Map<String,Object>>) list.get(i).get("subArr");
				
				// total사이즈를 구해주는 if문
				long listDepthLv  = Integer.parseInt((String)list.get(i).get("menuLv"));
				if(listDepthLv > totalCnt) {
					totalCnt = (int)(long)listDepthLv;
					System.out.println(">>>> totalCnt : "+  totalCnt);
				}
				
				// jsonObject의 부모계정코드(재무의계정코드)가 기준이되는 list의 계정코드와 동일할경우 (true일 경우)
				if(upperMenuId.equals(list.get(i).get("menuId"))) {
					Map<String,Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
					if(list.get(i).get("menuTotalDepth").equals("")) {
						newLinkedHashMap.put("menuTotalDepth",newLinkedHashMap.get("menuName"));
						subArr.add(newLinkedHashMap);
					}else {
						// accountSbjtTotalDepthName에 부모값을 불러와서 전 값을 쌓아주는 구문
						newLinkedHashMap.put("menuTotalDepth",list.get(i).get("menuTotalDepth")+ ">"+ newLinkedHashMap.get("menuName"));
						subArr.add(newLinkedHashMap);
					}
					
					break;
					// jsonObject의 부모계정코드가 기준이되는 list의 계정코드와 동일하지않을 경우(false일 경우)
				} else {
					//0만 없앰
					String upperPrefix = upperMenuId.replaceAll("0","");
					String menuPrefix = list.get(i).get("menuId").toString().replaceAll("0", "");
					String tempUpper = comapnyNum.replaceAll("0","");
					String tempPrefix = list.get(i).get("comapnyNum").toString().replaceAll("0", "");
					
					// 연관있는것만 if문 거침
					if (upperPrefix.contains(menuPrefix) || tempUpper.contains(tempPrefix)) {
						
						recursiveFunction(subArr, jsonObj);
					}
					
				}
			}
		}
		return list;
	}
	
	/**
	 * LinkedHashMap에 넣을 Json파일의 데이터들을 메서드로 만듬.
	 * 
	 * @author NamSangYuop
	 * @return  LinkedHashMap
	 */
	public Map<String, Object> makeNewLinkedHashMap(JSONObject jsonObj) {
		
			//계정구분 이름
			String menuName = (String) jsonObj.get("menuName");
			//계정구분 코드
			String menuId = (String) jsonObj.get("menuId");
			//계층레벨 (몇번째 뎁스인지)
			String menuLv = (String) jsonObj.get("menuLv");
			//부모코드
			String upperMenuId = (String) jsonObj.get("upperMenuId");
			//회사코드
			String comapnyNum = (String) jsonObj.get("comapnyNum");

		Map<String, Object> newLinkedHashMap = new LinkedHashMap<>();
			newLinkedHashMap.put("menuId", menuId);
			newLinkedHashMap.put("menuName", menuName);
			newLinkedHashMap.put("menuLv", menuLv);
			newLinkedHashMap.put("upperMenuId", upperMenuId);
			newLinkedHashMap.put("comapnyNum", comapnyNum);
			newLinkedHashMap.put("menuTotalDepth", "");
			newLinkedHashMap.put("subArr", new ArrayList<Map<String, Object>>());
				
			return newLinkedHashMap;
	}
	
	
	/**
	 * StringBuffer를 리턴해서 list를 넘겨주는 메소드
	 * 
	 * @author NamSangYuop
	 * @param list
	 * @param sb
	 * @return String
	 */
	public String tableParser(){
		// json 파일받기
		JSONArray jsonArr = getJSONFile();
		List<Map<String,Object>> list = replaceFormat(jsonArr);
		StringBuffer sb = new StringBuffer();
		sb.append("<tr style='text-align: center'>");
		for(int i = 0; i < totalCnt; i++) {
			
			sb.append("<td>Depth"+ i +"</td>");
			
		}
		sb.append("</tr>");
		// tempList안에는 재무의subArr가 들어있다.
		for(int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) list.get(i).get("subArr");
			recursiveParser(tempList,sb);
			
		}
			return sb.toString();
	}
	
	
	/**
	 * 자신을 계속 호출하면서 <td>를 만들어주는 메소드.
	 * 
	 * @author NamSangYuop
	 * @param parserList , StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer recursiveParser(List<Map<String, Object>> parserList, StringBuffer sb) {
		// map안에는 parserList안에 있는것들이 대체.
		for(Map<String,Object> map : parserList) {
			// tempArr 안에는 parserList안에 subArr들이 들어있음
			List<Map<String,Object>> tempArr = (List<Map<String,Object>>) map.get("subArr");
			
			
			
			// Array의 사이즈가 0일때
			if(tempArr.size() == 0) {
				// String 형태로 받아온다.
				String textMenu = map.get("menuTotalDepth").toString();
				String[] splitDepthNameArr = textMenu.split(">"); 
				
				sb.append("<tr>");
				sb.append("<td>" + map.get("menuTotalDepth").toString().replaceAll(">", "<td>") +"</td>");
				
				if(splitDepthNameArr.length < totalCnt) {
					
					int minus =  totalCnt - splitDepthNameArr.length;
						for(int i = 0; i < minus; i++) {
							sb.append("<td></td>");
						}
				}
				sb.append("</tr>");
				
				
				//Array의 사이즈가 0이 아닐때
			}else {
				recursiveParser(tempArr,sb);
			}
		}
		return sb;
	}
	
	
	/**
	 * ArrayList 항목 JSONArray 포맷으로 변경해 출력하는 메서드
	 * @author NamSangYuop
	 * @param list
	 */
	public void printArrayListToJSONArrayFormat(List<Map<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}
}
