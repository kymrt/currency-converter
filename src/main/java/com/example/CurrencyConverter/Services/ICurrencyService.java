package com.example.CurrencyConverter.Services;

import java.io.IOException;
import java.util.Map;

//Interface to fetch exchange rates from an external API.
public interface ICurrencyService {

    //Opens the connection for the external API.
    void openConnection() throws IOException;

    //Fetches the latest exchange rates from the external API.
    Map<String, Double> getRates();

    //Closes the connection for the external API.
    void closeConnection();
}
