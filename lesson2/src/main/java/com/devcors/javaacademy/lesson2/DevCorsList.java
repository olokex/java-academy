package com.devcors.javaacademy.lesson2;

/**
 * An interface that defines the operations that each list implementation must complete
 */
public interface DevCorsList<T> {

    /**
     * Insert element at the end of collection
     *
     * @param element - element for insertion
     */
    void add(T element);

    /**
     * Returns element on index i
     * When index is lower than zero throw {@link IllegalArgumentException}
     * When index is grater than collection size throw {@link IndexOutOfBoundsException}
     *
     * @param i index of element
     * @return element on index i
     */
    T get(int i);

    /**
     * Delete element on index i
     * When index is lower than zero throw {@link IllegalArgumentException}
     * When index is grater than collection size throw {@link IndexOutOfBoundsException}
     *
     * @param i index od deleted element
     */
    void remove(int i);

    /**
     * Returns collection size
     *
     * @return collection size
     */
    int size();
}