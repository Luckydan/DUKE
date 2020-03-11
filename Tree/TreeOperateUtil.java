import java.util.Deque;
import java.util.LinkedList;

public class TreeOperateUtil{
	
	/* 定义二叉树 */
    static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
	
    public static void main(String args[]){
        int[] nums = {1,2,8,9,4,5,11,6};
        constructMaximumBinaryTree(nums);
    }

    /**
     * 在无序且不重复的数组中，以最大的为根节点，以最大值划分的左右子数组为新的数组，并以相同的
     * 规则产生左子树和右子树
     * @param nums
     * @return
     */
    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        for(int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            while(!stack.isEmpty() && stack.peek().val < nums[i]) {
                curr.left = stack.pop();
            }
            if(!stack.isEmpty()) {
                stack.peek().right = curr;
            }
            stack.push(curr);
        }
        return stack.isEmpty() ? null : stack.removeLast();
    }

    /**
     * 判断该二叉树中的值是否都相同
     * @param root
     * @return
     */
    public boolean isUnivaluedBinaryTree(TreeNode root ){
        if(root.left != null){
            if(!isUnivaluedBinaryTree(root.left)){
                return false;
            }
            if(root.left.val != root.val){
                return false;
            }
        }

        if(root.right != null){
            if(! isUnivaluedBinaryTree(root.right)){
                return false;
            }
            if(root.right.val != root.val){
                return false;
            }
        }
        return true;
    }

    /**
     * 将给定的值插入到二分查找树中，并返回最终的BST.
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root,int val){
        if(root == null){
            return new TreeNode(val);
        }
        TreeNode bst = root;
        while(true){
            if(bst.val <= val) {
                if (bst.right != null) {
                    bst = bst.right;
                } else {
                    bst.right = new TreeNode(val);
                    break;
                }
            }else{
                if(bst.left != null){
                    bst = bst.left;
                }else{
                    bst.left = new TreeNode(val);
                    break;
                }
            }
        }
        return root;
    }

    /**
     * 将二叉树t1 和t2合并，得到一颗新树
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    /**
     * 在二叉树中查找值为val的子树
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val ){return root;}
        if (root.val > val){
            return searchBST(root.left,val);
        }else if(root.val < val){
            return searchBST(root.right,val);
        }
        return null;
    }
}



