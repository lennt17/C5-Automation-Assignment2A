import api.ApiProject;
import com.google.gson.JsonObject;
import listener.TestNGListener;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class simpleTest extends TestNGListener {
    private String nameProjectExpected = "Shopping List";
    String nameProjectUpdate = "Things To Buy";
    String idProjectCreated ;
    String nameProjectCreated;
    public ApiProject apiProject;
    Map<String, Object> mapPost = new HashMap<>();
    Map<String, Object> mapPut = new HashMap<>();
    public simpleTest(){
        super();
    }

    @Test(description = "Create project")
    public void Test01_createProject(){
        apiProject = new ApiProject();
        mapPost.put("name", nameProjectExpected);
        JsonObject ObjectProjectCreated = apiProject.create(accessToken, mapPost);
        idProjectCreated = ObjectProjectCreated.get("id").getAsString();
        nameProjectCreated = ObjectProjectCreated.get("name").getAsString();
        assertTrue(nameProjectCreated.equals(nameProjectExpected));
    }

    @Test(description = "Get a project")
    public void Test02_getProject(){
        String idProjectGet = idProjectCreated;
        JsonObject ObjectProjectGot = apiProject.get(accessToken, idProjectGet);
        String idProjectGot = ObjectProjectGot.get("id").getAsString();
        assertTrue(idProjectGot.equals(idProjectGet));
    }

    @Test(description = "Update project")
    public void Test03_updateProject(){
        String idProjectUpdate = idProjectCreated;
        mapPut.put("name", nameProjectUpdate);
        JsonObject ObjectProjectUpdated = apiProject.update(accessToken, idProjectUpdate, mapPut);
        String nameProjectUpdated = ObjectProjectUpdated.get("name").getAsString();
        assertTrue(nameProjectUpdated.equals(nameProjectUpdate));
    }

    @Test(description = "Get all project")
    public void Test04_getAllProjects(){
        apiProject.getAll(accessToken);
    }
}
