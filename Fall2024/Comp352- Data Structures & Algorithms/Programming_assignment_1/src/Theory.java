import java.util.Arrays;

public class Theory {
    public static void main(String[] args) throws Exception {
       Question5();
        
    }

    private static void Question5(){
        int[] array = {1,1,2,2,32,12,54,76,32,87,12};
        int arrLength = array.length;

        // Left half and right half boundaries
        int endLeft = arrLength / 2;
        int endRight = (arrLength % 2 == 0) ? endLeft : endLeft + 1;
       

        int left = 0;
        int right = array.length - 1;

        // Loop through and apply reverse and Negate
        while (left <= endLeft - 1 && right >= endRight + 1) {
            //Compare equalities
            if(array[left] == array[left+1])
                Negate(array, left, left + 1);

            //reverse right side pairs
            reverse(array, right, right - 1);

            //increment/decrement pointers
            left += 2;
            right -= 2;
        }

        System.out.println(Arrays.toString(array));
    }

     // Function to reverse two elements in the array
     public static void reverse(int[] array, int leftSide, int rightSide) {
        int temp = array[leftSide];
        array[leftSide] = array[rightSide];
        array[rightSide] = temp;
    }

    // Function to sum two elements and insert the sum back in the array
    public static void Negate(int[] array, int i, int j) {
        array[i] *= -1;
        array[j] *= -1;
    }

    private static void Question4(){
        char[] input = "gggN@@@@@KKeeeejjdsmmu".toCharArray();
        StringBuilder output = new StringBuilder();
        char currentChar = input[0];
        char nextChar;

        int counter =0;

        for (int index = 1; index < input.length; index++) {
            nextChar = input[index];

            if (nextChar == currentChar) {
                counter++;
            } else {
                appendCharWithCount(output, currentChar, counter);
                currentChar = nextChar;
                counter = 1;
            }
        }

        // Append the last character and its count
        appendCharWithCount(output, currentChar, counter);

        System.out.println(output);
    }

    private static void appendCharWithCount(StringBuilder output, char character, int count) {
        output.append(character);
        if (count > 1) {
            output.append(count);
        }
    }
}
