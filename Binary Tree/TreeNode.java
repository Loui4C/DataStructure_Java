package com.louischow.recite;

import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /************************前序遍历迭代算法（简单易懂版本）**********************************/
    class Solution {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) return result;
            Deque<TreeNode> deque = new LinkedList<>();
            deque.addFirst(root);
            TreeNode cur = root;
            while (!deque.isEmpty()) {
                cur = deque.removeFirst();
                result.add(cur.val);
                if (cur.right != null) deque.addFirst(cur.right);
                if (cur.left != null) deque.addFirst(cur.left);
            }
            return result;
        }
    }

    /************************前序遍历迭代算法（双while统一风格版本）**********************************/
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) return res;
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();//回溯
            root = root.right;
        }
        return res;
    }

    /************************中序遍历迭代算法（双while统一风格版本）**********************************/
    public List<Integer> inorderTraversal(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        while (root != null || !deque.isEmpty()) {
            while (root != null) {
                deque.addFirst(root);
                root = root.left;
            }
            root = deque.removeFirst();//回溯
            result.add(root.val);
            root = root.right;
        }
        return result;
    }

    /************************后序遍历迭代算法（双while统一风格版本）**********************************/
    public List<Integer> postorderTraversal(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        TreeNode pre = null;//pre记录上一次遍历的结点(是否是右子树)
        while (root != null || !deque.isEmpty()) {
            while (root != null) {
                deque.push(root);
                root = root.left;
            }
            root = deque.removeFirst();
            //需要判断是从左子树到的根节点，还是右子树到的根节点。
            if (root.right == null || pre == root.right) {
                result.add(root.val);
                pre = root;
                root = null;
            } else {
                deque.push(root);
                root = root.right;
            }
        }
        return result;
    }

    /************************层次遍历队列迭代法**********************************/
    public List<List<Integer>> levelOrder(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (root == null) return result;
        deque.addLast(root);
        while (!deque.isEmpty()) {
            int len = deque.size();
            List<Integer> level = new ArrayList<>();
            while (len > 0) {
                TreeNode temp = deque.removeFirst();
                level.add(temp.val);
                if (temp.left != null) deque.addLast(temp.left);
                if (temp.right != null) deque.addLast(temp.right);
                len--;
            }
            result.add(level);
        }
        return result;
    }



}
