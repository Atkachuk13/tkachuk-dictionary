package tkachuk.dictionary;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TouroDictionaryService
{
    @POST("/")
    Single<DictionaryResponse> lookupWord(@Body DictionaryRequest request);
}
