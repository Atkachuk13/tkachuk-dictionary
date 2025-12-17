package tkachuk.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TouroDictionary
{
    // create another constructor, that you pass in an input stream
    // the goal is: in our Request Handler, for our lambda we call the ither c instrycter passing the input stream
    // from our file in s3
    private HashMap<String, String> dictionary = new HashMap<>();

    public TouroDictionary()
    {
        loadDictionary();
    }

    // constructor for s3
    public TouroDictionary(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();

        while (line != null)
        {
            if (line.contains(" "))
            {
                dictionary.put(line.substring(0, line.indexOf(" ")),
                        line.substring(line.indexOf(" ") + 1));
            } else
            {
                dictionary.put(line, " ");
            }
            line = bufferedReader.readLine();
        }
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