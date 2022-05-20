package ru.vsu.cs.eliseev;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleQueueTest {
    @Test
    public void test1() {
        SimpleQueue<Double> test = new SimpleQueue<>();

        test.add(20.0);
        test.add(30.8);
        test.add(40.8);
        test.add(50.45);
        test.add(30.8);
        test.add(40.8);
        test.add(50.45);

        Double expected = 20.0;
        assertEquals(expected, test.poll());
    }

    @Test
    public void test2() {
        SimpleQueue<Double> test2 = new SimpleQueue<>();

        test2.add(20.0);

        Double expected = 20.0;
        assertEquals(expected, test2.poll());
    }

    @Test
    public void test3() {
        SimpleQueue<Double> test3 = new SimpleQueue<>();

        assertNull(test3.poll());
    }

}