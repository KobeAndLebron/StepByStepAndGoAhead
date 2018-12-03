package lettcode;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 *  Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 *
 *	Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 *
 *	Some examples:
 *
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 *
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年6月27日-下午7:05:29
 *
 */
public class EvaluateValueOfRPN {
	/**
	 * ^ The start of regular expression
	 * $ The end of regular expression
	 * \- It represents -
	 * \+ ....          +
	 */
	private static final Pattern DIGITS = Pattern.compile("^\\-*[0-9]+$");
	private static final Pattern OPERAND = Pattern.compile("^[\\+\\-\\*\\/]{1}$");
	
	public int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
        if(tokens == null){
        	throw new IllegalArgumentException("Input is null");
        }
        
        for(String token : tokens){
        	if(DIGITS.matcher(token).matches()){
        		stack.push(Integer.parseInt(token));
        	}else if(OPERAND.matcher(token).matches()){
        		Integer o1 = null;
        		Integer o2 = null;
        		try{
        			o1 = stack.pop();
        			o2 = stack.pop();
        		}catch(EmptyStackException e){
        			throw new RuntimeException("Ilegal input");
        		}
        		stack.push(getValue(o2, o1, token));
        	}else{
        		throw new RuntimeException("Input error:Content of input can only concain 0-9 or operand(+ - * /)");
        	}
        }
        
        int returnI = 0;
        try{
        	returnI = stack.pop();
		}catch(EmptyStackException e){
			throw new RuntimeException("Illegal input");
		}
        
        return returnI;
    }
	
	private int getValue(int o1, int o2, String operand){
		switch(operand){
			case "+":
				return o1 + o2;
			case "-":
				return o1 - o2;
			case "*":
				return o1 * o2;
			case "/":
				return o1 / o2;
			default :
				throw new RuntimeException("Ilegal operand");
		}
	}
}
