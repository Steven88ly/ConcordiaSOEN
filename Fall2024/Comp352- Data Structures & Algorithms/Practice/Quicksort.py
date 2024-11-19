arr = [4,8,1,9,2,3,7,8,4,3]

def quicksort(array, start, end):
    if (end <= start ):
        return
    
    pivot = partition(array, start, end)
    
    quicksort(array, start, pivot - 1)
    quicksort(array, pivot + 1, end)

def partition(array, start, end):
    pivot = array[end]
    i = start - 1
    
    for j in range(start, end):
        if(array[j] < pivot):
            i += 1
            temp = array[j]
            array[j] = array[i]
            array[i] = temp
    i += 1  
    temp = array[i]
    array[i] = array[end]
    array[end] = temp
    
    return i

quicksort(arr, 0, len(arr) - 1)

print(arr)