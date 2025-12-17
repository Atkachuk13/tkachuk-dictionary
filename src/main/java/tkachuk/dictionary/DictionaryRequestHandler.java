package tkachuk.dictionary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
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

        S3Client s3Client = S3Client.create();

        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket("tkachuk-dictionary")
                .key("dictionary.txt")
                .build();

        InputStream inputStream = s3Client.getObject(getObjectRequest);
        touroDictionary = new TouroDictionary();
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