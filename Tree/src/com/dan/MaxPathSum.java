package com.dan;

/**
 * letCode-124
 * 可以从任意节点开始，每个节点最多经过一次，问经过的节点的和最大是多少。
 * 此处的节点不单指一个完全的二级节点，也可以为多级节点。
 */
public class MaxPathSum {

    public static int max = Integer.MIN_VALUE;

    public static int maxPathSum(TreeNode root) {
        helper(root);
        return max;
    }

    static int helper(TreeNode root) {
        if (root == null) return 0;
        int left = Math.max(helper(root.left), 0);
        int right = Math.max(helper(root.right), 0);
        max = Math.max(max, left + right + root.val);
        System.out.println(Math.max(left, right) + root.val);
        return Math.max(left, right) + root.val;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(-1);
        TreeNode rootL = new TreeNode(9);
        TreeNode rootR = new TreeNode(20);
        TreeNode rootRL = new TreeNode(7);
        TreeNode rootRR = new TreeNode(15);
        root.left = rootL;
        root.right = rootR;
        rootR.left = rootRL;
        rootR.right = rootRR;
        System.out.println(maxPathSum(root));
    }
}
