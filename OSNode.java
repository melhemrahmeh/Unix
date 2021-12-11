package UnixOperatingSystem;
import java.util.*;

public class OSNode {
    private String command;      // data stored at this node
    private boolean isDirectory;  //if it's a directory or no
    private OSNode left;    // reference to left subtree
    private OSNode right;   // reference to right subtree
    private OSNode parent;     // a reference to the parent node (if any)


    // Constructs a leaf node with the given data.
    public OSNode(String data) {
        this(data, null, null, null , false);
    }

    // Constructs a branch node with the given data and links.
    public OSNode(String data, OSNode above, OSNode leftChild, OSNode rightChild , boolean isDir) {
        command = data;
        parent = above;
        left = leftChild;
        right = rightChild;
        isDirectory = isDir;
    }

    public String getCommand() { return command; }
    public OSNode getParent() { return parent; }
    public OSNode getLeft() { return left; }
    public OSNode getRight() { return right; }

    public boolean getDir() { return isDirectory; }

    // update methods
    public void setCommand(String com) { command = com; }
    public void setParent(OSNode parentNode) { parent = parentNode; }
    public void setLeft(OSNode leftChild) { left = leftChild; }
    public void setRight(OSNode rightChild) { right = rightChild; }

    public void setDirectory(boolean directory) { isDirectory = directory; }

    public boolean isInternal() { return numChildren() > 0; }

    public boolean isExternal() { return numChildren() == 0; }

    public boolean isRoot() { return parent == null; }

    public int numChildren() {
        int count=0;
        if (left != null)
            count++;
        if (right != null)
            count++;
        return count;
    }

    public OSNode sibling() {
        if (parent == null) {
            return null;
        }// p must be the root
        if (this == parent.left)  {                          // p is a left child
            return parent.right;
        }                        // (right child might be null)
        else{                                              // p is a right child
            return parent.left;                            // (left child might be null)
        }
    }

    public List<OSNode> children() {
        List<OSNode> snapshot = new ArrayList<>(2);    // max capacity of 2
        if (left != null)
            snapshot.add(left);
        if (right != null)
            snapshot.add(right);
        return snapshot;
    }
}
