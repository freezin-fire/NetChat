# NetChat
A cross platform network chat Java application.

# Usage
Run ServerMain.java on the server computer.
  usage - "java -jar NetworkChatServer.jar [port]"
Server commands:
  <ul>
    <li>/help - Print the help section.</li>
    <li>/raw - Enable raw mode.</li>
    <li>/clients - List out all the clients connected to the server.</li>
    <li>/kick [userID or username] - Kicks the specified user from the server.</li>
    <li>/quit - Shut down the server.</li>
  </ul>
Run Login.java on client computers and connect by entering the correct ip address and the port number assigned by the server.

Points to mention:
<ul>
<li>Port forwarding must be enabled on the host computer.</li>
<li>Chats are not saved on the server at the moment, and no account creation is required to use the app.</li>
<li>Can be implemented further in different java and android applications with little modification.</li>
  </ul>
