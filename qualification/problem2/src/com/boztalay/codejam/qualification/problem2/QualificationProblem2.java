package com.boztalay.codejam.qualification.problem2;

import com.sun.swing.internal.plaf.basic.resources.basic_it;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QualificationProblem2 {

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

        QualificationProblem2 prob2 = new QualificationProblem2();
        prob2.solve(input, writer);
        writer.close();
    }

    private void solve(String input, PrintWriter writer) {
        TestCase[] testCases = readCases(input);

        for(int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];

            StringBuilder builder = new StringBuilder();
            builder.append("Case #").append((i + 1)).append(": ");

            double currentProductionRate = 2.0;
            double elapsedTime = 0.0;
            while(true) {
                double timeToNextBuilding = testCase.buildingCost / currentProductionRate;
                double timeToGoalAfterNextBuilding = timeToNextBuilding + (testCase.cookieGoal / (currentProductionRate + testCase.buildingProductionRate));
                double timeToGoal = testCase.cookieGoal / currentProductionRate;

                if(timeToGoal < timeToGoalAfterNextBuilding) {
                    elapsedTime += timeToGoal;
                    break;
                } else {
                    elapsedTime += timeToNextBuilding;
                    currentProductionRate += testCase.buildingProductionRate;
                }
            }

            builder.append(String.format("%.7f", elapsedTime));
            writer.println(builder.toString());
        }
    }

    private TestCase[] readCases(String input) {
        String[] inputLines = input.split("\\r?\\n");
        int numCases = Integer.valueOf(inputLines[0]);

        TestCase[] testCases = new TestCase[numCases];

        for(int i = 0; i < numCases; i++) {
            testCases[i] = new TestCase();
            String[] numbers = inputLines[i + 1].split(" ");
            testCases[i].buildingCost = Double.valueOf(numbers[0]);
            testCases[i].buildingProductionRate = Double.valueOf(numbers[1]);
            testCases[i].cookieGoal = Double.valueOf(numbers[2]);
        }

        return testCases;
    }

    private class TestCase {
        public double buildingCost;
        public double buildingProductionRate;
        public double cookieGoal;

        @Override
        public String toString() {
            return (buildingCost + " " + buildingProductionRate + " " + cookieGoal);
        }
    }
}
