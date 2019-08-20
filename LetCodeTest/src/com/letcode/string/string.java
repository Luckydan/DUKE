package com.letcode.string;

import sun.rmi.runtime.Log;

public class string {

    public static void main(String[] args){
        String testStr = "abcdefgafbc";
        System.out.println(getLongestString(testStr));
    }


    public static int getLongestString(String orign){
        int startLength = orign.length();
        int longest = -1;
        int start = 0;
        for(int i = 1; i < startLength; i++){
            char tmep = orign.charAt(i);
            for(int j = i;j > start; j--){
                if(tmep == orign.charAt(j -1 )){
                   longest = ((i-start) > longest) ? (i - start): longest;
                   start = j;
                }
            }
        }
        longest = (longest > (startLength - start)) ? longest : (startLength - start);
        return longest;
    }
}
