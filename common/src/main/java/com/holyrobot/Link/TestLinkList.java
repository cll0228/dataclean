package com.holyrobot.Link;

/**
 * Created by cuill on 2018/4/18.
 */
public class TestLinkList {
    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        linkList.insertFirst(11);
        linkList.insertFirst(33);
        linkList.insertFirst(45);
        linkList.insertFirst(55);
        linkList.display();
    }
}
