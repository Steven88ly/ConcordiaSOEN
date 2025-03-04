

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
        
    		Network objNetwork = new Network("network");            
        objNetwork.start();
        
        Server objServer = new Server();        
        objServer.start();

        Client objClientSending = new Client("sending");
        objClientSending.start();

        Client objClientReceiving = new Client("receiving");
        objClientReceiving.start();
   
       
    }
    
 }
