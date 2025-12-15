package tkachuk.dictionary;

public class DictionaryResponse
{
    private String word;
    private String definition;

    public DictionaryResponse(String word, String definition)
    {
        this.word = word;
        this.definition = definition;
    }

    public String getDefinition()
    {
        return definition;
    }

    public String getWord()
    {
        return word;
    }
}
