package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;
import java.util.ArrayDeque;
import java.util.Deque;

public class ReverseStack implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : input.toCharArray()) {
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder(input.length());
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }
}
