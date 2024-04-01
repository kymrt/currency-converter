package com.example.CurrencyConverter.FileManager;

import com.example.CurrencyConverter.Model.AmountModel;

import java.io.*;
import java.util.*;

// File operations class for Txt File
public class TxtFileHandler implements IFileHandler {
    private static TxtFileHandler instance;
    private final String filePath;

    private TxtFileHandler(String filePath){
        this.filePath = filePath;
    }

    // Method to get the singleton instance
    public static TxtFileHandler getInstance(String filePath){
        if(instance == null){
            instance = new TxtFileHandler(filePath);
        }
        return instance;
    }

    @Override
    public List<AmountModel> readFile(String defaultCurrency) throws IOException {
        List<AmountModel> amountList = new ArrayList<>();
        String inputFilePath = filePath + "\\Input.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line = reader.readLine();
            while (line != null) {
                String[] valueAndCurrency = line.split(" ");
                if (valueAndCurrency.length == 1 || valueAndCurrency.length == 2) {
                    double amount = Double.parseDouble(valueAndCurrency[0]);
                    String currency = (valueAndCurrency.length == 2) ? valueAndCurrency[1] : defaultCurrency;
                    amountList.add(new AmountModel(amount, currency));
                } else {
                    System.out.println("Invalid format for the conversion.");
                }
                line = reader.readLine();
            }
        } catch (Exception ex) {
            System.out.println("An error occurred." + ex.getMessage());
            throw new IOException(ex);
        }
        return amountList;
    }

    @Override
    public void writeOutputFile(List<AmountModel> values) {
        try {
            String outputFilePath = filePath + "\\Output.txt";
            File outputFile = new File(outputFilePath);
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter writer = new FileWriter(outputFilePath, false);
            for (AmountModel amountModel : values) {
                writer.write(amountModel.getAmount() + " " + amountModel.getCurrency() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("An error occurred." + ex.getMessage());
        }
    }
}
