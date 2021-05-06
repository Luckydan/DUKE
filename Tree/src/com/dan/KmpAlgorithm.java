package com.dan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title:字符串匹配算法
 * @Description:
 * @Author: GL
 * @Date: 2021/4/29 16:06
 * @Version 1.0.0
 */
public class KmpAlgorithm {
    public static void main(String[] args) {
        String sourceStr = "SFAJDL;FAMDFAMD;FABCDABDDAF;DFAJSD";
        String targetStr = "ABCDABD";
        int[] next = getNext(targetStr);
        int index = KMP_index(sourceStr,targetStr,next);
        System.out.println(index);

    }

    /**
     * 通过KMP算法进行字符串匹配，返回匹配字符串在源字符串的起始索引
     * @param sourceStr 源字符串
     * @param targetStr 模式串
     * @param next 模式串的next数组
     * @return
     */
    public static int KMP_index(String sourceStr,String targetStr,int[] next){
        int i=0;
        int j=0;
        while(i < sourceStr.length()  && j < targetStr.length()) {
            if(j ==-1 || sourceStr.charAt(i) == targetStr.charAt(j)){
                i++;
                j++;
            }else{
                j=next[j];
            }
        }
        System.out.println("j="+j+",targetStr.length="+targetStr.length());
        if(j < targetStr.length()){
            return -1;
        }else{
            return i-targetStr.length();
        }
    }

    /**
     *  获取模式串的next数组:最长相同真前后缀长度值集合
     * @param modeStr 模式串
     * @return
     */
    public static int[] getNext(String modeStr){
        int len = modeStr.length();
        int[] next = new int[len+1];
        int k = -1;
        int j = 0;
        next[0] = -1;
        while(j < len){
            if(k == -1 || modeStr.charAt(j) == modeStr.charAt(k)){
                k++;
                j++;
                next[j] = k;
            }else{
                k = next[k];
            }
        }
        System.out.println(Arrays.toString(next));
        return next;
    }
}
