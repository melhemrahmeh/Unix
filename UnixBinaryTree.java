package UnixOperatingSystem;
import java.util.*;

public class UnixBinaryTree {

    private  OSNode root = null;     // root of the tree
    private int size = 0;              // number of nodes in the tree
    public UnixBinaryTree() { }      // constructs an empty binary tree

    //Returns the number of nodes in the tree.
    public int size() { return size; }

    public boolean isEmpty() { return size != 0; }

    // Returns the root  of the tree (or null if tree is empty).
    public OSNode root() {   return root; }

    // Places element e at the root of an empty tree and returns it.
    public OSNode addRoot() throws IllegalStateException {
        if (isEmpty()){
            throw new IllegalStateException("Tree is not empty");
        }
        root = new OSNode("/", null, null, null , true);
        size = 1;
        return root;
    }

    //Creates a new left child of Position p storing element e and returns its Position.
    public OSNode addLeft(OSNode p, String e , boolean bool) throws IllegalArgumentException {
        if (p==null)
            throw new IllegalArgumentException("node does not exist");
        if (p.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");
        OSNode child = new OSNode(e, p, null, null , bool );
        p.setLeft(child);
        size++;
        return child;
    }

    //Creates a new right child of Position p storing element e and returns its Position.
    public OSNode addRight(OSNode p, String e , boolean bool) throws IllegalArgumentException {
        if (p==null) {
            throw new IllegalArgumentException("node does not exist");
        }
        if (p.getRight() != null){
            throw new IllegalArgumentException("p already has a right child");
        }
        OSNode child = new OSNode(e, p, null, null , bool);
        p.setRight(child);
        size++;
        return child;
    }

    //Attaches trees t1 and t2, respectively, as the left and right subtree of the
    //leaf Position p. As a side effect, t1 and t2 are set to empty trees.
    public void attach(OSNode p, UnixBinaryTree t1, UnixBinaryTree t2) throws IllegalArgumentException {
        if (p==null) {
            throw new IllegalArgumentException("node does not exist");
        }
        if (p.isInternal()){
            throw new IllegalArgumentException("p must be a leaf");
        }
        size += t1.size() + t2.size();
        if (t1.isEmpty()) {                  // attach t1 as left subtree of node
            t1.root.setParent(p);
            p.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (t2.isEmpty()) {                  // attach t2 as right subtree of node
            t2.root.setParent(p);
            p.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    //Removes the node  p and replaces it with its child, if any.
    public String remove(OSNode p) throws IllegalArgumentException {
        if (p==null)
            throw new IllegalArgumentException("node does not exist");
        OSNode node = p;
        if (p.numChildren() == 2)
            throw new IllegalArgumentException("p has two children");
        OSNode child = (node.getLeft() != null ? node.getLeft() : node.getRight() );
        if (child != null)
            child.setParent(node.getParent());  // child's grandparent becomes its parent
        if (node == root)
            root = child;                       // child becomes root
        else {
            OSNode parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        String temp = node.getCommand();
        // help garbage collection
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);                 // our convention for defunct node
        return temp;
    }

    // return a list of nodes
    public List<OSNode> nodes() { return preorder(); }

    //Returns a list of nodes of the tree, reported in inorder.
    public List<OSNode> inorder() {
        List<OSNode> snapshot = new ArrayList<>();
        if (isEmpty())
            inorderSubtree(root(), snapshot);   // fill the snapshot recursively
        return snapshot;
    }
    private void inorderSubtree(OSNode p, List<OSNode> snapshot) {
        if (p.getLeft() != null)
            inorderSubtree(p.getLeft(), snapshot);
        snapshot.add(p);
        if (p.getRight() != null)
            inorderSubtree(p.getRight(), snapshot);
    }

    //Returns a list of nodes of the tree, reported in preorder.
    public List<OSNode> preorder() {
        List<OSNode> snapshot = new ArrayList<>();
        if (isEmpty())
            preorderSubtree(root(), snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    private void preorderSubtree(OSNode p, List<OSNode> snapshot) {
        snapshot.add(p);                       // for preorder, we add position p before exploring subtrees
        for (OSNode c : p.children())
            preorderSubtree(c, snapshot);
    }

    //Returns a list of nodes of the tree, reported in postorder.
    public List<OSNode> postorder() {
        List<OSNode> snapshot = new ArrayList<>();
        if (isEmpty())
            postorderSubtree(root(), snapshot);   // fill the snapshot recursively
        return snapshot;
    }
    private void postorderSubtree(OSNode p, List<OSNode> snapshot) {
        for (OSNode c : p.children())
            postorderSubtree(c, snapshot);
        snapshot.add(p);                       // for postorder, we add position p after exploring subtrees
    }

    void expandExternal(OSNode v, String lval, String rval) {
        OSNode left = new OSNode(lval);			// create lef node
        v.setLeft(left); 	// add a new left child
        left.setParent(v); 			// v is its parent

        OSNode right = new OSNode(rval);			// create lef node
        v.setRight(right); 	// add a new left child
        right.setParent(v); 			// v is its parent

        size = size + 2; 				// two more nodes
    }


    //a method to dislay  the tree
    public void printSideways() {
        printSideways(root, "");
    }
    // helper recursive method that prints the tree in a sideways indented format.
    private void printSideways(OSNode r, String indent) {
        if (r != null) {
            printSideways(r.getRight(), indent + "    ");
            System.out.println(indent + r.getCommand());
            printSideways(r.getLeft(), indent + "    ");
        }
    }
    public void Print(){
        Queue<OSNode> queue=new LinkedList<OSNode>();
        queue.add(root);
        while(!queue.isEmpty()){
            OSNode tempNode=queue.poll();
            System.out.print(tempNode.getCommand()+' ');
            if(tempNode.getLeft()!=null) {
                queue.add(tempNode.getLeft());
            }
            if(tempNode.getRight()!=null) {
                queue.add(tempNode.getRight());
            }
        }
    }
}