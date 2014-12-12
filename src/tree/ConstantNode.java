package tree;

import java.math.BigInteger;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class ConstantNode extends Node{
	
//	private String display;
//	private ArrayList<AdditionNode> args;
	
	BigInteger num;
	
	public ConstantNode(String num){	
		super(""+num);
		this.num = new BigInteger(num);
	}
	
	public ConstantNode(BigInteger num){
		super(""+num);
		this.num = num;
	}

//	
//	public void addChild(AdditionNode node){
//		if(node == null){
//			System.out.println("Warning!! improper equation detected");
//		}
//		if(args == null)
//			args = new ArrayList<AdditionNode>();
//		args.add(node);
//	}
//	
//	public int getNumChildren(){
//		if(args == null)
//			return 0;
//		return args.size();
//	}
//	
//	public String format(){
//		if(args == null)
//			return display;
//		String result = display;
//		for(AdditionNode n : args){
//			if(n!=null){
//				result+="("+n.format()+")";
//			}
//		}
//		return result;
//	}

	@Override
	public Node Differentiate(String var) {
		// TODO Auto-generated method stub
		return new ConstantNode("0");
	}

	@Override
	public Node simplify() {
		//Nothing to do here, return copy
		return clone();
	}
	
	public BigInteger getVal(){
		return num;
	}
	
	public String toString(){
		return ""+num;
	}
	
	public Node clone(){
		return new ConstantNode(""+num);
	}
	
	public boolean equals(ConstantNode node){
		return num.equals(node.num);
	}

	public boolean equals(Object o){
		if(o instanceof ConstantNode)
			return equals((ConstantNode)o);
		return false;
	}
	
}
