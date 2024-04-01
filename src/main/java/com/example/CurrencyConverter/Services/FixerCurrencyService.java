package com.example.CurrencyConverter.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

//Implements the CurrencyService interface to fetch exchange rates using Fixer Service.
public class FixerCurrencyService implements ICurrencyService{
    private final int SUCCESSFUL_RESPONSE_CODE = 200;
    private HttpURLConnection connection = null;
    private String baseCurrency = null;
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

    //Constructs an instance of FixerCurrencyService.
    public FixerCurrencyService(){
    }

    //Constructs an instance of FixerCurrencyService for the unit tests.
    public FixerCurrencyService(String baseCurrency, HttpURLConnection connection){
        this.baseCurrency = baseCurrency;
        this.connection = connection;
    }

    @Override
    public void openConnection() throws IOException{
        String apiUrl = properties.getProperty("fixer.api.url");
        String apiKey = properties.getProperty("fixer.api.key");
        baseCurrency = properties.getProperty("fixer.base.currency");
        String serviceURL = apiUrl + "?access_key=" + apiKey + "&base=" + baseCurrency;
        URL url = new URL(serviceURL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
    }

    @Override
    public Map<String, Double> getRates() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if(connection.getResponseCode() ==  SUCCESSFUL_RESPONSE_CODE) {
                String response = convertStreamToString(reader);
                JSONObject jsonResponse = new JSONObject(response);
                if(Boolean.parseBoolean(jsonResponse.get("success").toString())) {
                    Map<String,Double> result = convertToMap(jsonResponse.getJSONObject("rates"));
                    if(!Objects.equals(baseCurrency, "USD"))
                        result = convertToDollar(result);
                    return result;
                } else {
                    System.out.println("An error occurred. " +
                            jsonResponse.getJSONObject("error").get("info").toString());
                    throw new Exception(jsonResponse.getJSONObject("error").get("info").toString());
                }
            }
            else {System.out.println("An error occurred. " + connection.getResponseMessage());
                throw new Exception(connection.getResponseMessage());
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            connection.disconnect();
            System.out.println("Connection closed.");
        }
    }

    //Converts the given input stream into a string.
    private String convertStreamToString(BufferedReader reader) throws IOException {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        System.out.println("Response from API:\n" + response);
        return response.toString();
    }

    //Converts the given JSON string into a map.
    private Map<String, Double> convertToMap(JSONObject jsonObj)  throws JSONException {
        Map<String, Double> result = new HashMap<>();
        Iterator<String> keys = jsonObj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            double value = Double.parseDouble(jsonObj.get(key).toString());
            result.put(key, value);
        }
        return result;
    }

    /*
    * Converts the currency to Dollar from other currency if base currency is not Dollar.
    *  Example;
    *  1 EUR = 1.08 USD
    *  1 / 1.08 = 0.93
    *  1 USD = 0.93 EUR
     */
    private Map<String, Double> convertToDollar(Map<String, Double> rates) {
        Map<String, Double> usdResult = new HashMap<>();
        double usdAmount = rates.get("USD");
        double usdRate = 1 / usdAmount;
        usdResult.put(baseCurrency, usdRate);
        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            String currency = entry.getKey();
            double oldAmount = entry.getValue();
            double newAmount = usdRate * oldAmount;
            usdResult.put(currency, newAmount);
        }
        return usdResult;
    }
}
