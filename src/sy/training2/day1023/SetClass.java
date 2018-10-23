package sy.training2.day1023;



import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

import sy.training1.day1018.TestMain;

public class SetClass {
	
	/**
	 * 2018/10/22
	 * @author NamSangYuop
	 * @param args
	 */
	public static String getString() {
		JSONParser parser = new JSONParser();
		String text = "";
		
		try {
			// 현재 class의 상대경로를 조회
			String path = TestMain.class.getResource("").getPath();

			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "indicat_db.json"));
			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");
			
			
			HashMap<String, Integer> rowSpanMap = new HashMap<String, Integer>();
			StringBuffer sb = new StringBuffer();
			String companyCd = ""; // 회사코드
			String posCd = "";// 직책코드 HP01
			String evalCd = "";// 구분코드 HE01
			
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jsonO = (JSONObject) jsonArr.get(i);
				System.out.println(i + "번째 companyCd : " + companyCd);
				
				// 회사코드가 있을때 (100, 200, 300, 400, 500, 600, 700)
				if (companyCd.equals(jsonO.get("companyNum"))) {
					if(posCd.equals(jsonO.get("positionCd"))) {
						if(evalCd.equals(jsonO.get("evalFieldCd"))) {
							sb.append("<tr><td>"+jsonO.get("kpiEvalIndicatName") +"</td></tr>\n");
							
							int row = rowSpanMap.get(jsonO.get("companyNum") + "_" + jsonO.get("positionCd"));
							rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd"), ++row);
							
							
							row = rowSpanMap.get(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"));
							rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), ++row);
						
							
						//구분이 같지않을때
						}else {
							sb.append("<tr><td rowspan='"+ jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd") + "_" + "rowspan" +"'>"+ jsonO.get("evalFieldName") +"</td><td>"+ jsonO.get("kpiEvalIndicatName") +"</td></tr>\n");
							int row = rowSpanMap.get(jsonO.get("companyNum") + "_" + jsonO.get("positionCd"));
							rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd"), ++row);
							rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), 1);
							evalCd = (String) jsonO.get("evalFieldCd");
						} //여기 재무성과 2번 (출력문제)
						
					//직책코드가 같지않을때
					}else {
						
						sb.append("<tr><td rowspan='"+jsonO.get("companyNum")+"_"+jsonO.get("positionCd")+"_"+ "rowspan" +"'>"+ jsonO.get("positionName") +"</td><td rowspan='"+jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd") + "_" + "rowspan" +"'>"+ jsonO.get("evalFieldName") +"</td><td>"+ jsonO.get("kpiEvalIndicatName") +"</td></tr>\n");
						
						rowSpanMap.put(jsonO.get("companyNum")+"_"+jsonO.get("positionCd"), 1);
						rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), 1);
						posCd = (String) jsonO.get("positionCd");
						evalCd = (String) jsonO.get("evalFieldCd");
					}
					
				// 회사코드가 같지않을때 
				} else {
					System.out.println(">>>> 일치 안함 : " + companyCd);
//					sb.append("<h2>" + jsonO.get("companyName") + "</h2>");
					sb.append("<tr><td colspan='3'>" + jsonO.get("companyName") + "</td></tr>");
					rowSpanMap.put(jsonO.get("companyNum")+"_"+jsonO.get("positionCd"), 1);
					rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), 1);
					
					sb.append("<tr><td rowspan='"+ jsonO.get("companyNum")+"_"+jsonO.get("positionCd")+"_"+ "rowspan" +"'>" + jsonO.get("positionName") + "</td><td rowspan='"+ jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd") + "_" + "rowspan" +"'>" + jsonO.get("evalFieldName") + "</td><td>" + jsonO.get("kpiEvalIndicatName") + "</td></tr>\n");
					companyCd = (String) jsonO.get("companyNum");
					posCd = (String) jsonO.get("positionCd");
					evalCd = (String) jsonO.get("evalFieldCd");
				}
			}
			text = sb.toString();
			//replace(대체되는 문자열,  대체하는 값);
			for(String key : rowSpanMap.keySet()) {
				text = text.replace(key+"_rowspan", Integer.toString(rowSpanMap.get(key)));
			}
			
//			System.out.println(text);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return text;
	}
}