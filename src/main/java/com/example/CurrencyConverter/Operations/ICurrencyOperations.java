package com.example.CurrencyConverter.Operations;

import com.example.CurrencyConverter.Model.AmountModel;

import java.util.List;

//Interface for currency conversion operations.
public interface ICurrencyOperations {

    //Converts the specified amount from one currency to another.
    List<AmountModel> convertCurrency(List<AmountModel> inputValues);
}
