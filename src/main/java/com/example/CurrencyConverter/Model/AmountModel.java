package com.example.CurrencyConverter.Model;

//A model for currency conversion operation.
public class AmountModel {

    //Constructs an instance of AmountModel.
    public AmountModel(Double amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

   //Constructs an instance of AmountModel.
    public AmountModel(Double amount){
        this.amount = amount;
        this.currency = "";
    }

    private final Double amount;
    public Double getAmount(){
        return amount;
    }

    private final String currency;
    public String getCurrency(){
        return currency;
    }
}
