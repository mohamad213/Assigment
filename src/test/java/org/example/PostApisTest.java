package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

import static org.junit.Assert.*;

public class PostApisTest {
    static PostApisUtils postApisUtils = new PostApisUtils();

    @Test(dataProvider = "existingIds")
    public void postApiByIdTest(Long id) throws IOException {
       assertEquals("Status code for id=" + id + " should be 200", 200, postApisUtils.getPostApiById(id).code());
    }

    @Test(dataProvider = "notExistIds")
    public void postApiByNotExistIdTest(String id) throws IOException {
        assertEquals("Status code for id=" + id + " should be 404", 404, postApisUtils.getPostApiById(id).code());
    }

    @Test
    public void postApiTest() throws IOException {
        assertEquals("Status code should be 200", 200, postApisUtils.getPostsApi().code());
    }

    @Test(dataProvider = "existingIds")
    public void apiBodyStructureTest(Long id) throws IOException, ParseException {
        Object obj = new JSONParser().parse(postApisUtils.getPostApiById(id).body().string());
        JSONObject jsonObject = (JSONObject)obj;
        assertBodyStructure(jsonObject);
    }

    private static void assertBodyStructure(JSONObject jsonObject) {
        assertEquals("id type should be Integer", Long.class.getName(), jsonObject.get("id").getClass().getName());
        assertEquals("userId type should be Integer", Long.class.getName(), jsonObject.get("userId").getClass().getName());
        assertEquals("title type should be String", String.class.getName(), jsonObject.get("title").getClass().getName());
        assertEquals("body type should be String", String.class.getName(), jsonObject.get("body").getClass().getName());
    }

    @DataProvider(name="existingIds", parallel=true)
    public static Object[][] existingIds() throws IOException, ParseException {
        return postApisUtils.getPostsIds();
    }

    @DataProvider(name="notExistIds", parallel=true)
    public static Object[][] notExistIds() {
        return new Object[][]{ {"!"}, {"0"}, {"101"}, {"1000"}, {"-100"}, {"^"}, {"abcd"}} ;
    }

}