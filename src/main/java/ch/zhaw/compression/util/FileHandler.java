package ch.zhaw.compression.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileHandler {

	public static String readTextFile(File f) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(f));
		StringBuffer contents = new StringBuffer();
		
		try {
			String line = null; // not declared within while loop
			while ((line = input.readLine()) != null) {
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
			}
		} finally {
			input.close();
		}
		
		return contents.toString();
	}

	
}
