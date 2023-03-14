/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkchat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author FreezinFire
 */
public class Server implements Runnable{
    
    private List<ServerClient> clients = new ArrayList<ServerClient>();
    private List<Integer> clientResponse = new ArrayList<Integer>();
    
    private DatagramSocket socket;
    private int port;
    private boolean running = false;
    private Thread run, manage, send, recieve;
    private final int MAX_ATTEMPTS = 5;
    
    private boolean raw = false;
    
    public Server(int port){
        this.port = port;
        
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            ex.printStackTrace();
            return;
        }
        run = new Thread(this, "Server");
        run.start();
    }

    @Override
    public void run() {
        running = true;
        System.out.println("Server started on port "+port);
        manageClients();
        reciever();
        Scanner scanner = new Scanner(System.in);
        while(running){
            String text = scanner.nextLine();
            if(!text.startsWith("/")){
                sendToAll("/m/Server: "+text+"/e/");
                continue;
            }
            text = text.substring(1);
            if (text.equals("raw")){
                if (raw) System.out.println("Raw mode ON.");
                else System.out.println("Raw mode OFF.");
                raw = !raw;
            }
            else if (text.equals("clients")){
                System.out.println("Clients Connected:");
                System.out.println("=======================");
                for (int i = 0; i < clients.size() ; i++){
                    ServerClient c = clients.get(i);
                    System.out.println(c.name + "(" + c.getID() + ") " + c.address.toString() + ":"+ c.port);
                }
                System.out.println("=======================");
            }
            else if (text.startsWith("kick")){
                String name = text.split(" ")[1];
                int id = -1;
                boolean number = true;
                try{
                    id = Integer.parseInt(name);                    
                } catch (NumberFormatException e){
                    number = false;
                }
                if (number){
                    boolean exists = false;
                    for (int i = 0; i<clients.size(); i++){
                        if(clients.get(i).getID() == id){
                            exists = true;
                            break;
                        }
                    }
                    if (exists) disconnect(id, true);
                    else System.out.println("Client :" + id + " doesn't exist on server.");
                }
                else{
                    for (int i = 0; i<clients.size(); i++){
                        ServerClient c = clients.get(i);
                        if (name.equals(c.name)){
                            disconnect(c.getID(), true);
                            break;
                        }
                    }
                }
            }
            else if (text.startsWith("quit")){
                quit();
            }
            else if (text.startsWith("help")){
                printHelp();
            }else {
                System.out.println("Unknown command...");
                printHelp();
            }
        }
        scanner.close();
    }
    
    private void printHelp(){
        System.out.println("NetChat Server v1.0 - By Freezin.Fire");
        System.out.println();
        System.out.println("====================================================");
        System.out.println();
        System.out.println("Server commands and usage:");
        System.out.println("/help - Print this help section.");
        System.out.println("/raw - Enable raw mode.");
        System.out.println("/clients - list out all the clients connected to the server.");
        System.out.println("/kick [userID or username] - Kicks the specified user from the server.");
        System.out.println("/quit - Shut down the server.");
        System.out.println();
        System.out.println("====================================================");
        System.out.println();
        System.out.println("This program is open-source and free-to-use, and shall not be distributed \n"
                + "as a proprietory application without the developer's consent. \n \n"
                + "Source website : https://github.com/freezin-fire/NetChat");
        System.out.println();
        System.out.println("====================================================");
    }
    
    private void manageClients(){
        manage = new Thread("Manage"){
            public void run(){
                while(running){
                    //managing
                    sendToAll("/i/server");
                    sendStatus();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    for(int i = 0; i < clients.size(); i++){
                        ServerClient c = clients.get(i);
                        if(!clientResponse.contains(c.getID())){
                            if(c.attempt >= MAX_ATTEMPTS){
                                disconnect(c.getID(), false);
                            }
                            else{
                                c.attempt++;
                            }
                        } else{
                            clientResponse.remove(new Integer(c.getID()));
                            c.attempt = 0;
                        }
                    }
                }
            }
        };
        manage.start();
    }
    
    private void sendStatus(){
        if (clients.size() <= 0) return;
        String users = "/u/";
        for (int i = 0; i < clients.size()-1; i++){
            users += clients.get(i).name + "/n/";
        }
        users += clients.get(clients.size() - 1).name + "/e/";
        sendToAll(users);
    }
    
    private void reciever(){
        recieve = new Thread("Recieve"){
            public void run(){
                while(running){
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try{
                        socket.receive(packet);
                    } catch(SocketException e){
                        //nothing to print here
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                    process(packet);
                }
            }  
        };
        recieve.start();
    }       
    
    private void sendToAll(String message){
        if(message.startsWith("/m/")){
            String text = message.substring(3);
            text = text.split("/e/")[0];
            System.out.println(message);
        }
        for(int i = 0; i < clients.size(); i++){
            ServerClient client = clients.get(i);
            send(message.getBytes(), client.address, client.port);
        }
    }
    
    private void send(final byte[] data, final InetAddress address, final int port){
        send = new Thread("Send"){
            public void run(){
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        send.start();
    }
    
    private void send(String message, InetAddress address, int port){
        message += "/e/";
        send(message.getBytes(), address, port);
    }
    
    private void process(DatagramPacket packet)    {
        String string = new String(packet.getData());
        
        //raw mode
        if(raw) System.out.println(string);
        
        /*connection command*/
        if(string.startsWith("/c/")){
            int id = UniqueIdentifier.getIdentifier();
            String name = string.split("/c/|/e/")[1];
            System.out.println(name + " id(" + id + ") has connected.");
            clients.add(new ServerClient(name, packet.getAddress(), packet.getPort(), id));
            String ID = "/c/" + id;
            send(ID, packet.getAddress(), packet.getPort());
        }
        /*message command*/
        else if(string.startsWith("/m/")){
            sendToAll(string);
        } 
        /*disconnect command*/
        else if(string.startsWith("/d/")){
            String id = string.split("/d/|/e/")[1];
            disconnect(Integer.parseInt(id), true);
        }
        else if(string.startsWith("/i/")){
            clientResponse.add(Integer.parseInt(string.split("/i/|/e/")[1]));
        }
        else {
            System.out.println(string);
        }
    }
    
    private void disconnect(int id, boolean status){
        ServerClient c = null;
        boolean existed = false;
        for(int i = 0; i < clients.size(); i++){
            if (clients.get(i).getID()==id){
                c = clients.get(i);
                clients.remove(i);
                existed = true;
                break;
            }
        }
        if (!existed) return;
        String message = "";
        if(status){
            message = "Client " + c.name + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port + " has disconnected.";
        } else {
            message = "Client " + c.name + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port + " has timed out.";
        }
        
        System.out.println(message);
    }
    
    private void quit(){
        for (int i = 0; i < clients.size(); i++){
            disconnect(clients.get(i).getID(), true);
        }
        running = false;
        socket.close();
    }
}
