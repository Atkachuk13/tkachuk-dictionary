package tkachuk.dictionary;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TouroDictionaryServiceTest
{

    @Test
    void lookUpWords()
    {
        // given
        TouroDictionaryService service =
                new TouroDictionaryServiceFactory(
                        "https://k7dcdbsfnvtrowrakvqk23hqbe0rixxk.lambda-url.us-east-2.on.aws/")
                        .create();

        // when
        DictionaryRequest dictionaryRequest = new DictionaryRequest("AD");
        DictionaryResponse dictionaryResponse = service.lookupWord(dictionaryRequest)
                .subscribeOn(Schedulers.io())
                .blockingGet();

        // then
        assertEquals("an advertisement [n -S]", dictionaryResponse.getDefinition());
    }
}