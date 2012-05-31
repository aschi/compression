package ch.zhaw.compression.lzw;

import static org.junit.Assert.*;

import org.junit.Test;

public class LZWTest {

	@Test
	public void test() {
		LZW lzw = new LZW();
		
		String enc = lzw.encodeData("Hallo dies ist ein Test!");
		System.out.println(enc);
		System.out.println(lzw.decodeData(enc));
		
	}

}
