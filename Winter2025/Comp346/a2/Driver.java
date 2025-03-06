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
      /* TO PRINT OUTPUT
        try{
            PrintStream output = new PrintStream("output-with-20.txt");
            System.setOut(output);

        }catch(FileNotFoundException e){
            System.out.println("file cannot be found.");
            System.exit(0);
        }
        */
    	 /*******************************************************************************************************************************************
    	  * TODO : implement all the operations of main class   																					*
    	  ******************************************************************************************************************************************/
        
    	Network objNetwork = new Network("network");            /* Activate the network */
        objNetwork.start();

        Server objServer1 = new Server("server1");            /* Activate the server */
        objServer1.start();

        Server objServer2 = new Server("server2");        
        objServer2.start();

        Client objClientSending = new Client("sending");
        objClientSending.start();

        Client objClientReceiving = new Client("receiving");
        objClientReceiving.start();

        try {
            objServer1.join();  // Wait for server thread 1 to finish
            // objServer2.join();  // Wait for server thread 2 to finish
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        //Disconnect the network when both threads have terminated
        if(Server.getNumTerminatedThreads() == 2){
            System.out.println("Both server threads have terminated. Disconnecting the server.");
            Network.disconnect(Network.getServerIP());
        }

        /* Complete here the code for the main method ...*/
    }
}
