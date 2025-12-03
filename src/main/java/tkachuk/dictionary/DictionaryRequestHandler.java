package tkachuk.dictionary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DictionaryRequestHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    private TouroDictionary touroDictionary;
    private Gson gson;

    public DictionaryRequestHandler()
    {
        this.touroDictionary = new TouroDictionary();
        this.gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
    {
        try
        {
            String body = event.getBody();
            DictionaryRequest dictionaryRequest = gson.fromJson(body, DictionaryRequest.class);
            String definition = touroDictionary.lookUp(dictionaryRequest.getWord());

            DictionaryResponse dictionaryResponse = new DictionaryResponse(dictionaryRequest.getWord(), definition);

            // create the HTTP response with the DictionaryResponse
            String responseJson = gson.toJson(dictionaryResponse);
            APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
            apiResponse.setStatusCode(200);
            apiResponse.setBody(responseJson);
            return apiResponse;

        } catch (Exception e)
        {
            // this prints the stack trace to the AWS log file
            e.printStackTrace();

            // this outputs the stack trace to the client
            return toResponseEvent(e);
        }
    }

    private APIGatewayProxyResponseEvent toResponseEvent(Exception e)
    {
        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
        apiResponse.setStatusCode(500);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        apiResponse.setBody(stringWriter.toString());
        return apiResponse;
    }
}