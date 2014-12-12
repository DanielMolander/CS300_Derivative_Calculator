package parser;
import java.math.BigInteger;

import tree.*;

public abstract class Parser {

	private static String eq;
	
	public static Node parse(String eq){
		Parser.eq = eq;
//		Logger l = Logger.getInstance();
		return add();
	}
	
	private static Node add(){
		if(eq.length()==0)
			return null;
		AdditionNode addNode = new AdditionNode();
		addNode.addChild(multiply());
		String token = getToken(1);
		while(token.equals("+") || token.equals("-")){
			eq = eq.substring(1);
			if(token.equals("+")){
				addNode.addChild(multiply());
			}
			token = getToken(1);
		}
		if(addNode.getNumChildren()>1)
			return addNode;
		return addNode.getChild(0);
	}
	
	private static Node multiply(){
		if(eq.length()==0)
			return null;
		MultiplicationNode multNode = new MultiplicationNode();
		multNode.addChild(power());
		String token = getToken(1);
		while(token.equals("*") || token.equals("/")){
			eq = eq.substring(1);
			if(token.equals("*")){
				multNode.addChild(power());
			}
			token = getToken(1);
		}
		if(multNode.getNumChildren() > 1)
			return multNode;
		return multNode.getChild(0);
	}
	
	/**
	 * parse exponentiation from the equation and add it to the tree
	 * please note:
	 * 		there is no set convention for how to interpret a^b^c
	 * 		therefore, this function interprets this as (a^b)^c
	 * 
	 * 		if this is not the desired behavior, please add parenthesis
	 * 		to specify the order of operations  
	 */
	private static Node power(){
		if(eq.length()==0)
			return null;
		PowerNode powerNode = new PowerNode();
		PowerNode currentNode = powerNode;
		powerNode.addChild(trig());
		String token = getToken(1);
		while(token.equals("^")){
			eq = eq.substring(1);
			Node t = trig();
			if(currentNode.getNumChildren() == 2){
				PowerNode temp = new PowerNode();
				
				temp.addChild(currentNode);
				temp.addChild(t);
				currentNode = temp;
			}else
				currentNode.addChild(t);
			token = getToken(1);
		}
		if(powerNode.getNumChildren() > 1)
			return currentNode;
		return powerNode.getChild(0);
	}
	
	private static Node trig(){
//		System.out.println("m: "+eq);
		Node result = null;
		String token = getToken(3);
		if(isTrig(token)){
			eq = eq.substring(3);
			if(token.equalsIgnoreCase("sin")){
				result=new SinNode();
				result.addChild(group());
			}
			else if(token.equalsIgnoreCase("cos")){
				result=new CosNode();
				result.addChild(group());
			}
			else if(token.equalsIgnoreCase("tan")){
				result=new TanNode();
				result.addChild(group());
			}
		}else
			return group();
		return result;
	}
	
	private static boolean isTrig(String token){
		return token.equalsIgnoreCase("sin") || token.equalsIgnoreCase("cos") || token.equalsIgnoreCase("tan");
	}
	
	private static Node group(){
		String token = getToken(1);
		Node result = null;
		if(token.equals("(")){
			eq = eq.substring(1);
			result = add();
			if(!getToken(1).equals(")")){
				System.out.println("parenthesis mismatch! aborting");
				System.exit(1);
			}
			eq = eq.substring(1);
		}
		else
			result = number();
		return result;
	}
	
	private static Node number(){
		if(eq.length()==0)
			return null;
		BigInteger result = new BigInteger("0");
		int c;
		String s;
//		System.out.println("n: "+eq);
		s=getToken(1);
		if(s.equals(""))
			return null;
		c = s.charAt(0)-'0';
		while(c <= 9 && c >=0){
			eq = eq.substring(1);
			result=result.multiply(new BigInteger(""+10));
			result=result.add(new BigInteger(""+c));
//			System.out.println("calculating result: "+result);
			s=getToken(1);
			if(s.equals(""))
				break;
			c = s.charAt(0)-'0';
//			System.out.println("c: "+c);
		}
		MultiplicationNode multNode = new MultiplicationNode();
		ConstantNode coefficient = null;
//		System.out.println("result: "+result);
		if(!result.equals(new BigInteger(""+1)) && !result.equals(new BigInteger(""+0))){
			coefficient = new ConstantNode(""+result);
			multNode.addChild(coefficient);
		}
		s=getToken(1);
		if(s.equals(""))
			return coefficient;
		c = s.charAt(0);
		VariableNode varNode = null;

		while(Character.isAlphabetic(c) && !isTrig(getToken(3))){
			varNode = new VariableNode(""+(char)c);
			multNode.addChild(varNode);
			eq = eq.substring(1);
			s=getToken(1);
			if(s.equals(""))
				break;
			c = s.charAt(0);
		}
		if(multNode.getNumChildren()>1)
			return multNode;
		if(varNode != null)
			return varNode;
		return coefficient;
	}
	
	private static String getToken(int len){
		if(eq.length()<len){
//			System.out.println("returning \"\"");
			return "";
		}
		while(eq.charAt(0) == ' '){
			eq = eq.substring(1);
		}
		String t = eq.substring(0, len);
		return t;
	}
	
}
