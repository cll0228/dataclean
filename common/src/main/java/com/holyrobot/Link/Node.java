package com.holyrobot.Link;

/**
 * Created by cuill on 2018/4/18.
 */
public class Node {
    public long data;//数据域
    public Node next;//指針域
    public Node(long value){
        this.data = value;
    }

    /*
    显示方法
     */
    public void display(){
        System.out.println(data+" ");
    }

}
