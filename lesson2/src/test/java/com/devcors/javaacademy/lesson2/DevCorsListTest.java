package com.devcors.javaacademy.lesson2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DevCorsListTest {

    private static final String TEST_1 = "Test1";
    private static final String TEST_2 = "Test2";
    private static final String TEST_3 = "Test3";
    private static final String TEST_4 = "Test4";

    DevCorsList<String> devCorsList;

    @BeforeEach
    void setUp() {
        devCorsList = new DevCorsListImpl<>();
    }

    @Test
    void emptyListSizeTest() {
        assertNotNull(devCorsList);
        assertEquals(0, devCorsList.size());
    }

    @Test
    void emptyListIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> devCorsList.get(-1));
        assertThrows(IllegalArgumentException.class, () -> devCorsList.remove(-1));
    }

    @Test
    void emptyListIndexOutOfBoundsExceptionTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> devCorsList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> devCorsList.remove(1));
    }

    @Test
    void addAndGetTest() {
        devCorsList.add(TEST_1);
        devCorsList.add(TEST_2);
        assertEquals(2, devCorsList.size());
        assertEquals(TEST_1, devCorsList.get(0));
        assertEquals(TEST_2, devCorsList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> devCorsList.get(2));
    }

    @Test
    void addAndRemoveTest() {
        devCorsList.add(TEST_1);
        devCorsList.add(TEST_2);
        devCorsList.add(TEST_3);
        devCorsList.add(TEST_4);
        assertEquals(4, devCorsList.size());
        devCorsList.remove(1);
        assertEquals(TEST_1, devCorsList.get(0));
        assertEquals(TEST_3, devCorsList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> devCorsList.get(3));
        devCorsList.remove(2);
        assertThrows(IndexOutOfBoundsException.class, () -> devCorsList.get(2));
        assertEquals(2, devCorsList.size());
    }

}
