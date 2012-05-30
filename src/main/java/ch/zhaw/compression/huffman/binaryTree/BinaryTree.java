package ch.zhaw.compression.huffman.binaryTree;

public class BinaryTree<T> implements Comparable<BinaryTree<T>> {
	Node<T> root;

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public BinaryTree(Node<T> root) {
		super();
		this.root = root;
	}

	public int getHeight(){
		return root.getDepthBeyond()+1;
	}
	
	@Override
	public int compareTo(BinaryTree<T> o) {
		if(getRoot().getEmphasis() > o.getRoot().getEmphasis()){
			return 1;
		}else{
			return -1;
		}
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof BinaryTree){
			return getRoot().equals(((BinaryTree) o).getRoot());
		}else{
			return false;
		}
	}

	public BinaryTree<T> combineTrees(BinaryTree<T> t1,
			BinaryTree<T> t2) {
		//create new root node
		Node<T> newRoot = new Node<T>(null, t1.getRoot().getEmphasis()+t2.getRoot().getEmphasis());
		newRoot.setLeft(t1.getRoot());
		newRoot.setRight(t2.getRoot());
		
		//add parent reference
		t1.getRoot().setParent(newRoot);
		t2.getRoot().setParent(newRoot);
		
		
		return new BinaryTree<T>(newRoot);
	}
	
	
	
}
