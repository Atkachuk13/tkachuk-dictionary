package tkachuk.dictionary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;

public class DictionaryRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, DictionaryResponse>
{
    private TouroDictionary touroDictionary;

    @Override
    public DictionaryResponse handleRequest(APIGatewayProxyRequestEvent event, Context context)
    {
        String body = event.getBody();
        Gson gson = new Gson();
        DictionaryRequest dictionaryRequest = gson.fromJson(body, DictionaryRequest.class);
        String word = dictionaryRequest.getWord();
        String definition = touroDictionary.lookUp(word);

        return new DictionaryResponse(word, definition);
    }
}