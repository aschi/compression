package ch.zhaw.compression.huffman.binaryTree;

public class Node<T> {
	private T value;
	private int emphasis;
	
	private Node<T> parent;
	private Node<T> left;
	private Node<T> right;
	
	public Node(T value, int emphasis){
		this.value = value;
		this.emphasis = emphasis;
	}
	
	public T getValue(){
		return value;
	}
	
	public int getEmphasis(){
		return emphasis;
	}
	
	public void setValue(T value){
		this.value = value;
	}
	
	public void setRight(Node<T> right){
		this.right = right;
	}
	
	public void setLeft(Node<T> left){
		this.left = left;
	}
	
	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Node<T> getRight(){
		return right;
	}
	
	public Node<T> getLeft(){
		return left;
	}
	
	public int getDepthBeyond(){
		int ld =0;
		int rd =0;
		if(left == null && right == null){
			return 0;
		}else{
			if(left!= null){
				ld = left.getDepthBeyond();
			}
			if(right!=null){
				rd = right.getDepthBeyond();
			}
		}
		
		if(ld > rd){
			return (ld+1);
		}else{
			return (rd+1);
		}
	}

	@Override
	public String toString() {
		return "Node [value=" + value + ", emphasis=" + emphasis + ", parent="
				+ parent + ", left=" + left + ", right=" + right + "]";
		
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Node){
			Node comp = (Node)o;
			if(this.getEmphasis() != comp.getEmphasis()){
				return false;
			}
			if(this.getValue() == null){
				if(comp.getValue() != null){
					return false;
				}
			}else{
				if(!this.getValue().equals(comp.getValue())){
					return false;
				}
			}
			
			if(this.getLeft() == null){
				if(comp.getLeft() != null){
					return false;
				}
			}else{
				if(!this.getLeft().equals(comp.getLeft())){
					return false;
				}
			}
			
			if(this.getRight() == null){
				if(comp.getRight() != null){
					return false;
				}
			}else{
				if(!this.getRight().equals(comp.getRight())){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}

}
