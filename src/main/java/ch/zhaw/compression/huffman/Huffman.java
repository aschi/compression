package ch.zhaw.compression.huffman;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import netscape.security.ForbiddenTargetException;

import sun.security.util.BitArray;

import ch.zhaw.compression.huffman.binaryTree.BinaryTree;
import ch.zhaw.compression.huffman.binaryTree.Node;

public class Huffman {
	private String data;
	private BinaryTree<Character> binaryTree;
	private Map<Character, BitArray> encoding;
	
	public Huffman(String data) {
		this.data = data;
		buildBinaryTree();
	}

	private void buildBinaryTree() {
		System.out.println(data);
		Map<Character, Integer> counter = new HashMap<Character, Integer>();
		for (char c : data.toCharArray()) {
			//System.out.println(c);
			if (counter.keySet().contains(c)) {
				counter.put(c, counter.get(c) + 1);
			} else {
				counter.put(c, 1);
			}
		}
		
		Map<Character, Integer> sortedMap = new TreeMap<Character, Integer>(new ValueComparator(counter));
		sortedMap.putAll(counter);
		
        for(char c : sortedMap.keySet()){
        	System.out.println(c +" : " + counter.get(c));
        }
                
        List<BinaryTree<Character>> treeList = new ArrayList<BinaryTree<Character>>();
        
        for(Character c : sortedMap.keySet()){
        	System.out.println(c);
        	treeList.add(new BinaryTree<Character>(new Node<Character>(c, counter.get(c))));
        }
     
        System.out.println(treeList.size());
        
        int i = 0;
        while((treeList.size() > 1)){
        	BinaryTree<Character> t1 = treeList.get(0);
        	BinaryTree<Character> t2 = treeList.get(1);
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
        
        
        for(Character c : encoding.keySet()){
        	System.out.println(c + " : " + encoding.get(c).toString());
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
		encoding = new HashMap<Character, BitArray>();
		Node<Character> root = binaryTree.getRoot();
		
		BitArray ba = new BitArray(0);
		
		if(root.getLeft() != null){
			generateCodes(root.getLeft(), ba, false);
		}
		if(root.getRight() != null){
			generateCodes(root.getRight(), ba, true);
		}
	}
	
	/**
	 * 
	 * @param nd node
	 * @param previous BitSet of parent node
	 * @param direction boolean. left = false; right = true
	 */
	private void generateCodes(Node<Character> nd, BitArray previous, boolean direction){
		BitArray ba = new BitArray(previous.length()+1);
		for(int i = 0;i < previous.length();i++){
			ba.set(i, previous.get(i));
		}
		ba.set(previous.length(), direction);
		
		if(nd.getValue() != null){
			encoding.put(nd.getValue(), ba);
			return;
		}
		
		if(nd.getLeft()!=null){
			generateCodes(nd.getLeft(), ba, false);
		}
		if(nd.getRight()!=null){
			generateCodes(nd.getRight(), ba, true);
		}
	}
	
	public BitArray encodeString(String dataInput){
		long t1 = System.currentTimeMillis();
		System.out.println("encoding data..");
		BitArray rv = new BitArray(0);
		int i = 0;
		for(Character c : dataInput.toCharArray()){
			i++;
			if(i % 10000 == 0){
				System.out.print("\r"+i + " of " + dataInput.toCharArray().length);
			}
			rv = concatBitArrays(rv, encoding.get(c));
		}
		System.out.println("encoding finished (" + (System.currentTimeMillis()-t1) +"ms)");
		return rv;
	
	
	}
	
	
	private BitArray concatBitArrays(BitArray a1, BitArray a2){
		boolean[] rv = new boolean[a1.length()+a2.length()];
		
		System.arraycopy(a1.toBooleanArray(), 0, rv, 0, a1.length());
	    System.arraycopy(a2.toBooleanArray(), 0, rv, a1.length(), a2.length());
		
		return new BitArray(rv);
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
