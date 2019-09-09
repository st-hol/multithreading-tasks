package com.company;

import static com.company.MyArrayUtilServices.findMax;
import static com.company.MyArrayUtilServices.findMin;
import static com.company.MyArrayUtilServices.obtainFilledArray2D;
import static com.company.MyArrayUtilServices.outputArray2D;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingSolution {

    private static final int COLS = 5;
    private static final int ROWS = 5;

    public static void main(String[] args) throws InterruptedException {

        Integer[][] array2D = obtainFilledArray2D(ROWS,COLS, -50, 50);
        outputArray2D(array2D);

        int totalThreads = array2D.length;

        Integer[] maximumsFromEachDimensionFor = new Integer[totalThreads];
        Integer[] minimumsFromEachDimensionFor = new Integer[totalThreads];

        CountDownLatch endOfCalculationLatch = new CountDownLatch(totalThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            int currentThreadIndex = i;
            executorService.execute(
                    () -> {
                        int maxFromCurrentDimension = findMax(array2D[currentThreadIndex]);
                        int minFromCurrentDimension = findMin(array2D[currentThreadIndex]);
                        maximumsFromEachDimensionFor[currentThreadIndex] = maxFromCurrentDimension;
                        minimumsFromEachDimensionFor[currentThreadIndex] = minFromCurrentDimension;
                        endOfCalculationLatch.countDown();
                    });
        }
        endOfCalculationLatch.await();
        executorService.shutdown();

        int maxFromAllSubResults = findMax(maximumsFromEachDimensionFor);
        int minFromAllSubResults = findMin(minimumsFromEachDimensionFor);

        System.out.println();
        System.out.println("Max value = " + maxFromAllSubResults);
        System.out.println("Min value = " + minFromAllSubResults);

    }

}

