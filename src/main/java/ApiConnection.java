package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class ApiConnection {

    // Keys for JSON response of api
    public static final String CONVERSION_RATE = "conversion_rate";
    public static final String ERROR = "error";


    public Double getRate(ConversionRates.Codes base, ConversionRates.Codes target) {
        String apiResponse = ERROR;
        try {
            apiResponse = apiRequest(base, target);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double conversionRate = parseApiResponse(apiResponse);
        return conversionRate;
    }

    private Double parseApiResponse(String apiResponse) {
        String[] pairs = apiResponse.split(",");
        if (pairs[0].contains(ERROR)) {
            //TODO error handling
        }

        String conversionRate = (String) Arrays.stream(pairs).filter(pair -> pair.contains(CONVERSION_RATE)).toArray()[0];
        conversionRate = conversionRate.substring(0, conversionRate.length() -1);
        //TODO improve
        return Double.parseDouble(conversionRate.split(":")[1]);
    }

    private String apiRequest(ConversionRates.Codes base, ConversionRates.Codes target) throws IOException {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/90e2a85aec8220829a3185d0/pair/"
                + base.name() + "/" + target.name();

        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Reading response
        BufferedReader in = new BufferedReader(
                new InputStreamReader(request.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        request.disconnect();
        return content.toString();
    }
}
