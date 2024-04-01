package com.example.CurrencyConverter.Operations;

import com.example.CurrencyConverter.Model.AmountModel;
import com.example.CurrencyConverter.Services.FixerCurrencyService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrencyOperationsTest {

    @Test
    void convertCurrency() {
        Map<String, Double> mockedValues = new HashMap<>();
        mockedValues.put("EUR", 0.93);
        mockedValues.put("CAD", 1.35);
        mockedValues.put("DKK", 6.91);

        List<AmountModel> inputValues =new ArrayList<>();
        inputValues.add(new AmountModel(929.19,  "EUR"));
        inputValues.add(new AmountModel(21.1,  "DKK"));
        inputValues.add(new AmountModel(7829.9989,  "CAD"));

        List<AmountModel> expectedValues =new ArrayList<>();
        expectedValues.add(new AmountModel(999.1290322580645));
        expectedValues.add(new AmountModel(3.053545586107091));
        expectedValues.add(new AmountModel(5799.999185185185));

        FixerCurrencyService mockedCurrencyService = mock(FixerCurrencyService.class);
        when(mockedCurrencyService.getRates()).thenReturn(mockedValues);

        ICurrencyOperations operations = new CurrencyOperations(mockedCurrencyService);
        List<AmountModel> result = operations.convertCurrency(inputValues);

        for (int i = 0; i < result.size(); i++) {
            AmountModel resultItem = result.get(i);
            AmountModel expectedItem = expectedValues.get(i);
            assert(resultItem.getAmount().equals(expectedItem.getAmount()));
        }
    }
}