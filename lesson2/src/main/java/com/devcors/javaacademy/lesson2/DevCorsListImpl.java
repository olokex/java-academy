package com.devcors.javaacademy.lesson2;
import java.util.Iterator;

public final class DevCorsListImpl<T> implements DevCorsList<T>, Iterable<T>  {

    private int size;
    private Node first;
    private Node last;
    public DevCorsListImpl() {
        this.size = 0;
    }

    @Override
    public void add(T element) {
        Node node = new Node(element);

        if (size == 0) {
            first = node;
        } else {
            last.next = node;
        }

        last = node;
        size++;
    }

    @Override
    public T get(int i) {
        checkBoundaries(i);

        Node current = first;
        for (int j = 0; j < i; j++) {
            current = current.next;
        }

        return (T) current.value;
    }

    @Override
    public void remove(int i) {
        checkBoundaries(i);

        if (i == 0) {
            first = first.next;
        } else {
            Node current = first;
            for (int j = 0; j < i - 1; j++) {
                current = current.next;
            }
            current.next = current.next.next;
            if (i == size - 1) {
                last = current;
            }
        }

        size--;
    }

    @Override
    public int size() {
        return this.size;
    }

    private void checkBoundaries(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }

        if (i >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DevCorsListIterator();
    }

    private class DevCorsListIterator implements Iterator<T> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T value = (T) current.value;
            current = current.next;
            return value;
        }
    }
}
