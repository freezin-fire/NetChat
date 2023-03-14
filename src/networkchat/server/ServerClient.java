/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkchat.server;

import java.net.InetAddress;

/**
 *
 * @author FreezinFire
 */
public class ServerClient {
    public String name;
    public InetAddress address;
    public int port;
    private final int ID;
    public int attempt = 0;
    
    public ServerClient(String name, InetAddress address, int port, final int ID) {
	this.name = name;
	this.address = address;
	this.port = port;
	this.ID = ID;
    }
    
    public int getID() {
    	return ID;
    }
}
