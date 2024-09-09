# NetChat

**NetChat** is a cross-platform network chat application built using Java. It allows multiple users to chat with each other over a network in real-time. The project demonstrates socket programming to establish communication between clients and a server, providing a basic yet functional chat application.

## Features

-   **Real-Time Messaging:** Users can send and receive messages instantly.
-   **Multi-User Support:** The server can handle multiple clients simultaneously.
-   **Online/Offline Status:** Users can see who is currently connected.
-   **Server Commands:** The server admin can execute commands like kicking users or listing connected clients.
-   **Platform Flexibility:** Can be adapted for various platforms, including Java and Android, with minimal modifications.

## Usage

1.  **Clone the Repository:**
    
    `git clone https://github.com/freezin-fire/NetChat.git` 
    
2.  **Open the Project:**  
    Open the project in your preferred IDE (e.g., Eclipse, IntelliJ, NetBeans).
    
3.  **Start the Server:**
    
    -   Run the `Server.java` file to start the server.
    -   Alternatively, you can start the server using the command:
        
        ```java -jar NetworkChatServer.jar [port]```
        
4.  **Start the Client:**
    
    -   Run the `Login.java` file to start the client.
    -   Enter a username and provide the server IP and port when prompted.
5.  **Begin Chatting:** Once connected, you can chat with other users in real-time. Make sure the server is running before starting any client.
    

## Server Commands

-   `/help`: Display the help section.
-   `/raw`: Enable raw mode.
-   `/clients`: List all clients connected to the server.
-   `/kick [userID or username]`: Kick a specified user from the server.
-   `/quit`: Shut down the server.

## Technologies Used

-   **Java**
-   **Socket Programming**

## License

This project is licensed under the GPL-2.0 License.
