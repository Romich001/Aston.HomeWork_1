package aston.intensive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
class ArrayList_RomanVoytyukTest {

    private IntensiveList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList_RomanVoytyuk<>();
    }

    @Test
    void size() {
        assertEquals(0, list.size());
        list.add(1);
        assertEquals(1, list.size());
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    void add() {
        list.add(1);
        assertEquals(1, list.get(0));
    }

    @Test
    void shouldListGrowDynamically() {
        //capacity is 10, so add 11 elements
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
        assertEquals(11, list.size());
        assertEquals(10, list.get(10));
    }

    @Test
    void addWithIndex() {
        list.add(1);
        list.add(3);
        list.add(1, 2);
        assertEquals(2, list.get(1));
    }

    @Test
    void get() {
        list.add(1);
        assertEquals(1, list.get(0));
    }

    @Test
    void set() {
        list.add(1);
        list.set(0, 2);
        assertEquals(2, list.get(0));
    }

    @Test
    void remove() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(3, list.get(1));
        assertEquals(1, list.get(0));

    }

    @Test
    void removeFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(0);
        assertEquals(2, list.size());
        assertEquals(2, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void removeLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(2);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void removeOnly() {
        list.add(1);
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    void clear() {
        list.add(1);
        list.clear();
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {list.get(0);});
    }

    @Test
    void quickSort() {
        list.add(3);
        list.add(2);
        list.add(1);
        list.quickSort(Comparator.naturalOrder());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void isSorted() {
        list.add(3);
        list.add(2);
        list.add(1);
        assertFalse(list.isSorted());
        list.quickSort(Comparator.naturalOrder());
        assertTrue(list.isSorted());
        list.add(4);
        assertFalse(list.isSorted());
        list.quickSort(Comparator.naturalOrder());
        assertTrue(list.isSorted());



    }

    @Test
    void isNotSortedWhenAddElement() {
        list.add(3);
        list.add(2);
        list.add(1);
        list.quickSort(Comparator.naturalOrder());
        list.add(4);
        assertFalse(list.isSorted());
    }

    @Test
    void isNotSortedWhenSetElement() {
        list.add(3);
        list.add(2);
        list.add(1);
        list.quickSort(Comparator.naturalOrder());
        list.set(2, 4);
        assertFalse(list.isSorted());
    }

    @Test
    void isNotSortedWhenRemoveElement() {
        list.add(3);
        list.add(2);
        list.add(1);
        list.quickSort(Comparator.naturalOrder());
        list.remove(2);
        assertFalse(list.isSorted());
    }

    @Test
    void split() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.split(2);
        assertEquals(2, list.size());
    }
}