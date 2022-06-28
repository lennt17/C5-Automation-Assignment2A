import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import listener.TestNGListener;
import org.testng.ITestNGListener;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class simpleTest extends TestNGListener {
    Map<String, Object> mapPost = new HashMap<>();
    Map<String, Object> mapPut = new HashMap<>();

    Gson g = new Gson();
    public simpleTest(){
        super();
    }

    @Test(description = "Create project")
    public void Test01_createProject() {
        RestAssured.baseURI = "https://api.todoist.com/rest/v1";
        basePath = "/projects";

        mapPost.put("name", "Shopping List");

        System.out.println(accessToken);
        Response res = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .and()
                .body(mapPost)
                .when()
                .post()
                .then()
                .extract().response();
        res.prettyPrint();
    }

    @Test(description = "Get a project")
    public void Test02_get_A_Project() {
        final String GET_PROJECT = "/2293619235";
        Response response = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET_PROJECT)
                .then()
                .extract().response();

        Object res = response.as(Object.class);
        String a = g.toJson(res);
        JsonObject j = g.fromJson(a, JsonObject.class);
        System.out.println(j);
    }

    @Test(description = "Update project")
    public void Test03_updateProject() {
        mapPut.put("name", "Things To Buy");

        final String GET_PROJECT = "/2293619235";
        Response response = given()
                .header("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(mapPut)
                .when()
                .post(GET_PROJECT);
    }

    @Test(description = "Get all project")
    public void Test04_getAllProjects() {
        Response response = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get();

        response.prettyPrint();

        List<Object> res = response.as(List.class);
        for (int i = 0; i < res.size(); i++) {
            String a = g.toJson(res.get(i));
            JsonObject j = g.fromJson(a, JsonObject.class);
            System.out.println(j.get("id"));
        }
    }
}
