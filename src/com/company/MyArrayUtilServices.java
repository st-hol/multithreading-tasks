package com.company;

import java.util.Random;


/**
 * util
 */
public class MyArrayUtilServices {

    public static Integer[][] obtainFilledArray2D(int rows, int cols, int minBound, int MaxBound) {
        Random r = new Random();
        Integer[][] array2D =
                r.ints(rows, minBound , MaxBound)
                        .mapToObj(row -> r.ints(cols, minBound , MaxBound)
                                .boxed()
                                .toArray(Integer[]::new))
                        .toArray(Integer[][]::new);
        return array2D;
    }


    public static void outputArray2D(Integer[][] array2D){
        for (Integer[] ints : array2D) {
            System.out.println("\n");
            for (Integer anInt : ints) {
                System.out.print(anInt + "\t\t");
            }
        }
    }

    public static int findMax(Integer[] array) {
        int max = Integer.MIN_VALUE;
        for (int element : array) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public static int findMin(Integer[] array) {
        int min = Integer.MAX_VALUE;
        for (int element : array) {
            if (element < min) {
                min = element;
            }
        }
        return min;
    }
}
















//https://stackoverflow.com/questions/37643078/how-to-find-max-value-of-the-two-dimensional-array-in-java-using
// -multithreading
//https://stackoverflow.com/questions/20409836/multithreading-to-calculate-the-min-and-maximum-numbers-in-java


