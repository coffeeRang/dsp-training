package sy.training3.day1105;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sy.training3.day1105.TestMain;

public class FinTableBackUp {

	/**
	 * json파일을 읽어서, JSONArray 형태로 리턴하는 메서드
	 * @author NamSangYuop
	 * 
	 * @return
	 */
	public JSONArray getJSONFile() {
		JSONParser parser = new JSONParser();

		// 현재 class의 상대경로를 조회
		String path = FinTableBackUp.class.getResource("").getPath();
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
	public String testReplaceFormat(JSONArray jsonArr) {
		List<TreeMap<String, Object>> returnList = new ArrayList<TreeMap<String,Object>>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			recursiveFunction(returnList, jsonObj, sb);
		}
		return sb.toString();
	}
	
	/**
	 * TreeMap에 넣을 Json파일의 데이터들을 메서드로 만듬.
	 * 
	 * @return  TreeMap
	 */
	public TreeMap<String, Object> makeNewTreeMap(JSONObject jsonObj) {
		
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

			TreeMap<String, Object> newTreeMap = new TreeMap<>();
			newTreeMap.put("accountSbjtCd", accountSbjtCd);
			newTreeMap.put("accountSbjtName", accountSbjtName);
			newTreeMap.put("accountSbjtLvOrder", accountSbjtLvOrder);
			newTreeMap.put("upperAccountSbjtCd", upperAccountSbjtCd);
			newTreeMap.put("accountSbjtUnitCd", accountSbjtUnitCd);
			newTreeMap.put("accountSbjtUnitName", accountSbjtUnitName);
			newTreeMap.put("organizationNum", organizationNum);
			newTreeMap.put("organizationName", organizationName);
			newTreeMap.put("bizSecNum", bizSecNum);
			newTreeMap.put("bizSecName", bizSecName);
			newTreeMap.put("subArr", new ArrayList<TreeMap<String, Object>>());

		return newTreeMap;
	}
	
	/**
	 * recursiveFunction이라는 메서드를 만들어서 재귀함수를 구현
	 * @param list
	 * @param jsonObj
	 * @return List
	 */
	public List<TreeMap<String,Object>> recursiveFunction(List<TreeMap<String,Object>> list, JSONObject jsonObj, StringBuffer sb){
		
		//부모코드 찾아주는것
		String upperAccountSbjtCd = (String) jsonObj.get("upperAccountSbjtCd");
		
		// 부모코드가 null일때
		if(upperAccountSbjtCd == null) {
			//메서드화 시킨TreeMap을 넣어줄 TreeMap을 만듬
			TreeMap<String,Object> newTreeMap = makeNewTreeMap(jsonObj);
			list.add(newTreeMap);
			
			return list;
			// 부모코드가 null이 아닐때
		} else {
			for(int i = 0; i < list.size(); i++) {
				// subArr를 선언해주어서 tempList.get(i).get("subArr")를 꺼냄.
				ArrayList<TreeMap<String,Object>> subArr = (ArrayList<TreeMap<String,Object>>) list.get(i).get("subArr");

				// jsonObject의 부모계정코드가 기준이되는 list의 accountSbjtCd와 동일할경우 (true일 경우)
				if(upperAccountSbjtCd.equals(list.get(i).get("accountSbjtCd"))) {
					TreeMap<String,Object> newTreeMap = makeNewTreeMap(jsonObj);
					subArr.add(newTreeMap);
					sb.append("<tr>");
					if((long) list.get(i).get("accountSbjtLvOrder") == 1) {
						sb.append("<td>"+ jsonObj.get("accountSbjtName") +"</td>");
						
					} else {
						sb.append("<td></td>");
					}
					if(jsonObj.get("organizationName") == null) {
						sb.append("<td></td>");
					}else {
						
						sb.append("<td>"+ jsonObj.get("organizationName") +"</td>");
					}
					if(jsonObj.get("bizSecName") == null) {
						sb.append("<td></td>");
					}else {
						
						sb.append("<td>"+ jsonObj.get("bizSecName") +"</td>");
					}
						sb.append("<td></td>");
						sb.append("<td></td>");
						sb.append("<td></td>");
						sb.append("<td></td>");
					if(jsonObj.get("accountSbjtUnitName") == null) {
						sb.append("<td></td>");
					}else {
						
						sb.append("<td>"+ jsonObj.get("accountSbjtUnitName") +"</td>");
					}
						sb.append("</tr>");
					
					break;
				} else {		//jsonObject의 부모계정코드가 기준이되는 list의 accountSbjtCd와 다를경우(false)
							// 즉, F100000 , F150000 기준이 되는 부모코드의 자신의 계정코드가 다를경우 ( F300000, F200000 )
					
					if( upperAccountSbjtCd.equals(list.get(i).get("accountSbjtCd")) ) {
						recursiveFunction(subArr,jsonObj,sb);
					
					}

				}
			}
		}
		return list;
	}
	
	/**
	 * ArrayList 항목 JSONArray 포맷으로 변경해 출력하는 메서드
	 * @param list
	 */
	public void printArrayListToJSONArrayFormat(List<TreeMap<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}
}
