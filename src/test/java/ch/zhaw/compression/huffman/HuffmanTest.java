package ch.zhaw.compression.huffman;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ch.zhaw.compression.lzw.LZW;
import ch.zhaw.compression.util.FileHandler;

public class HuffmanTest {

	@Test
	public void test() {
		try {
			String data = FileHandler.readTextFile(new File("res/sample.txt"));
			int orig = data.length();
			
			LZW lzw = new LZW();
			String enc1 = lzw.encodeData(data);
			
			System.out.println(orig + " => " + enc1.length());
			
			
			Huffman h = new Huffman(enc1);
			h.encodeString(enc1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
