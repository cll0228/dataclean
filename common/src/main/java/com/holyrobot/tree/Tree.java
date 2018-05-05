package com.holyrobot.tree;

/**
 * Created by cuill on 2018/4/18.
 */
public class Tree {
    //根结点
    public Node root;

    /**
     * 插入节点
     */
    public void insert(long value) {
        //封装节点
        Node newNode = new Node(value);
        //引用當前結點
        Node current = root;
        //引用父节点
        Node parent;
        if (root == null) {
            root = newNode;
            return;
        } else {
            while (true) {
                //父節點指向當前節點
                parent = current;
                if (current.data > value) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                } else {
                    current = current.rigthChild;
                    if (current == null) {
                        parent.rigthChild = newNode;
                        return;
                    }
                }
            }

        }
    }
}
