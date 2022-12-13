package com.aldreduser.testapp;

import java.util.ArrayList;
import java.util.Arrays;

public class codewarsTest {

    public double[] tribonacci(double[] s, int n) {

        // best
        double[] result = Arrays.copyOf(s,n);
        for (int i = 3; i < n; i++) {
            result[i] = result[i-3] + result[i-2] + result[i-1];
        }
        return result;



        // middle
//        double[] result = new double[n];
//        if (n==0) {
//            return result;
//        } else {
//            for(int i = 0; i < n; i++) {
//
//            }
//        }





        // mine pt2
//        double[] tribonacciList = new double[n];
//        if (n < 3) {
//            switch (n) {
//                case 1:
//                    tribonacciList[n-1] = s[n-1];
//                    break;
//                case 2:
//                    tribonacciList[n - 1] = s[n - 1];
//                    tribonacciList[n - 2] = s[n - 2];
//                    break;
////                case 0:
////                    break;
//            }
//        } else {
//            for(int i = 0; i < n; i++) {
//                tribonacciList[i] = tribonacciList[i - 1] + tribonacciList[i - 2] + tribonacciList[i - 3];
//            }
//        }
//        return tribonacciList;





        // mine
//        System.out.println(Arrays.toString(s));

//        ArrayList<Double> tribonacciList = new ArrayList<>();
//        for (double num : s) {
//            tribonacciList.add(num);
//        }


//        if (n < 3) {
//            switch (n) {
//                case 1:
//
//                    break;
//                case 2:
//                    break;
//                case 0:
//                    break;
//            }
//        } else {
//
//        }


//        for (double num : tribonacciList) {
//
//        }
//
//        return tribonacciList;

    }
}
