package com.codebee.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentKeypad2 extends Fragment implements View.OnClickListener {

    private String operand1 = "", operand2 = "", result = "";
    private char operator = ' ';
    private double op1 = 0, op2 = 0, res = 0;
    private String expression = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keypad2, container, false);

        if (getArguments() != null) {
            operand1 = getArguments().getString("operand1");
            operand2 = getArguments().getString("operand2");
            result = getArguments().getString("result");
            expression = getArguments().getString("expression");

            operator = getArguments().getChar("operator");

            op1 = getArguments().getDouble("op1");
            op2 = getArguments().getDouble("op2");
        }

        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentKeypad1();
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

        view.findViewById(R.id.sin_button).setOnClickListener(this);
        view.findViewById(R.id.cos_button).setOnClickListener(this);
        view.findViewById(R.id.tan_button).setOnClickListener(this);
        view.findViewById(R.id.log_button).setOnClickListener(this);
        view.findViewById(R.id.ln_button).setOnClickListener(this);
        view.findViewById(R.id.exp_button).setOnClickListener(this);
        view.findViewById(R.id.pow_button).setOnClickListener(this);
        view.findViewById(R.id.fact_button).setOnClickListener(this);
        view.findViewById(R.id.pie_button).setOnClickListener(this);
        view.findViewById(R.id.inv_button).setOnClickListener(this);
        view.findViewById(R.id.sqrt_button).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (operator == ' ') {
            switch (v.getId()) {
                case R.id.sin_button:
                    operator = 's';
                    expression = "sin(" + expression + ")";
                    break;
                case R.id.cos_button:
                    operator = 'c';
                    expression = "cos(" + expression + ")";
                    break;
                case R.id.tan_button:
                    operator = 't';
                    expression = "tan(" + expression + ")";
                    break;
                case R.id.log_button:
                    expression = "log(" + expression + ")";
                    operator = 'L';
                    break;
                case R.id.ln_button:
                    operator = 'l';
                    expression = "ln(" + expression + ")";
                    break;
                case R.id.exp_button:
                    operator = 'e';
                    expression = "exp(" + expression + ")";
                    break;
                case R.id.pie_button:
                    operator = 'P';
                    expression = expression + "PIE";
                    break;
                case R.id.pow_button:
                    operator = 'p';
                    expression = expression + "^";
                    break;
                case R.id.fact_button:
                    operator = 'f';
                    expression = expression + "!";
                    break;
                case R.id.inv_button:
                    operator = 'i';
                    expression = "1/(" + expression + ")";
                    break;
                case R.id.sqrt_button:
                    operator = 'r';
                    expression = "sqrt(" + expression + ")";
                    break;
            }
            ((MainActivity) getActivity()).displayData(expression);
        } else {
            Toast.makeText(getContext(), "Cannot apply operator!", Toast.LENGTH_LONG).show();
        }
    }
}
