package Linear;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Linear {
    public static void main(String[] args) throws Exception {
         int oddo;                                             //Variable to hold calculated Oddonacci values
         final int MAX_ODDO = 40, INC=5;                      //MAX_ODDO defines the limit, INC determines steps between calculations
         final String FILE_NAME="./src/Linear/OddoOut.txt";  //Output file path
         double startTime, endTime,diff ;                      //Variables for measuring time taken for each calculation
         BufferedWriter writer = null;                         //BufferedWriter for outputting results to file
         File file = new File(FILE_NAME);                      //Represents the output file

         try{
            if(file.exists() && !file.isDirectory())              //Ensure the output file exists -> create if necessary
               writer = new BufferedWriter(new FileWriter(file)); //open existing file
            else{
               file.createNewFile();                              //create new file if it does not exist
               writer = new BufferedWriter(new FileWriter(file));
            }
            
         }catch(IOException e){
            System.out.println("Error setting up the file writer: " + e.getMessage());
            e.printStackTrace();
            return;  // Exit early if file setup fails
         }
            
         try{

            for(int i=5; MAX_ODDO >= i; i += INC){
               startTime = System.nanoTime();                     //record start time
               oddo = LinearOddo(i-1);                              //Calculate oddonacci
               endTime = System.nanoTime();                       //record end time
      
               diff = (endTime-startTime)/1000000.0;              //calculate elapsed time in ms
      
               writer.write("Oddonacci at position " + i + " is " + oddo + " calculated in " + diff + " ms");  //Log result to file
               writer.newLine();                                  //move to next line
               writer.flush();                                    //write to file
            }
   
            writer.close();
         }catch(Exception e){
            System.out.println("Error during Oddonacci calculation: " + e.getMessage());
            e.printStackTrace();
            
         }finally{
            try {                                                 //Ensure writer is closed properly
               if(writer != null)
                  writer.close();
            } catch (IOException e) {
               System.out.println("Error closing the file writer: " + e.getMessage());
            }
         }

    }

    public static int LinearOddo(int n) {
        return LinearOddoHelper(n, 1, 1, 1);  // Start with the first three Oddonacci numbers (1, 1, 1)
    }
    
    // Helper function to carry the last three Oddonacci numbers during recursion
    private static int LinearOddoHelper(int n, int a, int b, int c) {
        // Base case: when n is 0 return the most recent Oddonacci value (a)
        if (n == 0) {
            return a;
        }
    
        // Recursive case: shift the values and reduce n
        return LinearOddoHelper(n - 1, b, c, a + b + c);
    }
    
   
    
}


