package org.examples.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.Random;

public class BenchMarkValueOf {

    private static String[] generateRandomStrings(int numberOfStrings, int length) {
        String[] strings = new String[numberOfStrings];
        Random random = new Random();

        for (int j = 0; j < numberOfStrings; j++) {
            strings[j] = getRandomString(length);
        }

        return strings;
    }

    private static String[] generateAllCorrectHttpStatus(int numberOfStrings) {
        String[] strings = new String[numberOfStrings];
        Random random = new Random();

        HttpStatus[] enumvalues = HttpStatus.values();

        for (int j = 0; j < numberOfStrings; j++) {
            var randomIndex = random.nextInt(enumvalues.length);
            strings[j] = enumvalues[randomIndex].name().toLowerCase();
        }

        return strings;
    }

    private static String[] generateRealTraffic(int numberOfStrings, int percentageOfUnknowns) {
        String[] strings = new String[numberOfStrings];
        Random random = new Random();

        HttpStatus[] enumvalues = HttpStatus.values();

        for (int j = 0; j < numberOfStrings; j++) {

            if(random.nextInt(100) + 1 <= percentageOfUnknowns) {
                strings[j] = getRandomString(17);
            } else {
                var randomIndex = random.nextInt(enumvalues.length);
                strings[j] = enumvalues[randomIndex].name();
            }
        }

        return strings;
    }

    private static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            sb.append(randomChar);
        }
        return sb.toString();
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void test_RandomValues_ValueOf(ExecutionPlan plan) {

        for (String str : plan.randomValues) {
            HttpStatus.toCodeAlternative(str);
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void test_RandomValues_Values(ExecutionPlan plan) {

        for (String str : plan.randomValues) {
            HttpStatus.toCode(str);
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void test_AllCorrectEnumValues_ValueOf(ExecutionPlan plan) {

        for (String str : plan.correctEnumValues) {
            HttpStatus.toCodeAlternative(str);
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void test_AllCorrectEnumValues_Values(ExecutionPlan plan) {

        for (String str : plan.correctEnumValues) {
            HttpStatus.toCode(str);
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void test_RealisticLoad_ValueOf(ExecutionPlan plan) {

        for (String str : plan.realTrafficValues) {
            HttpStatus.toCodeAlternative(str);
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void test_RealisticLoad_Values(ExecutionPlan plan) {

        for (String str : plan.realTrafficValues) {
            HttpStatus.toCode(str);
        }

    }

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        public String[] randomValues = generateRandomStrings(5000, 17);

        public String[] correctEnumValues = generateAllCorrectHttpStatus(5000);

        public String[] realTrafficValues = generateRealTraffic(5000, 5);

    }

}