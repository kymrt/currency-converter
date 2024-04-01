package com.example.CurrencyConverter.Services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class FixerCurrencyServiceTest {
    private FixerCurrencyService fixerCurrencyService;

    @Mock
    private HttpURLConnection mockedConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fixerCurrencyService = new FixerCurrencyService("EUR", mockedConnection);
    }

    @AfterEach
    void tearDown() {
        fixerCurrencyService = null;
    }

    @Test
    void openConnection(){
        try {
            fixerCurrencyService.openConnection();
            verify(mockedConnection, times(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getRates() {
        try {
            Map<String, Double> expectedValues = new HashMap<>();
            expectedValues.put("EUR", 1.0799719207300609);
            expectedValues.put("CAD", 1.5793984556401532);
            expectedValues.put("DKK", 8.060922296020303);
            String testData = "{\"success\":true,\"timestamp\":1711829763,\"base\":\"EUR\",\"date\":\"2024-03-30\",\"rates\":{\"USD\":0.925950,\"CAD\":1.462444,\"DKK\":7.464011}}";
            InputStream inputStream = new ByteArrayInputStream(testData.getBytes());

            when(mockedConnection.getInputStream()).thenReturn(inputStream);
            when(mockedConnection.getResponseCode()).thenReturn(200);
            Map<String, Double> actualValues = fixerCurrencyService.getRates();
            assert(actualValues.get("EUR").equals(expectedValues.get("EUR")));
            assert(actualValues.get("CAD").equals(expectedValues.get("CAD")));
            assert(actualValues.get("DKK").equals(expectedValues.get("DKK")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void closeConnection() {
        fixerCurrencyService.closeConnection();
        verify(mockedConnection, times(1));
    }
}