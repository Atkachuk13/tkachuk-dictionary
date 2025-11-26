package tkachuk.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TouroDictionaryTest
{
    @Test
    public void loadDictionary()
    {
        // given

        // when
        TouroDictionary dictionary = new TouroDictionary();

        // then
        assertNotNull(dictionary);
    }

    @Test
    public void lookUpExistingWord()
    {
        // given
        TouroDictionary touroDictionary = new TouroDictionary();

        // when
        String definition = touroDictionary.lookUp("AB");

        // then
        assertTrue(definition.contains("an abdominal muscle [n -S]"));
    }

    @Test
    public void lookUpNotExistingWord()
    {
        // given
        TouroDictionary touroDictionary = new TouroDictionary();

        // when
        String definition = touroDictionary.lookUp("boogaBooga");

        // then
        assertNull(definition);
    }
}