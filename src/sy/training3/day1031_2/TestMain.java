package sy.training3.day1031_2;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TestMain {
	public static void main(String[] args) {
		FinTable ft = new FinTable();
		JSONArray jsonArr =  ft.getJSONFile();
		
		
		List<TreeMap<String,Object>> list = ft.testReplaceFormat(jsonArr);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			
		}
//		System.out.println(list);
	}

}
