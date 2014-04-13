package com.boztalay.codejam.qualification.problem1;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QualificationProblem1 {

    public static void main(String[] args) {
        String input;
        PrintWriter writer;

        try {
            String inFilePath = args[0];
            byte[] encodedFile = Files.readAllBytes(Paths.get(inFilePath));
            input = new String(encodedFile);
            writer = new PrintWriter(inFilePath.replace(".in", ".out"));
        } catch(Exception e) {
            System.out.println("Whoops, couldn't open the input file!");
            return;
        }

        QualificationProblem1 prob1 = new QualificationProblem1();
        prob1.solve(input, writer);
        writer.close();
    }

    private void solve(String input, PrintWriter writer) {
        TestCase[] testCases = readCases(input);

        for(int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];

            StringBuilder builder = new StringBuilder();
            builder.append("Case #").append((i + 1)).append(": ");

            int[] firstRow = testCase.firstCardGrid[testCase.firstAnswer];
            int[] secondRow = testCase.secondCardGrid[testCase.secondAnswer];

            int candidateCard = -1;
            outerLoop:
            for(int j = 0; j < firstRow.length; j++) {
                for(int k = 0; k < secondRow.length; k++) {
                    if(firstRow[j] == secondRow[k]) {
                        if(candidateCard > 0) {
                            candidateCard = -2;
                            break outerLoop;
                        }
                        candidateCard = firstRow[j];
                    }
                }
            }

            if(candidateCard == -1) {
                builder.append("Volunteer cheated!");
            } else if(candidateCard == -2) {
                builder.append("Bad magician!");
            } else {
                builder.append(candidateCard);
            }

            writer.println(builder.toString());
        }
    }

    private TestCase[] readCases(String input) {
        String[] inputLines = input.split("\\r?\\n");
        int numCases = Integer.valueOf(inputLines[0]);

        TestCase[] testCases = new TestCase[numCases];

        int fileIndex = 1;
        for(int i = 0; i < numCases; i++) {
            testCases[i] = new TestCase();
            fileIndex = readCaseAndAdvanceIndex(inputLines, testCases[i], fileIndex);
        }

        return testCases;
    }

    private int readCaseAndAdvanceIndex(String[] inputLines, TestCase testCase, int i) {
        testCase.firstAnswer = Integer.valueOf(inputLines[i++]) - 1; //Accommodating for 1-indexed answers

        for(int j = 0; j < 4; j++) {
            String[] cardStrings = inputLines[i++].split(" ");

            for(int k = 0; k < 4; k++) {
                testCase.firstCardGrid[j][k] = Integer.valueOf(cardStrings[k]);
            }
        }

        testCase.secondAnswer = Integer.valueOf(inputLines[i++]) - 1; //Accommodating for 1-indexed answers

        for(int j = 0; j < 4; j++) {
            String[] cardStrings = inputLines[i++].split(" ");

            for(int k = 0; k < 4; k++) {
                testCase.secondCardGrid[j][k] = Integer.valueOf(cardStrings[k]);
            }
        }

        return i;
    }

    private class TestCase {
        public int firstAnswer;
        public int[][] firstCardGrid;
        public int secondAnswer;
        public int[][] secondCardGrid;

        public TestCase() {
            firstCardGrid = new int[4][4];
            secondCardGrid = new int[4][4];
        }

        @Override
        public String toString() {
            String thisString = firstAnswer + "\n";
            for(int[] thing : firstCardGrid) {
                for(int thingy : thing) {
                    thisString += thingy + " ";
                }
                thisString += "\n";
            }

            thisString += secondAnswer + "\n";
            for(int[] thing : secondCardGrid) {
                for(int thingy : thing) {
                    thisString += thingy + " ";
                }
                thisString += "\n";
            }
            return thisString;
        }
    }
}
