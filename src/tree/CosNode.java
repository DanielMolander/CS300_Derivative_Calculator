package tree;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class CosNode extends TrigNode{

	public CosNode(){
		super("cos");
	}

	@Override
	public Node Differentiate(String var) {
		MultiplicationNode multNode = new MultiplicationNode();
		SinNode sinNode = new SinNode();
		sinNode.addChild(children.get(0).clone());
		multNode.addChild(new ConstantNode("-1"));
		multNode.addChild(sinNode);
		multNode.addChild(children.get(0).Differentiate(var));
		return multNode;
	}

	@Override
	public Node simplify() {
		CosNode newCosNode = new CosNode();
		newCosNode.addChild(children.get(0).simplify());
		return newCosNode;
	}
	
	public String toString(){
		return "cos("+children.get(0)+")";
	}
	
	public Node clone(){
		CosNode cosNode = new CosNode();
		cosNode.addChild(children.get(0).clone());
		return cosNode;
	}
	
	public boolean equals(CosNode node){
		return children.get(0).equals(node.children.get(0));
	}

	public boolean equals(Object o){
		if(o instanceof CosNode)
			return equals((CosNode)o);
		return false;
	}

}
