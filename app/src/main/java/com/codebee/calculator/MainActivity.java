package com.codebee.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.keypad_container,new FragmentKeypad1()).commit();
    }

    public void setCalculatedResult(){
        ((TextView)findViewById(R.id.result_text)).setText("Result set Successfully!");
    }

}

