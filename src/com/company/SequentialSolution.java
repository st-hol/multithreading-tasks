package com.company;

import static com.company.MyArrayUtilServices.obtainFilledArray2D;
import static com.company.MyArrayUtilServices.outputArray2D;

public class SequentialSolution {

    private static final int COLS = 5;
    private static final int ROWS = 5;

    public static void main(String[] args) {

        Integer[][] array2D = obtainFilledArray2D(ROWS,COLS, -50, 50);
        outputArray2D(array2D);

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (Integer[] ints : array2D) {
            for (int anInt : ints) {
                if (anInt > max) {
                    max = anInt;
                }
                if (anInt < min) {
                    min = anInt;
                }
            }
        }

        System.out.println();
        System.out.println("Max value = " + max);
        System.out.println("Min value = " + min);
    }

}













//public class Main {
//
//    private static final int COLS = 5;
//    private static final int ROWS = 5;
//
//    public static void main(String[] args) {
//
//        Integer[][] array2D = obtainFilledArray2D(ROWS, COLS, -50, 50);
//        outputArray2D(array2D);
//
//        int max = Integer.MIN_VALUE;
//        int min = Integer.MAX_VALUE;
//        for (Integer[] ints : array2D) {
//            max = findMax(ints);
//            min = findMin(ints);
//        }
//
//        System.out.println();
//        System.out.println("Max value = " + max);
//        System.out.println("Min value = " + min);
//    }
//
//}

