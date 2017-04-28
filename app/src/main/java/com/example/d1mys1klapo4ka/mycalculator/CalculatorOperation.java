package com.example.d1mys1klapo4ka.mycalculator;

/**
 * Created by d1mys1klapo4ka on 28.04.2017.
 */

public class CalculatorOperation {

    public static double add(double firstDigit, double secondDigit){
        return firstDigit + secondDigit;
    }
    public static double subtract(double firstDigit, double secondDigit){
        return firstDigit - secondDigit;
    }
    public static double divide(double firstDigit, double secondDigit){
        return secondDigit == 0 ? 0 : firstDigit / secondDigit;
    }
    public static double multiply(double firstDigit, double secondDigit){
        return firstDigit * secondDigit;
    }
    public static double root(double firstDigit, double secondDigit){
        return Math.exp(Math.log(secondDigit)/firstDigit);
    }
    public static double power(double firstDigit, double secondDigit){
        return Math.pow(firstDigit,secondDigit);
    }
    public static double percent(double firstDigit, double secondDigit){
        return (firstDigit * secondDigit)/100;
    }
}
