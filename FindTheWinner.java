package com.company;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindTheWinner {
    private static Map<String, Function<Integer, Boolean>> gameType = new HashMap<String, Function<Integer, Boolean>>(){{
        put("Even", (n)-> n%2==0);
        put("Odd", (n)-> n%2!=0);
    }};

    private static boolean inRange(BigInteger number) {
        return number.compareTo(BigInteger.valueOf(1)) >= 0 && number.compareTo(BigInteger.valueOf(10).pow(3)) <= 0;
    }

    private static Map<String, Integer> createPlayerList() {
        HashMap<String, Integer> players = new HashMap<>();
        players.put("andrea", 0);
        players.put("maria", 0);
        return players;
    }

    public static String winner(int[] andrea, int[] maria, String s) {

        Function<Integer, Boolean> gameType = FindTheWinner.gameType.get(s);
        Map<String, Integer> players = createPlayerList();

        IntStream.range(0, andrea.length).boxed()
                .forEach(i -> {
                    if(!inRange(BigInteger.valueOf(andrea[i])) || !inRange(BigInteger.valueOf(maria[i]))) {
                        try {
                            throw new Exception("Out of range");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    if(gameType.apply(i)){
                        players.put("andrea", andrea[i] - maria[i]);
                        players.put("maria", maria[i] - andrea[i]);
                    }
                });

//        return players.get("andrea") > players.get("maria") ? "andrea" : "maria";
        return players.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toList())
                .get(0)
                .getKey();

    }

    public static void main(String[] args) {
        System.out.println(winner(new int[] {1, 2, 3}, new int[] {2, 1, 3}, "Odd"));
        System.out.println(winner(new int[] {1, 2, 3}, new int[] {2, 1, 3}, "Even"));
    }
}
