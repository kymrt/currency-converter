package com.example.CurrencyConverter.FileManager;

import com.example.CurrencyConverter.Model.AmountModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

class TxtFileHandlerTest {
    private static final String DEFAULT_BASE_CURRENCY = "EUR";
    TxtFileHandler fileHandler;
    String filePath;

    @BeforeEach
    void setUp() {
        String rootPath = System.getProperty("user.dir");
        filePath = rootPath + "\\src\\test\\java\\com\\example\\CurrencyConverter\\Samples";
        fileHandler =  TxtFileHandler.getInstance(filePath);
    }

    @Test
    void readFile() {
        try {
            List<AmountModel> inputValues = fileHandler.readFile(DEFAULT_BASE_CURRENCY);
            List<AmountModel> expectedValues =new ArrayList<>();
            expectedValues.add(new AmountModel(929.19,  "EUR"));
            expectedValues.add(new AmountModel(21.1,  "DKK"));
            expectedValues.add(new AmountModel(7829.9989,  "CAD"));
            for (int i = 0; i < inputValues.size(); i++) {
                AmountModel resultItem = inputValues.get(i);
                AmountModel expectedItem = expectedValues.get(i);
                assert(resultItem.getAmount().equals(expectedItem.getAmount()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeOutputFile() {
        List<AmountModel> outputValues =new ArrayList<>();
        outputValues.add(new AmountModel(860.3834805000001));
        outputValues.add(new AmountModel(157.49063210000003));
        outputValues.add(new AmountModel(11450.9349113116));
        fileHandler.writeOutputFile(outputValues);
        File file = new File(filePath + "\\Output.txt");
        assertTrue("File exists?",file.exists());
    }
}