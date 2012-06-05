package ch.zhaw.compression.util;

public class BitStream {
	StringBuffer stream;
	
	
	public BitStream(){
		stream = new StringBuffer();
	}
	
	public BitStream(BitStream in){
		stream = new StringBuffer(in.toString());
	}
	
	public BitStream(int len){
		stream = new StringBuffer(len);
	}
	
	public BitStream(String string) {
		stream = new StringBuffer(string);
	}

	public void add(boolean bit){
		stream.append(bit ? '1' : '0');
	}
	
	public void add(BitStream in){
		stream.append(in.toString());
	}
	public void add(String in){
		stream.append(in);
	}
	
	public boolean get(int i){
		return stream.toString().charAt(i) == '1' ? true : false;
	}
	
	public int size(){
		return stream.toString().trim().length();
	}
	
	public String toString(){
		return stream.toString();
	}
	
	public int[] getNumericValues(int numberLength){
		int[] rv = new int[stream.length()/numberLength];
		
		for(int i = 0; i < stream.length()/numberLength;i++){
			StringBuffer sb = new StringBuffer();
			for(int n = 0; n < numberLength;n++){
				sb.append(stream.toString().charAt((i*numberLength)+n));
			}
			rv[i] = Integer.parseInt(sb.toString(), 2);	
		}
		return rv;
	}
	
	public BitStream getSubStream(int start, int length){
		char[] chr = new char[length];
		stream.getChars(start, start+length, chr, 0);
		return new BitStream(new String(chr));
	}
	
	public int hashCode(){
		return stream.toString().hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof BitStream){
			return ((BitStream)o).toString().equals(this.toString());
		}else{
			return false;
		}
	}
	
}
