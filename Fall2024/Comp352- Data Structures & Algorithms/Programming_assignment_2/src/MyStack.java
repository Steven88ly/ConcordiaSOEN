import java.util.EmptyStackException;

public class MyStack{
    private int max_size = 2;
    private final int SIZE_INCREMENT = 5;
    private final String RESIZE_MESSAGE = "Stack is full; its current size is %d%nWill be expanding the size to %d%n";
    private int current_size;
    private char[] stack;

    public MyStack(){
        this.stack = new char[this.max_size];
        this.current_size=0;
    }
    
    private void resize(){
        //Print Current array size and the new array size after resizing
        System.out.printf(this.RESIZE_MESSAGE, this.current_size, this.max_size + this.SIZE_INCREMENT);

        //Increase the maximum array size by a fixed amount (5 in this case)
        this.max_size += this.SIZE_INCREMENT;

        //Create our new array with the updated max size
        char[] newStack = new char[this.max_size];

        //Copy existing contents from old array to new resized array
        System.arraycopy(this.stack, 0, newStack, 0, this.current_size);

        //Update the reference to the new array
        this.stack = newStack;
    }

    public void push(char new_char){
        //Resize the array if current array is full
        if(this.isFull())
            this.resize();
        
        this.stack[this.current_size++] = new_char;

    }

    public void clear(){
        this.current_size=0;
    }

    public char pop(){
        //Throw Exception if stack is empty
        if(this.isEmpty())
            throw new EmptyStackException();
        
        //return value at top of the stack and decrement current size
        return this.stack[--current_size];
    }

    public char top(){
        //Throw Exception if stack is empty
        if(this.isEmpty())
            throw new EmptyStackException();

        //Return value at top of the stack
        return this.stack[current_size - 1];
    }

    public int size(){
        return this.current_size;
    }

    public boolean isEmpty(){
        return this.current_size == 0;
    }

    public boolean isFull(){
        return this.current_size >= this.max_size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack: ");
        for (int i = 0; i < current_size; i++) {
            sb.append(stack[i]).append(" ");
        }
        return sb.toString();
    }
}