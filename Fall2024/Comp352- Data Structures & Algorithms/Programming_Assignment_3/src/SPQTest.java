import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SPQTest {

    @Test
    void testInsertAndTop() {
        // Test inserting a single element and retrieving the top element
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        assertEquals(10, spq.top().getValue());
    }

    @Test
    void testInsertWithMultipleElements() {
        // Test inserting multiple elements and ensure Min-Heap property is maintained
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 5);
        spq.insert("C", 20);
        assertEquals(5, spq.top().getValue());
    }

    @Test
    void testRemoveTop() {
        // Test removing the top element and ensure the heap restructures correctly
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 5);
        spq.insert("C", 20);
        assertEquals(5, spq.removeTop().getValue());
        assertEquals(10, spq.top().getValue());
    }

    @Test
    void testRemoveSpecificEntry() {
        // Test removing a specific entry and ensure the heap property is restored
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 5);
        spq.insert("C", 20);
        Entry<String, Integer> entryToRemove = new Entry<>("A", 10);

        Entry<String, Integer> removedEntry = spq.remove(entryToRemove);

        assertEquals("A", removedEntry.getKey());
        assertEquals(10, removedEntry.getValue());
        assertEquals(5, spq.top().getValue()); // Ensure the top is updated
    }

    @Test
    void testRemoveNonExistentEntry() {
        // Test removing an entry that does not exist in the heap
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);

        Entry<String, Integer> nonExistentEntry = new Entry<>("X", 30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            spq.remove(nonExistentEntry);
        });

        assertTrue(exception.getMessage().contains("Entry not found"));
    }

    @Test
    void testRemoveAllEntries() {
        // Test removing all entries from the heap one by one
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);
        spq.insert("C", 5);

        spq.removeTop();
        spq.removeTop();
        spq.removeTop();

        assertTrue(spq.isEmpty()); // Ensure the heap is empty
    }

    @Test
    void testToggleHeapState() {
        // Test toggling from Min-Heap to Max-Heap and back
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);
        spq.insert("C", 5);

        spq.toggle(); // Max-Heap
        assertEquals(20, spq.top().getValue());

        spq.toggle(); // Min-Heap
        assertEquals(5, spq.top().getValue());
    }

    @Test
    void testReplaceValue() {
        // Test replacing the value of an entry and ensuring the heap property is maintained
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);

        Entry<String, Integer> entry = spq.top(); // Entry with key "A" and value 10
        spq.replaceValue(entry, 5);

        assertEquals(5, spq.top().getValue()); // Updated top value
    }

    @Test
void testReplaceKey() {
    // Test replacing the key of an entry
    SPQ spq = new SPQ();
    spq.insert("A", 10);
    spq.insert("B", 20);

    Entry<String, Integer> entry = spq.top(); // Entry with key "A" and value 10
    String oldKey = spq.replaceKey(entry, "Z");

    assertEquals("A", oldKey); // Ensure the old key is returned
    assertEquals("Z", spq.top().getKey()); // Ensure the key is updated
}


    @Test
    void testReplaceKeyHeapRebalancing() {
        // Test replacing a key and ensure the heap property is maintained
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);
        spq.insert("C", 5);

        Entry<String, Integer> entry = spq.top(); // Entry with key "C" and value 5
        spq.replaceKey(entry, "Z");

        assertEquals(5, spq.top().getValue()); // Ensure the top value remains unchanged
    }

    @Test
    void testReplaceKeyNonExistentEntry() {
        // Test replacing the key of a non-existent entry
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);

        Entry<String, Integer> nonExistentEntry = new Entry<>("X", 50);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            spq.replaceKey(nonExistentEntry, "Z");
        });

        assertTrue(exception.getMessage().contains("Entry not found"));
    }

    @Test
    void testArrayExtension() {
        // Test dynamic extension of the heap array
        SPQ spq = new SPQ();
        for (int i = 0; i < 10; i++) {
            spq.insert("Key" + i, i);
        }

        assertEquals(0, spq.top().getValue()); // Ensure Min-Heap property after extension
    }

    @Test
    void testSizeAndIsEmpty() {
        // Test size and isEmpty methods
        SPQ spq = new SPQ();
        assertTrue(spq.isEmpty());
        spq.insert("A", 10);
        spq.insert("B", 20);

        assertFalse(spq.isEmpty());
        assertEquals(2, spq.size());
    }

    @Test
    void testRebuildAfterToggle() {
        // Test rebuilding the heap after toggling from Min-Heap to Max-Heap
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("B", 20);
        spq.insert("C", 5);

        spq.toggle(); // Max-Heap
        assertEquals(20, spq.top().getValue());
    }

    @Test
    void testInsertDuplicateKeys() {
        // Test inserting duplicate keys and ensure the heap property is maintained
        SPQ spq = new SPQ();
        spq.insert("A", 10);
        spq.insert("A", 20);

        assertEquals(10, spq.top().getValue()); // Min-Heap with duplicate keys
    }

    @Test
    void testHeapifyLargeDataset() {
        // Test inserting a large dataset and validate heap integrity
        SPQ spq = new SPQ();
        for (int i = 100; i > 0; i--) {
            spq.insert("Key" + i, i);
        }
        assertEquals(1, spq.top().getValue()); // Min-Heap property
    }
}
