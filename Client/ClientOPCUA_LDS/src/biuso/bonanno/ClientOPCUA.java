/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno;


import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.utils.CertificateUtils;

/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
class ClientOPCUA {
    private static final String PRIVKEY_PASSWORD = "MyKey";
    private final Client myClient;
    private SessionChannel currentSession = null;
    private static Double FreeMemory = new Double(500);
    
    /**
    * Questa funzione carica il certificato dell'applicazione che deve essere
    * presente nella stessa directory di questo file. Se non presente lo crea
    *  
    * @param applicationName
    * @return
    * @throws ServiceResultException
    */
    private static KeyPair getCert(String applicationName) throws ServiceResultException {
        File certFile = new File(applicationName + ".der");
        File privKeyFile = new File(applicationName + ".pem");
        try {
            Cert myCertificate = Cert.load(certFile);
            PrivKey myPrivateKey = PrivKey.load(privKeyFile, PRIVKEY_PASSWORD);
            return new KeyPair(myCertificate, myPrivateKey);
        } catch (CertificateException e) {
            throw new ServiceResultException(e);
        } catch (IOException e) {
            try {
                KeyPair keys = CertificateUtils.createIssuerCertificate("SampleCA", 3650, null);
                keys.getCertificate().save(certFile);
                keys.getPrivateKey().save(privKeyFile, PRIVKEY_PASSWORD);
                return keys;
            } catch (Exception e1) {
                throw new ServiceResultException(e1);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceResultException(e);
        } catch (InvalidKeyException e) {
            throw new ServiceResultException(e);
        } catch (InvalidKeySpecException e) {
            throw new ServiceResultException(e);
        } catch (NoSuchPaddingException e) {
            throw new ServiceResultException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new ServiceResultException(e);
        } catch (IllegalBlockSizeException e) {
            throw new ServiceResultException(e);
        } catch (BadPaddingException e) {
            throw new ServiceResultException(e);
        } catch (InvalidParameterSpecException e) {
            throw new ServiceResultException(e);
        }
    }

    public ClientOPCUA() throws ServiceResultException {
        final KeyPair pair = getCert("OPCClient");
        myClient = Client.createClientApplication(pair);
        
        myClient.getApplication().addLocale(Locale.ENGLISH);
        myClient.getApplication().setApplicationName(new LocalizedText("Java Client", Locale.ENGLISH));
        myClient.getApplication().setProductUri("urn:JavaClient");
    }
    
    public ApplicationDescription[] findServers(String url_LDS) throws ServiceResultException{
        ApplicationDescription[] servers = myClient.discoverApplications(url_LDS);

        System.out.println("\n\nHo contattato il Server LDS: " + url_LDS);
        System.out.println("Il numero di server e'�: " + servers.length);
        
        return servers;
    }
    
    public EndpointDescription[] discoverEndpoints(String server_url) throws ServiceResultException{
        // nota: discoverEndpoints e' l'API per il servizio GetEndpoints
        EndpointDescription[] endpoints = myClient.discoverEndpoints(server_url);

        System.out.println("\n\nHo contattato il Server: " + server_url);
        System.out.println("Il numero di endpoint e' " + endpoints.length);

        return endpoints;        
    }
    
    public boolean createSession(String server_url, EndpointDescription endpoint) throws ServiceResultException{
        if(currentSession != null) this.closeSession();
            
        currentSession = myClient.createSessionChannel(server_url, endpoint);
        currentSession.activate();
        System.out.print("\n\nOPC UA JAVA Client: Connessione stabilita con " + server_url + "\n");
        
        return true;
    }
    
    public boolean closeSession() throws ServiceFaultException, ServiceResultException{
        if(currentSession == null) return true;
        
        currentSession.close();
        currentSession.closeAsync();
        currentSession = null;
        
        return true;
    }
    public String getFreeMemory() throws Exception{    
        if(currentSession == null) throw new Exception("Crea una sessione");
        
        int ns = 1; //namespace
        int idNum = 1; //nodeId     
        ReadResponse res = currentSession.Read(null, FreeMemory, TimestampsToReturn.Source,
                new ReadValueId(new NodeId(ns, idNum), Attributes.Value, null, null));
        
        System.out.println("\n\nValore Letto = " + res.getResults()[0].getValue());
        System.out.println("Status Letto = " + res.getResults()[0].getStatusCode());
        System.out.println("Timestamp Source Letto = " + res.getResults()[0].getSourceTimestamp());
                       
        return res.getResults()[0].getValue().toString();
    }
    
}
