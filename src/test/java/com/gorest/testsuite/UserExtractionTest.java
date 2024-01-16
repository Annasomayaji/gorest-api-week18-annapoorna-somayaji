package com.gorest.testsuite;

import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserExtractionTest extends TestBase {

    static ValidatableResponse response;

    @BeforeClass
    public void searchUsers() {
        Map<String, Integer> qParams = new HashMap<>();
        qParams.put("page", 1);
        qParams.put("per_page", 20);

        response = given()
                .queryParams(qParams)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

    }

    //    1. Extract the All Ids
    @Test
    public void extractIds(){
       List<Integer>id= response.extract().path("id");
        System.out.println("All ids: "+id);
    }

    //2. Extract the all Names
    @Test
    public void extractIdsNames(){
        List<String>names= response.extract().path("name");
        System.out.println("All names: "+names);
    }

    //3. Extract the name of 5th object
    @Test
    public void extractName(){
        String name= response.extract().path("[4].name");
        System.out.println("name of 5th object: "+name);
    }

    //4. Extract the names of all object whose status = inactive
    @Test
    public void extractNamesByStatus(){
        List<String> names= response.extract().path("findAll{it.status=='inactive'}.name");
        System.out.println("names of all with status inactive: "+names);
    }

    //5. Extract the gender of all the object whose status = active
    @Test
    public void extractGenderByStatus(){
        List<String> gender= response.extract().path("findAll{it.status=='inactive'}.gender");
        System.out.println("genders of all with status inactive: "+gender);
    }

    //6. Print the names of the object whose gender = female
    @Test
    public void extractNamesByGender(){
        List<String> names= response.extract().path("findAll{it.gender='female'}.name");
        System.out.println("names of all with gender is female: "+names);
    }

    //7. Get all the emails of the object where status = inactive
    @Test
    public void extractEmailByStatus(){
        List<String> email= response.extract().path("findAll{it.status=='inactive'}.email");
        System.out.println("emails of all with status inactive: "+email);
    }

    //8. Get the ids of the object where gender = male
    @Test
    public void extractIdsByGender(){
       List<Integer> ids= response.extract().path("findAll{it.gender=='male'}.id");
        System.out.println("Ids of all males: "+ ids);
    }

    //9. Get all the status
    @Test
    public void extractStatus(){
        List<String> status=response.extract().path("status");
        System.out.println("Status of all users: "+status);
    }

    //10. Get email of the object where name = Lal Dwivedi
    @Test
    public void extractEmailByName(){
        List<String> emails=response.extract().path("findAll{it.name=='Chandrabhan Adiga'}.email");
        System.out.println("Email by user name: "+emails.get(0));

    }


    //11. Get gender of id = 5914189
    @Test
    public void extractGender(){
        List<String> gender=response.extract().path("findAll{it.id==5914129}.gender");
        System.out.println("Gender by id: "+gender.get(0));
    }


}
