package listener;

import org.testng.ITestNGListener;
import org.testng.annotations.*;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;
import utils.configs.ConfigSettings;

public class TestNGListener implements ITestNGListener {
    private ConfigSettings configSettings;

    public TestNGListener() {
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    @BeforeTest
    public void beforeTest() {
        deleteFileFromDirectory();
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
