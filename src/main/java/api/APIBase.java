package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.configs.ConfigSettings;

import java.util.Map;
import static io.restassured.RestAssured.basePath;

import static io.restassured.RestAssured.given;

public class APIBase {
    Gson g = new Gson();
    private ConfigSettings configSettings;
    public APIBase(){
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }
    public JsonObject sendPost(String accessToken, String basePathPT, Map mapPost) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT;
        Response re = given()
                .header("authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .when()
                .body(mapPost)
                .post();
        re.prettyPrint();

        Object res = re.as(Object.class);
        String a = g.toJson(res);
        JsonObject k = g.fromJson(a, JsonObject.class);
        return k;
    }

    public JsonObject sendGet(String accessToken, String basePathPT, String id) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT;
        final String GET = "/" + id;
        Response respon = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET);

        respon.prettyPrint();

        Object res = respon.as(Object.class);
        String a = g.toJson(res);
        JsonObject k = g.fromJson(a, JsonObject.class);
        return k;
    }

    public JsonArray sendGet(String accessToken, String basePathPT) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT;
        final String GET = "/";
        Response respon = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET);

        respon.prettyPrint();

        Object res = respon.as(Object.class);
        String a = g.toJson(res);
        JsonArray k = g.fromJson(a, JsonArray.class);
        return k;
    }

    public JsonObject sendPut(String accessToken, String basePathPT, String id, Map mapPut) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT;
        final String GET_PROJECT = "/" + id;

        Response resp = given()
                .header("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(mapPut)
                .when()
                .post(GET_PROJECT);
        resp.prettyPrint();

        // Get name project to verify updated name
        Response response = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET_PROJECT);

        Object res = response.as(Object.class);
        String a = g.toJson(res);
        JsonObject k = g.fromJson(a, JsonObject.class);
        return k;
    }

}
