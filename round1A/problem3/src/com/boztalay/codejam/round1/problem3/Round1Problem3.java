package com.boztalay.codejam.round1.problem3;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Round1Problem3 {

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

        Round1Problem3 prob3 = new Round1Problem3();
        prob3.solve(input, writer);
        writer.close();
    }

    private void solve(String input, PrintWriter writer) {
        TestCase[] testCases = readCases(input);

        int numGood = 0;
        int numBad = 0;

        for(int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];

            StringBuilder builder = new StringBuilder();
            builder.append("Case #").append((i + 1)).append(": ");

            int thirdSize = testCase.permutation.length / 3;
            double[] thirdSums = new double[3];
            thirdSums[0] = 0;
            thirdSums[1] = 0;
            thirdSums[2] = 0;

            int offset = 0;
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < thirdSize; k++) {
                    thirdSums[j] += testCase.permutation[k + offset];
                }
                offset += thirdSize;
            }

            double delta1 = Math.abs(thirdSums[0] - thirdSums[1]) / thirdSums[0];
            double delta2 = Math.abs(thirdSums[0] - thirdSums[2]) / thirdSums[0];
            double deltadelta = Math.abs(delta1 - delta2);

            String goodBad;
            if(thirdSums[0] < thirdSums[1] && (delta1 >= 0.03 && delta2 >= 0.03 || deltadelta >= 0.05)) {
                goodBad = "BAD";
                numBad++;
            } else {
                goodBad = "GOOD";
                numGood++;
            }

            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println(thirdSums[0] + " " + thirdSums[1] + "\t" + df.format(delta1) + "\t" + df.format(delta2) + "\t" + df.format(deltadelta) + "\t" + goodBad);

            builder.append(goodBad).append("\n");

            writer.print(builder.toString());
        }

        System.out.println();
        System.out.println("Guessed good: " + (((double)numGood / (double)testCases.length) * 100.0) + "%");
        System.out.println("Guessed  bad: " + (((double)numBad / (double)testCases.length) * 100.0) + "%");
    }

    private TestCase[] readCases(String input) {
        String[] inputLines = input.split("\\r?\\n");
        int numCases = Integer.valueOf(inputLines[0]);

        TestCase[] testCases = new TestCase[numCases];

        for(int i = 0; i < numCases; i++) {
            testCases[i] = new TestCase();
            int permLen = Integer.valueOf(inputLines[(i * 2) + 1]);
            testCases[i].permutation = new int[permLen];
            String[] numberStrs = inputLines[(i * 2) + 2].split(" ");
            for(int j = 0; j < numberStrs.length; j++) {
                testCases[i].permutation[j] = Integer.valueOf(numberStrs[j]);
            }
        }

        return testCases;
    }

    private class TestCase {
        public int[] permutation;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for(int num : permutation) {
                builder.append(num).append(' ');
            }

            return builder.toString();
        }
    }

}