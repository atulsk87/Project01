package com.atul.spring.mvc;

import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/maths")
public class OperationsController {
	
	@RequestMapping(value="/maths.jsp", method = RequestMethod.POST)
    public String calculateExpression(HttpServletRequest request, ModelMap model)
    {
		System.out.println("OperationsController.......!!!!!!!");
		String sm = "";
		if(checkParenthesesDepth(request.getParameter("expression")) == -1)
			sm = "Invalid arithmetic expression. Please check the parentheses.";
		else
			sm = String.valueOf(evaluateInfix(request.getParameter("expression")));
        model.addAttribute("message", sm);
        
        HttpSession session = request.getSession();
        String str = String.valueOf(request.getAttribute("oldExpressions"));
        StringBuilder strb = new StringBuilder(str);
        strb.append(" | "+sm);
        session.setAttribute("oldExpressions", strb.toString());
        
        return "maths";
    }
	

	
	
	
	
	public static String evaluateInfix(String exps){
        
        /**remove if any spaces from the expression**/
        exps = exps.replaceAll("\\s+", "");
        /**we assume that the expression is in valid format**/
        MyGenericsStack<String> stack = new MyGenericsStack<String>(exps.length());
        /**break the expression into tokens**/
        StringTokenizer tokens = new StringTokenizer(exps, "{}()*/+-", true);
        while(tokens.hasMoreTokens()){
            String tkn = tokens.nextToken();
            /**read each token and take action**/
            if(tkn.equals("(") 
                    || tkn.equals("{")
                    || tkn.matches("[0-9]+") 
                    || tkn.equals("*")
                    || tkn.equals("/")
                    || tkn.equals("+")
                    || tkn.equals("-")){
                /**push token to the stack**/
                stack.push(tkn);
            } else if(tkn.equals("}") || tkn.equals(")")){
                try {
                    int op2 = Integer.parseInt(stack.pop()); 
                    String oprnd = stack.pop();
                    int op1 = Integer.parseInt(stack.pop());
                    /**Below pop removes either } or ) from stack**/
                    if(!stack.isStackEmpty()){
                        stack.pop();
                    }
                    int result = 0;
                    if(oprnd.equals("*")){
                        result = op1*op2;
                    } else if(oprnd.equals("/")){
                        result = op1/op2;
                    } else if(oprnd.equals("+")){
                        result = op1+op2;
                    } else if(oprnd.equals("-")){
                        result = op1-op2;
                    }
                    /**push the result to the stack**/
                    stack.push(result+"");
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        String finalResult = "";
        try {
            finalResult = stack.pop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalResult;
    }
     
	
	
	
	public static int evaluate(String expression) 
    { 
        char[] tokens = expression.toCharArray(); 
  
         // Stack for numbers: 'values' 
        Stack<Integer> values = new Stack<Integer>(); 
  
        // Stack for Operators: 'ops' 
        Stack<Character> ops = new Stack<Character>(); 
  
        for (int i = 0; i < tokens.length; i++) 
        { 
             // Current token is a whitespace, skip it 
            if (tokens[i] == ' ') 
                continue; 
  
            // Current token is a number, push it to stack for numbers 
            if (tokens[i] >= '0' && tokens[i] <= '9') 
            { 
                StringBuffer sbuf = new StringBuffer(); 
                // There may be more than one digits in number 
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') 
                    sbuf.append(tokens[i++]); 
                values.push(Integer.parseInt(sbuf.toString())); 
            } 
  
            // Current token is an opening brace, push it to 'ops' 
            else if (tokens[i] == '(') 
                ops.push(tokens[i]); 
  
            // Closing brace encountered, solve entire brace 
            else if (tokens[i] == ')') 
            { 
                while (ops.peek() != '(') 
                  values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
                ops.pop(); 
            } 
  
            // Current token is an operator. 
            else if (tokens[i] == '+' || tokens[i] == '-' || 
                     tokens[i] == '*' || tokens[i] == '/') 
            { 
                // While top of 'ops' has same or greater precedence to current 
                // token, which is an operator. Apply operator on top of 'ops' 
                // to top two elements in values stack 
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) 
                  values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
  
                // Push current token to 'ops'. 
                ops.push(tokens[i]); 
            } 
        } 
  
        // Entire expression has been parsed at this point, apply remaining 
        // ops to remaining values 
        while (!ops.empty()) 
            values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
  
        // Top of 'values' contains result, return it 
        return values.pop(); 
    } 
  
    // Returns true if 'op2' has higher or same precedence as 'op1', 
    // otherwise returns false. 
    public static boolean hasPrecedence(char op1, char op2) 
    { 
        if (op2 == '(' || op2 == ')') 
            return false; 
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) 
            return false; 
        else
            return true; 
    } 
  
    // A utility method to apply an operator 'op' on operands 'a'  
    // and 'b'. Return the result. 
    public static int applyOp(char op, int b, int a) 
    { 
        switch (op) 
        { 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case '*': 
            return a * b; 
        case '/': 
            if (b == 0) 
                throw new
                UnsupportedOperationException("Cannot divide by zero"); 
            return a / b; 
        } 
        return 0; 
    }
	
	static int checkParenthesesDepth(String S) { 
        int current_max = 0; // current count  
        int max = 0; // overall maximum count  
        int n = S.length(); 
  
        // Traverse the input string  
        for (int i = 0; i < n; i++) { 
            if (S.charAt(i) == '(') { 
                current_max++; 
  
                // update max if required  
                if (current_max > max) { 
                    max = current_max; 
                } 
            } else if (S.charAt(i) == ')') { 
                if (current_max > 0) { 
                    current_max--; 
                } else { 
                    return -1; 
                } 
            } 
        } 
  
        // finally check for unbalanced string  
        if (current_max != 0) { 
            return -1; 
        } 
  
        return max; 
    } 


}
