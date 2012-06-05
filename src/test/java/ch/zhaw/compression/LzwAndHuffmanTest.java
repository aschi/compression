package ch.zhaw.compression;

import java.io.File;

import org.junit.Test;

import ch.zhaw.compression.util.BitStream;

public class LzwAndHuffmanTest {

	@Test
	public void compressFileTest() {
		LzwAndHuffman lah = new LzwAndHuffman();
		BitStream result = lah.compressFile(new File("res/sample.txt"));
		
		System.out.println(lah.decompressBitStream(result));
	}

}
