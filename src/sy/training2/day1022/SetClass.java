package sy.training2.day1022;


import java.io.*;
import java.util.*;

import org.apache.catalina.ha.context.ReplicatedContext;
import org.json.simple.*;
import org.json.simple.parser.*;

import sy.training1.day1018.TestMain;

public class SetClass {
	
	
	/**
	 * 2018/10/22
	 * @author NamSangYuop
	 * @param args
	 */
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		
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
				// 회사코드가 있을때 (100, 200, 300, 400, 500, 600, 700)
				if (companyCd.equals(jsonO.get("companyNum"))) {
					if(posCd.equals(jsonO.get("positionCd"))) {
						if(evalCd.equals(jsonO.get("evalFieldCd"))) {
							sb.append("<tr><td>"+jsonO.get("kpiEvalIndicatName") +"</td></tr>\n");					
						
						//구분이 같지않을때
						}else {
						evalCd = (String) jsonO.get("evalFieldCd");
						sb.append("<tr><td rowspan='"+ jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd") + "_" + "rowspan" +"'>"+ jsonO.get("evalFieldName") +"</td><td>"+ jsonO.get("kpiEvalIndicatName") +"</td></tr>\n");
						int row = rowSpanMap.get(jsonO.get("companyNum") + "_" + jsonO.get("positionCd"));
						rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd"), ++row);
						rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), 1);
						}
						
					//직책코드가 같지않을때
					}else {
						posCd = (String) jsonO.get("positionCd");
						sb.append("<tr><td rowspan='"+jsonO.get("companyNum")+"_"+jsonO.get("positionCd")+"_"+ "rowspan" +"'>"+ jsonO.get("positionName") +"</td><td rowspan='"+jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd") + "_" + "rowspan" +"'>"+ jsonO.get("evalFieldName") +"</td><td>"+ jsonO.get("kpiEvalIndicatName") +"</td></tr>\n");
						
						rowSpanMap.put(jsonO.get("companyNum")+"_"+jsonO.get("positionCd"), 1);
						rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), 1);
					}
					
				// 회사코드가 같지않을때 
				} else {
					companyCd = (String) jsonO.get("companyNum"); // 제오 컴씨디 포스씨디 이발씨디
					posCd = (String) jsonO.get("positionCd");
					evalCd = (String) jsonO.get("evalFieldCd");

					rowSpanMap.put(jsonO.get("companyNum")+"_"+jsonO.get("positionCd"), 1);
					rowSpanMap.put(jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd"), 1);
					
					sb.append("<tr><td rowspan='"+ jsonO.get("companyNum")+"_"+jsonO.get("positionCd")+"_"+ "rowspan" +"'>" + jsonO.get("positionName") + "</td><td rowspan='"+jsonO.get("companyNum") + "_" + jsonO.get("positionCd")+ "_" + jsonO.get("evalFieldCd") + "_" + "rowspan" +"'>" + jsonO.get("evalFieldName") + "</td><td>" + jsonO.get("kpiEvalIndicatName") + "</td></tr>\n");
				}
			}
			String t = sb.toString();
			
			//replace(대체되는 문자열,  대체하는 값);
			for(int i=0; i<jsonArr.size();i++) {
				JSONObject jsonO = (JSONObject) jsonArr.get(i);
				String replace = t.replace(jsonO.get("positionCd")+"_"+jsonO.get("evalFieldCd"), jsonO.get("positionCd")+"_"+jsonO.get("evalFieldCd")+"_"+"rowspan");
			}
			
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}