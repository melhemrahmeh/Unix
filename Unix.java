package UnixOperatingSystem;
import java.io.*;
import java.util.*;

public class Unix {
    final static UnixBinaryTree tree = new UnixBinaryTree();
    static OSNode current;
    public static void createFile(String name){
        if (current.getLeft() == null){
            tree.addLeft( current, name , false);
        }
        else if (current.getRight() == null){
            tree.addRight(current , name , false);
        }
        else if (current.numChildren()==2){
            System.out.println("error: capacity exceeded");
        }
    }
    public static void changeToRoot(){
        current = tree.root();
    }
    public static void createDirectory(String name){
        if (current.getLeft() == null){
            tree.addLeft( current, name , true);
        }
        else if (current.getRight() == null){
            tree.addRight(current , name , true);
        }
    }
    public static void changeDirectory(String name){
        if (current.getLeft().getCommand().equals(name) && current.getDir()){
            current = current.getLeft();
        }
        else if (current.getRight().getCommand().equals(name) && current.getDir()){
            current = current.getRight();
        }

    }
    public static void removeFile(String filename){
        if (current.getLeft().getCommand().equals(filename)){
            current.setLeft(null);
        }
        else if (current.getRight().getCommand().equals(filename)){
            current.setRight(null);
        }
    }
    public static void removeDirectory(String DirName){
        if (current.getLeft().getCommand().equals(DirName)){
            current.setLeft(null);
        }
        else if (current.getRight().getCommand().equals(DirName)){
            current.setRight(null);
        }
    }

    public static int quota(List<OSNode> list){
        int quota = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDir()){
                quota+=3;
            }
            else {
                quota+=7;
            }
        }
        return quota;
    }
    public static void main(String[] args) throws FileNotFoundException {
        tree.addRoot();
        current = tree.root();
        Scanner input = new Scanner(new File("src//UnixOperatingSystem//command.txt"));
        ArrayList<String> commands = new ArrayList<>();
        while (input.hasNext()){
            commands.add(input.nextLine());
        }

        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).startsWith("mkdir")){
                createDirectory(commands.get(i).substring(6));
            }
            else if (commands.get(i).startsWith("quota")){
                quota(tree.inorder());
            }
            else if (commands.get(i).startsWith("createfile")){
                if (commands.get(i).startsWith("createfile") && commands.get(i).equals("createfile")){
                    System.out.println("error: cannot create file");
                }
                else {
                createFile(commands.get(i).substring(11));
                }
            }
            else if (commands.get(i).equals("cd")){
                changeToRoot();
            }
            else if (commands.get(i).startsWith("cd")){
                changeDirectory(commands.get(i).substring(3));
            }
        }
        tree.printSideways();
        System.out.println("\n\nQuota: "+quota(tree.inorder()) + " Bytes");
    }
}
