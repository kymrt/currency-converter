
# Currency Conversion

This is a simple Java program for converting currency amounts from one currency to USD using a specified API service. The program reads currency amounts from a file, converts them to a target currency (USD), and then creates a new file with the converted amounts.

## Features

- Reads currency amounts from a file.
- Converts currency amounts to a specified target currency (USD) using a currency conversion API.
- Generates a new file with the converted currency amounts.

## How to Use
1. Clone the repository:

```bash
git clone https://github.com/kymrt/currency-converter.git
```

2. Open the project in your preferred Java IDE.

3. Make sure you have the required dependencies included in your project. For this project, you will need:
* org.json library for JSON parsing (included in the lib folder).

4. Add your API key for Fixer, define the default base currencies for API and input file, and samples path in application.properties file.

5. Put your **input.txt** file under **Samples folder**. Template should be like that **[Amount]** or **[Amount][Space][Currency]**.
   Example;
```
929.19
21.1 EUR
7829.9989 CAD
92933.8311127 GBP
395.8209 
```

6. Run the Main class to execute the program.

7. Observe the informations provided by the program in the terminal.

8. The program will then convert the currency amounts and generate a new file with the converted amounts under the **Samples folder** as **output.txt**.
## Dependencies

[org.json](https://mvnrepository.com/artifact/org.json/json/20240303) - A simple Java library for JSON processing.

[Mockito](https://mvnrepository.com/artifact/org.mockito/mockito-all) - Mock objects library for java.

[JUnit](https://mvnrepository.com/artifact/junit/junit) - JUnit is a unit testing framework to write and run repeatable automated tests on Java.
## Lessons learned

* Implementing json library was tricky. Then, I figured out that I needed to add this dependecy in build.gradle file.

* Free plan for [Fixer](https://fixer.io/documentation#:~:text=The%20current%20subscription%20plan%20does%20not%20support%20this%20API%20endpoint.) subscription does not allow us to fecth rates from USD to other currencies at the same time.

## Assumptions and Decisions
* Considering that the user can enter different currencies, it is assumed that the user will prepare an input file in the [Amount][Space][Currency] (929.19 EUR) structure or just the [Amount] (929.19) structure. If the currency is not specified, EUR is used by default.

* A flexible structure was created considering the use of different file types and different service providers in the future.
  