package ch.zhaw.compression.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ch.zhaw.compression.huffman.binaryTree.BinaryTree;
import ch.zhaw.compression.huffman.binaryTree.Node;

public class Huffman {
	private String data;
	private BinaryTree binaryTree;
	
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
