package zarvis.bakery.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import zarvis.bakery.models.BakeryJsonWrapper;

public class Util {
	
	
	
	public static void main(String[] args) {
	    final String FILENAME = System.getProperty("user.dir") + "/bin/zarvis/bakery/config/sample-scenario.json";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILENAME));

			BakeryJsonWrapper jsonwrapper = new Gson().fromJson(reader, BakeryJsonWrapper.class);
	        
	        System.out.println(jsonwrapper.getMeta().getCustomers().getTotal_type3());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
