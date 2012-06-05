package ch.zhaw.compression;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.zhaw.compression.huffman.Huffman;
import ch.zhaw.compression.lzw.LZW;
import ch.zhaw.compression.util.BitStream;
import ch.zhaw.compression.util.FileHandler;


public class LzwAndHuffman {
	Huffman h;
	LZW lzw;
	
	public BitStream compressFile(File f){
		try {
			String data = FileHandler.readTextFile(f);
			int orig = data.length()*8;
			
			lzw = new LZW();
			
			List<Integer> enc1 = lzw.encodeData(data);
			
			System.out.println("LZW Compression result: " + orig + " => " + enc1.size()*12);
			
			h = new Huffman(enc1);
			BitStream rv =  h.encode(enc1);
			
			
			System.out.println("LZW + Huffman compression result: " + orig + "Bit => " + rv.size() +"Bit = " + ((double)rv.size()/orig)*100+"%");
			
			return rv;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String decompressBitStream(BitStream input){
		if(lzw == null){
			lzw = new LZW();
		}
		if(h == null){
			return null;
		}
		List<Integer> ilist = h.decode(input);
		String s = lzw.decodeData(ilist);
		
		return s;
	}
	
}
