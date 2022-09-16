package main.java.iot.labs.avl_tree;

public class AVLTree<T extends Comparable<T>> {
    private class Node {
	public Node left;
	public Node right;
	public T data;
	public int height;

	public Node(T data) {
	    this.data = data;
	    height = 1;
	}
    }

    private Node root;

    private int getHeight(Node node) {
	if (node == null)
	    return 0;
	return node.height;
    }

    private int getBalance(Node node) {
	if (node == null)
	    return 0;
	return getHeight(node.left) - getHeight(node.right);
    }

    private Node leftRotate(Node node) {
	Node leftRotateNode = node.right;
	Node adoptedNode = leftRotateNode.left;

	leftRotateNode.left = node;
	node.right = adoptedNode;

	node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
	leftRotateNode.height = Math.max(getHeight(leftRotateNode.left), getHeight(leftRotateNode.right)) + 1;

	return leftRotateNode;
    }

    private Node rightRotate(Node node) {
	Node rightRotateNode = node.left;
	Node adoptedNode = rightRotateNode.right;

	rightRotateNode.right = node;
	node.left = adoptedNode;

	node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
	rightRotateNode.height = Math.max(getHeight(rightRotateNode.left), getHeight(rightRotateNode.right)) + 1;

	return rightRotateNode;
    }

    private Node insertHelper(Node node, T data) {

	if (node == null)
	    return (new Node(data));

	if (data.compareTo(node.data) < 0)
	    node.left = insertHelper(node.left, data);
	else if (data.compareTo(node.data) > 0)
	    node.right = insertHelper(node.right, data);
	else
	    return node;

	node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

	int balance = getBalance(node);

	if (balance > 1 && data.compareTo(node.left.data) < 0) {
	    return rightRotate(node);
	}

	if (balance > 1 && data.compareTo(node.left.data) > 0) {
	    node.left = leftRotate(node.left);
	    return rightRotate(node);
	}

	if (balance < -1 && data.compareTo(node.right.data) > 0) {
	    return leftRotate(node);
	}

	if (balance < -1 && data.compareTo(node.right.data) < 0) {
	    node.right = rightRotate(node.right);
	    return leftRotate(node);
	}

	return node;
    }

    private Node getMinValue(Node node) {
	while (node.left != null)
	    node = node.left;
	return node;
    }

    private Node deleteHelper(Node node, T data) {
	if (node == null)
	    return node;

	if (data.compareTo(node.data) < 0)
	    node.left = deleteHelper(node.left, data);
	else if (data.compareTo(node.data) > 0)
	    node.right = deleteHelper(node.right, data);
	else {
	    if (node.left == null || node.right == null) {
		Node substitute;
		if (node.left == null)
		    substitute = node.right;
		else
		    substitute = node.left;

		if (substitute == null)
		    node = null;
		else
		    node = substitute;
	    } else {
		Node successor = getMinValue(node.right);
		node.data = successor.data;
		node.right = deleteHelper(node.right, successor.data);
	    }

	    if (node == null)
		return node;

	    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

	    int balance = getBalance(node);

	    if (balance > 1 && getBalance(node.left) >= 0)
		return rightRotate(node);

	    if (balance > 1 && getBalance(node.left) < 0) {
		node.left = leftRotate(node.left);
		return rightRotate(node);
	    }

	    if (balance < -1 && getBalance(node.right) <= 0)
		return leftRotate(node);

	    if (balance < -1 && getBalance(node.right) > 0) {
		node.right = rightRotate(node.right);
		return leftRotate(node);
	    }
	}
	return node;
    }

    public void insert(T data) {
	root = insertHelper(root, data);
    }

    public void delete(T data) {
	root = deleteHelper(root, data);
    }

    int tabs = 1;

    public void print() {
	System.out.println();
	printHelper(this.root);
    }

    private void printHelper(Node node) {

	if (node.right != null) {
	    tabs += 1;
	    printHelper(node.right);
	}

	for (int i = 0; i < tabs; i++) {
	    System.out.print("\t");
	}
	System.out.print("(" + node.data + ")\n");

	if (node.left != null) {
	    tabs += 1;
	    printHelper(node.left);
	}
	tabs -= 1;
    }
}
