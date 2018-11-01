package sy.training3.day1031_3;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 기능을 담고있는 FinTable클래스를 불러와서 TestMain에서 실행
 * 2018/10/31
 * @author NamSangYoup
 */
public class TestMain {
	public static void main(String[] args) {
		FinTable ft = new FinTable();
		JSONArray jsonArr =  ft.getJSONFile();
		
		List<TreeMap<String, Object>> list = ft.testReplaceFormat(jsonArr);
		ft.printArrayListToJSONArrayFormat(list);

	}

}

