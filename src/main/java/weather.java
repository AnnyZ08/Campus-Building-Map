//https://api.openweathermap.org/data/2.5/weather?lat=43.009953&lon=-81.273613&appid=cc88c863d14e1a5d92f85ecc1eea792b


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.UnknownHostException;
import java.util.Scanner;

public class weather {
    private URL url;
    private String temp;
    private String weather;
    private String icon;
    private static BufferedImage image;
    private String iconURL;

    public weather(){
        try {
            url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=43.009953&lon=-81.273613&appid=cc88c863d14e1a5d92f85ecc1eea792b");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //Check if connect is made
            try {
                conn.connect();
                int responseCode = conn.getResponseCode();


                // 200 means OK
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode: " + responseCode);
                } else {

                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }

                    //Close the scanner
                    scanner.close();

                    //convert strings to JSON
                    JSONParser parse = new JSONParser();
                    Object obj = parse.parse(String.valueOf(informationString));
                    JSONObject jsonObj = (JSONObject) obj;

                    // find temperature
                    Object main = jsonObj.get("main");
                    JSONObject jsonmain = (JSONObject) main;
                    double kelvin = (double) jsonmain.get("temp");
                    // Kelvin to Fahrenheit
                    double Celsius = kelvin - 273.15;
                    temp = String.format("%.2f", Celsius) + "°C";

                    // find weather
                    Object weatherField = jsonObj.get("weather");
                    JSONArray arr = (JSONArray) weatherField;
                    JSONObject jsonWeather = (JSONObject) arr.get(0);
                    Object text = jsonWeather.get("main");
                    weather = String.valueOf(text);

                    // find icon
                    Object iconObj = jsonWeather.get("icon");
                    icon = String.valueOf(iconObj);

                    JLabel label = new JLabel("Current temperature is: " + temp);
                    label.setBounds(600,100,30,10);
                    iconURL = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
                    image = ImageIO.read(new URL(iconURL));
                    ImageIcon img = new ImageIcon(image);

                }
            } catch (UnknownHostException e) {
                conn.disconnect();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    public String getImage(){
        return this.iconURL;
    }
    public String getTemp(){
        return this.temp;
    }

}
