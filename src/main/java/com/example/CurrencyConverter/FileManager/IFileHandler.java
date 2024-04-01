package com.example.CurrencyConverter.FileManager;

import com.example.CurrencyConverter.Model.AmountModel;

import java.io.IOException;
import java.util.List;

// File operations interface
public interface IFileHandler {

    // Method to read from file
    List<AmountModel> readFile(String defaultCurrency) throws IOException;

    // Method to write to file
    void writeOutputFile(List<AmountModel> values);
}
