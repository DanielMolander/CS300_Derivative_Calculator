package tree;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class SinNode extends TrigNode{

	public SinNode(){
		super("sin");
	}

	@Override
	public Node Differentiate(String var) {
		MultiplicationNode multNode = new MultiplicationNode();
		CosNode cosNode = new CosNode();
		cosNode.addChild(children.get(0).clone());
		multNode.addChild(cosNode);
		multNode.addChild(children.get(0).Differentiate(var));
		return multNode;
	}

	@Override
	public Node simplify() {
		SinNode newSinNode = new SinNode();
		newSinNode.addChild(children.get(0).simplify());
		return newSinNode;
	}
	
	public String toString(){
		return "sin("+children.get(0)+")";
	}
	
	public Node clone(){
		SinNode sinNode = new SinNode();
		sinNode.addChild(children.get(0).clone());
		return sinNode;
	}
	
	public boolean equals(SinNode node){
		return children.get(0).equals(node.children.get(0));
	}

	public boolean equals(Object o){
		if(o instanceof SinNode)
			return equals((SinNode)o);
		return false;
	}

}
