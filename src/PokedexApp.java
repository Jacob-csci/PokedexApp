import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PokedexApp {
    public static JSONObject getPokemonName(String pokemonName) {
        String urlString = "https://pokeapi.co/api/v2/pokemon/" + pokemonName.toLowerCase();

        try {
            HttpURLConnection conn = fetchApiResponse(urlString);

            System.out.println("Connecting to: " + urlString);
            System.out.println("Response Code: " + conn.getResponseCode());

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(resultJson.toString());

            return resultJsonObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");  // Mimic a web browser
        conn.setConnectTimeout(10000);  // 10 seconds
        conn.setReadTimeout(10000);     // 10 seconds
        return conn;
    }

    public static void main(String[] args) {
        JSONObject pokemon = getPokemonName("ditto");

        if (pokemon != null) {
            System.out.println("Pokemon Name: " + pokemon.get("name"));
            System.out.println("Pokemon ID: " + pokemon.get("id"));
        }
    }
}
