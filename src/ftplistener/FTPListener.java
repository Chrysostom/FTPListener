/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftplistener;

/**
 *
 * @author Leo 
 */
public class FTPListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 3){
            System.out.println("[+] FTPListener format: ");
            System.out.println("[+]                     ");
            System.out.println("[+] FTPListener <directory to monitor> "
                    + "<directory to move files to> [filepath to alert sound]");
            System.exit(0);
        }
        
        System.out.println("\n\n[+][+][+] WELCOME TO THE METAVERSE..."
                + " [+][+][+]\n\n");
        System.out.println("[+] FTP FILE UPLOAD LISTENER - by Flux");
        System.out.println("[+] Starting up...");
        
        String listenDirectory = args[0];
        String destinationDirectory = args[1];
        String soundFilePath = args[2];
                
        FileHandler handler = new FileHandler(listenDirectory, destinationDirectory);
        FileAlert alerter = new FileAlert(soundFilePath);        
        Thread handlerThread = new Thread(handler);
        handlerThread.start();
                
        while(true){
            try{
                Thread.sleep(5000);
            }catch(InterruptedException e){
                System.out.println("Main thread interrupted.");
                e.printStackTrace();
            }            
            
            if(!handler.running){
                System.out.println("The monitoring thread has somehow been"
                        + " stopped. The program will now exit.");
                return;
            }
            
            if(handler.fileDetected){
                System.out.println("[+] FileListener has received a file "
                        + "detection notification. Attempting to play alert.");
                alerter.playAlert();
                handler.fileDetected = false;
            }
        }        
                       
    }
    
}
