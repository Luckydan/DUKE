package com.letcode.tree;

import java.util.HashMap;
import java.util.HashSet;

public class LetCode {
    public static void main(String[] args) {
        // 测试twoSum
        //int[] nums ={2,7,11,15};
        //int target =9;
        //int[] aim =twoSum(nums, target);
        //int[] aim2 =twoSum2(nums,target);
        //System.out.println(aim2[0]+" "+aim2[1]);

        // 测试reverse
        //int x = 123;
        //int reverse = reverse(x);
        //System.out.println(reverse);

        // 测试Palindrome
        //System.out.println(isPalindrome(230));

        // 测试最长前缀
        //String[] strs = {"c"};
        //System.out.println(longestCommonPrefix(strs));

        // 测试isValid

        // 测试Jewels In Stones
        //String J = "aA";
        //String S = "aAAzzsff";
        //int t = numJewelsInStones(J,S);
        //System.out.println("Jewles num is :" +t );

        // 测试Max Increase to Keep City Skyline
        // [[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]
        //int[][] grid={{3,0,8,4},{2,4,5,7},{9,2,6,3},{0,3,1,0}};
        //int res = maxIncreaseKeepingSkyline(grid);
        //System.out.println("res:"+res);

        // 测试 toLowerCase() 方法

        //String s = toLowerCase(J);
        //System.out.println(s);

        System.out.println((int)(Math.random()*10));

        //System.out.println(Character.MAX_HIGH_SURROGATE);
    }


    // 求两数相加为目标值的索引 方法-
    public static int[] twoSum(int[] nums, int target) {
        int[] aims = new int[2];
        for (int i=0; i < nums.length; i++){
            for (int j =0; j<nums.length; j++){
                if (nums[j] == target - nums[i] &&  i != j ){
                    aims[0]=i;
                    aims[1]=j;
                    return aims;
                }
            }
        }
        return null;
    }

    // 求两束相加为目标值索引 ---方法二
    public static int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(integerIntegerHashMap.get(target - nums[i]) != null){
                int[] result = {integerIntegerHashMap.get(target - nums[i]),i};
                return result;
            }
            integerIntegerHashMap.put(nums[i], i);
        }
        return null;
    }

    // 对指定的整数进行反转操作
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

    // 判断指定整数是否时回文数
    public static boolean isPalindrome(int x) {
        String str = x + "";
        String strReverse = new StringBuffer(str).reverse().toString();
        System.out.println(strReverse);
        if(str.equals(strReverse)){
            return true;
        }
        return false;
    }

    // 获取字符串数组中公共的最长前缀字符串
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String aim = strs[0];
        int j = 1;
        String str = "";
        while (j <= aim.length()){
            for (int i =0; i < strs.length; i++){
                if (strs[i].startsWith(aim.substring(0,j))){
                    if(i==strs.length-1) {
                        str = str + aim.substring(j - 1, j);
                    }
                }else {
                    return str;
                }
            }
            j++;
        }
        return str;
    }

    // 获取字符串数组中公共的最长前缀字符串 -------方法二
    public static String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        return prefix;
    }

    public static boolean isValid(String s) {
        HashMap<Character, Character> hashMap = new HashMap<Character, Character>();
        hashMap.put('[',']');
        hashMap.put('(',')');
        hashMap.put('{', '}');
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++){
            Character character = new Character(chars[i]);
            //Character character1 = new Character(chars[chars.length - i - 1]);
            if (hashMap.containsKey(character)){
                return true;
            }
        }
        return false;
    }

    // Jewels In Stones
    public static int numJewelsInStones(String J, String S) {
        if(J.length() > 50 || S.length() > 50) return 0;
        char[] jewels = J.toCharArray();
        char[] stones = S.toCharArray();
        int t =0;
        HashSet jewelsSet =new HashSet<>();
        for (int i = 0; i < jewels.length; i++){
            jewelsSet.add(jewels[i]);
            for ( int j = 0; j < stones.length; j++){
                if (jewels[i] == stones[j]){
                    t ++;
                }
            }
        }
        if (jewelsSet.size() < jewels.length) return 0;
        return t;
    }

    // Max Increase to Keep City Skyline
    public static  int maxIncreaseKeepingSkyline(int[][] grid) {
        int n = grid.length;
        int[] col = new int[n], row = new int[n];
        //int[][] grid={{3,0,8,4},{2,4,5,7},{9,2,6,3},{0,3,1,0}};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                row[i] = Math.max(row[i], grid[i][j]);
                col[j] = Math.max(col[j], grid[i][j]);// 3,0,8,4  // 3,4,8,7 // 9,4,8,7 // 9,4,8,7
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                res += Math.min(row[i], col[j]) - grid[i][j];
                // res +=
        return res;
    }

    // implement the fuction of toLowerCase()
    public static String toLowerCase(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++){
            if (chars[i] >='A' && chars[i] <= 'Z'){
                char temp = (char)(chars[i] - 'A' + 'a');
                chars[i] = temp;
            }
        }
        return new String(chars);


    }
}

