package com.letcode.linkedlist;

import java.util.HashSet;

public class ArrayCode {

    public static void main(String[] args){
        int[] nums = {0,1,1,2,3,4,5};
        System.out.println("--------------------------------" +removeDuplicates(nums));
    }

    public static int removeDuplicates(int[] nums) {
        int len = nums.length;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] == nums[i+1]){
                len --;
            }
        }
        return len;

    }
}
