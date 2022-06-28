import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listener.TestNGListener;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;

public class simpleTest extends TestNGListener {
    Map<String, Object> mapPost = new HashMap<>();
    Map<String, Object> mapPut = new HashMap<>();
    private String nameProject = "Shopping List";
    private String idProjectCreated ;

    String nameProjectUpdate = "Things To Buy";

    Gson g = new Gson();
    public simpleTest(){
        super();
    }

    @Test(description = "Create project")
    public void Test01_createProject() {
        RestAssured.baseURI = "https://api.todoist.com/rest/v1";
        basePath = "/projects";

        mapPost.put("name", nameProject);

        System.out.println(accessToken);
        Response res = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .and()
                .body(mapPost)
                .when()
                .post();

        res.prettyPrint();
        Object re = res.as(Object.class);
        String a = g.toJson(re);
        JsonObject j = g.fromJson(a, JsonObject.class);
        idProjectCreated = j.get("id").getAsString();
        assertTrue((j.get("name").getAsString()).equals(nameProject));
    }

    @Test(description = "Get a project")
    public void Test02_get_A_Project() {
        final String GET_PROJECT = "/" + idProjectCreated;
        Response respon = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET_PROJECT);

        Object res = respon.as(Object.class);
        String a = g.toJson(res);
        JsonObject j = g.fromJson(a, JsonObject.class);
        assertTrue((j.get("id").getAsString()).equals(idProjectCreated));
    }

    @Test(description = "Update project")
    public void Test03_updateProject() {
        mapPut.put("name", nameProjectUpdate);

        final String GET_PROJECT = "/" + idProjectCreated;
        Response resp = given()
                .header("authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(mapPut)
                .when()
                .post(GET_PROJECT);

        // Get name project to verify updated name
        Response respon = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET_PROJECT);

        Object res = respon.as(Object.class);
        String a = g.toJson(res);
        JsonObject j = g.fromJson(a, JsonObject.class);
        assertTrue((j.get("name").getAsString()).equals(nameProjectUpdate));
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
