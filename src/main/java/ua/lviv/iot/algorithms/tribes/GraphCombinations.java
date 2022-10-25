package ua.lviv.iot.algorithms.tribes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphCombinations {

    public ArrayList<ArrayList<Integer>> scanTribesFromInput() {
	int[] firsts;
	int[] seconds;
	ArrayList<ArrayList<Integer>> tribes = new ArrayList<>();

	try (Scanner scanner = new Scanner(System.in)) {
	    System.out.print("Enter number of pairs: ");
	    int pairNum = scanner.nextInt();
	    firsts = new int[pairNum];
	    seconds = new int[pairNum];

	    System.out.println("Enter the pairs:");
	    for (int i = 0; i < pairNum; i++) {
		firsts[i] = scanner.nextInt();
		seconds[i] = scanner.nextInt();
	    }
	}

	Queue<Integer> vertexQueue;
	Set<Integer> visitedVertices;

	while (IntStream.of(firsts).anyMatch(x -> x != -1)) {
	    vertexQueue = new PriorityQueue<>();
	    visitedVertices = new HashSet<>();

	    int root = 0;
	    for (int i : firsts) {
		if (i != -1) {
		    root = i;
		    break;
		}
	    }

	    vertexQueue.add(root);
	    while (!vertexQueue.isEmpty()) {
		int current = vertexQueue.poll();
		visitedVertices.add(current);
		for (int i = 0; i < firsts.length; i++) {
		    if (firsts[i] == current) {
			vertexQueue.add(seconds[i]);
			firsts[i] = -1;
			seconds[i] = -1;
		    } else if (seconds[i] == current) {
			vertexQueue.add(firsts[i]);
			firsts[i] = -1;
			seconds[i] = -1;
		    }
		}
	    }
	    tribes.add(new ArrayList<>(visitedVertices));
	}

	return tribes;
    }

    public ArrayList<Set<Integer>> getPairs(ArrayList<ArrayList<Integer>> tribes) {
	ArrayList<Set<Integer>> pairs = new ArrayList<>();

	for (ArrayList<Integer> tribe : tribes) {
	    for (ArrayList<Integer> anotherTribe : tribes) {
		if (tribe != anotherTribe) {
		    ArrayList<Integer> women = tribe.stream().filter(person -> person % 2 == 0)
			    .collect(Collectors.toCollection(ArrayList::new));
		    ArrayList<Integer> men = tribe.stream().filter(person -> person % 2 == 1)
			    .collect(Collectors.toCollection(ArrayList::new));

		    ArrayList<Integer> otherWomen = anotherTribe.stream().filter(person -> person % 2 == 0)
			    .collect(Collectors.toCollection(ArrayList::new));
		    ArrayList<Integer> otherMen = anotherTribe.stream().filter(person -> person % 2 == 1)
			    .collect(Collectors.toCollection(ArrayList::new));

		    for (int woman : women) {
			for (int anotherMan : otherMen) {
			    Set<Integer> potentialPair = new HashSet<>(Arrays.asList(woman, anotherMan));

			    if (!pairs.contains(potentialPair)) {
				pairs.add(potentialPair);
			    }
			}
		    }

		    for (int man : men) {
			for (int anotherWoman : otherWomen) {
			    Set<Integer> potentialPair = new HashSet<>(Arrays.asList(man, anotherWoman));

			    if (!pairs.contains(potentialPair)) {
				pairs.add(potentialPair);
			    }
			}
		    }

		}
	    }
	}
	return pairs;
    }

    public static void main(String[] args) {
	GraphCombinations main = new GraphCombinations();
	
	ArrayList<Set<Integer>> pairs = main.getPairs(main.scanTribesFromInput());
	System.out.println(pairs.size());
	System.out.println(pairs);
    }
}
