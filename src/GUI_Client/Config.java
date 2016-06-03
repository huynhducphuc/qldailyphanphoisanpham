/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Noname
 */
public class Config {

    private String host;
    private int port;

    public Config() {
        readConfigFile();
    }
    
    private void readConfigFile() {
        try {
            FileReader fileReader = new FileReader("src/GUI_CLient/config.txt");
            Scanner scan = new Scanner(fileReader);

            String line = "";
            if (scan.hasNext()) {
                this.host = scan.nextLine();
                line = scan.nextLine();
                this.port = Integer.parseInt(line);
            }
            
            scan.close();
        } catch (FileNotFoundException ex) {
            // set default
            this.host = "localhost";
            this.port = 1111;
            // Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }
    
    public void saveConfig(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            PrintWriter pw = new PrintWriter("src/GUI_CLient/config.txt");
            pw.println(host);
            pw.println(port);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    

}
