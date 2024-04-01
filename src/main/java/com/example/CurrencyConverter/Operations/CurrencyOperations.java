package com.example.CurrencyConverter.Operations;

import com.example.CurrencyConverter.Model.AmountModel;
import com.example.CurrencyConverter.Services.ICurrencyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Implements the CurrencyOperations interface to perform currency conversion operations.
public class CurrencyOperations implements ICurrencyOperations{
    ICurrencyService currencyService;

    //Constructs an instance of CurrencyOperations.
    public CurrencyOperations(ICurrencyService service)
    {
        currencyService = service;
    }

    @Override
    public List<AmountModel> convertCurrency(List<AmountModel> inputValues) {
        try {
            List<AmountModel> convertedValues = new ArrayList<>();
            currencyService.openConnection();
            Map<String, Double> currencyRates = currencyService.getRates();
            currencyService.closeConnection();
            for (AmountModel amountModel : inputValues) {
                if(!currencyRates.containsKey(amountModel.getCurrency())){
                    System.out.printf("Rate is not found for this currency (%s)! " +
                            "This conversion will be ignored!%n", amountModel.getCurrency());
                    continue;
                }
                double rate = currencyRates.get(amountModel.getCurrency());
                double newAmount = (1 / rate) * amountModel.getAmount();
                convertedValues.add(new AmountModel(newAmount));
            }
            return convertedValues;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
