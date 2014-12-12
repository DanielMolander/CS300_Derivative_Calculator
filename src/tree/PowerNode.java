package tree;

import java.math.BigInteger;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class PowerNode extends Node{

	public PowerNode(){
		super("^");
	}

	//NEEDS WORK
	public Node Differentiate(String var) {
		// TODO Auto-generated method stub
		MultiplicationNode multNode = new MultiplicationNode();
		PowerNode powerNode = new PowerNode();
		//x^c
		if(children.get(0) instanceof ConstantNode){

		}else{
			if(children.get(1) instanceof ConstantNode){
				ConstantNode n = (ConstantNode)children.get(1);
				multNode.addChild(n.clone());
				powerNode.addChild(children.get(0).clone());
				ConstantNode exp = (ConstantNode) n.clone();
				exp.num = exp.num.subtract(BigInteger.ONE);
				powerNode.addChild(exp);
				multNode.addChild(powerNode);
				multNode.addChild(children.get(0).Differentiate(var));
			}
		}
		//		powerNode.addChild(children.get(0));
		//		powerNode.addChild(node);
		return multNode;
	}

	public Node simplify() {
//		System.out.println("1");
		if(children.get(0) instanceof ConstantNode){
			ConstantNode c0 = (ConstantNode) children.get(0);
//			System.out.println("2");
			if(c0.num.equals(BigInteger.ONE))
				return new ConstantNode(""+1);
			if(c0.num.equals(BigInteger.ZERO))
				return new ConstantNode(""+0);
			if(children.get(1) instanceof ConstantNode){
				ConstantNode c1 = (ConstantNode) children.get(1);
				return new ConstantNode(""+c0.num.pow(c1.num.intValue()));
			}
		}
		if(children.get(1) instanceof ConstantNode){
			ConstantNode c1 = (ConstantNode) children.get(1);
			if(c1.num.equals(BigInteger.ZERO))
				return new ConstantNode(""+1);
			if(c1.num.equals(BigInteger.ONE))
				return children.get(0).clone();
		}
		return clone();
//		BigInteger num = new BigInteger(""+0);
//		for(Node n : children){
//			if(n instanceof ConstantNode){
//				ConstantNode leaf = (ConstantNode) n;
//				if(num.equals(BigInteger.ZERO))
//					num = leaf.getVal();
//				else
//					num=num.pow(leaf.getVal().intValue());
//			}else{
//				
//			}
//		}
//		return new ConstantNode(num);
	}

	public String toString(){
		return "("+children.get(0)+"^"+children.get(1)+")";
	}

	public Node clone(){
		PowerNode powerNode = new PowerNode();
		for(Node n : children){
			powerNode.addChild(n.clone());
		}
		return powerNode;
	}

	public boolean equals(PowerNode node){
		return children.get(0).equals(node.children.get(0)) && children.get(1).equals(node.children.get(1));
	}

	public boolean equals(Object o){
		if(o instanceof PowerNode)
			return equals((PowerNode)o);
		return false;
	}
}
