/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.BrowseResult;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.ReferenceDescription;

import biuso.bonanno.supportClass.TreeBrowseObject;
import biuso.bonanno.supportClass.TreeServerObject;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
public class ClientGUI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;


	/**
     * Creates new form ClientGUI
     */
    public ClientGUI() {
        initComponents();
        
        ClientGUI me = this;        
        try {
            clientOpcua = new ClientOPCUA();
            
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
                    } catch (ServiceResultException e1) {
                        JOptionPane.showMessageDialog(me, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }  
                    dispose();
                }
            });
        } catch (ServiceResultException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
    
        jTree_serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                jButton_selectServer.setEnabled(true);
            }
        });
        
        jTree_browse.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();

                if (node == null || node.isRoot()) return;
                             
                node.removeAllChildren();    
                TreeBrowseObject nodeInfo = (TreeBrowseObject) node.getUserObject();               
                try {      
                	jLabel_log.setText("Browse in corso...");
                	jLabel_log.paintImmediately(jLabel_log.getVisibleRect());
                	
                    BrowseResult[] rx = clientOpcua.getBrowse(nodeInfo.getNodeId());
                    printObjectTree(rx, node);
                    jLabel_log.setText("Seleziona una variabile per visualizzarne il valore...");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(me, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }                          
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        jTree_browse.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree_browse.getLastSelectedPathComponent();

                if(node == null || node.isRoot()) {
                	addTableValues(null);
                	return;
                } 
                
                if(!node.isLeaf()) jTree_browse.expandPath(e.getPath());
                TreeBrowseObject nodeInfo = (TreeBrowseObject) node.getUserObject();    
                try {        
                	jLabel_log.setText("Read in corso...");
                	jLabel_log.paintImmediately(jLabel_log.getVisibleRect());
                	HashMap<String, DataValue> values = clientOpcua.getAttributes(nodeInfo.getNodeId());
                    addTableValues(values);
                    
                    jLabel_log.setText("Seleziona una variabile per visualizzarne il valore...");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(me, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }            	
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jTextField_discoveryUrl = new javax.swing.JTextField();
        jButton_findServer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree_serverTree = new javax.swing.JTree();
        jButton_selectServer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_endpoint = new javax.swing.JList<>();
        jButton_newSession = new javax.swing.JButton();
        jLabel_log = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree_browse = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTree_attributes = new javax.swing.JTree();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Client OPCUA - LDS");

        jTextField_discoveryUrl.setToolTipText("Inserisci Discovery URL");

        jButton_findServer.setText("Cerca Server");
        jButton_findServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_findServerActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Servers");
        jTree_serverTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree_serverTree);

        jButton_selectServer.setText("Seleziona server");
        jButton_selectServer.setEnabled(false);
        jButton_selectServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_selectServerActionPerformed(evt);
            }
        });

        jList_endpoint.setModel(new DefaultListModel<String>());
        jScrollPane1.setViewportView(jList_endpoint);

        jButton_newSession.setText("Crea sessione");
        jButton_newSession.setEnabled(false);
        jButton_newSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newSessionActionPerformed(evt);
            }
        });

        jLabel_log.setText("Inserisci l'url del LDS e cerca i server disponibili...");

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Objects");
        jTree_browse.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane3.setViewportView(jTree_browse);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");        
        jTree_attributes.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree_attributes.setRootVisible(false);
        jTree_attributes.setShowsRootHandles(true);
        jScrollPane4.setViewportView(jTree_attributes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_log, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField_discoveryUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_findServer))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(284, 284, 284)
                                .addComponent(jButton_selectServer))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton_newSession))
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane4))))
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
                .addComponent(jLabel_log)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_selectServer)
                    .addComponent(jButton_newSession))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
        
        reset(false);
        
        try {
            ApplicationDescription[] servers = clientOpcua.findServers(url_lds.replaceAll("\\s+",""));       

            DefaultTreeModel model = (DefaultTreeModel) jTree_serverTree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.removeAllChildren();           
            
            for(ApplicationDescription server : servers){
            	System.out.println("\n<-- ServerInfo -->\n"+server.toString());
            	
                if(server.getApplicationType().name() == "DiscoveryServer") continue;
                
                DefaultMutableTreeNode a = new DefaultMutableTreeNode(new TreeServerObject(server));
                a.add(new DefaultMutableTreeNode("Name: " + server.getApplicationName().getText()));
                a.add(new DefaultMutableTreeNode("Type: " + server.getApplicationType()));
                a.add(new DefaultMutableTreeNode("URL: " + server.getDiscoveryUrls()[0]));
                a.add(new DefaultMutableTreeNode("Uri: " + server.getApplicationUri()));
                root.add(a);                
            }
            model.reload();
            
            jLabel_log.setText("Seleziona un server...");
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
        
        reset(true);
        
        if(selRow.isLeaf()) selRow = (DefaultMutableTreeNode) selRow.getParent();
        TreeServerObject treeServer = (TreeServerObject) selRow.getUserObject();
        
        String server_url = treeServer.getDiscoveryUrl();

        try {            
            endpoints = clientOpcua.discoverEndpoints(server_url);

            DefaultListModel<String> model = (DefaultListModel<String>) jList_endpoint.getModel();
            model.clear();
            for (EndpointDescription endpoint : endpoints){      
            	String secPoly = endpoint.getSecurityPolicyUri().substring(endpoint.getSecurityPolicyUri().indexOf('#')+1);
                model.addElement(endpoint.getEndpointUrl() + " - " + endpoint.getSecurityMode() + " - "+ secPoly + " - "+ endpoint.getSecurityLevel());
                                
                System.out.println("\n<-- EndpointInfo -->");
                System.out.println("URL: " + endpoint.getEndpointUrl());
                System.out.println("Security Mode: " + endpoint.getSecurityMode());
                System.out.println("Security Policy: " + endpoint.getSecurityPolicyUri());
                System.out.println("Transport Protocol: " + endpoint.getTransportProfileUri());
                System.out.println("Security Level: " + endpoint.getSecurityLevel());           
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
        if(endpoints == null || jList_endpoint.getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(this, "Seleziona un endpoint", "Seleziona un endpoint", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        EndpointDescription endpoint = endpoints[jList_endpoint.getSelectedIndex()];
        String server_url = endpoint.getEndpointUrl();
        
        try {     
            clientOpcua.createSession(server_url, endpoint);
            jLabel_log.setText("Sessione creata, Browse in corso...");
        	jLabel_log.paintImmediately(jLabel_log.getVisibleRect());
        	
            DefaultTreeModel model = (DefaultTreeModel) jTree_browse.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.removeAllChildren();
            printObjectTree(clientOpcua.getBrowse(null), root);            
            model.reload();
            
            jLabel_log.setText("Sessione creata, Seleziona una variabile per visualizzarne il valore...");
        } catch (ServiceResultException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);     
        }     
    }//GEN-LAST:event_jButton_newSessionActionPerformed

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
    private EndpointDescription[] endpoints;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_findServer;
    private javax.swing.JButton jButton_newSession;
    private javax.swing.JButton jButton_selectServer;
    private javax.swing.JLabel jLabel_log;
    private javax.swing.JList<String> jList_endpoint;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField_discoveryUrl;
    private javax.swing.JTree jTree_attributes;
    private javax.swing.JTree jTree_browse;
    private javax.swing.JTree jTree_serverTree;
    // End of variables declaration//GEN-END:variables


    private void printObjectTree(BrowseResult[] result, DefaultMutableTreeNode node) throws Exception{
        for(BrowseResult r : result){   
            for(ReferenceDescription ref : r.getReferences()){   
                DefaultMutableTreeNode a = new DefaultMutableTreeNode(new TreeBrowseObject(ref));  
                a.add(new DefaultMutableTreeNode());  
                node.add(a);               
                System.out.println(ref);
            }                
        }
    }
    
    private void addTableValues(HashMap<String, DataValue> values){
               
        DefaultTreeModel model = (DefaultTreeModel) jTree_attributes.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();  
        
        if(values == null) {
        	model.reload();     
        	return;
        }

        for(String key : values.keySet()) {
        	 DataValue val = values.get(key);
             Variant value = val.getValue();
             
             if (value.isEmpty()) continue; 
             
             DefaultMutableTreeNode a = new DefaultMutableTreeNode(key);
     		if(value.isArray()) {     
     			Object[] myObject = (Object[])value.getValue();
     			for(Object object : myObject) a.add(new DefaultMutableTreeNode(object.toString()));        			
     		}
     		else a.add(new DefaultMutableTreeNode(value.toString()));   
            root.add(a);       
        }
        model.reload();     
    }
    
    private void reset(boolean onlyTree) {
    	
    	DefaultTreeModel modelT = (DefaultTreeModel) jTree_browse.getModel();
        DefaultMutableTreeNode rootT = (DefaultMutableTreeNode) modelT.getRoot();
        rootT.removeAllChildren();
        modelT.reload();
        
        if(onlyTree) return;
    	endpoints = null;
        ((DefaultListModel<String>) jList_endpoint.getModel()).clear();        
        
        jButton_newSession.setEnabled(false);
        jButton_selectServer.setEnabled(false);        
    }

}
