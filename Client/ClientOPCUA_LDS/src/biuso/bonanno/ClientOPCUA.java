/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno;


import biuso.bonanno.supportClass.KeysUtils;
import java.util.Locale;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.BrowseDirection;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.BrowseResult;
import org.opcfoundation.ua.core.BrowseResultMask;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;



/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
class ClientOPCUA {
    
    private final Client myClient;
    private SessionChannel currentSession = null;
    @SuppressWarnings("deprecation")
	private static Double MaxAge = new Double(500);
    
    public ClientOPCUA() throws ServiceResultException {
        final KeyPair pair = KeysUtils.getCert("OPCClient");
        myClient = Client.createClientApplication(pair);
        
        myClient.getApplication().addLocale(Locale.ENGLISH);
        myClient.getApplication().setApplicationName(new LocalizedText("Java Client", Locale.ENGLISH));
        myClient.getApplication().setProductUri("urn:JavaClient");
        
        myClient.getApplication().getOpctcpSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);
        myClient.getApplication().getHttpsSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);
    }
    
    public ApplicationDescription[] findServers(String url_LDS) throws ServiceResultException {
        if(currentSession != null) this.closeSession();
        ApplicationDescription[] servers = myClient.discoverApplications(url_LDS);

        System.out.println("\n<-- FindServers -->\nHo contattato il Server LDS: " + url_LDS);
        System.out.println("Il numero di server e': " + servers.length);
                
        return servers;
    }
    
    public EndpointDescription[] discoverEndpoints(String server_url) throws ServiceResultException {
        if(currentSession != null) this.closeSession();
        // nota: discoverEndpoints e' l'API per il servizio GetEndpoints
        EndpointDescription[] endpoints = myClient.discoverEndpoints(server_url);

        System.out.println("\n<-- DiscoverEndpoints -->\nHo contattato il Server: " + server_url);
        System.out.println("Il numero di endpoint e': " + endpoints.length);

        return endpoints;        
    }
    
    public boolean createSession(String server_url, EndpointDescription endpoint) throws ServiceResultException {
        if(currentSession != null ) this.closeSession();
        
        currentSession = myClient.createSessionChannel(server_url, endpoint);
        currentSession.activate();
        System.out.print("\n<-- CreateSessionChannel -->\nOPC UA JAVA Client: Connessione stabilita con " + server_url + "\n");
        
        return true;
    }
    
    public boolean closeSession() throws ServiceFaultException, ServiceResultException{
        if(currentSession == null) return true;
        
        currentSession.close();
        currentSession.closeAsync();
        currentSession = null;
        
        return true;
    }
    
    
    public BrowseResult[] getBrowse(ExpandedNodeId node) throws Exception{    
        if(currentSession == null) throw new Exception("Crea una sessione");
        
        BrowseDescription browse = new BrowseDescription();

        if(node == null) browse.setNodeId(Identifiers.ObjectsFolder);
        else browse.setNodeId(NodeId.get(node.getIdType(), node.getNamespaceIndex(), node.getValue()));
                
        browse.setBrowseDirection(BrowseDirection.Forward);
        browse.setIncludeSubtypes(true);
        browse.setNodeClassMask(NodeClass.Object, NodeClass.Variable);
		browse.setResultMask(BrowseResultMask.All);
		BrowseResponse res = currentSession.Browse(null, null, null, browse);
	
		return res.getResults();        
    }
    
    
    public String getVariable(ExpandedNodeId node) throws Exception {    
        if(currentSession == null) throw new Exception("Crea una sessione");
        
        ReadResponse res = currentSession.Read(null, MaxAge, TimestampsToReturn.Source,
                new ReadValueId(NodeId.get(node.getIdType(), node.getNamespaceIndex(), node.getValue()), Attributes.Value, null, null));
        
        System.out.println("\n<-- GetVariable -->\nValore letto: " + res.getResults()[0].getValue());
        System.out.println("Status letto: " + res.getResults()[0].getStatusCode());
        System.out.println("Timestamp source letto: " + res.getResults()[0].getSourceTimestamp());
        
        return res.getResults()[0].getValue().toString();
    }
    
}
