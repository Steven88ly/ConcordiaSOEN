
import java.io.FileNotFoundException;
import java.io.PrintStream;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class Driver {

    /** 
     * main class
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
    	 /*******************************************************************************************************************************************
    	  * TODO : implement all the operations of main class   																					*
    	  ******************************************************************************************************************************************/
        
    	Network objNetwork = new Network("network");            
        objNetwork.start();
        
        Server objServer = new Server();        
        objServer.start();

        Client objClientSending = new Client("sending");
        objClientSending.start();

        Client objClientReceiving = new Client("receiving");
        objClientReceiving.start();
    try{
            PrintStream output = new PrintStream("output-with-20.txt");
            System.setOut(output);

        }catch(FileNotFoundException e){
            System.out.println("file cannot be found.");
            System.exit(0);
        }
        
        /* Complete here the code for the main method ...*/
    }
}
