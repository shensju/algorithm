package com.shensju.tree;

/**
 * @Author: shensju
 * @Date: 2021/5/30 22:43
 * 二叉排序树（Binary Sort Tree）
 *     一棵空树，或者是具有下列性质的二叉树：
 *         1）若左子树不为空，则左子树上所有结点的值均小于它的根结点的值；
 *         2）若右子树不为空，则右子树上所有结点的值均大于它的根结点的值；
 *         3）左、右子树也分别为二叉排序树；
 *         4）没有键值相等的结点；
 */
public class BSTree<E> {

    private static class TreeNode<E> {
        private E data;
        private TreeNode<E> left;
        private TreeNode<E> right;

        public TreeNode() {
        }

        public TreeNode(E data) {
            this.data = data;
        }

        public TreeNode(E data, TreeNode<E> left, TreeNode<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private TreeNode<E> root;

    public BSTree() {
    }

    public BSTree(TreeNode<E> root) {
        this.root = root;
    }


}

