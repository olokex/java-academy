package com.devcors.javaacademy.lesson2;

/*
* maybe private members and add methods set/get
* */
public class Node<T> {
    public T value;
    public Node next;

    public Node(T value) {
        this.value = value;
    }
}
