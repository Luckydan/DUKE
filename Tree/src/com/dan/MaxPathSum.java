package com.dan;

/**
 * letCode-124
 * 可以从任意节点开始，每个节点最多经过一次，问经过的节点的和最大是多少。
 */
public class MaxPathSum {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        helper(root);
        return max;
    }

    int helper(TreeNode root){
        if(root == null) return 0;
        int left = Math.max(helper(root.left), 0);
        int right = Math.max(helper(root.right),0);
        max = Math.max(max,left + right + root.val);
        return Math.max(left,right) + root.val;
    }
}
