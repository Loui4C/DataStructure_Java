package com.louischow.recite;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class TreeNode1 {
    /************************判断是否为完全二叉树(层次遍历，最佳版本)**********************************/
    public boolean isCompleteTree(TreeNode root) {
        if(root==null) return true;
        Deque<TreeNode> queue=new LinkedList<>();
        queue.addLast(root);
        boolean flag=false;
        while(!queue.isEmpty()){
            root=queue.removeFirst();
            if(flag&&root!=null) return false;
            if(root==null){
                flag=true;
                continue;
            }
            queue.addLast(root.left);
            queue.addLast(root.right);
        }
        return true;
    }
    /************************判断是否为完全二叉树(层次遍历，思路一样写法不同)**********************************/
    public boolean isCompleteTree1(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        boolean flag = true;
        while(!deque.isEmpty()){
            TreeNode node = deque.pollLast();
            if(node == null){
                flag = false;
            }
            else{
                // 如果在非空节点前出现了空节点那么则为false
                if(!flag){
                    return false;
                }
                deque.addFirst(node.left);
                deque.addFirst(node.right);
            }
        }
        return true;
    }
    /************************已知前序中序求二叉树(递归，无优化)**********************************/
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return bulid(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
        //这个先写，你传入的参数肯定就是这几个
    }

    public TreeNode bulid(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        //最后写递归出口，就是两个数组之一越界（其实写1个也行，因为两个数组长度肯定相同的）
        if (preStart > preEnd || inStart > inEnd) return null;

        //1.根节点的值定为先序遍历数组的第一个
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);
        //2.递归构造左子树
        // root.left = bulid(preorder, ?, ?, inorder, ?, ?);//？需要我们画图去求的，也就是左右子树的索引
        // root.right = bulid(preorder, ?, ?, inorder, ?, ?);//？需要我们画图去求的，也就是左右子树的索引
        //首先通过rootVal在inorder中的索引（index），然后就能够知道inorder中左子树和右子树的边界
        //可以改进的，一开始用hashMap把inorder的值和索引存好，到时候直接查就行。
        int index = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (rootVal == inorder[i]) {
                index = i;
                break;
            }
        }
        //找到了index，确定inorder中左右子树的边界
        // root.left = bulid(preorder, ?, ?, inorder, inStart, index - 1);//确定inorder中左子树的边界
        // root.right = bulid(preorder, ?, ?, inorder, index + 1, inEnd);//确定inorder中右子树的边界
        //通过inorder中左子树节点的数目来确定preorder中左右子树的边界
        int nums_of_left_tree = index - inStart;
        // root.left = bulid(preorder, preStart + 1, preStart + nums_of_left_tree, inorder, ?, ?);//确定preorder中左子树的边界
        // root.right = bulid(preorder, preStart + nums_of_left_tree + 1, preEnd, inorder, ?, ?);//确定preorder中右子树的边界
        //最终确认好preorder和inorder中的左右子树边界
        root.left = bulid(preorder, preStart + 1, preStart + nums_of_left_tree, inorder, inStart, index - 1);
        root.right = bulid(preorder, preStart + nums_of_left_tree + 1, preEnd, inorder, index + 1, inEnd);
        return root;
    }
    /************************优化，用一个HashMap把中序遍历数组的每个元素的值和下标存起来**********************************/
    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length, inorder, 0, inorder.length, map);
    }

    private TreeNode buildTreeHelper(int[] preorder, int p_start, int p_end, int[] inorder, int i_start, int i_end,
                                     HashMap<Integer, Integer> map) {
        if (p_start == p_end) {
            return null;
        }
        int root_val = preorder[p_start];
        TreeNode root = new TreeNode(root_val);
        int i_root_index = map.get(root_val);
        int leftNum = i_root_index - i_start;
        root.left = buildTreeHelper(preorder, p_start + 1, p_start + leftNum + 1, inorder, i_start, i_root_index, map);
        root.right = buildTreeHelper(preorder, p_start + leftNum + 1, p_end, inorder, i_root_index + 1, i_end, map);
        return root;
    }
    /************************已知后序中序求二叉树(递归，无优化)**********************************/
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        return buildTreeHelper(inorder,0,inorder.length-1,postorder,0,postorder.length-1);
    }

    public TreeNode buildTreeHelper(int[] inorder,int inStart,int inEnd,int[] postorder,int postStart,int postEnd){
        if(inStart>inEnd||postStart>postEnd) return null;
        int rootval=postorder[postEnd];
        TreeNode root=new TreeNode(rootval);
        int index=0;
        for(int i=0;i<inorder.length;i++){
            if(rootval==inorder[i]){
                index=i;
                break;
            }
        }
        int treeNodeNum=index-inStart;
        root.left=buildTreeHelper(inorder,inStart,index-1,postorder,postStart,postStart+treeNodeNum-1);
        root.right=buildTreeHelper(inorder,index+1,inEnd,postorder,postStart+treeNodeNum,postEnd-1);
        return root;
    }

}
