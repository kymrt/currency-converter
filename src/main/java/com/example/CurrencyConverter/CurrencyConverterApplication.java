package com.example.CurrencyConverter;

import com.example.CurrencyConverter.FileManager.IFileHandler;
import com.example.CurrencyConverter.FileManager.TxtFileHandler;
import com.example.CurrencyConverter.Model.AmountModel;
import com.example.CurrencyConverter.Operations.CurrencyOperations;
import com.example.CurrencyConverter.Operations.ICurrencyOperations;
import com.example.CurrencyConverter.Services.FixerCurrencyService;
import com.example.CurrencyConverter.Services.ICurrencyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class CurrencyConverterApplication {

	private static final Properties properties;
	static {
		properties = new Properties();
		try {
			ClassLoader classLoader = FixerCurrencyService.class.getClassLoader();
			InputStream applicationPropertiesStream = classLoader.getResourceAsStream("application.properties");
			properties.load(applicationPropertiesStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CurrencyConverterApplication.class, args);
		String defaultBaseCurrency = properties.getProperty("input.base.currency");
		String samplesPath = properties.getProperty("samples.path");
		String rootPath = System.getProperty("user.dir");
		String filePath = rootPath + samplesPath;
		ICurrencyService currencyService = new FixerCurrencyService();
		ICurrencyOperations currencyOperations = new CurrencyOperations(currencyService);
		IFileHandler fileHandler =  TxtFileHandler.getInstance(filePath);
		List<AmountModel> inputValues = fileHandler.readFile(defaultBaseCurrency);
		List<AmountModel> outputValues = currencyOperations.convertCurrency(inputValues);
		fileHandler.writeOutputFile(outputValues);
		System.out.print("Program executed successfully!");
	}

}
