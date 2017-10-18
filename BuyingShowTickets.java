package com.company;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.stream.IntStream.rangeClosed;

public class BuyingShowTickets {

    private static class MyNode<T> {
        private T element;
        private MyNode<T> next = null;
        private boolean head = false;

        public MyNode(T value) {
            this.element = value;
        }

        public void updateAll(Consumer<MyNode> operation) {
            if (!head) {
                operation.accept(this);
                this.next.updateAll(operation);
            }
        }

    }

    private static class CircularLinkedList {
        private MyNode head = null;
        private MyNode tail = null;

        public <T> void insert(T value) {
            MyNode<T> node = new MyNode<>(value);
            if (head == null) {
                head = node;
                head.head = true;
            } else if (tail == null) {
                tail = node;
                head.next = tail;
                tail.next = head;
            } else {
                tail.next = node;
                tail = tail.next;
                tail.next = head;
            }

        }

        public void visit(Consumer<MyNode> operation) {
            operation.accept(head);
            head.next.updateAll(operation);
            shiftHead();
        }

        public void shiftHead() {
            head.head = false;
            head = head.next;
            head.head = true;

        }

        public MyNode getHead() {
            return head;
        }
    }

    public static long buyingShowTickets(int[] tickets, int p) {
        CircularLinkedList customers = new CircularLinkedList();
        rangeClosed(1, tickets.length).boxed()
                .forEach(i -> {
                    HashMap<String, Object> customer = new HashMap<>();
                    customer.put("custId", i.toString());
                    customer.put("ticketCount", tickets[i - 1]);
                    customer.put("time", 0);
                    customers.insert(customer);
                });

        while (!(((Map) customers.getHead().element).get("custId").equals(String.valueOf(p)) &&
                ((Integer) ((Map) customers.getHead().element).get("ticketCount")) == 0)) {
            customers.visit((n) -> {
                if (customers.getHead() == n) {
                    if (((Integer) ((Map) n.element).get("ticketCount")) > 0) {
                        ((Map) n.element).put("ticketCount", ((Integer) ((Map) n.element).get("ticketCount")) - 1);
                        ((Map) n.element).put("time", ((Integer) ((Map) n.element).get("time")) + 1);
                    }
                } else {
                    if (((Integer) ((Map) n.element).get("ticketCount")) > 0) {
                        ((Map) n.element).put("time", ((Integer) ((Map) n.element).get("time")) + 1);
                    }
                }

            });
        }
        return (Integer) ((Map) customers.getHead().element).get("time") - 1;

    }

    public static void main(String[] args) {
        System.out.println(buyingShowTickets(new int[]{5, 5, 2, 3}, 4));
    }
}
