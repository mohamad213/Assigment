package org.example;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class PostApisUtils {

    public OkHttpClient client = new OkHttpClient();
    final String baseApiUrl = "https://jsonplaceholder.typicode.com/posts/";

    public Response sendRequest(String url) throws IOException {

        Request request = new Request.Builder().get()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        return response;
    }

    public Response getPostApiById(Object id) throws IOException {
        return sendRequest(baseApiUrl + id);
    }

    public Response getPostsApi() throws IOException {
        return sendRequest(baseApiUrl);
    }

    public Object[][] getPostsIds() throws IOException, ParseException {
        String jsonString = getPostsApi().body().string();
        JSONObject jsonObject;

        Object parsedJson = new JSONParser().parse(jsonString);
        JSONArray jsonArray = (JSONArray)parsedJson;

        Object[][] arrayListForIds = new Object[jsonArray.size()][];
        for(int i=0; i<jsonArray.size(); i++)
        {
            jsonObject = (JSONObject) jsonArray.get(i);
            arrayListForIds[i] = new Object[]{jsonObject.get("id")};
        }
        return arrayListForIds;
    }

}