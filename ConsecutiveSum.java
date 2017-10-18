package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ConsecutiveSum {

    private static <T> List<List<T>> consecutive(List<T> numList, List<List<T>> result, Integer progress) {
        if (progress > numList.size()) {
            return result;
        }
        result.add(numList.subList(0, progress));
        return consecutive(numList, result, ++progress);
    }

    private static <T> List<List<T>> findAllConsecutive(List<T> numList, List<List<T>> result) {
        if (numList.size() == 0) {
            return result;
        }
        ArrayList<T> nums = new ArrayList<>();
        nums.addAll(numList);
        result.addAll(consecutive(nums, new ArrayList<>(), 0));
        numList.remove(0);
        return findAllConsecutive(numList, result);
    }


    public static int consecutive(long num) throws Exception {
        if (num < 0 || BigInteger.valueOf(num).compareTo(BigInteger.valueOf(10).pow(12)) > 0) {
            throw new Exception("Out of range");
        }
        return new Long(findAllConsecutive(LongStream.range(1, num).boxed().collect(Collectors.toList()), new ArrayList<>())
                .stream()
                .filter(conList -> conList.stream().reduce(0L, (n1, n2) -> n1 + n2) == num)
                .count()).intValue();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(consecutive(15));
        System.out.println(consecutive(10));
    }
}
