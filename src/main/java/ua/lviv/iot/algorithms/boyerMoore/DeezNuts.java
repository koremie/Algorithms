package ua.lviv.iot.algorithms.boyerMoore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DeezNuts {
    public void doBoyerMoore(File text, String pattern) {
        Map<Character, Integer> stopHashJavaDictionaryMap = new HashMap<>();
        char[] charPattern = pattern.toCharArray();

        for (int i = charPattern.length - 1; i >= 0; i--) {
            if (!stopHashJavaDictionaryMap.containsKey(charPattern[i])) {
                stopHashJavaDictionaryMap.put(charPattern[i], i);
            }
        }

        try (Scanner lineScanner = new Scanner(text)) {
            int lineCounter = 0;
            while (lineScanner.hasNextLine()) {
                lineCounter++;
                char[] lineChar = lineScanner.nextLine().toCharArray();
                int currentPos = charPattern.length - 1;
                int i = currentPos;
                while (currentPos < lineChar.length) {
                    Boolean patternMatched = true;
                    for (; i >= 0; i--) {
                        if (charPattern[i] != lineChar[currentPos - charPattern.length + 1 + i]) {
                            patternMatched = false;
                            break;
                        }
                    }

                    if (patternMatched) {
                        i++;
                        System.out.println("Pattern \"" + pattern + "\" matched at: " + lineCounter + ":"
                                + (currentPos - charPattern.length + 2) + " (in " + text.getName() + ")");
                    }

                    int shift = i - stopHashJavaDictionaryMap
                            .getOrDefault(lineChar[currentPos - charPattern.length + 1 + i], -1);
                    currentPos += shift > 0 ? shift : 1;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}