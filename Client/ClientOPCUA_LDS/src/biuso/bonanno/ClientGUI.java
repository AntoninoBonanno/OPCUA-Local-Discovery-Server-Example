/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.EndpointDescription;

/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
public class ClientGUI extends javax.swing.JFrame {

    /**
     * Creates new form ClientGUI
     */
    public ClientGUI() {
        initComponents();
        try {
            clientOpcua = new ClientOPCUA();
            
            javax.swing.JFrame me = this;
            addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    int result = JOptionPane.showConfirmDialog(me,
                    "Sei sicuro di voler chiudere la sessione?",
                    "Conferma uscita", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result != JOptionPane.YES_OPTION) return;
                    try {
                        clientOpcua.closeSession();
                        dispose();
                    } catch (ServiceResultException e1) {
                        JOptionPane.showMessageDialog(me, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }  
                }
            });
        } catch (ServiceResultException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(1);
        }
        treeServers = new HashMap<String, String>();        
        jTree_serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                jButton_selectServer.setEnabled(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_discoveryUrl = new javax.swing.JTextField();
        jButton_findServer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree_serverTree = new javax.swing.JTree();
        jButton_selectServer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_endpoint = new javax.swing.JList<>();
        jButton_newSession = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel_freeMemory = new javax.swing.JLabel();
        jButton_getFreeMemory = new javax.swing.JButton();
        jLabel_log = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Client OPCUA - LDS");

        jTextField_discoveryUrl.setToolTipText("Inserisci Discovery URL");

        jButton_findServer.setText("Cerca Server");
        jButton_findServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_findServerActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Server");
        jTree_serverTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree_serverTree);

        jButton_selectServer.setText("Seleziona server");
        jButton_selectServer.setEnabled(false);
        jButton_selectServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_selectServerActionPerformed(evt);
            }
        });

        jList_endpoint.setModel(new DefaultListModel());
        jScrollPane1.setViewportView(jList_endpoint);

        jButton_newSession.setText("Crea sessione");
        jButton_newSession.setEnabled(false);
        jButton_newSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newSessionActionPerformed(evt);
            }
        });

        jLabel1.setText("Memoria libera nel server: ");

        jLabel_freeMemory.setText("0");

        jButton_getFreeMemory.setText("Leggi memoria");
        jButton_getFreeMemory.setEnabled(false);
        jButton_getFreeMemory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_getFreeMemoryActionPerformed(evt);
            }
        });

        jLabel_log.setText("Inserisci l'url del LDS e cerca i server disponibili...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField_discoveryUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_findServer))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton_selectServer)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton_newSession)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_getFreeMemory))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel_log)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_freeMemory)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_findServer)
                    .addComponent(jTextField_discoveryUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_selectServer)
                    .addComponent(jButton_newSession)
                    .addComponent(jButton_getFreeMemory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel_freeMemory)
                    .addComponent(jLabel_log))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_findServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_findServerActionPerformed
        String url_lds = jTextField_discoveryUrl.getText();
        if(url_lds.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Inserire un Url valido", "Inserire Url", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            ApplicationDescription[] servers = clientOpcua.findServers(url_lds);       

            DefaultTreeModel model = (DefaultTreeModel) jTree_serverTree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.removeAllChildren();
            treeServers.clear();
            
            for(ApplicationDescription server : servers){
                
                if(server.getApplicationType().toString() == "DiscoveryServer") continue;
                
                DefaultMutableTreeNode a = new DefaultMutableTreeNode(server.getProductUri());
                a.add(new DefaultMutableTreeNode("Name: " + server.getApplicationName()));
                a.add(new DefaultMutableTreeNode("Type: " + server.getApplicationType()));
                a.add(new DefaultMutableTreeNode("URL: " + server.getDiscoveryUrls()[0]));
                a.add(new DefaultMutableTreeNode("Uri: " + server.getApplicationUri()));
                root.add(a);
                treeServers.put(server.getProductUri(), server.getDiscoveryUrls()[0]);
                System.out.println(server.toString());
            }
            model.reload();
            
            jLabel_log.setText("Seleziona un server e scegli un endpoint...");
        } catch (ServiceResultException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_findServerActionPerformed

    private void jButton_selectServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_selectServerActionPerformed
        DefaultMutableTreeNode selRow = (DefaultMutableTreeNode) jTree_serverTree.getLastSelectedPathComponent();
        if(selRow == null || selRow.isRoot()){
            JOptionPane.showMessageDialog(this, "Seleziona un server", "Seleziona un server", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String server_url = treeServers.get(selRow.getPath()[1].toString());

        try {            
            endpoints = clientOpcua.discoverEndpoints(server_url);

            DefaultListModel model = (DefaultListModel) jList_endpoint.getModel();
            model.clear();
            for (EndpointDescription endpoint : endpoints){      
            	String secPoly = endpoint.getSecurityPolicyUri().substring(endpoint.getSecurityPolicyUri().indexOf('#')+1);
                model.addElement(endpoint.getEndpointUrl() + " - " + endpoint.getSecurityMode() + " - "+ secPoly + " - "+ endpoint.getSecurityLevel());
                
                System.out.println("\n\nInformazioni dell'endpoint");
                System.out.println("URL  e' " + endpoint.getEndpointUrl());
                System.out.println("Security Mode " + endpoint.getSecurityMode());
                System.out.println("Security Policy " + endpoint.getSecurityPolicyUri());
                System.out.println("Transport Protocol " + endpoint.getTransportProfileUri());
                System.out.println("Security Level  " + endpoint.getSecurityLevel());             
            }
            
            jButton_newSession.setEnabled(true);
            jLabel_log.setText("Seleziona un endpoint...");
        } catch (ServiceResultException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);           
        }
    }//GEN-LAST:event_jButton_selectServerActionPerformed

    private void jButton_newSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newSessionActionPerformed
        DefaultMutableTreeNode selRow = (DefaultMutableTreeNode) jTree_serverTree.getLastSelectedPathComponent();
        if(selRow == null){
            JOptionPane.showMessageDialog(this, "Seleziona un server", "Seleziona un server", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String server_url = treeServers.get(selRow.getPath()[1].toString());
        
        if(endpoints == null || jList_endpoint.getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(this, "Seleziona un endpoint", "Seleziona un endpoint", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {            
            clientOpcua.createSession(server_url, endpoints[jList_endpoint.getSelectedIndex()]);
            jButton_getFreeMemory.setEnabled(true);       
            jLabel_log.setText("Sessione creata, puoi leggere lo stato della memoria...");
        } catch (ServiceResultException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);           
        }       
    }//GEN-LAST:event_jButton_newSessionActionPerformed

    private void jButton_getFreeMemoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_getFreeMemoryActionPerformed
        try {
            String freeMemory = clientOpcua.getFreeMemory(); 
            jLabel_freeMemory.setText(freeMemory);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore lettura memoria", JOptionPane.ERROR_MESSAGE);     
        }
    }//GEN-LAST:event_jButton_getFreeMemoryActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    private ClientOPCUA clientOpcua;
    private Map<String, String> treeServers;
    private EndpointDescription[] endpoints;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_findServer;
    private javax.swing.JButton jButton_getFreeMemory;
    private javax.swing.JButton jButton_newSession;
    private javax.swing.JButton jButton_selectServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_freeMemory;
    private javax.swing.JLabel jLabel_log;
    private javax.swing.JList<String> jList_endpoint;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField_discoveryUrl;
    private javax.swing.JTree jTree_serverTree;
    // End of variables declaration//GEN-END:variables
}
