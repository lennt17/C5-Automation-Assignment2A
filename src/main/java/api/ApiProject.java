package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import utils.configs.ConfigSettings;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiProject extends APIBase {
    public ConfigSettings configSettings;
    public APIBase apiBase;
    public JsonObject ObjectCreated;
    public JsonObject ObjectGot;
    public JsonArray ArrayObject;
    public JsonObject ObjectUpdated;

    Gson g = new Gson();
    public ApiProject(){
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    public JsonObject create(String accessToken, Map mapPost){
        apiBase = new APIBase();
        basePath = configSettings.getBasePathProject();
        return ObjectCreated = apiBase.sendPost(accessToken, basePath, mapPost);
    }

    public JsonObject get(String accessToken, String idProjectGet){
        basePath = configSettings.getBasePathProject();
        return ObjectGot = apiBase.sendGet(accessToken, basePath, idProjectGet);
    }

    public JsonArray getAll(String accessToken){
        basePath = configSettings.getBasePathProject();
        return ArrayObject = apiBase.sendGet(accessToken, basePath);
    }

    public JsonObject update(String accessToken, String idProjectUpdate, Map mapPut){
        basePath = configSettings.getBasePathProject();
        return ObjectUpdated = apiBase.sendPut(accessToken, basePath, idProjectUpdate, mapPut);
    }
}
