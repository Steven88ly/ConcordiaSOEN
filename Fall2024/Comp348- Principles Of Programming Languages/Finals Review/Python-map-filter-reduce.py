from functools import reduce

nums = list([2,4,5,7,8,10]) 

def square(x):
    return x*x

squared_list = list(map(square,nums))

print("squared List: ")
print (", ".join(map(str,squared_list)))

nums_greater_10 = list(filter(lambda x: x > 10, squared_list))
print("nums greater than 10: ")
print(", ".join(map(str, nums_greater_10)))

sum = reduce(lambda x, y: x+y, nums_greater_10)

print(f"sum: {sum}")