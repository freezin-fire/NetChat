/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package networkchat;

import java.util.Arrays;


/**
 *
 * @author FreezinFire
 */
public class ClientWindow extends javax.swing.JFrame implements Runnable {
    
    private Client client;
    private OnlineUsers users;
    
    private Thread run, listen;
    private boolean running = false;
    
    //ClientWindow function
    public ClientWindow(String name, String address, int port) {
        client = new Client(name, address, port);
        
        this.setVisible(true);
        initComponents();
        boolean connect = client.openConnection(address);
        if(!connect){
            System.err.println("Connection Failed;");
            console("Connection Failed!");
        }
        console("Connecting to "+address+":"+port+" (user: " + name+")");
        String connection = "/c/" + name + "/e/";
        client.send(connection.getBytes());
        users = new OnlineUsers();
        running = true;
        run = new Thread(this, "Running Thread");
        run.start();
    }
    
    public void run(){
        listen();
    }
    
    public void console(String message){
        msgArea.append(message+"\n");
    }
    
    public void listen(){
        listen = new Thread("Listen"){
            public void run(){
                while(running){
                    String message = client.recieve();
                    if (message.startsWith("/c/")){
                        client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        console("Connection successfull with server! ID :"+ client.getID());
                    } else if (message.startsWith("/m/")){
                        String text = message.substring(3).split("/e/")[0];
                        console(text);
                    } else if (message.startsWith("/i/")){
                        String text = "/i/" + client.getID() + "/e/";
                        send(text, false);
                    } else if (message.startsWith("/u/")){
                        String[] u = message.split("/u/|/n/|/e/");
                        users.update(Arrays.copyOfRange(u, 1, u.length - 1));
                    }
                }
            }
        };
        listen.start();
    }
    
    private void send(String message, boolean text){
        if ("".equals(message)) return;
        if (text){
            message = client.getName()+": "+message;
            message = "/m/" + message + "/e/";
            msgField.setText("");
        }
        client.send(message.getBytes());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msgArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        msgField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        onlineFM = new javax.swing.JMenuItem();
        exitFM = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NetChat!");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(400, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        msgArea.setEditable(false);
        msgArea.setColumns(20);
        msgArea.setRows(5);
        jScrollPane1.setViewportView(msgArea);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        msgField.setMinimumSize(new java.awt.Dimension(64, 23));
        msgField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgFieldActionPerformed(evt);
            }
        });
        msgField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msgFieldKeyPressed(evt);
            }
        });

        jMenu1.setText("File");

        onlineFM.setText("Online Users");
        onlineFM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineFMActionPerformed(evt);
            }
        });
        jMenu1.add(onlineFM);

        exitFM.setText("Exit");
        exitFM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitFMActionPerformed(evt);
            }
        });
        jMenu1.add(exitFM);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msgField, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msgField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msgFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msgFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        send(msgField.getText(), true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void msgFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msgFieldKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
            send(msgField.getText(), true);
        }
    }//GEN-LAST:event_msgFieldKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String disconnect = "/d/"+client.getID()+"/e/";
        send(disconnect, false);
        client.close();
        running = false;
    }//GEN-LAST:event_formWindowClosing

    private void onlineFMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineFMActionPerformed
        users.setVisible(true);
    }//GEN-LAST:event_onlineFMActionPerformed

    private void exitFMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitFMActionPerformed
        String disconnect = "/d/"+client.getID()+"/e/";
        send(disconnect, false);
        client.close();
        running = false;
    }//GEN-LAST:event_exitFMActionPerformed

    /**
     * @param args the command line arguments
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exitFM;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JTextField msgField;
    private javax.swing.JMenuItem onlineFM;
    // End of variables declaration//GEN-END:variables
}
