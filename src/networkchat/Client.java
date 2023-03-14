/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FreezinFire
 */
public class Client {
    private DatagramSocket socket;
    
    private Thread send;
    private InetAddress ip;
    private String name, address;
    private int port;
    
    private int ID = -1;
    
    public Client (String name, String address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
    }
    
    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public int getPort(){
        return port;
    }
    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    
    public boolean openConnection(String address){
        try{
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
        } catch (SocketException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public String recieve(){
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        String message = new String(packet.getData());
        
        return message;
    }
    
    public void send(final byte[] data){
        send = new Thread("Send"){
            public void run(){
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        send.start();
    }
    
    public void close(){
        new Thread(){
            public void run(){
                synchronized (socket){
                    socket.close();
                }
            }
        }.start();
    }
}
