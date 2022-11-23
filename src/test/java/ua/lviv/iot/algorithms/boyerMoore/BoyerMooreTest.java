package ua.lviv.iot.algorithms.boyerMoore;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoyerMooreTest {

    private DeezNuts deezNuts;

    @BeforeEach
    void setUp() {
        deezNuts = new DeezNuts();
    }

    @Test
    void naturalTextTest() {
        System.out.println("k scenario");
        System.out.println();
        
        long startTime = System.currentTimeMillis();
        deezNuts.doBoyerMoore(
                new File(Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources", "BoyerMoore.1.txt"),
                "variabl");
        long endTime = System.currentTimeMillis();
        
        System.out.println(
                "The time of execution of above program is: " + (endTime - startTime) + " ms");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    @Test
    void syntheticTextBestScenarioTest() {
        System.out.println("best scenario");
        System.out.println();
        
        long startTime = System.currentTimeMillis();
        deezNuts.doBoyerMoore(
                new File(Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources", "BoyerMoore.2.txt"),
                "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        long endTime = System.currentTimeMillis();

        System.out.println(
                "The time of execution of above program is: " + ((endTime - startTime)) + " ms");
        System.out.println("-------------------------------------------------------------------------------------");
    }
    
    @Test
    void syntheticTextWorstScenarioTest() {
        System.out.println("worst scenario");
        System.out.println();
        
        long startTime = System.currentTimeMillis();
        deezNuts.doBoyerMoore(
                new File(Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources", "BoyerMoore.3.txt"),
                "kaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        long endTime = System.currentTimeMillis();

        System.out.println(
                "The time of execution of above program is: " + ((endTime - startTime)) + " ms");
        System.out.println("-------------------------------------------------------------------------------------");
    }

}
