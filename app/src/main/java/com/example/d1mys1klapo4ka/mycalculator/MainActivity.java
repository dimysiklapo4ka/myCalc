package com.example.d1mys1klapo4ka.mycalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.EnumMap;

public class MainActivity extends Activity {

    private EditText textOperation;
    private EditText textResult;

    private Button divide;
    private Button multiply;
    private Button subtract;
    private Button add;
    private Button root;
    private Button power;
    private Button percent;

    private EnumMap<Sequence, Object> comand = new EnumMap<Sequence, Object>(Sequence.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        textOperation = (EditText)findViewById(R.id.et_text_operation);
        textResult = (EditText)findViewById(R.id.et_text_result);

        divide = (Button)findViewById(R.id.btn_divide);
        multiply = (Button)findViewById(R.id.btn_multiply);
        subtract = (Button)findViewById(R.id.btn_subtract);
        add = (Button)findViewById(R.id.btn_add);
        root = (Button)findViewById(R.id.btn_root);
        power = (Button)findViewById(R.id.btn_power);
        percent = (Button)findViewById(R.id.btn_percent);

        divide.setTag(Operation.DIVIDE);
        multiply.setTag(Operation.MULTIPLY);
        subtract.setTag(Operation.SUBTRACT);
        add.setTag(Operation.ADD);
        root.setTag(Operation.ROOT);
        power.setTag(Operation.POWER);
        percent.setTag(Operation.PERCENT);
    }

    private Operation operation;

    public void buttonClick(View view){

        switch (view.getId()){

            case R.id.btn_divide:
            case R.id.btn_multiply:
            case R.id.btn_subtract:
            case R.id.btn_add:
            case R.id.btn_root:
            case R.id.btn_power:
            case R.id.btn_percent:{

                operation = (Operation)view.getTag();

            if (!comand.containsKey(Sequence.OPERATION)){

                if (!comand.containsKey(Sequence.FIRST_DIGIT)){
                    comand.put(Sequence.FIRST_DIGIT, textOperation.getText());
                }
                comand.put(Sequence.OPERATION, operation);
            }
            else if (!comand.containsKey(Sequence.SECOND_DIGIT)){
                comand.put(Sequence.SECOND_DIGIT, textOperation.getText());
                doCalc();
                comand.put(Sequence.FIRST_DIGIT, textOperation.getText());
                //comand.put(Sequence.OPERATION, operation);
                comand.remove(Sequence.OPERATION);
                comand.remove(Sequence.SECOND_DIGIT);
            }
            comand.put(Sequence.OPERATION, operation);
            break;
            }
            case R.id.btn_clear:{
                textOperation.setText("0");
                comand.clear();
                break;
            }
            case R.id.btn_equal:{

                if (comand.containsKey(Sequence.FIRST_DIGIT) &&
                        comand.containsKey(Sequence.OPERATION)){

                    comand.put(Sequence.SECOND_DIGIT, textOperation.getText());

                    doCalc();

                    comand.remove(Sequence.OPERATION);
                    //comand.put(Sequence.OPERATION, operation);
                    comand.remove(Sequence.SECOND_DIGIT);
                }
                //comand.put(Sequence.OPERATION, operation);
                break;
            }
            case R.id.btn_tochka:{
                if (comand.containsKey(Sequence.FIRST_DIGIT) &&
                        getDouble(textOperation.getText().toString()) ==
                        getDouble(comand.get(Sequence.FIRST_DIGIT).toString())){

                    textOperation.setText("0" + view.getContentDescription().toString());
                }

                if (!textOperation.getText().toString().contains(".")){

                    textOperation.setText(textOperation.getText() + ".");
                }

                break;
            }
            case R.id.btn_delete:{

                textOperation.setText(textOperation.getText().delete(
                        textOperation.getText().length() - 1,
                        textOperation.getText().length()));

                if (textOperation.getText().toString().trim().length() == 0){
                    textOperation.setText("0");
                }
                break;
            }
            default:{

                if (textOperation.getText().toString().equals("0") ||
                        comand.containsKey(Sequence.FIRST_DIGIT) &&
                                getDouble(textOperation.getText()) ==
                                        getDouble(comand.get(Sequence.FIRST_DIGIT))){

                    textOperation.setText(view.getContentDescription().toString());

                }else {
                    textOperation.setText("" + textOperation.getText() +
                            view.getContentDescription().toString());
                }
            }

        }
    }

    private double getDouble(Object value){

        double rezult = 0.0;

        try{
            rezult = Double.valueOf(value.toString()).doubleValue();
        }catch (Exception e){
            e.printStackTrace();
            rezult = 0.0;
        }
        return rezult;
    }

    private void doCalc(){

        Operation operationTMP = (Operation)comand.get(Sequence.OPERATION);

        double rez = 0.0;
        try {

         rez = calc(operationTMP,
                getDouble(comand.get(Sequence.FIRST_DIGIT)),
                getDouble(comand.get(Sequence.SECOND_DIGIT)));

        }catch (Exception e){
            e.printStackTrace();
        }

        if (rez % 1 == 0){
            textOperation.setText(String.valueOf((int)rez));
        }else {
            textOperation.setText(String.valueOf(rez));
        }

        comand.put(Sequence.FIRST_DIGIT, rez);
    }

    private Double calc(Operation oper, double a, double b){
        switch (oper){
            case DIVIDE:
                return CalculatorOperation.divide(a, b);
            case MULTIPLY:
                return CalculatorOperation.multiply(a, b);
            case SUBTRACT:
                return CalculatorOperation.subtract(a, b);
            case ADD:
                return CalculatorOperation.add(a, b);
            case ROOT:
                return CalculatorOperation.root(a, b);
            case POWER:
                return CalculatorOperation.power(a, b);
            case PERCENT:
                return CalculatorOperation.percent(a, b);
        }
        return null;
    }
}
