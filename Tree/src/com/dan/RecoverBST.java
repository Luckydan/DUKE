package com.dan;

import java.util.HashMap;

/**
 * 在不改动BST的结构条件下，修复BST
 * BST介绍：
 * 1.若BST的左子树不为空，则左子树上的所有节点都小于根节点
 * 2.若BST的右子树不为空，则右子树上的所有节点都大于根节点
 * 3.左右子树都为二叉排序树
 */
public class RecoverBST {

    public void recoverTree(TreeNode root) {
       TreeNode prev = root.left;
    }

    public void traverse(TreeNode root){
        if(root == null){
            return;
        }
        traverse(root.left);
        if(root.val < root.left.val){

        }
        traverse(root.right);
    }


    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("str",null);
        System.out.println(map.get("str").toString());
    }
    
}
