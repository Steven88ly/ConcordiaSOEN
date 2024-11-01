import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;

public class MainClass {
    public static void main(String[] args) throws Exception {
       
        String filePath = "./src/sample.txt"; // Replace with the path to your input file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String input;
            // Read each line from the file
            while ((input = reader.readLine()) != null) {
                validateParentheses(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateParentheses(String input){
        MyStack MainStack = new MyStack();
        MyStack WildCardStack = new MyStack();

        boolean isValid = true;

        for (char currentChar : input.toCharArray()) {
            // Stop processing when '$' is encountered
            if(currentChar == '$')                  
                break;
            // Push '*' (wildcard) to WildCardStack
            else if (currentChar == '*')            
                WildCardStack.push(currentChar);
            // If ')' is encountered, handle as follows:
            // 1. If top of MainStack is '(', pop it to match the closing bracket
            // 2. If MainStack has no '(', check if there's a wildcard '*' in WildCardStack to match the ')'
            // 3. If neither, set isValid to false (invalid string)
            else if(currentChar == ')'){    
                try{
                    if(!MainStack.isEmpty())
                    MainStack.pop();
                    else if(!WildCardStack.isEmpty())
                        WildCardStack.pop();
                    else{
                        isValid = false;
                        break;
                    }
                }catch(EmptyStackException e){
                     System.out.println("Stack was empty");
                }
            }
            // For any other character, push to MainStack
            else
                MainStack.push(currentChar);          
        }

        if(!MainStack.isEmpty()){
            isValid = false;
        }
        // edge case missing closing for a remaining opening 

        System.out.printf("input: %s isValid: %b%n",input, isValid);
        return isValid;
    }

    
    
}

