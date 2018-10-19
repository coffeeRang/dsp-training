package sy.training1.day1018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetClass {
	public static void main(String[] args) {

		HashMap<String, Object> jsonO = (HashMap<String, Object>) TestMain.getJson();
		System.out.println(jsonO);
		System.out.println(jsonO.keySet());

		for (String key : jsonO.keySet()) {
			HashMap<String, Object> map1 = (HashMap<String, Object>) jsonO.get(key);
			System.out.println(map1);

			// 직책 이름
			for (String key2 : map1.keySet()) {
				if (map1.get(key2) != map1.get("companyName")) {
					HashMap<String, Object> map2 = (HashMap<String, Object>) map1.get(key2);
					System.out.println(">> map2 : " + map2.get("positionName"));
					
					// 구분이름
					for (String key3 : map2.keySet()) {
						

						if (map2.get(key3) != map2.get("positionName")) {

							HashMap<String, Object> map3 = (HashMap<String, Object>) map2.get(key3);

							List<String> list1 = (ArrayList<String>) map3.get("list");

						}
					}
				}
//				break;
			}
		}

	}

}
