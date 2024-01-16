package com.gorest.testsuite;

import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserAssertionTest extends TestBase {

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

    //1. Verify the if the total record is 20
    @Test
    public void verifyTotalRecords() {
        List<Map<String, ?>> list = response.extract().path("$");
        Assert.assertEquals(list.size(), 20);

    }

    //2. Verify the if the name of id = 5914127 is equal to ”Bhilangana Dhawan”
    @Test
    public void verifyNameById() {
        response.body("findAll{it.id==5914127}.get(0).name", equalTo("Malati Gupta II"));
    }

    //3. Check the single ‘Name’ in the Array list (Dev Bhattacharya)
    @Test
    public void verifySingleName() {
        response.body("name", hasItem("Chidambaram Talwar"));
    }

    //4. Check the multiple ‘Names’ in the ArrayList (Usha Kaul Esq., Akshita Mishra, Chetanaanand Reddy )
    @Test
    public void verifyMultipleNames() {
        response.body("name", hasItems("Paramartha Kocchar", "Akula Kakkar", "Asha Pandey"));
    }

    //5. Verify the email of userid = 5914185 is equal “tandon_iv_aanandinii@prosacco.example”
    @Test
    public void verifyEmailByUserId() {
        response.body("findAll{it.id==5914124}.email.get(0)", equalTo("kakkar_akula@jones-bednar.example"));

    }

    //6. Verify the status is “Active” of username is “Amaresh Rana”
    @Test
    public void verifyStatus() {
        response.body("findAll{it.name=='Bhadrak Singh'}.status.get(0)", equalTo("active"));
    }

    //7.Verify the Gender = male of user name is “Dhanalakshmi Pothuvaal”
    @Test
    public void verifyGenderByUserName() {
        response.body("findAll{it.name=='Asha Pandey'}.gender.get(0)", equalTo("female"));
    }

}
