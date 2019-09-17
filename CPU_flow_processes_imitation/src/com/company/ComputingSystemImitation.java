package com.company;


/**
 * Вариант 7
 * <p>
 * Программа моделирует обслуживание одного потока процессов одним центральным процессором компьютера с одной
 * очередью.
 * <p>
 * Если очередной процесс генерируется в момент, когда процессор свободен, процесс поступает на обработку в
 * процессор, иначе процесс поступает в очередь.
 * <p>
 * Если процессор свободен и в очереди есть процессы, процесс удаляется
 * из очереди.
 * <p>
 * Определите максимальную длину очереди.
 */
public class ComputingSystemImitation {

    public static void main(String[] args) {

        //resource
        ProcessQueue processQueue = new ProcessQueue();

        //consumer
        new Thread(new CPU(processQueue)).start();


        int nProcessesSubmittedToExecution = 5;
        //producer
        new Thread(new ProcessFlow(processQueue, nProcessesSubmittedToExecution)).start();


    }

}



//https://metanit.com/java/tutorial/8.5.php
//https://github.com/tpidgur/Block12_CPU_maintanence2/blob/master/src/CPU/CPU.java
//http://www.cyberforum.ru/java-j2se/thread2037747.html












//    public static final int nCPU = 1;
//    public static final int nQueues = 1;
//    public static final int nProcesses = 1;

