package com.example.calulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] BtnNumeric =  {
            R.id.btnOne,
            R.id.btnTwo,
            R.id.btnTree,
            R.id.btnFour,
            R.id.btnFive,
            R.id.btnSix,
            R.id.btnSev,
            R.id.btnEgt,
            R.id.btnNin,
            R.id.btnZero
    };

    private int[] BtnOprator = {
            R.id.btnDiv,
            R.id.btnMul,
            R.id.btnMod,
            R.id.btnSub,
            R.id.btnPluse
    };

    private TextView textScreen,textScreen1;
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textScreen  = (TextView) findViewById(R.id.tv1);
        this.textScreen1 = (TextView) findViewById(R.id.tv2);
        setNumericClickListner();
        setOperatorOnClick();
    }
    private void setNumericClickListner(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(stateError){
                    textScreen.setText(button.getText());
                    stateError = false;
                }else{
                    textScreen.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        for (int id : BtnNumeric){
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClick(){

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric && !stateError){
                    Button button = (Button) v;
                    textScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }else{
                    Toast.makeText(MainActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        };
        for (int id : BtnOprator){
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnDec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric && !stateError){
                    textScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }else{}
            }
        });

        findViewById(R.id.btnAc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textScreen.setText("");
                textScreen1.setText("");
                lastDot = false;
                lastNumeric = false;
                stateError = false;
            }
        });

        findViewById(R.id.btnCncl).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textScreen.setText("");
                return false;
            }
        });
        findViewById(R.id.btnCncl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt1 = textScreen.getText().toString();
                if(!txt1.equals("")){
                    textScreen.setText(txt1.substring(0,txt1.length() -1 ));
                }
            }
        });

                findViewById(R.id.btnEql).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onEqual();
                    }
                });
    }

    private void onEqual(){
        if(lastNumeric && !stateError){
            String txt = textScreen.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try{
                double result = expression.evaluate();
//                textScreen.setText("");
                textScreen1.setText(Double.toString(result));
                lastDot = true;

            }catch (ArithmeticException ex){
                textScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }



}
