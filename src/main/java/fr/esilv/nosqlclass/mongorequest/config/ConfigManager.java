package fr.esilv.nosqlclass.mongorequest.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import fr.esilv.nosqlclass.mongorequest.objects.Config;

public class ConfigManager {

	public static final String configFileName = "config.json";
	
	private File theConfigFile = null;
	private Gson gson = new Gson();
	
	public ConfigManager() throws IOException {
		this.theConfigFile = new File(configFileName);
		
	}
	
	public Config loadConfig() throws IOException {
		if(!this.theConfigFile.exists()) {
			System.out.println("Loading default config");
			return new Config();
		}
		
		BufferedReader fr = new BufferedReader(new FileReader(this.theConfigFile));
		String cfg = "", buff = "";
		while((buff = fr.readLine()) != null)
			cfg += buff;
		fr.close();
		return gson.fromJson(cfg, Config.class);
	}
	
	public boolean saveConfig(Config cfg) throws IOException {
		if(!this.theConfigFile.exists())
			this.theConfigFile.createNewFile();
		
		FileWriter fw = new FileWriter(this.theConfigFile);
		fw.write(gson.toJson(cfg));
		fw.close();
		
		return true;
	}
	
	
}
