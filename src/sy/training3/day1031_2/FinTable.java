package sy.training3.day1031_2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sy.training3.day1031_2.TestMain;

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
	 * 읽어들인 json data 기반으로 포맷변경해서 list에 담아서 리턴해주는 메서
	 * 
	 * @param jsonArr
	 * @return
	 */
	public List<TreeMap<String, Object>> testReplaceFormat(JSONArray jsonArr) {
		List<TreeMap<String, Object>> returnList = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
			returnList = recursiveFunction(returnList, jsonObj);
			count++;
		}
		System.out.println("count : " + count + " | returnList size : " + returnList.size());
		return returnList;
	}

	/**
	 * 리턴형을 맵으로 하는 method를 만들어 자료를 Map에 넣어준다. 
	 * 2018/10/30
	 * @author NamSangYuop
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
		
		if(upperAccountSbjtCd == null) {
			tempList = new ArrayList<>();
			TreeMap<String,Object> newTreeMap = makeNewTreeMap(accountSbjtCd, accountSbjtName, accountSbjtLvOrder, upperAccountSbjtCd, accountSbjtUnitCd, accountSbjtUnitName, organizationNum, organizationName, bizSecNum, bizSecName);
			tempList.add(newTreeMap);
			return tempList;
			
		} else {
			for(int i = 0; i < tempList.size(); i++) {
				ArrayList<TreeMap<String,Object>> subArr = (ArrayList<TreeMap<String,Object>>) tempList.get(i).get("subArr");
				
				// jsonObject의 부모계정코드가 기준이되는 tempList의 계정코드와 동일할경우 (true)
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
	 * 새로운 treeMap 만드는 메서드
	 * @param accountCd
	 * @param accountName
	 * @param accountLv
	 * @param upperAccountCd
	 * @param accountUnitCd
	 * @param accountUnitName
	 * @param organizationNum
	 * @param organizationName
	 * @param bizSecNum
	 * @param bizSecName
	 * @return
	 */
	public TreeMap<String, Object> makeNewTreeMap(String accountSbjtCd, String accountSbjtName, long accountSbjtLvOrder, String upperAccountSbjtCd, String accountSbjtUnitCd, String accountSbjtUnitName, String organizationNum
			, String organizationName, String bizSecNum, String bizSecName) {

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
}
