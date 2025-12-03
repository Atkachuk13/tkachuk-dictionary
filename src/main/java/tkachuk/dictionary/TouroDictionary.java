package tkachuk.dictionary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TouroDictionary
{
    private HashMap<String, String> dictionary = new HashMap<>();

    public TouroDictionary()
    {
        loadDictionary();
    }

    private void loadDictionary()
    {
        try
        {
            InputStream inputStream = TouroDictionary.class
                    .getClassLoader()
                    .getResourceAsStream("dictionary.txt");

            if (inputStream == null)
            {
                throw new RuntimeException("File not found.");
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\s+", 2);

                if (parts.length >= 1)
                {
                    String word = parts[0];
                    String definition = parts.length == 2 ? parts[1] : "";
                    dictionary.put(word, definition);
                }
            }

            reader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String lookUp(String word)
    {
        return dictionary.get(word.toUpperCase());
    }
}