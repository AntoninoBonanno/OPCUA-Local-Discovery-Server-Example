/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biuso.bonanno.supportClass;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.core.ReferenceDescription;

/**
 *
 * @author Bonanno Antonino, Biuso Mario
 */
public class TreeBrowseObject {
    private final String displayName;
    private final NodeClass nodeClass;
    private final ExpandedNodeId nodeId;

    public TreeBrowseObject(ReferenceDescription referenceDescription) {
        this.displayName = referenceDescription.getDisplayName().getText();
        this.nodeClass = referenceDescription.getNodeClass();
        this.nodeId = referenceDescription.getNodeId();
    }

    public String getDisplayName() {
        return displayName;
    }

    public NodeClass getNodeClass() {
        return nodeClass;
    }

    public ExpandedNodeId getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return displayName;
    }
    
}
