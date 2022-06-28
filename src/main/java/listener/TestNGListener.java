package listener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.ITestNGListener;
import org.testng.annotations.*;
import org.slf4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;
import utils.configs.ConfigSettings;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class TestNGListener implements ITestNGListener {
    private ConfigSettings configSettings;
    protected String accessToken;
    Gson g = new Gson();

    Map<String, Object> mapLogin = new HashMap<>();

    public TestNGListener() {
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
        this.accessToken = accessToken;
    }

    @BeforeTest
    public void beforeTest() {
        deleteFileFromDirectory();

        RestAssured.baseURI = "https://todoist.com/API/v8.7/user/login";
        mapLogin.put("email", "lennt2k@gmail.com");
        mapLogin.put("password", "Len181403032");
        Response res = given()
                .contentType(ContentType.JSON)
                .and()
                .body(mapLogin)
                .when()
                .post()
                .then()
                .extract().response();
        res.prettyPrint();
        Object response = res.as(Object.class);
        String a = g.toJson(response);
        JsonObject j = g.fromJson(a, JsonObject.class);
        accessToken = (j.get("token")).getAsString();
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    // Text attachments for allure
    @Attachment(value = "Form screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // Text attachments for allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    public void deleteFileFromDirectory() {
        //String user = System.getProperty("user home");   // user if data in your user profile
        //String filePath = user + "/Downloads/Test;
        String directory = "D:\\C5-VMO-git\\C5-Automation-Assignment2A\\target\\allure-results"; // If download is in IDE project folder

        File file = new File(directory);
        String[] currentFiles;
        if (file.isDirectory()) {
            currentFiles = file.list();
            for (int i = 0; i < currentFiles.length; i++) {
                File myFile = new File(file, currentFiles[i]);
                myFile.delete();
            }
        }
    }

    @AfterTest
    public void afterTest() {
        //
    }
}
