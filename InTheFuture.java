package com.company;

public class InTheFuture {

    private static boolean isValidVelocity(int velocity) {
        return velocity >= 1 && velocity <= 100;
    }

    private static boolean isValidLead(int lead) {
        return lead >=0 && lead <=100;
    }

    public static int minNum(int A, int K, int P) {
        if(!isValidVelocity(A) && !isValidVelocity(K) && isValidLead(P)) {
            return 0;
        }
        double daysRequiredToOverCome = Math.floor((double) P / (K - A)) + 1;

        return new Double(daysRequiredToOverCome).intValue();
    }

    public static void main(String[] args) {
        System.out.println(InTheFuture.minNum(3, 5, 1));
        System.out.println(InTheFuture.minNum(4, 5, 1));
    }

}
