# NetChat
A cross platform network chat Java application.

# Usage
Run ServerMain.java on the server computer.
  usage - "java -jar NetworkChatServer.jar [port]"
Server commands:
  <li>
    <ul>/help - Print the help section.</ul>
    <ul>/raw - Enable raw mode.</ul>
    <ul>/clients - List out all the clients connected to the server.</ul>
    <ul>/kick [userID or username] - Kicks the specified user from the server.</ul>
    <ul>/quit - Shut down the server.</ul>
  </li>
Run Login.java on client computers and connect by entering the correct ip address and the port number assigned by the server.

Points to mention:
<li>
<ul>Port forwarding must be enabled on the host computer.</ul>
<ul>Chats are not saved on the server at the moment, and no account creation is required to use the app.</ul>
<ul>Can be implemented further in different java and android applications with little modification.</ul>
  </li>
