package sy.training3.day1106;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FinTable {

	/**
	 * json파일을 읽어서, JSONArray 형태로 리턴하는 메서드
	 * @author NamSangYuop
	 * 
	 * @return
	 */
	public JSONArray getJSONFile() {
		JSONParser parser = new JSONParser();

		// 현재 class의 상대경로를 조회
		String path = FinTable.class.getResource("").getPath();
		// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
		Object obj;
		JSONArray returnJsonArr = null;
		try {
			obj = parser.parse(new FileReader(path + "fin_account_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			returnJsonArr = (JSONArray) jsonObj.get("accountObj");
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
	 * @param jsonArr
	 * @return List
	 */
	public List<LinkedHashMap<String, Object>> replaceFormat(JSONArray jsonArr) {
		List<LinkedHashMap<String, Object>> returnList = new ArrayList<LinkedHashMap<String,Object>>();
		
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			jsonObj.put("pathName", "");
			recursiveFunction(returnList, jsonObj);
		}
		printArrayListToJSONArrayFormat(returnList);
		return returnList;
	}
	
	/**
	 * LinkedHashMap에 넣을 Json파일의 데이터들을 메서드로 만듬.
	 * 
	 * @return  LinkedHashMap
	 */
	public LinkedHashMap<String, Object> makeNewLinkedHashMap(JSONObject jsonObj) {
		
		//계정구분 이름
		String accountSbjtName = (String) jsonObj.get("accountSbjtName");
		//계정구분 코드
		String accountSbjtCd = (String) jsonObj.get("accountSbjtCd");
		// 계층레벨 (몇번째 뎁스인지)
		long accountSbjtLvOrder = (long) jsonObj.get("accountSbjtLvOrder");
		//부모코드 찾아주는것
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");
		//단위 (백만원)
		String accountSbjtUnitName = (String) jsonObj.get("accountSbjtUnitName");
		String accountSbjtUnitCd = (String) jsonObj.get("accountSbjtUnitCd");
		//조직에 들어가는것
		String organizationName = (String) jsonObj.get("organizationName");
		String organizationNum = (String) jsonObj.get("organizationNum");
		// 사업부문에 들어가는것
		String bizSecName = (String) jsonObj.get("bizSecName");
		String bizSecNum = (String) jsonObj.get("bizSecNum");

		LinkedHashMap<String, Object> newLinkedHashMap = new LinkedHashMap<>();
		newLinkedHashMap.put("accountSbjtCd", accountSbjtCd);
		newLinkedHashMap.put("accountSbjtName", accountSbjtName);
		newLinkedHashMap.put("accountSbjtLvOrder", accountSbjtLvOrder);
		newLinkedHashMap.put("upperAccountSbjtCd", upperAccountSbjtCd);
		newLinkedHashMap.put("accountSbjtUnitCd", accountSbjtUnitCd);
		newLinkedHashMap.put("accountSbjtUnitName", accountSbjtUnitName);
		newLinkedHashMap.put("organizationNum", organizationNum);
		newLinkedHashMap.put("organizationName", organizationName);
		newLinkedHashMap.put("bizSecNum", bizSecNum);
		newLinkedHashMap.put("bizSecName", bizSecName);
		newLinkedHashMap.put("pathName", "");
		newLinkedHashMap.put("subArr", new ArrayList<LinkedHashMap<String, Object>>());
		
		return newLinkedHashMap;
	}
	
	/**
	 * recursiveFunction이라는 메서드를 만들어서 재귀함수를 구현
	 * @param list
	 * @param jsonObj
	 * @return List
	 */
	public List<LinkedHashMap<String,Object>> recursiveFunction(List<LinkedHashMap<String,Object>> list, JSONObject jsonObj){
		//부모코드 찾아주는것
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");
		
		// 부모코드가 null일때
		if(upperAccountSbjtCd == null) {
			//메서드화 시킨LinkedHashMap을 넣어줄 LinkedHashMap을 만듬
				LinkedHashMap<String,Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
				list.add(newLinkedHashMap);
				return list;
			// 부모코드가 null이 아닐때
		} else {
			for(int i = 0; i < list.size(); i++) {
				// 재무안에 들어있는 subArr안에 PL,BS,CF들을 꺼내어 옴.
				ArrayList<LinkedHashMap<String,Object>> subArr = (ArrayList<LinkedHashMap<String,Object>>) list.get(i).get("subArr");
				
				// jsonObject의 부모계정코드(재무의계정코드)가 기준이되는 list의 계정코드와 동일할경우 (true일 경우)
				if(upperAccountSbjtCd.equals(list.get(i).get("accountSbjtCd"))) {
					LinkedHashMap<String,Object> newLinkedHashMap = makeNewLinkedHashMap(jsonObj);
					
					if(list.get(i).get("pathName").equals("")) {
						newLinkedHashMap.put("pathName",newLinkedHashMap.get("accountSbjtName"));
						subArr.add(newLinkedHashMap);
						
					}else {
						// pathName에 부모값을 불러와서 PL > 매출액 형태로 만들어주는 구문
						newLinkedHashMap.put("pathName",list.get(i).get("pathName") + ">" + newLinkedHashMap.get("accountSbjtName"));
						subArr.add(newLinkedHashMap);
					}
					
					break;
					// jsonObject의 부모계정코드가 기준이되는 list의 계정코드와 동일하지않을 경우(false일 경우)
				} else {
					String accountPrefix =  list.get(i).get("accountSbjtCd").toString().substring(0,2);
					String upperPrefix = upperAccountSbjtCd.substring(0, 2);
					
					System.out.println(">> accountPrefix : " + accountPrefix + ", upperPrefix : " + upperPrefix);
					

					recursiveFunction(subArr, jsonObj);
				}
				
			}
		}
		return list;
	}
	
	/**
	 * tempList를 넘겨주는 메소드
	 * 
	 * @param list
	 * @param sb
	 * @return String
	 */
	public String tableParser(){
		// json 파일받기
		JSONArray jsonArr = getJSONFile();
		List<LinkedHashMap<String,Object>> list = replaceFormat(jsonArr);
		StringBuffer sb = new StringBuffer();
		// tempList안에는 재무의subArr가 들어있다.
		for(int i = 0; i < list.size(); i++) {
			List<LinkedHashMap<String, Object>> tempList = (List<LinkedHashMap<String, Object>>) list.get(i).get("subArr");
			
			recursiveParser(tempList,sb);
			
		}
		return sb.toString();
	}
	
	/**
	 * 출력메소드
	 * 
	 * @param tempList
	 * @return StringBuffer
	 */
	public StringBuffer recursiveParser(List<LinkedHashMap<String, Object>> tempList, StringBuffer sb) {
		
		// map안에는 tempList안에 있는것들이 대체.
		for(LinkedHashMap<String,Object> map : tempList) {
			// tempArr 안에는 tempList안에 subArr들이 들어있음
			List<LinkedHashMap<String,Object>> tempArr = (List<LinkedHashMap<String,Object>>) map.get("subArr");
			
			// Array의 사이즈가 0일때
			if(tempArr.size() == 0) {
				sb.append("<tr>");
				sb.append("<td>"+ map.get("pathName") +"</td>");
				if(map.get("organizationName") != null) {
				sb.append("<td>"+ map.get("organizationName") +"</td>");
				}else {
					sb.append("<td></td>");
				}
				if(map.get("bizSecName") != null) {
				sb.append("<td>"+ map.get("bizSecName") +"</td>");
				}else {
					sb.append("<td></td>");
				}
				sb.append("<td></td>");
				sb.append("<td></td>");
				sb.append("<td></td>");
				sb.append("<td></td>");
				if(map.get("accountSbjtUnitName") != null) {
				sb.append("<td>"+ map.get("accountSbjtUnitName") +"</td>");
				}else {
					sb.append("<td></td>");
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
	 * @param list
	 */
	public void printArrayListToJSONArrayFormat(List<LinkedHashMap<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}
}
