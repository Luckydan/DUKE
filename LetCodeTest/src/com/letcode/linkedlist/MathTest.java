package com.letcode.linkedlist;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.HashMap;

public class MathTest {

    public static void main(String[] args){
        System.out.println(reverse(Integer.MAX_VALUE /10));
    }

    /**
     * 对参数X进行反转，并且x 在int区间内
     * @param x
     * @return
     */
    public static int reverse(int x) {
        int result = 0;
        while (x != 0){
            int pop = x % 10;
            x /= 10;
            if ( result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7) ) return 0;
            if ( result < Integer.MIN_VALUE /10  || (result == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            result = result * 10 + pop;
        }
        return result;

    }

    // I V X  L  C   D    M
    // 1 5 10 50 100 500  1000
    public static int romanToInt(String s) {
        int result = 0;

        return result;
    }
}
