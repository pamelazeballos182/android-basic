package com.example.zeballos.calculator;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Stack;
import java.util.StringTokenizer;


public class CalculatorActivity extends Activity {
    int num = 0;
    public int operate = 0;
    public boolean clear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void onClick(View v) {
        String id = null;
        String s;
        TextView et = ((TextView) findViewById(R.id.tv_screen));


        if (operate >= 0) {

            if (clear)et.setText("0");
            clear = false;
            if (et.getText().toString().charAt(0) == '0') et.setText("");

                s = et.getText() + getValue(v);
            et.setText(s);

            if(s != ""){

            }


        } else {
            System.out.println("llll");
        }


    }

    public String getValue(View v) {
        String id = " ";
        switch (v.getId()) {
            case R.id.btn_zero:
                id = "0";
                operate = 1;
               // clear = false;
                break;
            case R.id.btn_one:
                id = "1";
                operate = 1;
               // clear = false;
                break;
            case R.id.btn_two:
                id = "2";
                operate = 1;
                clear = false;
                break;
            case R.id.btn_three:
                id = "3";
                operate = 1;
               // clear = false;
                break;
            case R.id.btn_four:
                id = "4";
                operate = 1;
                clear = false;
                break;
            case R.id.btn_five:
                id = "5";
                operate = 1;
               // clear = false;
                break;
            case R.id.btn_six:
                id = "6";
                operate = 1;
                //clear = false;
                break;
            case R.id.btn_seven:
                id = "7";
                operate = 1;
                //clear = false;
                break;
            case R.id.btn_eight:
                id = "8";
                operate = 1;
                //clear = false;
                break;
            case R.id.btn_nine:
                id = "9";
                operate = 1;
                //clear = false;
                break;
            case R.id.btn_add:
                if (operate == 1) {
                    id = "+";
                    operate = 0;
                   // clear = false;
                }
                break;
            case R.id.btn_minus:
                if (operate == 1) {
                    id = "-";
                    operate = 0;
                   // clear = false;
                }
                break;
            case R.id.btn_multiply:
                if (operate == 1) {
                    id = "x";
                    operate = 0;
                   // clear = false;
                }
                break;
            case R.id.btn_divide:
                if (operate == 1) {
                    id = "/";
                    operate = 0;
                    //clear = false;
                }
                break;
            case R.id.btn_equal:
                if (operate == 1) {
                   // clear = false;
                    id = showResult();

                }
                break;
            default:
                break;
        }
        return id;
    }

    public String showResult() {
        String s;
        TextView et = ((TextView) findViewById(R.id.tv_screen));
        s = " = " + calculate(et.getText().toString());
        clear = true;
        operate = 0;
        return (s);
    }

    public String calculate(String str) {
        int cnt = 0;

        Stack<Integer> Stk_Num = new Stack<Integer>();
        StringTokenizer ST_Num = new StringTokenizer(str, "+-/x ");
        StringTokenizer ST_Oper = new StringTokenizer(str, "1234567890 ");

        Stk_Num.push(Integer.parseInt(ST_Num.nextToken()));
        while (ST_Num.hasMoreTokens()) {
            char oper = ST_Oper.nextToken().charAt(0);
            String num = ST_Num.nextToken();
            int a;

            if (oper == 'x') {
                a = Stk_Num.pop();
                a *= Integer.parseInt(num);
                Stk_Num.push(a);

            } else if (oper == '/') {
                a = Stk_Num.pop();
                a /= Integer.parseInt(num);
                Stk_Num.push(a);
            } else if (oper == '+') {
                Stk_Num.push(Integer.parseInt(num));
            } else if (oper == '-') {
                Stk_Num.push(-1 * (Integer.parseInt(num)));
            }
        }
        while (!Stk_Num.isEmpty()) {
            cnt += Stk_Num.pop();
        }
        return Integer.toString(cnt);
    }

    public void onClickClear(View v) {
        TextView et = ((TextView) findViewById(R.id.tv_screen));
        et.setText("0");
    }
}
