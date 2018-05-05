package com.holyrobot.Link;

/**
 * Created by cuill on 2018/4/18.
 */
public class LinkList {
    //头结点
    private Node first;

    public LinkList(){
        first = null;
    }

    /**
     * 插入一个头结点
     *
     */
    public void insertFirst(long value){
        Node node= new Node(value);
        node.next = first;
        first = node;
    }

    /**
     * 顯示方法
     */
    public void display(){
        Node current = first;
        while (current!= null){
            current.display();
            current = current.next;
        }
    }
}
