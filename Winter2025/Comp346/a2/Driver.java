/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.PrintStream;

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
    //   /* TO PRINT OUTPUT
        try{
            PrintStream output = new PrintStream("semaphore-3threads.txt");
            System.setOut(output);

        }catch(FileNotFoundException e){
            System.out.println("file cannot be found.");
            System.exit(0);
        }
        
    	 /*******************************************************************************************************************************************
    	  * TODO : implement all the operations of main class   																					*
    	  ******************************************************************************************************************************************/
        
    	Network objNetwork = new Network("network");            /* Activate the network */
        objNetwork.start();

        Server objServer1 = new Server("server1");            /* Activate the server */
        objServer1.start();

        Server objServer2 = new Server("server2");        
        objServer2.start();

        Server objServer3 = new Server("server3");        
        objServer3.start();

        Client objClientSending = new Client("sending");
        objClientSending.start();

        Client objClientReceiving = new Client("receiving");
        objClientReceiving.start();

        

        /* Complete here the code for the main method ...*/
    }
}
