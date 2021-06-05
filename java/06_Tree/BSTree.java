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
     * 按前序遍历，将对象数组构建为一棵二叉排序树
     * @param datas 对象数组
     */
    public void buildTree(E[] datas) {
        for (int i = 0; i < datas.length; i++)
            add(datas[i]);
    }

    /**
     * 将数据元素为 data 的结点插入到二叉排序树中
     * @param data 待插入的数据元素
     * @return 如果插入成功，返回 true；否则返回 false
     */
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
     * 将数据元素为 data 的结点从二叉排序树中删除
     * @param data 待删除的数据元素
     * @return 如果删除成功，返回 true；否则返回 false
     */
    public boolean remove(E data) {
        // 查找待删除的数据元素所在的结点
        TreeNode<E> removeNode = get(data);
        if (removeNode == null) {
            return false;
        }
        /**
         * 1）待删除结点的左孩子结点不存在；
         * 2）待删除结点的右孩子结点不存在；
         * 3）待删除结点的左右孩子结点都存在；
         */
        if (removeNode.getLeft() == null) {
            transplant(removeNode, removeNode.getRight());
            // 将待删除结点的右孩子结点置为空，便于JVM垃圾回收
            removeNode.setRight(null);
        } else if (removeNode.getRight() == null) {
            transplant(removeNode, removeNode.getLeft());
            // 将待删除结点的左孩子结点置为空，便于JVM垃圾回收
            removeNode.setLeft(null);
        } else {
            // 将待删除结点右子树中的最小结点作为后继结点
            TreeNode<E> successor = getMin(removeNode.getRight());
            // 获取后继结点的父结点
            TreeNode<E> parentNode = getParent(successor);
            // 当后继结点的父结点不是待删除结点
            if (parentNode != removeNode) {
                transplant(successor, successor.getRight());
                successor.setRight(removeNode.getRight());
            }
            transplant(removeNode, successor);
            successor.setLeft(removeNode.getLeft());
            // 将待删除结点的左右孩子结点置为空，便于JVM垃圾回收
            removeNode.setLeft(null);
            removeNode.setRight(null);
        }
        return true;
    }

    /**
     * 获取二叉排序树中数据元素等于 data 的结点
     * @param data 待查找的数据元素
     * @return 如果存在数据元素等于 data 的结点，返回该结点；否则返回 null
     */
    public TreeNode<E> get(E data) {
        TreeNode<E> current = root;
        while (current != null && data.compareTo(current.getData()) != 0) {
            if (data.compareTo(current.getData()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return current;
    }

    /**
     * 获取二叉排序树中数据元素最小的结点
     * @return 返回二叉排序树中数据元素最小的结点
     */
    public TreeNode<E> getMin() {
        return getMin(root);
    }

    /**
     * 获取二叉排序树中数据元素最小的结点
     * @param treeNode 待查找的二叉排序树的根结点
     * @return 返回二叉排序树中数据元素最小的结点
     */
    private TreeNode<E> getMin(TreeNode<E> treeNode) {
        while (treeNode.getLeft() != null) {
            treeNode = treeNode.getLeft();
        }
        return treeNode;
    }

    /**
     * 获取二叉排序树中数据元素最大的结点
     * @return 返回二叉排序树中数据元素最大的结点
     */
    public TreeNode<E> getMax() {
        return getMax(root);
    }

    /**
     * 获取二叉排序树中数据元素最大的结点
     * @param treeNode 待查找的二叉排序树的根结点
     * @return 返回二叉排序树中数据元素最大的结点
     */
    private TreeNode<E> getMax(TreeNode<E> treeNode) {
        while (treeNode.getRight() != null) {
            treeNode = treeNode.getRight();
        }
        return treeNode;
    }

    /**
     * 获取待查找结点在二叉排序树中的父节点
     * @param treeNode 待查找结点
     * @return 返回待查找结点在二叉排序树中的父节点
     */
    public TreeNode<E> getParent(TreeNode<E> treeNode) {
        TreeNode<E> parentNode = null;
        TreeNode<E> currentNode = root;
        while (treeNode.getData().compareTo(currentNode.getData()) != 0) {
            parentNode = currentNode;
            if (treeNode.getData().compareTo(currentNode.getData()) < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }
        return parentNode;
    }

    /**
     * 使用新结点替换老结点
     * @param oldNode 老结点
     * @param newNode 新结点
     */
    private void transplant(TreeNode<E> oldNode, TreeNode<E> newNode) {
        if (oldNode == root) {
            root = newNode;
            return;
        }
        // 判断老结点属于左孩子结点还是右孩子结点
        TreeNode<E> parantNode = null;
        TreeNode<E> currentNode = root;
        boolean isLeft = false;
        while (oldNode.getData().compareTo(currentNode.getData()) != 0) {
            parantNode = currentNode;
            if (oldNode.getData().compareTo(currentNode.getData()) < 0) {
                isLeft = true;
                currentNode = currentNode.getLeft();
            } else {
                isLeft = false;
                currentNode = currentNode.getRight();
            }
        }
        // 判断老结点属于左孩子结点还是右孩子结点
        if (isLeft) {
            parantNode.setLeft(newNode);
        } else {
            parantNode.setRight(newNode);
        }
    }

    /**
     * 前序遍历（递归）
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
     * @param root 根结点
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
        Integer[] datas = new Integer[] {15, 15, 6, 3, 2, 4, 7, 13, 9, 18, 17, 16, 20};
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
        System.out.println("--------------------------------------------------");
        System.out.print("中序遍历（递归）：");
        bsTree.inOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.print("中序遍历（非递归）：");
        bsTree.inOrderTraverseNRecur(bsTree.getRoot());
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.print("后序遍历（递归）：");
        bsTree.postOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.print("后序遍历（非递归）1：");
        bsTree.postOrderTraverseNRecur1(bsTree.getRoot());
        System.out.println();
        System.out.print("后序遍历（非递归）2：");
        bsTree.postOrderTraverseNRecur2(bsTree.getRoot());
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.print("层序遍历：");
        bsTree.levelOrderTraverse(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("二叉排序树的最大值为：" + bsTree.getMax());
        System.out.println("二叉排序树的最小值为：" + bsTree.getMin());
        System.out.println("------------------------------------------------------------");
        System.out.println("数据元素为 7 的结点的父结点信息：" + bsTree.getParent(bsTree.get(7)));
        System.out.println("数据元素为 7 的结点的信息：" + bsTree.get(7));
        System.out.println("删除数据元素为 7 的结点，是否删除成功：" + bsTree.remove(7));
        System.out.print("删除数据元素为 7 的结点后，中序遍历：");
        bsTree.inOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("数据元素为 17 的结点的信息：" + bsTree.get(17));
        System.out.println("删除数据元素为 17 的结点，是否删除成功：" + bsTree.remove(17));
        System.out.print("删除数据元素为 17 的结点后，中序遍历：");
        bsTree.inOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("数据元素为 18 的结点的信息：" + bsTree.get(18));
        System.out.println("删除数据元素为 18 的结点，是否删除成功：" + bsTree.remove(18));
        System.out.print("删除数据元素为 18 的结点后，中序遍历：");
        bsTree.inOrderTraverseRecur(bsTree.getRoot());
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("数据元素为 16 的结点的信息：" + bsTree.get(16));
        System.out.println("删除数据元素为 16 的结点，是否删除成功：" + bsTree.remove(16));
        System.out.print("删除数据元素为 16 的结点后，中序遍历：");
        bsTree.inOrderTraverseRecur(bsTree.getRoot());
        System.out.println();

        /**
         * 前序遍历（递归）：15 6 3 2 4 7 13 9 18 17 16 20 
         * 前序遍历（非递归）1：15 6 3 2 4 7 13 9 18 17 16 20 
         * 前序遍历（非递归）2：15 6 3 2 4 7 13 9 18 17 16 20 
         * --------------------------------------------------
         * 中序遍历（递归）：2 3 4 6 7 9 13 15 16 17 18 20 
         * 中序遍历（非递归）：2 3 4 6 7 9 13 15 16 17 18 20 
         * --------------------------------------------------
         * 后序遍历（递归）：2 4 3 9 13 7 6 16 17 20 18 15 
         * 后序遍历（非递归）1：2 4 3 9 13 7 6 16 17 20 18 15 
         * 后序遍历（非递归）2：2 4 3 9 13 7 6 16 17 20 18 15 
         * --------------------------------------------------
         * 层序遍历：15 6 18 3 7 17 20 2 4 13 16 9 
         * ------------------------------------------------------------
         * 二叉排序树的最大值为：TreeNode{data=20, left=null, right=null}
         * 二叉排序树的最小值为：TreeNode{data=2, left=null, right=null}
         * ------------------------------------------------------------
         * 数据元素为 7 的结点的父结点信息：TreeNode{data=6, left=3, right=7}
         * 数据元素为 7 的结点的信息：TreeNode{data=7, left=null, right=13}
         * 删除数据元素为 7 的结点，是否删除成功：true
         * 删除数据元素为 7 的结点后，中序遍历：2 3 4 6 9 13 15 16 17 18 20 
         * ------------------------------------------------------------
         * 数据元素为 17 的结点的信息：TreeNode{data=17, left=16, right=null}
         * 删除数据元素为 17 的结点，是否删除成功：true
         * 删除数据元素为 17 的结点后，中序遍历：2 3 4 6 9 13 15 16 18 20 
         * ------------------------------------------------------------
         * 数据元素为 18 的结点的信息：TreeNode{data=18, left=16, right=20}
         * 删除数据元素为 18 的结点，是否删除成功：true
         * 删除数据元素为 18 的结点后，中序遍历：2 3 4 6 9 13 15 16 20 
         * ------------------------------------------------------------
         * 数据元素为 16 的结点的信息：TreeNode{data=16, left=null, right=null}
         * 删除数据元素为 16 的结点，是否删除成功：true
         * 删除数据元素为 16 的结点后，中序遍历：2 3 4 6 9 13 15 20 
         */
    }
}
