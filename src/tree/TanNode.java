package tree;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class TanNode extends TrigNode{

	public TanNode(){
		super("tan");
	}

	@Override
	public Node Differentiate(String var) {
		MultiplicationNode multNode = new MultiplicationNode();
		PowerNode powerNode = new PowerNode();
		CosNode cosNode = new CosNode();
		cosNode.addChild(children.get(0).clone());
		powerNode.addChild(cosNode);
		powerNode.addChild(new ConstantNode("-2"));
		multNode.addChild(powerNode);
		multNode.addChild(children.get(0).Differentiate(var));
		return multNode;
	}

	@Override
	public Node simplify() {
		TanNode tanNode = new TanNode();
		tanNode.addChild(children.get(0).simplify());
		return tanNode;
	}
	
	public String toString(){
		return "tan("+children.get(0)+")";
	}
	
	public Node clone(){
		TanNode tanNode = new TanNode();
		tanNode.addChild(children.get(0).clone());
		return tanNode;
	}
	
	public boolean equals(TanNode node){
		return children.get(0).equals(node.children.get(0));
	}

	public boolean equals(Object o){
		if(o instanceof TanNode)
			return equals((TanNode)o);
		return false;
	}

}
