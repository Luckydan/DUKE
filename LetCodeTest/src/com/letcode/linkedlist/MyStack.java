package com.letcode.linkedlist;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 通过队列实现栈的功能
 *
 * @author gl  @time 2019年5月26日 16点39分
 * @version  1.0.0
 */
public class MyStack {

    public Queue<Integer> q1 = null;
    public Queue<Integer> q2 = null;

    /** Initialize your data structure here. */
    public MyStack() {
        q1 = new LinkedList<Integer>();
        q2 = new LinkedList<Integer>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        q1.add(x);
        while(!q2.isEmpty()){
            q1.add(q2.poll());
        }
        while(!q1.isEmpty()){
            q2.add(q1.poll());
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return q2.poll();
    }

    /** Get the top element. */
    public int top() {
        return q2.peek();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return  q2.isEmpty() && q1.isEmpty();
    }
}
