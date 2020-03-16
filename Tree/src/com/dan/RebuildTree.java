package com.dan;

import java.util.HashMap;
import java.util.Map;

/**
 * Letcode-105
 * 根据前序遍历和中序遍历的结果，构造出原来的二叉树
 * 分析：先序遍历先确定根节点，然后通过中序遍历确定左右子树，最后通过递归即可恢复一颗二叉树
 * 难点：在判断root.right的前序遍历起始位置时，只能使用prestart+numleft+1,而不能使用inRoot+1,因为当该节点为叶子节点时，会出现差异。
 */
public class RebuildTree {

    public static  TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) return null;
        HashMap<Integer, Integer> treeMap = new HashMap<>();
        for (int i = 0; i < preorder.length; i++) {
            treeMap.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length - 1, inorder,0,inorder.length-1, treeMap);
    }

    public static TreeNode buildTreeHelper(int[] preorder, int prestart, int preend, int[] inorder, int instart,int inend, Map<Integer, Integer> map) {
        if (prestart > preend) return null;
        TreeNode root = new TreeNode(preorder[prestart]);
        int inRoot = map.get(root.val);
        int numsLeft = inRoot - instart;
        System.out.println(prestart + numsLeft + 1);
        System.out.println(inRoot + 1);
        System.out.println(prestart + numsLeft + 1 == inRoot + 1);
        root.left = buildTreeHelper(preorder, prestart+1, prestart + numsLeft,
                inorder,instart,inRoot-1, map);
        root.right = buildTreeHelper(preorder,prestart+numsLeft+1, preend, inorder,inRoot+1,inend, map);
        return root;
    }

    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9,3,15,20,7};
        TreeNode root = buildTree(preorder,inorder);
        System.out.println(root.val);
    }
}
