package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;

import org.json.JSONObject;


public class App 
{
    public static void main(String[] args) {
        Container container = generateContainer();
        writeContainerToJsonFile(container, "container.json");
    }

    public static Container generateContainer() {
        Container container = new Container();
        container.setUuid(generateRandomUuid());
        
        container.setDecimal1(generateRandomDecimal());
        container.setDecimal2(generateRandomDecimal());
        container.setCalculationResult(calculateResult(container.getDecimal1(), container.getDecimal2()));


        return container;
    }

    private static String generateRandomUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid;
    }

    private static double generateRandomDecimal() {
        Random random = new Random();
        double randomNumber = random.nextDouble() * 99 + 1;
        return round(randomNumber, 4);
    }

    private static double round(double value, int scale) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static String calculateResult(double decimal1, double decimal2) {
        double divisor = Math.min(decimal1, decimal2);
        double result = Math.ceil(decimal1 / divisor * 100) / 100;
        return String.valueOf(result);
    }


     private static void writeContainerToJsonFile(Container container, String fileName) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", container.getUuid());
            jsonObject.put("calculationResult", container.getCalculationResult());
            jsonObject.put("decimal1", container.getDecimal1());
            jsonObject.put("decimal2", container.getDecimal2());
            String jsonString = jsonObject.toString();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(jsonString);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
