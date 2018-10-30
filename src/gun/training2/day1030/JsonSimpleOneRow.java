package gun.training2.day1030;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonSimpleOneRow {

	/** json값들을 포함한 html태그가 들어있는 문자열을 반환함.
	 * @return */
	public String getList() {
		JSONParser parser = new JSONParser();
		String table = "";
		try {
			//현재 class의 상대경로를 조회
			String path = JsonSimple.class.getResource("").getPath();

			Object obj = null;
			try {
				//해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
				obj = parser.parse(new FileReader(path + "indicat_db.json"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "<h1>표출할 json파일이 없습니다.</h1>";
			}

			JSONObject jsonObj = (JSONObject) obj;
			JSONArray jsonArr = (JSONArray) jsonObj.get("dataArray");

			//전 Row의 companyCd를 나타냄
			String comCd = "";
			//전 Row의 postionCd를 나타냄
			String posCd = "";
			//전 Row의 evalFieldCd를 나타냄
			String evalCd = "";

			StringBuffer sb = new StringBuffer();

			//rowSpan값이 들어갈 map
			HashMap<String, Integer> rowSpanMap = new HashMap<String, Integer>();

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jsonO = (JSONObject) jsonArr.get(i);

				String nowComCd = (String) jsonO.get("companyNum"); //i번째 companyNum 정보를 할당
				String nowComName = (String) jsonO.get("companyName"); //i번째 companyName 정보를 할당
				String nowPosCd = (String) jsonO.get("positionCd"); //i번째 positionCd 정보를 할당
				String nowPosName = (String) jsonO.get("positionName"); //i번째 positionName 정보를 할당
				String nowEvalFieldCd = (String) jsonO.get("evalFieldCd"); //i번째 evalFieldCd 정보를 할당
				String nowEvalFieldName = (String) jsonO.get("evalFieldName"); //i번째 evalFieldName 정보를 할당
				String nowKpiEvalName = (String) jsonO.get("kpiEvalIndicatName"); //i번째 kpiEvalIndicatName 정보를 할당

				//comCd와 현재 루프의 comCd와 같은 경우
				if (comCd.equals(nowComCd)) {

					//posCd와 현재 루프의 PosCd와 같은 경우
					if (posCd.equals(nowPosCd)) {

						//evalCd와 현재 루프의 evalCd와 같은 경우
						if (evalCd.equals(nowEvalFieldCd)) {
							sb.append("<tr><td>" + nowKpiEvalName + "</td></tr>\n");

							//posCd가 같고, evalCd가 같은 경우이므로 이미 회사코드_직책코드, 
							//회사코드_직책코드_구분코드로 되어있는 값이 존재하는 것이므로 둘 다 증가해준다. 
							int row = rowSpanMap.put(nowComCd + "_" + nowPosCd, 1);
							rowSpanMap.put(nowComCd + "_" + nowPosCd, ++row);

							row = rowSpanMap.put(nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd, 1);
							rowSpanMap.put(nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd, ++row);

							//evalCd와 현재 루프의 evalCd가 같지 않은 경우
						} else {

							sb.append("<tr><td rowspan='" + nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd + "_ROWSPAN'>" + nowEvalFieldName + "</td><td>" + nowKpiEvalName + "</td></tr>\n");

							evalCd = nowEvalFieldCd;

							//posCd가 같고, evalCd가 같지 않은 경우이므로 이미 회사코드_직책코드는
							//생성이 되어 있지만, 회사코드_직책코드_구분코드로 되어있는 값은 존재하지 않으므로 새로 넣어주고,
							//이미 존재하는 것은 증가해준다.
							int row = rowSpanMap.put(nowComCd + "_" + nowPosCd, 1);
							rowSpanMap.put(nowComCd + "_" + nowPosCd, ++row);

							rowSpanMap.put(nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd, 1);
						}

						//posCd와 현재 루프의 posCd와 같지 않은 경우
					} else {
						sb.append("<tr><td rowspan='" + nowComCd + "_" + nowPosCd + "_ROWSPAN'>" + nowPosName + "</td><td rowspan='" + nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd + "_ROWSPAN'>" + nowEvalFieldName + "</td><td>"
								+ nowKpiEvalName + "</td></tr>\n");

						//회사코드는 같지만 posCd가 다르므로 회사코드_직책코드 와 회사코드_직책코드_구분코드를 새로 생성해서 넣어준다.
						rowSpanMap.put(nowComCd + "_" + nowPosCd, 1);
						rowSpanMap.put(nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd, 1);

						posCd = nowPosCd;
						evalCd = nowEvalFieldCd;
					}
					//(i + 1)이 jsonArr의 size랑 같지 않을 경우
					//** NullPointerException 방지용
					if ((i + 1) != jsonArr.size()) {
						//현재 Row의 다음 Row를 가져옴
						JSONObject nextRow = (JSONObject) jsonArr.get(i + 1);

						String nextComCd = (String) nextRow.get("companyNum");

						//다음 Row와 현재 Row의 comCd 값이 같지 않을 경우 table태그를 닫아줌
						if (!nowComCd.equals(nextComCd)) {
							sb.append("</table>");
						}
					}

					//comCd와 현재 루프의 comCd와 같지 않은 경우
				} else {
					sb.append("<h1>" + nowComName + "</h1>");
					sb.append("<hr style=\"border: solid 1px black;\">");
					sb.append("<table border='1' style='width:100%;'>");
					sb.append("<tr><th style='width: 20%; height:40px;' rowspan='2'>직책</th><th style='width: 80%; height:40px;' colspan='2'>KPI</th></tr>");
					sb.append("<tr><th style='width: 20%; height:40px;'>구분</th><th style='width: 80%;'>성과지표</th></tr>");
					sb.append("<tr><td rowspan='" + nowComCd + "_" + nowPosCd + "_ROWSPAN'>" + nowPosName + "</td><td rowspan='" + nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd + "_ROWSPAN'>" + nowEvalFieldName + "</td><td>"
							+ nowKpiEvalName + "</td></tr>\n");

					//회사코드가 존재하지 않으므로 새로 넣어준다.
					rowSpanMap.put(nowComCd + "_" + nowPosCd, 1);
					rowSpanMap.put(nowComCd + "_" + nowPosCd + "_" + nowEvalFieldCd, 1);

					//다음 Row의 처리를 위해 다음 Row의 기준으로 전 Row를 현재 Row의 값들을 치환
					comCd = nowComCd;
					posCd = nowPosCd;
					evalCd = nowEvalFieldCd;
				}
			}

			table = sb.toString();

			//rowSpanMap의 key의 갯수만큼
			for (String key : rowSpanMap.keySet()) {
				//replace 할 대상
				String replaceKey = key + "_ROWSPAN";

				table = table.replace(replaceKey, Integer.toString(rowSpanMap.get(key)));
			}
			System.out.println(table);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return table;
	}
}
