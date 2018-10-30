package gun.training2.day1030;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonSimpleSaleOneRow {
//	public static void main(String[] args) {
	/**json 값들을 String으로 반환함.
	 * @return
	 */
	public String getJson() {
		JsonSimpleSale jsonSimple = new JsonSimpleSale();
		
		JSONParser parser = new JSONParser();
		
		//반환할 문자열이 들어있는 변수
		String table = "";
		try {
			//현재 class의 상대경로를 조회
			String path = JsonSimpleSale.class.getResource("").getPath();

			Object obj = null;
			try {
				//해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
				obj = parser.parse(new FileReader(path + "pl_table_db.json"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "<h1>표출할 json파일이 없습니다.</h1>";
			}

			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("seriesName");

			//전 Row의 companyCd를 나타냄
			String accountSbjtCd = "";
			//전 Row의 postionCd를 나타냄
			String stdCd = "";

			//출력여부를 확인하기 위한 변수
			//true일 경우 출력. 
			//false일 경우 출력안함.
			boolean isOut = true;
			
			//seriesData 값이 들어갈 변수
			Map<String, Double> seriesMap = new HashMap<String, Double>();
			
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("<table style='width:90%; margin: 0 auto;' border='1'>\n");
			sb.append("<tr><th>펼치기</th><th>당기</th><th>전기</th><th>전년동기</th></tr>\n");
			
			//jsonArr의 size만큼
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jsonO = (JSONObject) jsonArr.get(i);

				//현재 Row의 계정과목코드
				String nowAccountSbjtCd = (String) jsonO.get("account_sbjt_cd");
				//현재 Row의 계정과목이름
				String accountSbjtName = (String) jsonO.get("account_sbjt_name");
				//현재 Row의 항목코드
				String nowStdCd = (String) jsonO.get("std_cd");
				//현재 Row의 항목이름
				String stdName = (String) jsonO.get("std_name");
				//현재 Row의 구분
				String nowPrevCurrent = (String) jsonO.get("prev_current");
				//현재 Row의 값
				String seriesData = (String) jsonO.get("seriesdata");

				//전 Row의 부모코드와 현재 Row의 부모코드가 같은 경우.
				if (accountSbjtCd.equals(nowAccountSbjtCd)) {

					//전 Row의 자식코드랑 현재 Row의 자식코드가 같은 경우
					if (stdCd.equals(nowStdCd)) {
						//seriesData를 Map에 저장
						seriesMap.put(nowAccountSbjtCd+"_"+nowStdCd+"_"+nowPrevCurrent, jsonSimple.getRound(seriesData));
						
						//seriesData가 출력될 태그들을 미리 입력
						if(isOut) {
							sb.append("<td>"+nowAccountSbjtCd+"_"+nowStdCd+"_02</td>");
							sb.append("<td>"+nowAccountSbjtCd+"_"+nowStdCd+"_01</td>");
							sb.append("<td>"+nowAccountSbjtCd+"_"+nowStdCd+"_16</td>");
							
							isOut = false;
						}
					//전 Row의 자식코드랑 현재 Row의 자식코드가 같지 않은 경우.
					} else {
						isOut = true;
						
						//seriesData를 Map에 저장
						seriesMap.put(nowAccountSbjtCd+"_"+nowStdCd+"_"+nowPrevCurrent, jsonSimple.getRound(seriesData));
						
						sb.append("</tr>\n");
						sb.append("<tr>");
						sb.append("<td style='text-align:left;'>"+stdName+"</td>");
						
						stdCd = nowStdCd;

					}
					
				//전 Row의 부모코드와 현재 Row의 부모코드가 같지 않은 경우.
				} else {
					sb.append("\n<tr style='background-color:#f9f9f9;'><th style='text-align:left;'>" + accountSbjtName + "</th><td>"+nowAccountSbjtCd+"_02</td><td>"+nowAccountSbjtCd+"_01</td><td>"+nowAccountSbjtCd+"_16</td></tr>\n");
					sb.append("<tr><td style='text-align:left;'>" + stdName + "</td>");
					
					//seriesData를 Map에 저장
					seriesMap.put(nowAccountSbjtCd+"_"+nowStdCd+"_"+nowPrevCurrent, jsonSimple.getRound(seriesData));

					
					isOut = true;
					
					
					accountSbjtCd = nowAccountSbjtCd;
					stdCd = nowStdCd;

				}
				//seriesMap에 nowAccountSbjtCd+"_"+nowPrevCurrent 와 같은 키 값이 존재하지 않는 경우.
				if(seriesMap.get(nowAccountSbjtCd+"_"+nowPrevCurrent) == null) {		
					seriesMap.put(nowAccountSbjtCd+"_"+nowPrevCurrent, jsonSimple.getRound(seriesData));
					
				//seriesMap에 nowAccountSbjtCd+"_"+nowPrevCurrent 와 같은 키 값이 존재하는 경우.
				}else {
					
					double val1 = jsonSimple.getRound(seriesMap.get(nowAccountSbjtCd+"_"+nowPrevCurrent));
					double val2 = jsonSimple.getRound(seriesData);
					
					double result = val1 + val2;
					
					seriesMap.put(nowAccountSbjtCd+"_"+nowPrevCurrent, result);
				}
				
			}
			
			sb.append("\n\n</table>\n");
			
			table = sb.toString();
			
			//seriesMap 안에 key의 갯수 만큼 반복
			for(String key : seriesMap.keySet()) {
				//대체할 대상
				String replaceKey = key;
				
				table = table.replace(replaceKey, Double.toString(seriesMap.get(key)));
			}
			
			System.out.println(table);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return table;
	}

	/** 테스트용 메소드
	 * @param jsonArr */
	private static void printTest(JSONArray jsonArr) {
		System.out.println("=========출력 테스트=========");
		for (int i = 0; i < jsonArr.size(); i++) {

			JSONObject jsonO = (JSONObject) jsonArr.get(i);

			System.out.println("부모코드 : " + jsonO.get("account_sbjt_cd"));
			System.out.println("부모명 : " + jsonO.get("account_sbjt_name"));
			System.out.println("자식코드 : " + jsonO.get("std_cd"));
			System.out.println("자식명 : " + jsonO.get("std_name"));
			if (jsonO.get("prev_current").equals("16")) {
				System.out.println("전 흐름 : 전년동기");
			} else if (jsonO.get("prev_current").equals("01")) {
				System.out.println("전 흐름 : 전기");
			} else {
				System.out.println("전 흐름 : 당기");
			}
			System.out.println("데이터 : " + jsonO.get("seriesdata"));
			System.out.println("------------------------");
		}
	}
}
