/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftplistener;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Leo
 * Opens the specified webpage with the default browser. Try it with
 * a youtube or soundcloud URL to play a victorious song!
 */
public class FileAlert {
    
    public String mp3FilePath;
    
    public FileAlert(String mp3FilePath){
        this.mp3FilePath = mp3FilePath;        
    }
    
    public void playAlert() {
        try{
        if (Desktop.isDesktopSupported()) {
            System.out.println("[+] FileAlert has confirmed that the "
                    + "desktop software interface is supported.");
            System.out.println("[+] Opening sound file.");
            //Desktop.getDesktop().browse(new URI(url));
            Desktop.getDesktop().open(new File(mp3FilePath));
        }
        }catch(IOException e){
            e.printStackTrace();
        }
    }    
}
