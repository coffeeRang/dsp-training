package sy.training3.day1031;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FinTable {
	/**
	 * 리턴형을 맵으로 하는 method를 만들어 자료를 Map에 넣어준다. 
	 * 2018/10/30
	 * @author NamSangYuop
	 */
	public static void main(String[] args) {
//	public Map<String,Object> getJson() {
		JSONParser parser = new JSONParser();
		Map<String, Object> firstMap = new HashMap<String, Object>();
		
		try {
			// 현재 class의 상대경로를 조회
			String path = FinTable.class.getResource("").getPath();
			
			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "fin_account_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("accountObj");
			
			/**
			 * jsonArr 사이즈 만큼 돌려서 Map에 값을 넣어준다.
			 * @author NamSangYuop
			 */
			for (int i = 0; i < jsonArr.size(); i++) {
				
				List<Map<String,Object>> list = null;
				
				JSONObject jsonO = (JSONObject) jsonArr.get(i);
				
				//계정구분 이름
				String accountSbjtName = (String) jsonO.get("accountSbjtName");
				//계정구분 코드
				String accountSbjtCd = (String) jsonO.get("accountSbjtCd");
				// 계층레벨 (몇번째인지)
				String accountSbjtLvOrder = (String) jsonO.get("accountSbjtLvOrder");
				//부모코드 찾아주는것
				String upperAccountSbjtCd = (String) jsonO.get("upperAccountSbjtCd");
				//단위 (백만원)
				String accountSbjtUnitName = (String) jsonO.get("accountSbjtUnitName");
				//단위코드
				String accountSbjtUnitCd = (String) jsonO.get("accountSbjtUnitCd");
				//조직에 들어가는것
				String organizationName = (String) jsonO.get("organizationName");
				//조직코드
				String organizationNum = (String) jsonO.get("organizationNum");
				// 사업부문에 들어가는것
				String bizSecName = (String) jsonO.get("bizSecName");
				// 사업부문 코드
				String bizSecNum = (String) jsonO.get("bizSecNum");
				// 찾을때 마다 map에 넣어줄 리스트
				List<Map<String,Object>> subList = null;
				
				list = new ArrayList<Map<String,Object>>();
				firstMap.put("accountSbjtCd", accountSbjtCd);
				firstMap.put("accountSbjtName", accountSbjtName);
				firstMap.put("accountSbjtLvOrder", accountSbjtLvOrder);
				firstMap.put("upperAccountSbjtCd", upperAccountSbjtCd);
				firstMap.put("accountSbjtUnitCd", accountSbjtUnitCd);
				firstMap.put("accountSbjtUnitName", accountSbjtUnitName);
				firstMap.put("organizationNum", organizationNum);
				firstMap.put("organizationName", organizationName);
				firstMap.put("bizSecNum", bizSecNum);
				firstMap.put("bizSecName", bizSecName);
				
				// 부모가 널일때
				if(firstMap.get("upperAccountSbjtCd") == null) {
					list.add(firstMap);
					
				}else {
					
					//찾아주는역할
					for( Map<String,Object> map1 : list) {
						//map1이 가지고있는 부모코드와 firstMap의 계정구분 코드가 같으면 ???????
						if(map1.get("upperAccountSbjtCd") == firstMap.get("accountSbjtCd")) {
							
						}else {
							
						}
					}
						break;
				///////////////////////////
				}
					
				
			}
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
//		return map;
	}
	
}
