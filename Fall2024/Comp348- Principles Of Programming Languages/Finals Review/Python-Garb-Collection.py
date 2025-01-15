import gc
import sys
import time

gc.set_debug(True)

class Link:
    def __init__(self, next_link, value):
        self.next_link = next_link
        self.value = value
        
    def __repr__(self) -> str:
        return self.value
    
link = Link(None, "Main Link")

myList=[]

start = time.perf_counter()

for i in range(5):
    l_temp = Link(link, "L")
    myList.append(l_temp)
    
end = time.perf_counter() 

print(end-start)   