package sy.training3.day1031_2;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sy.training3.day1031_2.TestMain;

public class FinTableBak {
	/**
	 * 리턴형을 맵으로 하는 method를 만들어 자료를 Map에 넣어준다. 
	 * 2018/10/30
	 * @author NamSangYuop
	 */
	public static void main(String[] args) {
//	public Map<String,Object> getJson() {
		JSONParser parser = new JSONParser();
		
		try {
			// 현재 class의 상대경로를 조회
			String path = FinTableBak.class.getResource("").getPath();
			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "fin_account_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("accountObj");
			
			//최상위 맵
			TreeMap<String, Object> firstMap = new TreeMap<String, Object>();
			List<TreeMap<String,Object>> list = new ArrayList<TreeMap<String,Object>>();
			List<TreeMap<String,Object>> subArr = null;
			TreeMap<String,Object> map = null;
			
			for (int i = 0; i < jsonArr.size(); i++) {
				
				JSONObject jsonO = (JSONObject) jsonArr.get(i);
				
				//계정구분 이름
				String accountName = (String) jsonO.get("accountSbjtName");
				//계정구분 코드
				String accountCd = (String) jsonO.get("accountSbjtCd");
				// 계층레벨 (몇번째 뎁스인지)
				long accountLv = (long) jsonO.get("accountSbjtLvOrder");
				//부모코드 찾아주는것
				String upperAccountCd = (String) jsonO.get("upperAccountSbjtCd");
				//단위 (백만원)
				String accountUnitName = (String) jsonO.get("accountSbjtUnitName");
				String accountUnitCd = (String) jsonO.get("accountSbjtUnitCd");
				//조직에 들어가는것
				String organizationName = (String) jsonO.get("organizationName");
				String organizationNum = (String) jsonO.get("organizationNum");
				// 사업부문에 들어가는것
				String bizSecName = (String) jsonO.get("bizSecName");
				String bizSecNum = (String) jsonO.get("bizSecNum");
					
				subArr = new ArrayList<TreeMap<String,Object>>();
				
				firstMap.put("accountCd", accountCd);
				firstMap.put("accountName", accountName);
				firstMap.put("accountLv", accountLv);
				firstMap.put("upperAccountCd", upperAccountCd);
				firstMap.put("accountUnitCd", accountUnitCd);
				firstMap.put("accountUnitName", accountUnitName);
				firstMap.put("organizationNum", organizationNum);
				firstMap.put("organizationName", organizationName);
				firstMap.put("bizSecNum", bizSecNum);
				firstMap.put("bizSecName", bizSecName);
				firstMap.put("subArr", subArr);
				
				// accountLv이 1일때
				if((long)firstMap.get("accountLv") == 1) {
					list.add(firstMap);
//					System.out.println(list.get(0));
				// 2이상일때
				}else {
					System.out.println(list.get(0));
					
					
					
//						map = firstMap;	
//					for (int j = 0; j < list.size(); j++) {
//						if(list.get(j).get("accountCd") == map.get("upperAccountCd")) {
//						}else {
//						}
//					}
				}
				
			}
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
//		return map;
	}
}
