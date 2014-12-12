package tree;

import java.math.BigInteger;

/**
 * this is a node in the equation graph
 * @author Daniel
 *
 */
public class AdditionNode extends Node{

	
	public AdditionNode(){
		super("+");
	}

	//DONE
	public Node Differentiate(String var) {
		// TODO Auto-generated method stub
		AdditionNode addNode = new AdditionNode();
		for(Node n : children){
			addNode.addChild(n.Differentiate(var));
		}
		return addNode;
	}

	//NEEDS WORK
	public Node simplify() {
		AdditionNode addNode = new AdditionNode();
		BigInteger sum = new BigInteger(""+0);
		for(Node n : children){
			if(n instanceof ConstantNode){
				ConstantNode temp = (ConstantNode)n;
				sum=sum.add(new BigInteger(""+temp.num));
			}else if(n instanceof AdditionNode){
				Node temp = n.clone();
				for(int i = 0; i < n.getNumChildren(); i++){
					Node c = temp.getChild(0);
					addNode.addChild(c.simplify());
					temp.children.remove(0);
				}
//				System.out.println("addNode so far: "+addNode);
			}else{
				addNode.addChild(n.simplify());
			}
		}
		if(!sum.equals(BigInteger.ZERO) || addNode.getNumChildren() == 0){
			addNode.addChild(new ConstantNode(sum));
		}
		if(addNode.getNumChildren() == 1)
			return addNode.getChild(0).clone();
		return addNode;
	}
	
	public String toString(){
		String result = "(";
		for(int i = 0; i < children.size(); i++){
			if(i!=0)
				result+="+";
			result+=children.get(i).toString();
		}
		return result+")";
	}
	
	public Node clone(){
		AdditionNode addNode = new AdditionNode();
		for(Node n : children){
			addNode.addChild(n.clone());
		}
		return addNode;
	}
	
	public boolean equals(AdditionNode node){
		for(int i = 0; i < getNumChildren(); i++){
			if(!children.get(i).equals(node.children.get(i))){
				return false;
			}
		}
		return true;
	}

	public boolean equals(Object o){
		if(o instanceof AdditionNode)
			return equals((AdditionNode)o);
		return false;
	}

}
