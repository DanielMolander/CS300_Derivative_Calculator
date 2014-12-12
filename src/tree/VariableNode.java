package tree;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class VariableNode extends Node{

	String sym;

	public VariableNode(String sym){
		super(sym);
		this.sym = sym;
	}

	@Override
	public Node Differentiate(String var) {
		// TODO Auto-generated method stub
		if(var.equals(sym)){
			return new ConstantNode("1");
		}else{
			return new ConstantNode("0");
		}
	}

	@Override
	public Node simplify() {
		//there is nothing to simplify
		return clone();
	}

	public Node clone(){
		return new VariableNode(sym);
	}

	public String toString(){
		return sym;
	}

	public boolean equals(VariableNode node){
		return sym.equals(node.sym);
	}

	public boolean equals(Object o){
		if(o instanceof VariableNode)
			return equals((VariableNode)o);
		return false;
	}

}
