package zarvis.bakery.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

import zarvis.bakery.models.BakeryJsonWrapper;

public class Util {
	
	public static BakeryJsonWrapper getWrapper(){
		final String FILENAME = System.getProperty("user.dir") + "/bin/zarvis/bakery/config/sample-scenario.json";
		BakeryJsonWrapper jsonwrapper = null;
		try {
			// read json file and convert them to objects
			BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
			jsonwrapper = new Gson().fromJson(reader, BakeryJsonWrapper.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return jsonwrapper;	
	}

}
