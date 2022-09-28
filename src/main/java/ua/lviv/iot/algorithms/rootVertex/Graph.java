package ua.lviv.iot.algorithms.rootVertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Graph {

    public int getRootVertex(File file) {
	ArrayList<ArrayList<Integer>> graph = readGraphFromFile(file);
	for (int i = 0; i < graph.size(); i++) {
	    Stack<Integer> vertexStack = new Stack<>();
	    ArrayList<Integer> visitedVertices = new ArrayList<>();
	    vertexStack.push(i);

	    while (vertexStack.isEmpty() == false) {
		int currentVertex = vertexStack.pop();
		if (visitedVertices.contains(currentVertex) == false) {
		    visitedVertices.add(currentVertex);
		    ArrayList<Integer> neighbours = graph.get(currentVertex);

		    for (int vertex : neighbours) {
			if (visitedVertices.contains(vertex) == false)
			    vertexStack.push(vertex);
		    }
		}
	    }
	    if (visitedVertices.size() == graph.size())
		return i;
	}
	return -1;
    }

    public ArrayList<ArrayList<Integer>> readGraphFromFile(File file) {
	ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
	try (Scanner scanner = new Scanner(file)) {
	    while (scanner.hasNextLine()) {
		ArrayList<Integer> element = Pattern.compile("(\\d+)").matcher(scanner.nextLine()).results()
			.map(MatchResult::group).map(Integer::valueOf).collect(Collectors.toCollection(ArrayList::new));
		int index = element.get(0);
		element.remove(0);
		graph.add(index, element);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return graph;
    }

    public void writeToFile(int vertexIndex) {
	try (FileWriter writer = new FileWriter("output.txt", Charset.defaultCharset())) {
	    writer.write("The root vertex is: " + vertexIndex);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	Graph graphManager = new Graph();

	graphManager.writeToFile(graphManager.getRootVertex(
		new File(Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources", "input.txt")));
    }
}