package com.company;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PsychometricTesting {

    private class Pair<T> {
        private T fst;
        private T snd;

        public Pair(T fst, T snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }

    private boolean isInLimit(int number) {
        return number >= 1 && BigInteger.valueOf(number).compareTo(BigInteger.valueOf(10).pow(9)) <= 0;
    }

    private boolean isAllValueInLimit(int[] values) {
        return Arrays.stream(values)
                .boxed()
                .allMatch(this::isInLimit);
    }

    private List<Pair<Integer>> createLimits(int[] lowerLimits, int[] upperLimits) throws Exception {
        if (lowerLimits.length != upperLimits.length) {
            throw new Exception("invalid input");
        }

        return IntStream.range(0, lowerLimits.length)
                .boxed()
                .map(i -> new Pair<>(lowerLimits[i], upperLimits[i]))
                .collect(Collectors.toList());

    }

    public int[] jobOffers(int[] scores, int[] lowerLimits, int[] upperLimits) throws Exception {
        if (!isAllValueInLimit(scores)) {
            throw new Exception("Invalid scores");
        }

        if (!isAllValueInLimit(lowerLimits) || !isAllValueInLimit(upperLimits)) {
            throw new Exception("Invalid limits");
        }

        return createLimits(lowerLimits, upperLimits).stream()
                .map(p -> Arrays.stream(scores)
                        .boxed()
                        .filter(s -> s >= p.fst && s <= p.snd)
                        .count()
                )
                .mapToInt(Long::intValue)
                .toArray();
    }

    public static void main(String[] args) throws Exception {
        PsychometricTesting psychometricTesting = new PsychometricTesting();
        System.out.println(Arrays.toString(psychometricTesting.jobOffers(new int[]{1, 3, 5, 6, 8}, new int[]{2}, new int[]{6})));
        System.out.println(Arrays.toString(psychometricTesting.jobOffers(new int[]{4, 7, 8}, new int[]{2, 4}, new int[]{8, 4})));
    }
}
