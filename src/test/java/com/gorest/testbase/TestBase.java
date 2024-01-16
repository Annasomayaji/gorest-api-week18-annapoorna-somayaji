package com.gorest.testbase;

import com.gorest.utils.PropertyReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestBase {
    String baseURI= PropertyReader.getInstance().getProperty("baseUri");
   String basePath=PropertyReader.getInstance().getProperty("basePath");

    @BeforeClass
    public void inIt(){
        RestAssured.baseURI=baseURI;
      //  RestAssured.port=port;
        RestAssured.basePath=basePath;
    }
}
