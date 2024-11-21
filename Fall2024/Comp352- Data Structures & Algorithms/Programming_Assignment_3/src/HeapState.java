public enum HeapState {
    Min, Max;

    public HeapState toggle(){
        return this == Min ? Max : Min;
    }
}