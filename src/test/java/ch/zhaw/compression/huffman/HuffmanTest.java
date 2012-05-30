package ch.zhaw.compression.huffman;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ch.zhaw.compression.FileHandler;

public class HuffmanTest {

	@Test
	public void test() {
		try {
			String data = FileHandler.readTextFile(new File("res/sample.txt"));
			Huffman h = new Huffman(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
