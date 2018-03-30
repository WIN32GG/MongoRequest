package fr.esilv.nosqlclass.mongorequest;

import java.io.IOException;

public class Launcher {

	public static void main(String[] args) {
		try {
			MongoRequestSession.getInstance().start();
		} catch (IOException e) {
			System.out.println("Err in launcher");
			e.printStackTrace();
		}
	}

}
