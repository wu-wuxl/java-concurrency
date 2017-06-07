package com.wuxl.practice.forkjoin.stream;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SumRange {
    public static void main(String[] args) throws Exception {
        final long firstNum = 1L;
        final long lastNum = 1000000L;

        List<Long> numbers;

        try (final LongStream longStream = LongStream.rangeClosed(firstNum, lastNum)) {
            numbers = longStream.boxed().collect(Collectors.toList());
        }

        final int parallelism = ForkJoinPool.commonPool().getParallelism();

        assertThat(sum(numbers), equalTo(sum(numbers, parallelism)));
    }

    /**
     * The parallel stream uses the ForkJoinPool.commonPool(),
     * a Thread Pool shared by the entire application.
     */
    public static long sum(final List<Long> numbers) {
        return numbers.parallelStream().reduce(0L, Long::sum);
    }

    /**
     * The parallel stream uses a customer ThreadPool.
     */
    public static long sum(final List<Long> numbers, final int parallelism)
            throws ExecutionException, InterruptedException {
        final ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);

        return customThreadPool.submit(() -> numbers.parallelStream().reduce(0L, Long::sum)).get();
    }
}
