package tree;
import java.util.ArrayList;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public abstract class Node {
	
	private String display;
	protected ArrayList<Node> children;
	
	public Node(String display){
		this.display = display;
	}
	
	public void addChild(Node node){
		if(node == null){
			System.out.println("Warning!! improper equation detected");
		}
		if(children == null)
			children = new ArrayList<Node>();
		children.add(node);
	}
	
	public void addChild(Node node, int index){
		if(node == null){
			System.out.println("Warning!! improper equation detected");
		}
		if(children == null)
			children = new ArrayList<Node>();
		children.add(index, node);
	}
	
	public Node getChild(int index){
		return children.get(index);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public int getNumChildren(){
		if(children == null)
			return 0;
		return children.size();
	}
	
	public abstract Node Differentiate(String var);
	public abstract Node simplify();
	public abstract String toString();
	public abstract Node clone();
	public abstract boolean equals(Object o);
}
