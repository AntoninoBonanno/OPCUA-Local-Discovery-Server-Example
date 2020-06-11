/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno.supportClass;

import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.utils.ObjectUtils;

/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
public class TreeServerObject {
    
    private final String ApplicationUri;
    private final String ProductUri;
    private final String ApplicationName;
    private final String ApplicationType;
    private final String DiscoveryUrl;

    public TreeServerObject(ApplicationDescription applicationDescription) {
        this.ApplicationUri = applicationDescription.getApplicationUri();
        this.ProductUri = applicationDescription.getProductUri();
        this.ApplicationName = applicationDescription.getApplicationName().toString(); //.getText()
        this.ApplicationType = applicationDescription.getApplicationType().name();
        this.DiscoveryUrl = applicationDescription.getDiscoveryUrls()[0];
    }

    public String getApplicationUri() {
        return ApplicationUri;
    }

    public String getProductUri() {
        return ProductUri;
    }

    public String getApplicationName() {
        return ApplicationName;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public String getDiscoveryUrl() {
        return DiscoveryUrl;
    }
	
    @Override
    public String toString() {
        return this.ProductUri;
    }

    public String toStringAll() {
        return "ApplicationDescription: "+ObjectUtils.printFieldsDeep(this);
    }
}
