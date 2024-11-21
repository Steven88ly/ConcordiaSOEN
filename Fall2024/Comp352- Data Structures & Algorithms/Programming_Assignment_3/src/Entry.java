public class Entry<K,V>{
    private K key; 
    private V value;

    public Entry(K key, V value){
        this.key = key;
        this.value = value;
    }

    public void setKey(K newKey){
        this.key = newKey;
    }

    public K getKey(){ return this.key; }
        
    public void setValue(V newValue){ this.value = newValue; }

    public V getValue(){ return this.value; }

    @Override
    public String toString() {
        return "Entry{" + "key=" + key + ", value=" + value + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) obj;
        return key.equals(entry.key) && value.equals(entry.value);
    }
}
