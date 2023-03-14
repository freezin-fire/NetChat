/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkchat.server;

/**
 *
 * @author FreezinFire
 */
public class ServerMain {
    private int port;
    private Server server;
        
    public ServerMain(int port) {
    	this.port = port;
    	server = new Server(port);
    }

    public static void main(String[] args) {
    	int port;
    	if (args.length != 1) {
            System.out.println("Usage: java -jar NetworkChatServer.jar [port]");
            return;
	}
	port = Integer.parseInt(args[0]);
	new ServerMain(port);
    }
}
