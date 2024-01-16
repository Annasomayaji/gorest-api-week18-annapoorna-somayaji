package com.gorest.testsuite;

import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostsExtractionTest extends TestBase {
    static ValidatableResponse response;
    @BeforeClass
    public void searchUsers() {
        Map<String, Integer> qParams = new HashMap<>();
        qParams.put("page", 1);
        qParams.put("per_page", 25);

        response = given()
                .queryParams(qParams)
                .when()
                .get("/posts")
                .then()
                .statusCode(200);

    }


    //  1. Extract the title
    @Test
    public void extractTitle(){
        List<String> titles=response.extract().path("title");
        System.out.println("All titles: "+titles);
    }

    //2. Extract the total number of record
    @Test
    public void totalRecords(){
        List <Map<String,?>> list=response.extract().path("");
        System.out.println("Total number of records: "+list.size());
    }

    //3. Extract the body of 15th record
    @Test
    public void extractAllByRecordNumber(){
        Map<String,?> record=response.extract().path("[14]");
        System.out.println("15th record: "+record);
    }

    //4. Extract the user_id of all the records
    @Test
    public void extractUserId(){
        List<Integer> user_id=response.extract().path("user_id");
        System.out.println("User ids are: "+user_id);
    }

    //5. Extract the title of all the records
    @Test
    public void extractTitles(){
        List<Integer> titles=response.extract().path("title");
        System.out.println("titles are: "+titles);
    }

    //6. Extract the title of all records whose user_id = 5914200
    @Test
    public void extractTitlesByUserId(){
        List<String> titles=response.extract().path("findAll{it.user_id==5914249}.title");
        System.out.println("titles are: "+titles);
    }

    //7. Extract the body of all records whose id = 93957
    @Test
    public void extractRecordsById(){
        List<Map<String,?>> records=response.extract().path("findAll{it.id== 93952}");
        System.out.println("Records are: "+records);
    }

}
