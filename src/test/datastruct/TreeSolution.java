package datastruct;

import java.util.*;
import java.io.*;

class Node {
    Node left;
    Node right;
    int data;
    
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}


public class TreeSolution {

	/*
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
	public static int height(Node node) {
      	// Write your code here.
		if (node == null) 
            return 0; 
        else 
        { 
            /* compute the depth of each subtree */
            int lDepth = height(node.left); 
            System.out.println("lDepth: " + lDepth );
            int rDepth = height(node.right); 
            System.out.println("rDepth: " + rDepth );
   
            /* use the larger one */
            if (lDepth > rDepth) 
                return (lDepth + 1); 
             else 
                return (rDepth + 1); 
        } 
//		
//		return -1;
    }
	
	
	public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        System.out.println("t: "+ t);
        Node root = null;
        System.out.println("-----");
        while(t-- > 0) {
            System.out.println("t: " + t );
            int data = scan.nextInt();
            System.out.println("data: " + data);
            root = insert(root, data);
        }
        scan.close();
        int height = height(root);
        System.out.println("height: " + height);
    }
	
}
