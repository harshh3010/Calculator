package com.codebee.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.*;

public class FragmentKeypad1 extends Fragment {

    private String operand1 = "", operand2 = "", result = "";
    private char operator = ' ';
    private double op1 = 0, op2 = 0, res = 0;
    private String expression = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keypad1, container, false);

        if (getArguments() != null) {
            operand1 = getArguments().getString("operand1");
            operand2 = getArguments().getString("operand2");
            result = getArguments().getString("result");
            expression = getArguments().getString("expression");

            operator = getArguments().getChar("operator");

            op1 = getArguments().getDouble("op1");
            op2 = getArguments().getDouble("op2");
        }

        view.findViewById(R.id.num0_button).setOnClickListener(numClick);
        view.findViewById(R.id.num1_button).setOnClickListener(numClick);
        view.findViewById(R.id.num2_button).setOnClickListener(numClick);
        view.findViewById(R.id.num3_button).setOnClickListener(numClick);
        view.findViewById(R.id.num4_button).setOnClickListener(numClick);
        view.findViewById(R.id.num5_button).setOnClickListener(numClick);
        view.findViewById(R.id.num6_button).setOnClickListener(numClick);
        view.findViewById(R.id.num7_button).setOnClickListener(numClick);
        view.findViewById(R.id.num8_button).setOnClickListener(numClick);
        view.findViewById(R.id.num9_button).setOnClickListener(numClick);
        view.findViewById(R.id.decimal_button).setOnClickListener(numClick);

        view.findViewById(R.id.multiply_button).setOnClickListener(operatorClick);
        view.findViewById(R.id.divide_button).setOnClickListener(operatorClick);
        view.findViewById(R.id.addition_button).setOnClickListener(operatorClick);
        view.findViewById(R.id.percent_button).setOnClickListener(operatorClick);
        view.findViewById(R.id.subtract_button).setOnClickListener(operatorClick);

        view.findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        view.findViewById(R.id.equals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        view.findViewById(R.id.del_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        view.findViewById(R.id.more_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentKeypad2();
                Bundle bundle = new Bundle();
                bundle.putString("operand1", operand1);
                bundle.putString("operand2", operand2);
                bundle.putString("result", result);
                bundle.putString("expression", expression);
                bundle.putDouble("op1", op1);
                bundle.putDouble("op2", op2);
                bundle.putDouble("res", res);
                bundle.putChar("operator", operator);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.keypad_container, fragment).commit();
            }
        });

        return view;
    }

    private View.OnClickListener numClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;

            if (op1 == 0 && operator == ' ') {
                if (button.getText().toString().equals(".")) {
                    if (operand1.contains(".")) {
                        Toast.makeText(getContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
                    } else {
                        operand1 = operand1 + button.getText().toString();
                        expression = expression + button.getText().toString();
                    }
                } else {
                    operand1 = operand1 + button.getText().toString();
                    expression = expression + button.getText().toString();
                }

            } else {

                if (button.getText().toString().equals(".")) {
                    if (operand2.contains(".")) {
                        Toast.makeText(getContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
                    } else {
                        operand2 = operand2 + button.getText().toString();
                        expression = expression + button.getText().toString();
                        calculateResult();
                        op1 = res;
                        res = 0;
                        operand1 = String.valueOf(op1);
                        result = "";
                    }
                } else {
                    operand2 = operand2 + button.getText().toString();
                    expression = expression + button.getText().toString();
                    calculateResult();
                }
            }
            ((MainActivity) getActivity()).displayData(expression);
        }
    };

    private View.OnClickListener operatorClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;

            if (operator == ' ') {

                if (operand1.isEmpty()) {
                    operand1 = "0";
                    expression = expression + operand1 + button.getText().toString();
                } else if (operand1.equals(".")) {
                    expression = expression + "0" + button.getText().toString();
                } else {
                    expression = expression + button.getText().toString();
                }
                operator = button.getText().toString().charAt(0);
            } else {

                operator = button.getText().toString().charAt(0);

                if (expression.charAt(expression.length() - 1) == '+' ||
                        expression.charAt(expression.length() - 1) == '\u2014' ||
                        expression.charAt(expression.length() - 1) == '/' ||
                        expression.charAt(expression.length() - 1) == 'X' ||
                        expression.charAt(expression.length() - 1) == '%') {
                    StringBuilder sb = new StringBuilder(expression);
                    sb.deleteCharAt(expression.length() - 1);
                    sb.append(operator);
                    expression = sb.toString();
                } else {

                    op1 = res;
                    res = 0;
                    op2 = 0;
                    operand1 = String.valueOf(op1);
                    operand2 = "";
                    result = "";
                    expression = expression + operator;
                }
            }
            ((MainActivity) getActivity()).displayData(expression);
        }
    };

    private void calculateResult() {
        if (operand2.isEmpty()) {
            if (operand1.isEmpty() || operator == ' ') {
                Toast.makeText(getContext(), "Please enter valid input!", Toast.LENGTH_SHORT).show();
            } else {
                if (operator == 's' ||
                        operator == 'c' ||
                        operator == 't' ||
                        operator == 'l' ||
                        operator == 'L' ||
                        operator == 'i' ||
                        operator == 'f' ||
                        operator == 't' ||
                        operator == 'e' ||
                        operator == 'P' ||
                        operator == 'r') {

                    if (operand1.equals(".")) {
                        op1 = 0;
                    } else {
                        op1 = Double.parseDouble(operand1);
                    }

                    switch (operator) {
                        case 's':
                            res = Math.sin(op1);
                            break;
                        case 'c':
                            res = Math.cos(op1);
                            break;
                        case 't':
                            res = Math.tan(op1);
                            break;
                        case 'L':
                            res = Math.log10(op1);
                            break;
                        case 'l':
                            res = Math.log(op1);
                            break;
                        case 'e':
                            res = Math.exp(op1);
                            break;
                        case 'P':
                            op1 = op1 * 3.14;
                            break;
                        case 'f':
                            res = factorial(op1);
                            break;
                        case 'i':
                            op1 = 1 / op1;
                            break;
                        case 'r':
                            res = Math.sqrt(op1);
                            break;
                    }
                    result = String.valueOf(res);
                    ((MainActivity) getActivity()).setResult(result);

                }else{
                    Toast.makeText(getContext(), "Please enter valid input!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (operand1.equals(".")) {
                op1 = 0;
            } else {
                op1 = Double.parseDouble(operand1);
            }
            if (operand2.equals(".")) {
                op2 = 0;
            } else {
                op2 = Double.parseDouble(operand2);
            }

            switch (operator) {
                case '+':
                    res = op1 + op2;
                    break;
                case '\u2014':
                    res = op1 - op2;
                    break;
                case 'X':
                    res = op1 * op2;
                    break;
                case '/':
                    res = op1 / op2;
                    break;
                case '%':
                    res = op1 / 100 * op2;
                    break;
                case 's':
                    res = Math.sin(op1);
                    break;
                case 'c':
                    res = Math.cos(op1);
                    break;
                case 't':
                    res = Math.tan(op1);
                    break;
                case 'L':
                    res = Math.log10(op1);
                    break;
                case 'l':
                    res = Math.log(op1);
                    break;
                case 'e':
                    res = Math.exp(op1);
                    break;
                case 'P':
                    op1 = op1 * 3.14;
                    break;
                case 'f':
                    res = factorial(op1);
                    break;
                case 'i':
                    op1 = 1 / op1;
                    break;
                case 'r':
                    res = Math.sqrt(op1);
                    break;
                case 'p':
                    res = Math.pow(op1, op2);
                    break;
            }
            result = String.valueOf(res);
            ((MainActivity) getActivity()).setResult(result);
        }
    }

    private double factorial(double op1) {

        if(op1 == Math.floor(op1)){
            int num = 10;
            long factorial = 1;
            for(int i = 1; i <= num; ++i)
            {
                factorial *= i;
            }
            return (double)factorial;
        }else{
            Toast.makeText(getContext(),"This operator can only be applied on whole numbers.",Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    private void reset() {
        operand1 = "";
        operand2 = "";
        operator = ' ';
        result = "";
        op1 = 0;
        op2 = 0;
        res = 0;
        expression = "";
        ((MainActivity) getActivity()).clearAll();
    }

    private void delete() {
        if (!expression.isEmpty()) {
            if (operator == ' ') {
                StringBuilder sb = new StringBuilder(operand1);
                sb.deleteCharAt(operand1.length() - 1);
                operand1 = sb.toString();
                expression = operand1;
                ((MainActivity) getActivity()).displayData(expression);
            } else {
                if (expression.charAt(expression.length() - 1) == '+' ||
                        expression.charAt(expression.length() - 1) == '\u2014' ||
                        expression.charAt(expression.length() - 1) == '/' ||
                        expression.charAt(expression.length() - 1) == 'X' ||
                        expression.charAt(expression.length() - 1) == '%') {

                    operator = 'D';
                    StringBuilder sb = new StringBuilder(expression);
                    sb.deleteCharAt(expression.length() - 1);
                    expression = sb.toString();
                    ((MainActivity) getActivity()).displayData(expression);
                } else if (operator == 'D') {
                    reset();
                } else {

                    StringBuilder sb = new StringBuilder(operand2);
                    sb.deleteCharAt(operand2.length() - 1);
                    operand2 = sb.toString();

                    StringBuilder sb1 = new StringBuilder(expression);
                    sb1.deleteCharAt(expression.length() - 1);
                    expression = sb1.toString();
                    ((MainActivity) getActivity()).displayData(expression);

                    if (!operand2.isEmpty()) {
                        calculateResult();
                    }
                }
            }
        }
        ((MainActivity) getActivity()).setResult("");
    }
}
