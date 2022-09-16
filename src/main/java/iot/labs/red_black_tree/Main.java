package main.java.iot.labs.red_black_tree;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
	RedBlackTree<Integer> redBlackTreee = new RedBlackTree<>();

	int treSize = ThreadLocalRandom.current().nextInt(0, 100);

	for (int i = 0; i < treSize; i++) {
	    redBlackTreee.insert(ThreadLocalRandom.current().nextInt(0, 1001));
	}

	redBlackTreee.print(System.out);
    }
}
