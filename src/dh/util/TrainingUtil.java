package dh.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

public class TrainingUtil {

	
	/**
	 * 현재 TrainingUtil class가 존재하는 package 에서 fileName에 해당하는 파일 retur하는 메서드
	 * @author dhkim
	 * @param fileName
	 * @return
	 */
	public Object getFile(String fileName) {
		JSONParser parser = new JSONParser();
		String path = TrainingUtil.class.getResource("").getPath();

		try {
			Object obj = parser.parse(new FileReader(path + fileName));
			return obj;
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * JSONObject 파일로 return 하는 메서드
	 * @author dhkim
	 * @return
	 */
	public JSONObject getJsonFile() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		
		try {
			// 현재 class의 상대경로를 조회
			String path = UseJson.class.getResource("").getPath();

			// 해당 상대경로에 존재하는 indicat_db.json 파일을 읽어들인다.
			Object obj = parser.parse(new FileReader(path + "fin_account_db.json"));

			jsonObj = (JSONObject)obj;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj;
		
	}



	
	
}
