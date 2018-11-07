package dh.training3.day1107;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dh.util.TrainingUtil;

public class TestMain {
	
	public static void main(String[] args) {
		
		TrainingUtil util = new TrainingUtil();
		Object obj = util.getFile("menu_db.json");
		JSONArray jsonArr = null;
		if (obj != null) {
			jsonArr = (JSONArray)obj;
		}
		
		Menu menu = new Menu();
		menu.replaceFormat(jsonArr);
		
		
		
		
		
	}

}
