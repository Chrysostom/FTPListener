/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftplistener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Leo
 * Watches directory and waits for a file to appear, notifies user
 * when a file shows up, then transfers the file immediately to a
 * private folder so that no remote FTP users can access the file.
 */
public class FileHandler implements Runnable{
    
    public String listenDir;
    public String destinationDir;
    public boolean fileDetected;
    public boolean running;
    
    public FileHandler(String listenDirectory, String destinationDirectory){
        listenDir = listenDirectory;
        destinationDir = destinationDirectory;
        fileDetected = false;  
        running = true;
    }
    
    @Override
    public void run(){        
        File listenDirFile = new File(listenDir);
        System.out.println("\n[+] FileHandler is now monitoring the directory.");
        while(true){
            listenDirFile = new File(listenDir);
            if(listenDirFile.listFiles().length!=0){
                fileDetected = true;
                try{
                    System.out.println("[+] FileHandler detected file, "
                            + "sleeping now for 30 seconds...");
                    Thread.sleep(30000);
                    System.out.println("[+] FileHandler woke up! Attempting "
                            + "to transfer file now.");
                }catch(InterruptedException e){
                    System.err.println("FileHandler thread has been "
                            + "interrupted with exception:");
                    e.printStackTrace();
                    running = false;
                    return;
                }
                for(File currentFile : listenDirFile.listFiles()){
                    try{                        
                        Files.copy(currentFile.toPath(), new File(destinationDir
                                +File.separatorChar+currentFile.getName()).toPath(), 
                                StandardCopyOption.REPLACE_EXISTING);
                        Files.deleteIfExists(currentFile.toPath());
                        System.out.println("[+] FileHandler has copied and "
                                + "deleted the file safely.");
                    }catch(IOException e){
                        System.err.println("Failed to copy/delete file "+
                                currentFile.getName()+" with exception:");
                        e.printStackTrace();
                    }
                }
            }            
        }
    }
    
}
