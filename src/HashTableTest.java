import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {
    HashTable test1;
    HashTable test2;
    HashTable test3;

    @Before
    public void hash() {
        test1 = new HashTable(6);
        test2 = new HashTable();
        test3 = new HashTable(18);
    }
    @Test
    public void hashConstructor1() {
        assertEquals(0, test2.size());
        assertEquals(15, test2.capacity());
        assertEquals("", test2.getStatsLog());
    }

    @Test
    public void hashConstructor2() {
        assertEquals(0, test1.size());
        assertEquals(0, test3.size());
        assertEquals(6, test1.capacity());
        assertEquals(18, test3.capacity());
        assertEquals("", test1.getStatsLog());
        assertEquals("", test3.getStatsLog());
    }

    @Test(expected = IllegalArgumentException.class)
    public void hashConstructorException() {
        HashTable test4 = new HashTable(4);
    }

    @Test
    public void insertTest() {
        test1.insert("hi!");
        assertEquals("[null, hi!, null, null, null, null]", test1.toString());
        test1.insert("1");
        test1.insert("2");
        test1.insert("3");
        test1.insert("4");
        assertEquals(12, test1.capacity());
        assertEquals("[null, hi!, 1, 2, 3, 4, null, null, null, null, null, null]", test1.toString());
        test2.insert("1");
        test2.insert("2");
        assertEquals(2, test2.size());
        test3.insert("1");
        assertFalse(test3.insert("1"));

    }

    @Test (expected = NullPointerException.class)
    public void insertTestException() {
        test1.insert(null);
    }

    @Test
    public void deleteTest() {
        test1.insert("1");
        assertEquals(1, test1.size());
        test1.delete("1");
        assertEquals(0, test1.size());
        test2.insert("ceci");
        assertTrue(test2.lookup("ceci"));
        test2.delete("ceci");
        assertEquals(0, test1.size());
    }

    @Test (expected = NullPointerException.class)
    public void deleteException() {
        test2.delete(null);
    }

    @Test
    public void lookupTest() {
        test1.insert("ceci");
        test1.insert("lailai");
        test1.insert("kk");
        assertTrue(test1.lookup("ceci"));
        assertTrue(test1.lookup("lailai"));
        assertTrue(test1.lookup("kk"));
        assertFalse(test1.lookup("aha"));
        test2.insert("jj");
        assertTrue(test2.lookup("jj"));
    }

    @Test (expected = NullPointerException.class)
    public void lookupException() {
        test1.insert("ceci");
        test1.lookup(null);
    }


    @Test
    public void sizeTest() {
        assertEquals(0, test1.size());
        assertEquals(0, test2.size());
        assertEquals(0, test3.size());
        test1.insert("a");
        test1.insert("b");
        test1.insert("c");
        assertEquals(3, test1.size());
        test1.delete("a");
        assertEquals(2, test1.size());
        test3.insert("1");
        assertEquals(1, test3.size());
    }

    @Test
    public void capacityTest() {
        assertEquals(6, test1.capacity());
        assertEquals(15, test2.capacity());
        assertEquals(18, test3.capacity());
        test1.insert("1");
        test1.insert("2");
        test1.insert("3");
        assertEquals(6, test1.capacity());
        test1.insert("4");
        test1.insert("5");
        assertEquals(12, test1.capacity());
        test2.insert("1");
        assertEquals(15, test2.capacity());

    }

    @Test
    public void getStatsLogTest() {
        assertEquals("", test1.getStatsLog());
        test1.insert("1");
        assertEquals("", test1.getStatsLog());
        test1.insert("2");
        test1.insert("3");
        test1.insert("4");
        assertEquals("", test1.getStatsLog());
        test1.insert("5");
        assertEquals("Before rehash # 1: load factor 0.67, 0 collision(s).\n", test1.getStatsLog());
        test2.insert("2");
        assertEquals("", test2.getStatsLog());

    }

    @Test
    public void testToStringTest() {
        test1.insert("1");
        assertEquals("[null, 1, null, null, null, null]", test1.toString());
        test1.insert("2");
        assertEquals("[null, 1, 2, null, null, null]", test1.toString());
        test1.insert("3");
        assertEquals("[null, 1, 2, 3, null, null]", test1.toString());
        test2.insert("111");
        assertEquals("[null, 111, null, null, null, null, null, null, null, null, " +
                "null, null, null, null, null]", test2.toString());
        assertEquals("[null, null, null, null, null, null, null, null, " +
                "null, null, null, null, null, null, null, null, null, null]", test3.toString());
    }
}