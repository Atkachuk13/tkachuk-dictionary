package tkachuk.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TouroDictionaryServiceTest
{

    @Test
    void postWords()
    {
        // given
        TouroDictionaryService service = new TouroDictionaryServiceFactory()
                .create();
        DictionaryRequest dictionaryRequest = new DictionaryRequest("AD");

        // when
        DictionaryResponse dictionaryResponse = service.lookupWord(dictionaryRequest)
                .blockingGet();

        // then
        assertNotNull(dictionaryResponse);
        assertEquals("AD", dictionaryResponse.getWord());
    }
}