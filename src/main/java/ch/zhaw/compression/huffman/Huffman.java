package ch.zhaw.compression.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ch.zhaw.compression.huffman.binaryTree.BinaryTree;
import ch.zhaw.compression.huffman.binaryTree.Node;
import ch.zhaw.compression.util.BitStream;

public class Huffman {
	private List<Integer> lzwInput;
	private BinaryTree<Integer> binaryTree;
	private Map<Integer, BitStream> encoding;
	private Map<BitStream, Integer> decoding;
	
	
	public Huffman(List<Integer> lzwInput) {
		this.lzwInput = lzwInput;
		buildBinaryTree();
	}

	private void buildBinaryTree() {
		System.out.println("building huffman tree...");
		Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
		for (int i : lzwInput) {
			//System.out.println(c);
			if (counter.keySet().contains(i)) {
				counter.put(i, counter.get(i) + 1);
			} else {
				counter.put(i, 1);
			}
		}
		
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(new ValueComparator(counter));
		sortedMap.putAll(counter);
		
        for(int i : sortedMap.keySet()){
        	System.out.println(i +" : " + counter.get(i));
        }
                
        List<BinaryTree<Integer>> treeList = new ArrayList<BinaryTree<Integer>>();
        
        for(Integer i : sortedMap.keySet()){
        	System.out.println(i);
        	treeList.add(new BinaryTree<Integer>(new Node<Integer>(i, counter.get(i))));
        }
     
        System.out.println(treeList.size());
        
        int i = 0;
        while((treeList.size() > 1)){
        	BinaryTree<Integer> t1 = treeList.get(0);
        	BinaryTree<Integer> t2 = treeList.get(1);
        	treeList.remove(t1);
        	treeList.remove(t2);
        	
        	treeList.add(t1.combineTrees(t1, t2));
        	
        	Collections.sort(treeList);
        	
        	//System.out.println("Iteration: " + ++i + " size: " + treeList.size());
        }
        
        binaryTree = treeList.get(0);
        
        System.out.println(binaryTree);
        System.out.println("height:"+binaryTree.getHeight());
        
        generateEncodingFromTree();
        
        
        for(Integer out : encoding.keySet()){
        	System.out.println(out + " : " + encoding.get(out).toString());
        }
	}

	private List<BinaryTree<Character>> getFirstTrees(Set<BinaryTree<Character>> treeSet){
		List<BinaryTree<Character>> rv = new ArrayList<BinaryTree<Character>>();
		int i = 0;
		for(BinaryTree<Character> bt : treeSet){
			rv.add(bt);
			i++;
			if(i >= 2){
				break;
			}
		}
		return rv;
	}
	
	private void generateEncodingFromTree(){
		encoding = new HashMap<Integer, BitStream>();
		decoding = new HashMap<BitStream, Integer>();
		Node<Integer> root = binaryTree.getRoot();
				
		if(root.getLeft() != null){
			generateCodes(root.getLeft(), new BitStream(), false);
		}
		if(root.getRight() != null){
			generateCodes(root.getRight(), new BitStream(), true);
		}
	}
	
	/**
	 * 
	 * @param nd node
	 * @param previous BitSet of parent node
	 * @param direction boolean. left = false; right = true
	 */
	private void generateCodes(Node<Integer> nd, BitStream previous, boolean direction){
		String enc = previous.toString()+(direction ? '1' : '0');
		
		if(nd.getValue() != null){
			encoding.put(nd.getValue(), new BitStream(enc));
			decoding.put(new BitStream(enc), nd.getValue());
			return;
		}
		
		if(nd.getLeft()!=null){
			generateCodes(nd.getLeft(), new BitStream(enc), false);
		}
		if(nd.getRight()!=null){
			generateCodes(nd.getRight(), new BitStream(enc), true);
		}
	}
	
	public BitStream encode(List<Integer> dataInput){
		long t1 = System.currentTimeMillis();
		System.out.println("huffman: encoding data..");
		int decodedSize = (dataInput.size() * 12);
		
		
		BitStream out = new BitStream();
		int i = 0;
		for(int in : dataInput){
			out.add(encoding.get(in));
		}
		
		System.out.println("encoding finished (" + (System.currentTimeMillis()-t1) +"ms)");
		int encodedSize = out.size();
		
		System.out.println("decoded size: " + decodedSize + "Bit; encoded size: " + encodedSize + "Bit; Compression: " + ((double)encodedSize/(double)decodedSize*100)+"%");
		return out;
	}
	
	public List<Integer> decode(BitStream input){
		List<Integer> rv = new LinkedList<Integer>();
		
		String inputStr = input.toString();
		
		int pos = 0;
		while(true){
			int length = 1;
			
			while(decoding.get(new BitStream(inputStr.substring(pos, pos+length))) == null){
				if(length+pos < input.size()){
					length++;
				}
			}
			
			if(decoding.get(new BitStream(inputStr.substring(pos, pos+length))) != null){
				rv.add(decoding.get(input.getSubStream(pos, length)));
			}
			
			pos+=length;
			
			if(pos >= input.size()){
				break;
			}
		}
		
		
		return rv;
	}
	
	private class ValueComparator implements Comparator {
		  Map base;
		  public ValueComparator(Map base) {
		      this.base = base;
		  }

		  public int compare(Object a, Object b) {

		    if((Integer)base.get(a) > (Integer)base.get(b)) {
		      return 1;
		    } else {
		      return -1;
		    }
		  }
		}
}
