
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author Michał
 */
public class Server extends javax.swing.JFrame {

    ServerSocket serverSocket;
    HashMap clientCall = new HashMap();
    //Set<String> clientCall = new HashSet<>();
   

    public Server() {
        try {
            initComponents();
            serverSocket = new ServerSocket(2089);
            this.statusLabel.setText("Serwer started.");
            new ClientAccept().start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class ClientAccept extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Socket sock = serverSocket.accept();
                    String inp = new DataInputStream(sock.getInputStream()).readUTF();
                    //add
                    DataOutputStream dataOut = new DataOutputStream(sock.getOutputStream());
                    if (clientCall.containsKey(inp)) {
                        dataOut.writeUTF("Jesteś już zalogowany");
                    } else {
                        clientCall.put(inp, sock); 
                        serwerTextArea.append(inp + " joined!\n");
                        
                        dataOut.writeUTF("");
                        new MessageRead(sock, inp).start();
                        new PrepareClientList().start();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class MessageRead extends Thread {

        Socket socket;
        String ID;

        MessageRead(Socket socket, String ID) {
            this.socket = socket;
            this.ID = ID;
        }
        
        @Override
        public void run() {
            while (!clientCall.isEmpty()) {
                try {
                    String insert = new DataInputStream(socket.getInputStream()).readUTF();
                    System.out.println("wiadomosc z messageRead: " + insert);
                    if (insert.equals("mkoihgteazdcvgyhujb09785542AXTY")) {
                        clientCall.remove(ID);
                        serwerTextArea.append(ID + ": usunięty! \n");
                        new PrepareClientList().start();
                        Set k = clientCall.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                   new DataOutputStream(((Socket) clientCall.get(key)).getOutputStream()).writeUTF(key);
                                     new DataOutputStream(((Socket) clientCall.get(key)).getOutputStream()).writeUTF(insert);
                                } catch (Exception ex) {
                                    clientCall.remove(key);
                                    serwerTextArea.append(key + ": usunięty! \n");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    } 
                    else if (insert.contains("4344554@@@@@67667@@")) {
                        insert = insert.substring(19);
                        StringTokenizer stringtokenizer = new StringTokenizer(insert, ":");
                        String id = stringtokenizer.nextToken();
                        insert = stringtokenizer.nextToken();
                        try {
                            new DataOutputStream(((Socket) clientCall.get(id)).getOutputStream()).writeUTF("< " + ID + " to " + id + " > " + insert);
                            System.out.println("male id: " + id);
                            System.out.println("duze ID: " + ID);
                        } catch (Exception ex) {
                            clientCall.remove(id);
                            serwerTextArea.append(id + ": usunięty!\n");
                            new PrepareClientList().start();
                        }
                    } 
                    else {
                        Set k = clientCall.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                    new DataOutputStream(((Socket) clientCall.get(key)).getOutputStream()).writeUTF("< " + ID + " to ALL > " + insert);
                                } catch (Exception ex) {
                                    clientCall.remove(key);
                                    serwerTextArea.append(key + ": usunięty! \n");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class PrepareClientList extends Thread {
        @Override
        public void run() {
            try {
                String ids = "";
                Set k = clientCall.keySet();
                System.out.println("keySet server: " + k);
                Iterator itr = k.iterator();
                while (itr.hasNext()) {
                    String key = (String)itr.next();
                    System.out.println("iterator: " + key);
                    ids += key + ","; 
                    
                 }
                if (ids.length() != 0) {
                    ids = ids.substring(0, ids.length() - 1);
                    System.out.println("drugi if: " + ids);
                }
                itr = k.iterator();          
                while (itr.hasNext()) {
                    String key = (String)itr.next();
                    System.out.println("z while: " + key);
                    try {
                        new DataOutputStream(((Socket)clientCall.get(key)).getOutputStream()).writeUTF(":;.,/=" + ids); //+"\n");
                    } catch (Exception ex) {
                        clientCall.remove(key);
                        serwerTextArea.append(key + ": usunięty!\n");
                    }
                }
            } 
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        serwerTextArea = new javax.swing.JTextArea();
        serwerLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My server");

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        serwerTextArea.setColumns(20);
        serwerTextArea.setRows(5);
        jScrollPane1.setViewportView(serwerTextArea);

        serwerLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        serwerLabel.setText("Serwer status");

        statusLabel.setText("..........................................................");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(serwerLabel)
                        .addGap(32, 32, 32)
                        .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serwerLabel)
                    .addComponent(statusLabel))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel serwerLabel;
    private javax.swing.JTextArea serwerTextArea;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
