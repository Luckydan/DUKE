package com.dan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 在不改动BST的结构条件下，修复BST
 * BST介绍：
 * 1.若BST的左子树不为空，则左子树上的所有节点都小于根节点
 * 2.若BST的右子树不为空，则右子树上的所有节点都大于根节点
 * 3.左右子树都为二叉排序树
 * 思路：
 * 一个合格的搜索二叉树经过中序遍历得到的序列一定是一个升序序列,将顺序不一致的记录并交换即可
 * @author gl
 */
public class RecoverBST {
    public static ArrayList<Integer> treeValList = new ArrayList<>();
    public static TreeNode prev = null;
    public static TreeNode firstNode = null;
    public static TreeNode secondNode = null;

    public static void recoverTree(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root);
        if (firstNode != null && secondNode != null) {
            int temp = firstNode.val;
            firstNode.val = secondNode.val;
            secondNode.val = temp;
        }
    }

    public static void traverse(TreeNode root) {
        if (root.left != null) {
            traverse(root.left);
        }
        if (prev != null && prev.val > root.val) {
            if (firstNode == null) {
                firstNode = prev;
            }
            if (firstNode != null) {
                secondNode = root;
            }
        }
        prev = root;
        if (root.right != null) {
            traverse(root.right);
        }
    }

    /**
     * 中序遍历二叉树
     *
     * @param node
     */
    public static void traversalBST(TreeNode node) {
        if (node == null) {
            return;
        }
        traversalBST(node.left);
        treeValList.add(node.val);
        traversalBST(node.right);
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        TreeNode nodeL = new TreeNode(3);
        TreeNode nodeR = new TreeNode(4);
        TreeNode nodeRL = new TreeNode(2);
        TreeNode nodeRR = new TreeNode(15);
        node.left = nodeL;
        node.right = nodeR;
        nodeR.left = nodeRL;
        nodeR.right = nodeRR;

        recoverTree(node);
        traversalBST(node);
        System.out.println(treeValList);
    }

}
