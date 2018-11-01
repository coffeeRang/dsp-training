package sy.training3.day1031_3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sy.training3.day1031_3.TestMain;

public class FinTable {

	/**
	 * json파일을 읽어서, JSONArray 형태로 리턴하는 메서드
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
	public List<TreeMap<String, Object>> testReplaceFormat(JSONArray jsonArr) {
		List<TreeMap<String, Object>> returnList = new ArrayList<TreeMap<String,Object>>();
		
		int count = 0;
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			returnList = recursiveFunction(returnList, jsonObj);
			count++;
		}
//		System.out.println("count : " + count + " | returnList size : " + returnList.size());
		return returnList;
	}
	
	/**
	 * recursiveFunction이라는 메서드를 만들어서 재귀함수를 구현
	 * @param list
	 * @param jsonObj
	 * @return List
	 */
	public List<TreeMap<String,Object>> recursiveFunction(List<TreeMap<String,Object>> list, JSONObject jsonObj){
		List<TreeMap<String, Object>> tempList = list;
		
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
		
		// 부모코드가 null일때
		if(upperAccountSbjtCd == null) {
			tempList = new ArrayList<>();
			//메서드화 시킨TreeMap을 넣어줄 TreeMap을 만듬
			TreeMap<String,Object> newTreeMap = makeNewTreeMap(accountSbjtCd, accountSbjtName, accountSbjtLvOrder, upperAccountSbjtCd, accountSbjtUnitCd, accountSbjtUnitName, organizationNum, organizationName, bizSecNum, bizSecName);
			// tempList안에 TreeMap을 넣어줌
			tempList.add(newTreeMap);
			
			return tempList;
			// 부모코드가 null이 아닐때
		} else {
			for(int i = 0; i < tempList.size(); i++) {
				// subArr를 선언해주어서 tempList.get(i).get("subArr")를 꺼냄. add 할수있게 만듬.
				ArrayList<TreeMap<String,Object>> subArr = (ArrayList<TreeMap<String,Object>>) tempList.get(i).get("subArr");
				
				// jsonObject의 부모계정코드가 기준이되는 tempList의 계정코드와 동일할경우 (true일 경우)
				if(upperAccountSbjtCd.equals(tempList.get(i).get("accountSbjtCd"))) {
					TreeMap<String,Object> newTreeMap = makeNewTreeMap(accountSbjtCd, accountSbjtName, accountSbjtLvOrder, upperAccountSbjtCd, accountSbjtUnitCd, accountSbjtUnitName, organizationNum, organizationName, bizSecNum, bizSecName);
					subArr.add(newTreeMap);
					break;
					
				} else {	// false일 경우
					if(i == tempList.size()-1) {
						recursiveFunction(subArr, jsonObj);
					}
				}
			}
		}
		return tempList;
	}
	/**
	 * TreeMap에 넣을 Json파일의 데이터들을 메서드로 만듬.
	 * @param accountSbjtCd
	 * @param accountSbjtName
	 * @param accountSbjtLvOrder
	 * @param upperAccountSbjtCd
	 * @param accountSbjtUnitCd
	 * @param accountSbjtUnitName
	 * @param organizationNum
	 * @param organizationName
	 * @param bizSecNum
	 * @param bizSecName
	 * @return  TreeMap
	 */
	public TreeMap<String, Object> makeNewTreeMap(String accountSbjtCd,
			String accountSbjtName, long accountSbjtLvOrder, String upperAccountSbjtCd,
			String accountSbjtUnitCd, String accountSbjtUnitName,
			String organizationNum, String organizationName, String bizSecNum,
			String bizSecName) {

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
	 * ArrayList 항목 JSONArray 포맷으로 변경해 출력하는 메서드
	 * @param list
	 */
	public void printArrayListToJSONArrayFormat(List<TreeMap<String, Object>> list) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(list.get(0));
		System.out.println(jsonArr);
	}
}
