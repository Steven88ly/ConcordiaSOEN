public class SPQ {

    private HeapState heapState; // Current state of the heap (Min-Heap or Max-Heap)
    private Entry<String, Integer>[] heap; // Array to store heap entries
    private int size; // Number of elements in the heap
    private int max_size = 5; // Initial capacity of the heap

    // Constructor to initialize the heap with a default size and set it as a Min-Heap
    @SuppressWarnings("unchecked")
    public SPQ() {
        this.heapState = HeapState.Min; // Default to Min-Heap
        this.heap = new Entry[max_size]; // Initialize the heap array
        this.size = 0; // Start with an empty heap
    }

    // Dynamically extends the heap array when the size exceeds the capacity
    private void extendArray() {
        this.max_size += 2; // Increase capacity by 2
        @SuppressWarnings("unchecked")
        Entry<String, Integer>[] extendedHeap = new Entry[this.max_size];
        System.arraycopy(this.heap, 0, extendedHeap, 0, this.size); // Copy valid entries to the new array
        this.heap = extendedHeap; // Update the heap reference
    }

    // Toggles the heap state between Min-Heap and Max-Heap and rebuilds the heap
    public void toggle() {
        this.heapState = this.heapState == HeapState.Min ? HeapState.Max : HeapState.Min; // Switch state
        rebuild(); // Rebuild the heap based on the new state
    }

    // Rebuilds the heap efficiently by heapifying all parent nodes
    private void rebuild() {
        for (int i = this.size / 2 - 1; i >= 0; i--) {
            downheap(i); // Apply downheap from the last parent node to the root
        }
    }

    // Moves an element up the heap to restore the heap property
    private void upheap(int index) {
        while (index > 0) {
            int parentIndex = parentIndex(index); // Get parent index
            if (parentIndex == -1 || !compare(index, parentIndex)) break; // Stop if heap property is valid

            swap(index, parentIndex); // Swap with parent
            index = parentIndex; // Update index to parent
        }
    }

    // Moves an element down the heap to restore the heap property
    private void downheap(int index) {
        while (hasLeftChild(index)) {
            int leftChildIndex = leftChildIndex(index);
            int rightChildIndex = rightChildIndex(index);
            int selectedChildIndex = leftChildIndex; // Start with left child

            // Choose the child to compare based on heap state and availability
            if (hasRightChild(index) && compare(rightChildIndex, leftChildIndex)) {
                selectedChildIndex = rightChildIndex;
            }

            // Stop if heap property is valid
            if (!compare(selectedChildIndex, index)) break;

            swap(index, selectedChildIndex); // Swap with the selected child
            index = selectedChildIndex; // Update index to the selected child
        }
    }

    // Compares two indices based on the heap state (Min-Heap or Max-Heap)
    private boolean compare(int index1, int index2) {
        if (!isValid(index1) || !isValid(index2)) {
            throw new IllegalArgumentException("Invalid indices for comparison.");
        }
        int value1 = this.heap[index1].getValue();
        int value2 = this.heap[index2].getValue();
        return this.heapState == HeapState.Min ? value1 < value2 : value1 > value2;
    }

    // Removes and returns the top element of the heap (min or max based on state)
    public Entry<String, Integer> removeTop() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Heap is empty.");
        }

        Entry<String, Integer> top = this.heap[0]; // Store the top element
        this.heap[0] = this.heap[--this.size]; // Replace top with the last element
        this.heap[this.size] = null; // Avoid memory leaks
        downheap(0); // Restore heap property

        return top; // Return the removed top element
    }

    public Entry<String, Integer> remove(Entry<String, Integer> e) {
        // Step 1: Search for the entry in the heap
        int targetIndex = -1; // Index of the entry to be removed
        for (int i = 0; i < this.size; i++) {
            if (this.heap[i].equals(e)) {
                targetIndex = i;
                break;
            }
        }
    
        // Step 2: If the entry is not found, throw an exception
        if (targetIndex == -1) {
            throw new IllegalArgumentException("Entry not found in the heap.");
        }
    
        // Step 3: Store the entry to be removed for return
        Entry<String, Integer> removedEntry = this.heap[targetIndex];
    
        // Step 4: Replace the target entry with the last element in the heap
        this.heap[targetIndex] = this.heap[--this.size];
        this.heap[this.size] = null; // Avoid memory leaks
    
        // Step 5: Restore the heap property
        if (targetIndex < this.size) { // Only if the replacement index is valid
            int parentIdx = parentIndex(targetIndex);
            if (parentIdx >= 0 && compare(targetIndex, parentIdx)) {
                upheap(targetIndex); // Move the element up if necessary
            } else {
                downheap(targetIndex); // Move the element down if necessary
            }
        }
    
        // Step 6: Return the removed entry
        return removedEntry;
    }
    

    // Inserts a new key-value pair into the heap
    public void insert(String k, Integer v) {
        if (this.size >= this.max_size) {
            extendArray(); // Extend heap array if necessary
        }

        this.heap[this.size] = new Entry<>(k, v); // Add the new entry
        upheap(this.size++); // Restore heap property by moving up
    }

    // Returns the top element of the heap without removing it
    public Entry<String, Integer> top() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Heap is empty.");
        }
        return this.heap[0]; // Return the top element
    }

    // Replaces the value of an existing entry and restores the heap property
    public Integer replaceValue(Entry<String, Integer> entry, Integer v) {
        if (v == null) {
            throw new IllegalArgumentException("Illegal Null Value");
        }

        for (int i = 0; i < this.size; i++) {
            if (this.heap[i] != null && this.heap[i].equals(entry)) {
                Integer oldValue = this.heap[i].getValue(); // Store the old value
                this.heap[i].setValue(v); // Update with the new value
    
                // Decide whether to upheap or downheap
                int parentIdx = parentIndex(i);
                if (parentIdx >= 0 && compare(i, parentIdx)) {
                    upheap(i); // Restore heap by moving up
                } else {
                    downheap(i); // Restore heap by moving down
                }
    
                return oldValue; // Return the replaced value
            }
        }
    
        throw new IllegalArgumentException("Entry not found."); // Throw exception if entry is not found
    }

    public String replaceKey(Entry<String, Integer> entry, String newKey) {
        for (int i = 0; i < this.size; i++) {
            if (this.heap[i] != null && this.heap[i].equals(entry)) {
                String oldKey = this.heap[i].getKey(); // Store the old key
                this.heap[i].setKey(newKey); // Replace with the new key
                return oldKey; // Return the old key
            }
        }
    
        throw new IllegalArgumentException("Entry not found in the heap."); // If the entry is not found
    }
    
    

    // Checks if the heap is empty
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Returns the current size of the heap
    public int size() {
        return this.size;
    }

    // Helper method to get the left child index
    private int leftChildIndex(int index) {
        int left = (index * 2) + 1;
        return left < this.size ? left : -1;
    }

    // Helper method to get the right child index
    private int rightChildIndex(int index) {
        int right = (index * 2) + 2;
        return right < this.size ? right : -1;
    }

    // Helper method to get the parent index
    private int parentIndex(int index) {
        return index > 0 ? (index - 1) / 2 : -1;
    }

    // Checks if a node has a left child
    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) != -1;
    }

    // Checks if a node has a right child
    private boolean hasRightChild(int index) {
        return rightChildIndex(index) != -1;
    }

    // Validates if an index is within bounds
    private boolean isValid(int index) {
        return index >= 0 && index < this.size;
    }

    // Swaps two entries in the heap
    private void swap(int index1, int index2) {
        Entry<String, Integer> temp = this.heap[index1];
        this.heap[index1] = this.heap[index2];
        this.heap[index2] = temp;
    }
}
