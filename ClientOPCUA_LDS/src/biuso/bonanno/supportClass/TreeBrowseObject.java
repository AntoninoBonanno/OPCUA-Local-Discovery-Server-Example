/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno.supportClass;

import org.opcfoundation.ua.core.ReferenceDescription;

/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
public class TreeBrowseObject extends ReferenceDescription{
       
    public TreeBrowseObject(ReferenceDescription rd) {        
        super(rd.getReferenceTypeId(), rd.getIsForward(), rd.getNodeId(),rd.getBrowseName(), rd.getDisplayName(), rd.getNodeClass(), rd.getTypeDefinition());
    }

    public String getReferenceDescriptionString() {
    	return super.toString();
    }

    @Override
    public String toString() {
        return this.getDisplayName().getText();
    }
    
}
