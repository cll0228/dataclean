package com.holyrobot.tree;

/**
 * Created by cuill on 2018/4/18.
 */
public class TestTree {
    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(15);
        tree.insert(3);
        System.out.println(tree);
    }
}
