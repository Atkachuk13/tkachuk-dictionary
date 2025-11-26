package tkachuk.dictionary;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TouroDictionaryServiceFactory
{
    private String lambdaUrl = "https://ktr2vtjral4jnkcmzn5pkzfyhi0kltie.lambda-url.us-east-2.on.aws/";

    public TouroDictionaryService create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(lambdaUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        TouroDictionaryService service = retrofit.create(TouroDictionaryService.class);
        return service;
    }
}