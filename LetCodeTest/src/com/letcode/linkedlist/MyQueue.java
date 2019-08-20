package com.letcode.linkedlist;

import java.util.Stack;

/**
 * 通过栈实现队列的功能
 *
 * @author gl @time 2019年5月26日 16点30分
 * @version 1.0.0
 */
public class MyQueue {

    public Stack<Integer> st1 = null;
    public Stack<Integer> st2 = null;

    /** Initialize your data structure here. */
    public MyQueue() {
        st1 = new Stack();
        st2 = new Stack();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        while(!st2.isEmpty()){
            st1.push(st2.pop());
        }
        st1.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        while(!st1.empty()){
            st2.push(st1.pop());
        }
        return st2.pop();
    }

    /** Get the front element. */
    public int peek() {
        while(!st1.empty()){
            st2.push(st1.pop());
        }
       return st2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return st1.empty() && st2.empty();
    }
}
