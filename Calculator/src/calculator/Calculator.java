package calculator;

//Here's an example implementation of a calculator in Java and Java Swing that
//takes more than two operands and operators from the user, converts the expre
//ssion to postfix notation, and evaluates it:

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField inputField;
    private JLabel resultLabel;

    public Calculator() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        panel.add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        String[] buttons = {"9", "8","7", "6", "5", "4", "3", "2", "1", "0", "+", "-", "*", "/", "(", ")", "C"};
        for (String button : buttons) {
            JButton jButton = new JButton(button);
            jButton.addActionListener(this);
            buttonPanel.add(jButton);
        }
        panel.add(buttonPanel, BorderLayout.CENTER);

        resultLabel = new JLabel("");
        panel.add(resultLabel, BorderLayout.SOUTH);

        setContentPane(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals("C")) {
            inputField.setText("");
            resultLabel.setText("");
        } else if (action.equals("=")) {
            String input = inputField.getText();
            String postfix = infixToPostfix(input);
            double result = evaluatePostfix(postfix);
            resultLabel.setText("= " + result);
        } else {
            inputField.setText(inputField.getText() + action);
        }
    }

    private String infixToPostfix(String input) {
        String result = "";
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                result += c;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    result += stack.pop();
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<Double>();
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(Double.parseDouble(String.valueOf(c)));
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                switch (c) {
                    case '+':
                        stack.push(operand1 + operand2);
                        break;
                    case '-':
                        stack.push(operand1 - operand2);
                        break;
                    case '*':
                        stack.push(operand1 * operand2);
                        break;
                    case '/':
                        stack.push(operand1 / operand2);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
//This implementation uses a JTextField to allow the user to input the expression, and a JLabel to display the result. It also uses a JPanel with a GridLayout to layout the buttons for the calculator.
//
//The actionPerformed method handles the button clicks. If the user clicks the "C" button, the input and result fields are cleared. If the user clicks the "=" button, the input expression is converted to postfix notation, evaluated, and the result is displayed. Otherwise, the clicked button text is appended to the input field.
//
//The infixToPostfix method converts the input expression from infix notation to postfix notation using a stack to keep track of operators.
//
//The evaluatePostfix method evaluates the postfix expression using a stack to keep track of operands.
//
//The precedence method returns the precedence of an operator for use in the conversion from infix to postfix notation.
//
//Note that this implementation does not handle errors, such as division by zero or invalid input, and is intended as a starting point for further development.
