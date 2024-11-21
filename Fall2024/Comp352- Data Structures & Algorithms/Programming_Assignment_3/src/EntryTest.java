import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 

class EntryTest {

    @Test
    void testConstructorAndGetters() {
        Entry<String, Integer> entry = new Entry<>("Key1", 10);
        assertEquals("Key1", entry.getKey());
        assertEquals(10, entry.getValue());
    }

    @Test
    void testSetters() {
        Entry<String, Integer> entry = new Entry<>("Key1", 10);
        entry.setKey("Key2");
        entry.setValue(20);
        assertEquals("Key2", entry.getKey());
        assertEquals(20, entry.getValue());
    }

    @Test
    void testToString() {
        Entry<String, Integer> entry = new Entry<>("Key1", 10);
        assertEquals("Entry{key=Key1, value=10}", entry.toString());
    }

    @Test
    void testEqualsSameObject() {
        Entry<String, Integer> entry = new Entry<>("Key1", 10);
        assertTrue(entry.equals(entry)); // Same reference
    }

    @Test
    void testEqualsDifferentObject() {
        Entry<String, Integer> entry1 = new Entry<>("Key1", 10);
        Entry<String, Integer> entry2 = new Entry<>("Key1", 10);
        assertTrue(entry1.equals(entry2)); // Same content
    }

    @Test
    void testEqualsWithDifferentKey() {
        Entry<String, Integer> entry1 = new Entry<>("Key1", 10);
        Entry<String, Integer> entry2 = new Entry<>("Key2", 10);
        assertFalse(entry1.equals(entry2)); // Different key
    }

    @Test
    void testEqualsWithDifferentValue() {
        Entry<String, Integer> entry1 = new Entry<>("Key1", 10);
        Entry<String, Integer> entry2 = new Entry<>("Key1", 20);
        assertFalse(entry1.equals(entry2)); // Different value
    }

    @Test
    void testEqualsWithNull() {
        Entry<String, Integer> entry = new Entry<>("Key1", 10);
        assertFalse(entry.equals(null)); // Null comparison
    }

    @Test
    void testEqualsWithDifferentClass() {
        Entry<String, Integer> entry = new Entry<>("Key1", 10);
        assertFalse(entry.equals("NotAnEntry")); // Different class
    }
}
