package ch.zhaw.compression.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitStreamTest {

	@Test
	public void getNumericValuesTest() {
		BitStream bs = new BitStream("010110100011111010111011");
		int[] expected = new int[]{1443,3771};	
		assertTrue(bs.getNumericValues(12)[0] == expected[0] && bs.getNumericValues(12)[1] == expected[1] && bs.getNumericValues(12).length == expected.length);
	}

	@Test
	public void getSubStreamStartTest(){
		BitStream bs = new BitStream("010110100011111010111011");
		BitStream expected = new BitStream("0101");
		assertTrue(bs.getSubStream(0, 4).equals(expected));
	}
	
	@Test
	public void getSubStreamEndTest(){
		BitStream bs = new BitStream("010110100011111010111011");
		BitStream expected = new BitStream("1011");
		assertTrue(bs.getSubStream(20, 4).equals(expected));
	}
	
	@Test
	public void getSubStreamMidTest(){
		BitStream bs = new BitStream("010110100011111010111011");
		BitStream expected = new BitStream("1000");
		assertTrue(bs.getSubStream(6, 4).equals(expected));
	}
}
