package dh.training3.day1101;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dh.util.UseJson;

public class TestMain {
	
	public static void main(String[] args) {
		UseJson useJson = new UseJson();
		JSONObject jsonObj =  useJson.getJsonFile();
		JSONArray jsonArr = (JSONArray)jsonObj.get("accountObj");
		
		AccountSumr sumr = new AccountSumr();
		List<LinkedHashMap<String, Object>> list = sumr.replaceFormat(jsonArr);
		
		sumr.printArrayListToJSONArrayFormat(list);



		
	}
	
	

		
		
	

}
