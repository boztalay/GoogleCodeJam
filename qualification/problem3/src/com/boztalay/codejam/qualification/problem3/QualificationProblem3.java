package com.boztalay.codejam.qualification.problem3;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QualificationProblem3 {

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

        QualificationProblem3 prob3 = new QualificationProblem3();
        prob3.solve(input, writer);
        writer.close();
    }

    private void solve(String input, PrintWriter writer) {
        TestCase[] testCases = readCases(input);

        for(int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];

            StringBuilder builder = new StringBuilder();
            builder.append("Case #").append((i + 1)).append(":\n");

            int numSpaces = testCase.width * testCase.height;
            int emptySpaces = numSpaces - testCase.numMines;

            int emptySpacesMin = 4;
            if((testCase.width == 1 || testCase.height == 1) && (testCase.width < 3 || testCase.height < 3)) {
                emptySpacesMin = 2;
            } else if(testCase.width == 2 && testCase.height == 2) {
                emptySpacesMin = 2;
            }

            if(emptySpaces < emptySpacesMin) {
                builder.append("Impossible\n");
            } else {
                char[][] field = new char[testCase.width][testCase.height];
                for(int j = 0; j < field.length; j++) {
                    for(int k = 0; k < field[j].length; k++) {
                        field[j][k] = '*';
                    }
                }

                int squareSideSize = (int)Math.ceil(Math.sqrt(emptySpaces));
                int emptySpacesPrinted = 0;
                int curX = 0;
                int curY = 0;
                while(emptySpacesPrinted < emptySpaces) {
                    field[curX][curY] = '.';
                    emptySpacesPrinted++;

                    curX++;
                    if(curX >= squareSideSize || curX >= testCase.width) {
                        curY++;
                        if(curY >= testCase.height) {
                            break;
                        }

                        curX = 0;
                    }
                }

                field[0][0] = 'c';
                for(char[] thing : field) {
                    for(char thingy : thing) {
                        builder.append(thingy);
                    }
                    builder.append('\n');
                }
            }

            writer.print(builder.toString());
        }
    }

    private TestCase[] readCases(String input) {
        String[] inputLines = input.split("\\r?\\n");
        int numCases = Integer.valueOf(inputLines[0]);

        TestCase[] testCases = new TestCase[numCases];

        for(int i = 0; i < numCases; i++) {
            testCases[i] = new TestCase();
            String[] numbers = inputLines[i + 1].split(" ");
            testCases[i].height = Integer.valueOf(numbers[0]);
            testCases[i].width = Integer.valueOf(numbers[1]);
            testCases[i].numMines = Integer.valueOf(numbers[2]);
        }

        return testCases;
    }

    private class TestCase {
        public int height;
        public int width;
        public int numMines;

        @Override
        public String toString() {
            return (height + " " + width + " " + numMines);
        }
    }

}