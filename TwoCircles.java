package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TwoCircles {

    private class Pair<T>{
        private T fst;

        public Pair(T fst, T snd) {
            this.fst = fst;
            this.snd = snd;
        }

        private T snd;
    }

    private class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double distance(Point other) {
            return Math.sqrt(Math.pow((this.x - other.x), 2) + Math.pow((this.y - other.y), 2));
        }


        public boolean isEqual(Point other) {
            return this.x == other.x && this.y == other.y;
        }
    }

    private class Circle{
        private Point center;
        private int r;

        public Circle(int x, int y, int r) {
            this.center = new Point(x, y);
            this.r = r;
        }

        public boolean touching(Circle other) {
            return this.center.distance(other.center) == (this.r + other.r);
        }

        public boolean concentric(Circle other) {
            return this.center.isEqual(other.center);
        }

        public boolean intersecting(Circle other) {
            return Math.pow((this.center.x - other.center.x), 2) +
                    Math.pow((this.center.y - other.center.y), 2) <= Math.pow((this.r + other.r), 2);
        }

        public boolean disjointOutSide(Circle other) {
            return this.center.distance(other.center) > (this.r + other.r);
        }

        public boolean disjointInSide(Circle other) {
            return this.center.distance(other.center) < Math.abs(this.r + other.r);
        }

        public String relation(Circle other) {
            if(this.touching(other)) {
                return "Touching";
            } else if(this.concentric(other)) {
                return "Concentric";
            } else if(this.intersecting(other)) {
                return "Intersecting";
            } else if(this.disjointInSide(other)) {
                return "DisjointInSide";
            } else if(this.disjointOutSide(other)) {
                return "DisjointOutSide";
            }
            return "";
        }
    }

    public String[] circles(String[] info) {
        List<String> input = new ArrayList<>();
        Collections.addAll(input, info);
        return input.stream()
                .map(inputRow -> {
                    String[] split = inputRow.split(" ");
                    return new Pair<>(new Circle(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2])),
                            new Circle(Integer.valueOf(split[3]), Integer.valueOf(split[4]), Integer.valueOf(split[5])));
                })
                .map(pair -> pair.fst.relation(pair.snd))
                .collect(Collectors.toList())
                .toArray(new String[input.size()]);

    }

    public static void main(String[] args) {
        TwoCircles twoCircles = new TwoCircles();
        System.out.println(Arrays.toString(
                twoCircles.circles(new String[]{"12 0 21 14 0 23", "0 45 8 0 94 9", "35 0 13 10 0 38", "0 26 8 0 9 25"})));
    }

}
