package com.wuxl.practice.forkjoin.dumb;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Sum extends RecursiveTask<Long> {
    private static final int THRESHOLD = 100;

    private final int low;

    private final int high;

    private final int[] array;

    public Sum(final int low, final int high, final int[] array) {
        this.low = low;
        this.high = high;
        this.array = array;
    }

    @Override
    protected Long compute() {
        if (high - low < THRESHOLD) {
            return execute();
        }

        final int middle = low + (high - low) / 2;

        final Sum left = new Sum(low, middle, array);
        final Sum right = new Sum(middle, high, array);

        left.fork();

        final long rightSum = right.compute();

        final long leftSum = left.join();

        return leftSum + rightSum;
    }

    private Long execute() {
        long sum = 0;

        for (int i = low; i < high; i++) {
            sum += array[i];
        }

        System.out.println("[" + Thread.currentThread().getName() + "]"
                + "low " + low + " high " + high + " sum " + sum);

        return sum;
    }

    public static void main(String[] args) {
        final int low = 0;
        final int high = 5000;

        final int[] array = new int[high];

        for (int i = low; i < high; i ++) {
            array[i] = i;
        }

        final long sum = ForkJoinPool.commonPool().invoke(new Sum(low, high, array));

        System.out.println("Sum " + sum);
    }
}
