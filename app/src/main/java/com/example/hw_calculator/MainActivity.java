package com.example.hw_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    TextView workingTV;
    TextView resultTV;

    String workingString = "";
    String formula = "";
    String tempFormula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }
    void initTextViews(){
        workingTV = (TextView) findViewById(R.id.workingTextView);
        resultTV = (TextView) findViewById(R.id.resultTextView);
    }
    void setWorkingString(String values){
        workingString = workingString + values;
        workingTV.setText(workingString);
    }
    public void equalOnClick(View view) {
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

        try {
            result = (double)engine.eval(formula);
        } catch (ScriptException e)
        {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if(result != null)
            resultTV.setText(String.valueOf(result.doubleValue()));
    }
    private void checkForPowerOf()
    {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0; i < workingString.length(); i++)
        {
            if (workingString.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        formula = workingString;
        tempFormula = workingString;
        for(Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        formula = tempFormula;
    }

    private void changeFormula(Integer index)
    {
        String numberLeft = "";
        String numberRight = "";

        for(int i = index + 1; i< workingString.length(); i++)
        {
            if(isNumeric(workingString.charAt(i)))
                numberRight = numberRight + workingString.charAt(i);
            else
                break;
        }

        for(int i = index - 1; i >= 0; i--)
        {
            if(isNumeric(workingString.charAt(i)))
                numberLeft = numberLeft + workingString.charAt(i);
            else
                break;
        }

        String original = numberLeft + "^" + numberRight;
        String changed = "Math.pow("+numberLeft+","+numberRight+")";
        tempFormula = tempFormula.replace(original,changed);
    }

    private boolean isNumeric(char c)
    {
        if((c <= '9' && c >= '0') || c == '.')
            return true;

        return false;
    }

    boolean leftBracket = true;

    public void clearOnClick(View view) {
        workingTV.setText("");
        workingString = "";
        resultTV.setText("");
        leftBracket = true;
    }
    public void bracketsOnClick(View view)
    {
        if(leftBracket)
        {
            setWorkingString("(");
            leftBracket = false;
        }
        else
        {
            setWorkingString(")");
            leftBracket = true;
        }
    }

    public void powerOnClick(View view) {
        setWorkingString("^");
    }
    public void divideOnClick(View view) {
        setWorkingString("/");
    }
    public void sevenOnClick(View view) {
        setWorkingString("7");
    }
    public void eightOnClick(View view) {
        setWorkingString("8");
    }
    public void nineOnClick(View view) {
        setWorkingString("9");
    }
    public void multiplyOnClick(View view) {
        setWorkingString("*");
    }
    public void sixOnClick(View view) {
        setWorkingString("6");
    }
    public void fiveOnClick(View view) {
        setWorkingString("5");
    }
    public void fourOnClick(View view) {
        setWorkingString("4");
    }
    public void subtractOnClick(View view) {
        setWorkingString("-");
    }
    public void oneOnClick(View view) {
        setWorkingString("1");
    }
    public void twoOnClick(View view) {
        setWorkingString("2");
    }
    public void threeOnClick(View view) {
        setWorkingString("3");
    }
    public void addOnClick(View view) {
        setWorkingString("+");
    }
    public void dotOnClick(View view) {
        setWorkingString(".");
    }
    public void zeroOnClick(View view) {
        setWorkingString("0");
    }


    public void deleteOnClick(View view) {
        if (workingString.length() == 1){
            workingString = "";
        }
        if (workingString.length() > 1) {
            workingString = workingString.substring(0, workingString.length() - 1);
        }
        workingTV.setText(workingString);
    }
}