package com.shensju.tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

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
public class BSTree<E extends Comparable<E>> {

    /**
     * 内部结点类定义
     */
    private static class TreeNode<E extends Comparable<E>> {
        private E data; // 数据元素
        private TreeNode<E> left; // 左孩子结点
        private TreeNode<E> right; // 右孩子结点

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

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" + "data=" + data +
                    ", left=" + (left == null ? null : left.data) +
                    ", right=" + (right == null ? null : right.data) + '}';
        }
    }

    private TreeNode<E> root; // 根结点

    public TreeNode<E> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<E> root) {
        this.root = root;
    }

    public BSTree() {
    }

    public BSTree(TreeNode<E> root) {
        this.root = root;
    }

    /**
     * 将对象数组构建为一棵二叉排序树
     * @param datas 对象数组
     */
    public void buildTree(E[] datas) {
        for (int i = 0; i < datas.length; i++)
            add(datas[i]);
    }

//    public void add(E data) {
//        root = add(root, data);
//    }
//
//    private TreeNode<E> add(TreeNode<E> node, E data) {
//        if (node == null) {
//            node = new TreeNode<>(data);
//        } else {
//            if (data.compareTo(node.data) < 0) {
//                node.left = add(node.left, data);
//            } else if (data.compareTo(node.data) > 0) {
//                node.right = add(node.right, data);
//            } else {
//                return node;
//            }
//        }
//        return node;
//    }

    public boolean add(E data) {
        TreeNode<E> newNode = new TreeNode<>(data);
        TreeNode<E> parentNode = null;
        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            parentNode = currentNode;
            if (data.compareTo(currentNode.getData()) < 0) {
                currentNode = currentNode.getLeft();
            } else if (data.compareTo(currentNode.getData()) > 0) {
                currentNode = currentNode.getRight();
            } else {
                break;
            }
        }

        if (parentNode == null) {
            root = newNode;
        } else if (data.compareTo(parentNode.getData()) < 0) {
            parentNode.setLeft(newNode);
        } else if (data.compareTo(parentNode.getData()) > 0) {
            parentNode.setRight(newNode);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 前序遍历（递归）
     * @param root
     */
    public void preOrderTraverseRecur(TreeNode<E> root) {
        if (root == null) {
            return;
        } else {
            System.out.print(root.getData() + " ");
            preOrderTraverseRecur(root.getLeft());
            preOrderTraverseRecur(root.getRight());
        }
    }

    /**
     * 前序遍历（非递归）
     * 压入栈的是右孩子结点
     * @param root
     */
    public void preOrderTraverseNRecur1(TreeNode<E> root) {
        Stack<TreeNode<E>> stack = new Stack<>();
        stack.push(root);
        TreeNode<E> temp = null;
        while (!stack.isEmpty()) {
            temp = stack.pop();
            System.out.print(temp.getData() + " ");
            if (temp.getRight() != null) {
                stack.push(temp.getRight());
            }
            if (temp.getLeft() != null) {
                stack.push(temp.getLeft());
            }
        }
    }

    /**
     * 前序遍历（非递归）
     * 压入栈的是根结点，利用根结点来找右孩子结点
     * @param root
     */
    public void preOrderTraverseNRecur2(TreeNode<E> root) {
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> temp = root;
        while (!stack.isEmpty() || temp != null) {
            if (temp != null) {
                System.out.print(temp.getData() + " ");
                stack.push(temp);
                temp = temp.getLeft();
            } else {
                temp = stack.pop();
                temp = temp.getRight();
            }
        }
    }

    /**
     * 中序遍历（递归）
     * @param root
     */
    public void inOrderTraverseRecur(TreeNode<E> root) {
        if (root == null) {
            return;
        } else {
            inOrderTraverseRecur(root.getLeft());
            System.out.print(root.getData() + " ");
            inOrderTraverseRecur(root.getRight());
        }
    }

    /**
     * 中序遍历（非递归）
     * 压入栈的是根结点，利用根结点来找右孩子结点
     * @param root
     */
    public void inOrderTraverseNRecur(TreeNode<E> root) {
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> temp = root;
        while (!stack.isEmpty() || temp != null) {
            if (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            } else {
                temp = stack.pop();
                System.out.print(temp.getData() + " ");
                temp = temp.getRight();
            }
        }
    }

    /**
     * 后序遍历（递归）
     * @param root
     */
    public void postOrderTraverseRecur(TreeNode<E> root) {
        if (root == null) {
            return;
        } else {
            postOrderTraverseRecur(root.getLeft());
            postOrderTraverseRecur(root.getRight());
            System.out.print(root.getData() + " ");
        }
    }

    /**
     * 后序遍历（非递归）
     * @param root
     */
    public void postOrderTraverseNRecur1(TreeNode<E> root) {
        // 栈 stack1 存放结点
        Stack<TreeNode<E>> stack1 = new Stack<>();
        // 栈 stack2 用于记录从左子树返回还是从右子树返回
        Stack<Boolean> stack2 = new Stack<>();
        TreeNode<E> temp = root;
        Boolean flag;
        while (!stack1.isEmpty() || temp != null) {
            if (temp != null) {
                stack1.push(temp);
                stack2.push(false);
                temp = temp.getLeft();
            } else {
                temp = stack1.pop();
                flag = stack2.pop();
                if (!flag) { // false 代表从左子树返回，此时需要先到右子树，并且将 flag 改为 true
                    stack1.push(temp);
                    stack2.push(true);
                    temp = temp.getRight();
                } else { // 从右子树返回，则说明该结点的左右子树都遍历过了，直接访问结点
                    System.out.print(temp.getData() + " ");
                    // 手动置为空，避免出现死循环
                    temp = null;
                }
            }
        }
    }

    /**
     * 后序遍历（非递归）
     * @param root
     */
    public void postOrderTraverseNRecur2(TreeNode<E> root) {
        Stack<TreeNode<E>> stack1 = new Stack<>();
        Stack<TreeNode<E>> stack2 = new Stack<>();
        TreeNode<E> temp = null;
        stack1.push(root);
        while (!stack1.isEmpty()) {
            temp = stack1.pop();
            stack2.push(temp);
            if (temp.getLeft() != null) {
                stack1.push(temp.getLeft());
            }
            if (temp.getRight() != null) {
                stack1.push(temp.getRight());
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().getData() + " ");
        }
    }

    /**
     * 层次遍历
     * @param root
     */
    public void levelOrderTraverse(TreeNode<E> root) {
        Queue<TreeNode<E>> queue = new ArrayDeque<>();
        TreeNode<E> temp = null;
        queue.offer(root);
        while (!queue.isEmpty()) {
            temp = queue.poll();
            System.out.print(temp.getData() + " ");
            if (temp.getLeft() != null) {
                queue.offer(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.offer(temp.getRight());
            }
        }
    }

    public static void main(String[] args) {
        Integer[] datas = new Integer[] {5, 3, 1, 2, 4, 10, 8, 9};
        BSTree<Integer> bsTree = new BSTree<>();
        bsTree.buildTree(datas);
        System.out.print("前序遍历（递归）：");
        bsTree.preOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.print("前序遍历（非递归）1：");
        bsTree.preOrderTraverseNRecur1(bsTree.getRoot());
        System.out.println();
        System.out.print("前序遍历（非递归）2：");
        bsTree.preOrderTraverseNRecur2(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------");
        System.out.print("中序遍历（递归）：");
        bsTree.inOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.print("中序遍历（非递归）：");
        bsTree.inOrderTraverseNRecur(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------");
        System.out.print("后序遍历（递归）：");
        bsTree.postOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.print("后序遍历（非递归）1：");
        bsTree.postOrderTraverseNRecur1(bsTree.getRoot());
        System.out.println();
        System.out.print("后序遍历（非递归）2：");
        bsTree.postOrderTraverseNRecur2(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------");
        System.out.print("层序遍历：");
        bsTree.levelOrderTraverse(bsTree.getRoot());

        /**
         * 前序遍历（递归）：5 3 1 2 4 10 8 9
         * 前序遍历（非递归）1：5 3 1 2 4 10 8 9
         * 前序遍历（非递归）2：5 3 1 2 4 10 8 9
         * ------------------------------------
         * 中序遍历（递归）：1 2 3 4 5 8 9 10
         * 中序遍历（非递归）：1 2 3 4 5 8 9 10
         * ------------------------------------
         * 后序遍历（递归）：2 1 4 3 9 8 10 5
         * 后序遍历（非递归）1：2 1 4 3 9 8 10 5
         * 后序遍历（非递归）2：2 1 4 3 9 8 10 5
         * ------------------------------------
         * 层序遍历：5 3 10 1 4 8 2 9
         * Process finished with exit code 0
         */
    }
}
