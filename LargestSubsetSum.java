package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LargestSubsetSum {

    private static int gcd(int number1, int number2) {
        return (number1 % number2 == 0) ? number2 : gcd(number2, number1 % number2);
    }

    private static int lcm(int number1, int number2) {
        return (number1 * number2) / gcd(number1, number2);
    }

    private static int lcmList(List<Integer> numberList) {
        return numberList
                .stream()
                .reduce(1, LargestSubsetSum::lcm);
    }

    public static <T> ArrayList<ArrayList<T>> powerSet(List<T> list) {

        ArrayList<ArrayList<T>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (T i : list) {
            ArrayList<ArrayList<T>> temp = new ArrayList<>();

            for (ArrayList<T> innerList : result) {
                innerList = new ArrayList<>(innerList);
                innerList.add(i);
                temp.add(innerList);
            }
            result.addAll(temp);
        }

        return result;
    }

    public static long[] maxSubsetSum(int[] k) {
        return Arrays.stream(k)
                .boxed()
                .map(x -> powerSet(IntStream.rangeClosed(1, x).boxed().collect(Collectors.toList()))
                        .stream()
                        .filter(aSet -> lcmList(aSet) == x)
                        .max((l1, l2) -> Integer.compare(l1.stream().reduce(0, (n1, n2) -> n1 + n2),
                                l2.stream().reduce(0, (n1, n2) -> n1 + n2)))
                        .get()
                )
                .map(x -> x.stream().reduce(0, (n1, n2) -> n1 + n2).longValue())
                .mapToLong(l -> l)
                .toArray();
    }

    public static void main(String[] args) {
        System.out.println(powerSet(Arrays.asList(1, 2, 3, 4)));
        System.out.println(lcmList(Arrays.asList(2, 4, 8, 16)));
        System.out.println(Arrays.toString(maxSubsetSum(new int[]{2, 4})));
    }
}
