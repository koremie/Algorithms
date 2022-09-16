package main.java.iot.labs.avl_tree;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
	AVLTree<Integer> avlTreee = new AVLTree<>();

	int treSize = ThreadLocalRandom.current().nextInt(0, 100);

	for (int i = 0; i < treSize; i++) {
	    avlTreee.insert(ThreadLocalRandom.current().nextInt(0, 1001));
	}

	avlTreee.print();
    }
}
