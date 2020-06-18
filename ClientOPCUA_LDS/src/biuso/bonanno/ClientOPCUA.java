/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno;


import biuso.bonanno.supportClass.KeysUtils;

import java.util.HashMap;
import java.util.Locale;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.DataValue;
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
        
        myClient.setTimeout(0);
        
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
    
    
    public HashMap<String, DataValue> getAttributes(ExpandedNodeId node) throws Exception {    
        if(currentSession == null) throw new Exception("Crea una sessione");
        
        NodeId nodeId = NodeId.get(node.getIdType(), node.getNamespaceIndex(), node.getValue());        
       
        String[] nameNodesRead = {
        	"NodeId",
        	"NodeClass",
        	"BrowseName",
        	"DisplayName",
        	"Description",        	
        	"Value",
        	"DataType",
        	
        	/*
        	"WriteMask",
        	"UserWriteMask",
        	"AccessLevel",
        	"IsAbstract",
        	"Symmetric",
        	"InverseName",        	
        	"ContainsNoLoops",
        	"EventNotifier",        	
        	"ValueRank",
        	"ArrayDimensions",        	
        	
        	"UserAccessLevel",
        	"MinimumSamplingInterval",
        	"Historizing",
        	"Executable",
        	"UserExecutable",
        	"DataTypeDefinition",
        	"RolePermissions",
        	"UserRolePermissions",
        	"AccessRestrictions",
        	"AccessLevelEx"	*/
        };
        
        ReadValueId[] nodesToRead = {
    		new ReadValueId(nodeId, Attributes.NodeId, null, null),
    		new ReadValueId(nodeId, Attributes.NodeClass, null, null),
    		new ReadValueId(nodeId, Attributes.BrowseName, null, null),
    		new ReadValueId(nodeId, Attributes.DisplayName, null, null),
    		new ReadValueId(nodeId, Attributes.Description, null, null),
    		new ReadValueId(nodeId, Attributes.Value, null, null),
    		new ReadValueId(nodeId, Attributes.DataType, null, null),
    		
    		
    		/*
    		new ReadValueId(nodeId, Attributes.WriteMask, null, null),
    		new ReadValueId(nodeId, Attributes.UserWriteMask, null, null),
    		new ReadValueId(nodeId, Attributes.AccessLevel, null, null),
    		new ReadValueId(nodeId, Attributes.IsAbstract, null, null),
    		new ReadValueId(nodeId, Attributes.Symmetric, null, null),
    		new ReadValueId(nodeId, Attributes.InverseName, null, null),
    		new ReadValueId(nodeId, Attributes.ContainsNoLoops, null, null),
    		new ReadValueId(nodeId, Attributes.EventNotifier, null, null),    		
    		new ReadValueId(nodeId, Attributes.ValueRank, null, null),
    		new ReadValueId(nodeId, Attributes.ArrayDimensions, null, null),   	

    		new ReadValueId(nodeId, Attributes.UserAccessLevel, null, null),
    		new ReadValueId(nodeId, Attributes.MinimumSamplingInterval, null, null),
    		new ReadValueId(nodeId, Attributes.Historizing, null, null),
    		new ReadValueId(nodeId, Attributes.Executable, null, null),
    		new ReadValueId(nodeId, Attributes.UserExecutable, null, null),
    		new ReadValueId(nodeId, Attributes.DataTypeDefinition, null, null),
    		new ReadValueId(nodeId, Attributes.RolePermissions, null, null),
    		new ReadValueId(nodeId, Attributes.UserRolePermissions, null, null),
    		new ReadValueId(nodeId, Attributes.AccessRestrictions, null, null),
    		new ReadValueId(nodeId, Attributes.AccessLevelEx, null, null),  */		
        };
        
        ReadResponse res = currentSession.Read(null, MaxAge, TimestampsToReturn.Source, nodesToRead);
        
        System.out.println("\n<-- GetAttributes -->\n");
       
        DataValue[] resData = res.getResults();
        HashMap<String, DataValue> results = new HashMap<String, DataValue>();
        for(int i=0; i<resData.length; i++) {
        	System.out.println(i +") Valore "+nameNodesRead[i]+": " + resData[i].getValue().toString());
        	results.put(nameNodesRead[i], resData[i]);
        }
                
        return results;
    }
    
}
