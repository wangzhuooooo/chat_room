package Client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;
import java.text.*;

class ClientReader extends Thread{
    private DataInputStream dis;
    private JTextArea output;
    Date dNow = new Date( );
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
    public ClientReader(DataInputStream dis, JTextArea output){
        this.dis=dis;
        this.output=output;
    }
    public void run(){
        String msg;
        try {
            while (true){
                msg = dis.readUTF();
                output.append(ft.format(dNow)+" "+msg+"\n");
            }
        }catch (IOException e){
            System.out.println(e);
        }

    }
}