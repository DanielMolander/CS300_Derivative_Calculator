import java.util.Scanner;

import parser.Parser;
import tree.Node;


public class Main {

	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter equation to derivate (ex. \"x^2*y^2*z^12+abc\"):");
		String eq = sc.nextLine();//"x^2*y^2*z^12+abc";
		String var = "";
		while(var.length()!= 1){
			System.out.println("Please enter single character for variable to differentiate in terms of (case sensitive, ex. \"x\"):");
			var = sc.nextLine();
			if(var.length() != 0)
				if(!Character.isAlphabetic((int)var.charAt(0))){
					System.out.println("Invalid input. Must be single letter");
					var = "";
				}
		}
		Node tree = Parser.parse(eq);
		System.out.println("parsed:\n"+tree);
		tree = tree.Differentiate(var);
		System.out.println("differentiated:\n"+tree);
		Node temp = null;
		while(!tree.equals(temp)){
			temp = tree.clone();
			tree = tree.simplify();
		}
		System.out.println("simplified:\n"+tree);
	}
}
