package tree;

import java.math.BigInteger;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class MultiplicationNode extends Node{
	
	public MultiplicationNode(){
		super("*");
	}

	@Override
	public Node Differentiate(String var) {
		// TODO Auto-generated method stub
		Node simplifiedNode = simplify();
		if(!(simplifiedNode instanceof MultiplicationNode))
			return simplifiedNode.Differentiate(var);
		//these 2 cases are unnecessary
//		if(simplifiedNode.getNumChildren() == 0){
//			return simplifiedNode.Differentiate(var);
//		}
//		if(simplifiedNode.getNumChildren() == 1){
//			return simplifiedNode.getChild(0).Differentiate(var);
//		}
		Node leftNode = simplifiedNode.children.get(0);
		simplifiedNode.children.remove(0);
		AdditionNode addNode = new AdditionNode();
		MultiplicationNode left = new MultiplicationNode();
		MultiplicationNode right = new MultiplicationNode();
		left.addChild(leftNode.clone());
		left.addChild(simplifiedNode.Differentiate(var));
		right.addChild(leftNode.Differentiate(var));
		right.addChild(simplifiedNode);
		addNode.addChild(left);
		addNode.addChild(right);
		return addNode;
	}
	
	//NEEDS WORK
	public Node simplify() {
		if(getNumChildren() == 1)
			return children.get(0).clone();
		BigInteger num = new BigInteger("1");
		MultiplicationNode multNode = new MultiplicationNode();
		for(Node n : children){
			if(n instanceof ConstantNode){
				ConstantNode leaf = (ConstantNode) n;
				if(leaf.getVal().equals(BigInteger.ZERO))
					return new ConstantNode("0");
				num=num.multiply(leaf.getVal());
			}else if(n instanceof MultiplicationNode){
				Node temp = n.clone();
				for(int i = 0; i < n.getNumChildren(); i++){
					Node c = temp.getChild(0);
					multNode.addChild(c.simplify());
					temp.children.remove(0);
				}
			}else{
				multNode.addChild(n.simplify());
			}
		}
		if(multNode.getNumChildren() == 0){
			return new ConstantNode(""+num);
		}
		if(!num.equals(BigInteger.ONE)){
			ConstantNode n = new ConstantNode(""+num);
//			System.out.println("N: "+n);
			multNode.addChild(n, 0);
		}
		if(multNode.getNumChildren() == 1){
				return multNode.getChild(0).clone();
		}
		return multNode;
	}

	public String toString(){
		if(getNumChildren() == 0)
			return "";
		String result = "(";
//		if(children == null)
//			return "( m had no kids )";
		for(int i = 0; i < children.size(); i++){
			if(i != 0)
				result+="*";
//			System.out.println("found child "+i+" to be: "+children.get(i));
			result+=children.get(i);
		}
		return result+")";
	}
	
	public Node clone(){
		MultiplicationNode multNode = new MultiplicationNode();
		for(Node n : children){
			multNode.addChild(n.clone());
		}
		return multNode;
	}
	
	public boolean equals(MultiplicationNode node){
		for(int i = 0; i < getNumChildren(); i++){
			if(!children.get(i).equals(node.children.get(i)))
				return false;
		}
		return true;
	}

	public boolean equals(Object o){
		if(o instanceof MultiplicationNode)
			return equals((MultiplicationNode)o);
		return false;
	}
}
