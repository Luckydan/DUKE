package com.letcode.linkedlist;

import java.util.PriorityQueue;

/**
 * 获取流式数据中第k大的k个数据。(此处为数组nums  )
 *  LetCodeNo:703
 *
 * @author gl  @time 2019年5月27日 22点02分
 * @version 1.0.0
 */
public class KthLargest {
    final PriorityQueue<Integer> priorityQueue;
    final int k;
    public KthLargest(int k, int[] nums) {
         this.k = k;
         priorityQueue = new PriorityQueue<Integer>(k);
         for(int val : nums){
             add(val);
         }
    }

    public int add(int val) {
        if(priorityQueue.size() < k){
            priorityQueue.offer(val);
        }else if(priorityQueue.peek() < val){
            priorityQueue.poll();
            priorityQueue.offer(val);
        }
        return priorityQueue.peek();
    }

}
