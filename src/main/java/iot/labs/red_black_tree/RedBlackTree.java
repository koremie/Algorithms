package main.java.iot.labs.red_black_tree;

public class RedBlackTree<T extends Comparable> {
    private Node root;

    private class Node {
	public T data = null;
	public Node left = null;
	public Node right = null;
	public Node parent = null;
	public Color color;

	public Node(T data) {
	    this.data = data;
	    color = Color.RED;
	}
    }

    public RedBlackTree() {
	root = null;
    }

    public void inOrder() {
	inOrderHelper(this.root);
    }

    private void inOrderHelper(Node node) {
	if (node != null) {
	    inOrderHelper(node.left);
	    System.out.println(node.data);
	    inOrderHelper(node.right);
	}
    }

    int tabs = 0;
    StringBuilder tree;

    public void printTree() {
	printHelper(this.root, "", true);
    }

    private void printHelper(Node root, String indent, boolean last) {
	if (root != null) {
	    System.out.print(indent);
	    if (last) {
		System.out.print("R----");
		indent += "   ";
	    } else {
		System.out.print("L----");
		indent += "|  ";
	    }

	    System.out.println(root.data + "(" + root.color + ")");
	    printHelper(root.left, indent, false);
	    printHelper(root.right, indent, true);
	}
    }

    public void print() {
	tree = new StringBuilder();
	printHelper(this.root);
	System.out.print(tree.toString());
    }

    private void printHelper(Node node) {

	if (node.left != null) {
	    printHelper(node.left);
	}
	for (int i = 0; i < tabs; i++) {
	    System.out.print("  ");
	}
	System.out.println(node.data + " " + node.color);
	if (node.right != null) {
	    printHelper(node.right);
	    tabs += 2;
	}
	tabs -= 2;
    }

    private void leftRotate(Node current) {
	Node leftRotateNode = current.right;

	current.right = leftRotateNode.left;
	if (leftRotateNode.left != null) {
	    leftRotateNode.left.parent = current;
	}
	leftRotateNode.parent = current.parent;
	if (current.parent == null) {
	    this.root = leftRotateNode;
	    leftRotateNode.parent = null;
	} else if (current.parent.left == current) {
	    current.parent.left = leftRotateNode;
	} else {
	    current.parent.right = leftRotateNode;
	}

	leftRotateNode.left = current;
	current.parent = leftRotateNode;
    }

    private void rightRotate(Node current) {
	Node rightRotateNode = current.left;

	current.left = rightRotateNode.right;
	if (rightRotateNode.right != null) {
	    rightRotateNode.right.parent = current;
	}
	rightRotateNode.parent = current.parent;
	if (current.parent == null) {
	    this.root = rightRotateNode;
	    rightRotateNode.parent = null;
	} else if (current.parent.right == current) {
	    current.parent.right = rightRotateNode;
	} else {
	    current.parent.left = rightRotateNode;
	}

	rightRotateNode.right = current;
	current.parent = rightRotateNode;
    }

    private Node getGrandparent(Node node) {
	if (node == null || node.parent == null) {
	    return null;
	} else {
	    return node.parent.parent;
	}
    }

    private Node getUncle(Node node) {
	if (getGrandparent(node) == null) {
	    return null;
	} else if (getGrandparent(node).left == node.parent) {
	    return getGrandparent(node).right;
	} else {
	    return getGrandparent(node).left;
	}
    }

    public void insert(T data) {
	Node nodeToInsert = new Node(data);

	Node previous = null;
	Node current = this.root;

	while (current != null) {
	    previous = current;
	    if (nodeToInsert.data.compareTo(current.data) < 0) {
		current = current.left;
	    } else if (nodeToInsert.data.compareTo(current.data) > 0) {
		current = current.right;
	    } else {
		System.out.println("Such data is already present in da tree");
		break;
	    }
	}

	nodeToInsert.parent = previous;
	if (previous == null) {
	    root = nodeToInsert;
	    root.color = Color.BLACK;
	} else if (nodeToInsert.data.compareTo(previous.data) < 0) {
	    previous.left = nodeToInsert;
	} else {
	    previous.right = nodeToInsert;
	}

	if (nodeToInsert.parent == null || nodeToInsert.parent.parent == null) {
	    return;
	}

	insertCase1(nodeToInsert);
    }

    private void insertCase1(Node node) {
	if (node.parent == null) {
	    node.color = Color.BLACK;
	} else {
	    insertCase2(node);
	}
    }

    private void insertCase2(Node node) {
	if (node.parent.color == Color.BLACK) {
	    return;
	} else {
	    insertCase3(node);
	}
    }

    private void insertCase3(Node node) {
	if (getUncle(node) == null) {
	    insertCase4(node);
	} else if (getUncle(node).color == Color.RED && node.parent.color == Color.RED) {
	    getGrandparent(node).color = Color.RED;
	    getUncle(node).color = Color.BLACK;
	    node.parent.color = Color.BLACK;

	    insertCase1(getGrandparent(node));
	}
    }

    private void insertCase4(Node node) {
	if (node == node.parent.right && node.parent == getGrandparent(node).left) {
	    leftRotate(node.parent);

	    node = node.left;
	} else if (node == node.parent.left && node.parent == getGrandparent(node).right) {
	    rightRotate(node.parent);

	    node = node.right;
	}
	insertCase5(node);
    }

    private void insertCase5(Node node) {
	node.parent.color = Color.BLACK;
	getGrandparent(node).color = Color.RED;

	if (node == node.parent.left && node.parent == getGrandparent(node).left) {
	    rightRotate(getGrandparent(node));
	} else {
	    leftRotate(getGrandparent(node));
	}
    }
}