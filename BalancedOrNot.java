package com.company;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BalancedOrNot {

    private static boolean isOpenElement(String element) {
        return "<".equals(element);
    }

    private static boolean isCloseElement(String element) {
        return ">".equals(element);
    }

    private static int getBalanceFactor(String expression) {
        if (expression.length() < 0 && expression.length() > Math.pow(10L, 2L)) {
            return 0;
        }
        Stack<String> openElements = new Stack<>();
        Stack<String> closeElements = new Stack<>();
        new ArrayList<>(Arrays.asList(expression.split("")))
                .forEach(e -> {
                    if (isOpenElement(e)) {
                        openElements.push(e);
                    }

                    if (isCloseElement(e)) {
                        if (!openElements.empty()) {
                            openElements.pop();
                        } else {
                            closeElements.push(e);
                        }
                    }
                });
        return closeElements.size() + openElements.size();
    }

    private static boolean isValidLength(Integer number) {
        return BigInteger.valueOf(number).compareTo(BigInteger.valueOf(10).pow(5)) > 1;
    }

    public static int[] balancedOrNot(String[] expressions, int[] maxReplacements) throws Exception {
        if (isValidLength(expressions.length) || isValidLength(maxReplacements.length)) {
            throw new Exception("Bad input");
        }
        List<Integer> balanceFactors = Arrays
                .stream(expressions).map(BalancedOrNot::getBalanceFactor)
                .collect(Collectors.toList());

        return IntStream.range(0, balanceFactors.size()).boxed()
                .map(index -> maxReplacements[index] - balanceFactors.get(index) >= 0 ? 1 : 0)
                .mapToInt(i -> i)
                .toArray();

    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(balancedOrNot(new String[]{"<><", "<>><"}, new int[]{1, 0})));
    }
}
