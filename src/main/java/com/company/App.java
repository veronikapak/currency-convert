package com.company;

import com.company.model.FixerCurrencyResponse;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Hello world!
 */
public class App {

    //private static final String PATH = readProperties("saitpath");
    //private static final String LATEST = readProperties("latest");
    //private static final String BASE = readProperties("base");
    //private static final String SYMBOLS = readProperties("symbols");

    @SneakyThrows
    public static void main(String[] args) throws Exception{
        RestTemplate restTemplate = new RestTemplate();

        String path = readProperties("saitpath");
        String latest = readProperties("latest");
        String base = readProperties("base");
        String symbols = readProperties("symbols");

        String url = path;
        String symbolStr = null;
        String baseStr = null;
        String sumStr = null;
        Double sum = null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter date yyyy-mm-dd:");
        String date = bufferedReader.readLine();
        if(date==null || date.equalsIgnoreCase("")){
             url+=latest;
        }else {
            url+=date.replaceAll("\\s+","");
        }

        System.out.println("Enter currency:");
        baseStr = bufferedReader.readLine();
        baseStr = baseStr.toUpperCase().replaceAll("\\s+", "");

        if (baseStr!=null && !baseStr.equals("")){
            url+=base+baseStr;
            System.out.println(url);

            System.out.println("Enter currency to change:");
            symbolStr = bufferedReader.readLine();
            symbolStr = symbolStr.toUpperCase().replaceAll("\\s+","");
            if(symbolStr!=null && !symbolStr.equals("")){
                url+="&"+symbols+symbolStr;
                
                System.out.println("Enter sum to change:");
                sumStr = bufferedReader.readLine();
                sum = Double.parseDouble(sumStr);
            }
        }

        System.out.println(url);

        FixerCurrencyResponse body = restTemplate.getForEntity(url, FixerCurrencyResponse.class).getBody();
        System.out.println(body.toString());

        if(sumStr!=null && !sumStr.equals("")){
            String[] parts = symbolStr.split(",");
            for (String str:parts) {
                double sumNew = body.getRates().get(str) * sum;
                System.out.println(sumStr+baseStr+"="+sumNew+str);
                sumNew = sum;
            }
        }
        if(bufferedReader==null){
            bufferedReader.close();
        }
    }

    private static String readProperties(String s) throws IOException {
        Properties properties = new Properties();
        InputStream is = App.class.getResourceAsStream("/application.properties");
        properties.load(is);
        return properties.getProperty(s);
    }
}
