package com.letcode.sort;
import java.util.Arrays;
import java.util.List;

public class AllSortMethod {
    public static void main(String[] args){
        int[] array = {18,4,13,3,9,12};
        bubbleSort(array);
        System.out.println("--------------------------------" +Arrays.toString(sortArray(array)));
    }

    /**
     * 冒泡排序
     *
     * @param array
     * @return
     */
    public static void bubbleSort(int[] array){
        for (int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length - 1-i; j++){
                if(array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }

    /**
     * 对奇数和偶数个数相同的数组排序，数组长度满足 array.lenght % 2 ==0
     * 使得偶数索引下的元素为偶数，奇数索引下的元素为奇数。
     *
     * @param arr
     * @return
     */
    public static int[] sortArray(int[] arr){
        int[] temp = new int[arr.length];
        int i = 0;
        int j = 1;
        int right = arr.length - 2;
        while(i < arr.length){
            if(arr[i] % 2 == 0){
                temp[right] = arr[i++];
                right -= 2;
            }else{
                temp[j] = arr[i++];
                j += 2;
            }
        }
        return  temp;
    }

}
