package com.letcode.linkedlist;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class QueueTest {

    /**
     * 获取固定大小k的滑窗中最大的数,并返回最大数组成的数组
     * LetCodeNo:239
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums==null || k>nums.length || k<0) return null;
        if(k==0 || nums.length==0) return new int[0];
        Deque<Integer> dq = new ArrayDeque<>();
        int[] res = new int[nums.length-k+1];
        int index = 0; // index in r is equal to the left side of window
        for(int i=0; i<nums.length; i++){
            // remove elements out of window
            while(!dq.isEmpty() && dq.peek() < i-k+1){
                dq.poll();
            }
            // remove smaller elements, which have no change to become max
            while(!dq.isEmpty() && nums[dq.peekLast()]<nums[i]){
                dq.pollLast();
            }
            dq.offer(i);
            if(i >= k - 1) res[index++] = nums[dq.peek()];
        }
        return res;
    }
}
