package com.gorest.crudtest;

import com.gorest.model.PostsPojo;
import com.gorest.model.UserPojo;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserCRUDTest extends TestBase {
    static String name = "Annu";
    static String nameForUpdate = "Anna";

    static String email = TestUtils.getRandomValue() + "annusom@gmail.com";
    static String emailForUpdate = TestUtils.getRandomValue() + "annasom@gmail.com";
    static String gender = "female";
    static String status = "active";
    static int userId;
    static ValidatableResponse response;

    @Test(priority = 1)
    public void createUser() {
        PostsPojo postsPojo = new PostsPojo();
        postsPojo.setName(name);
        postsPojo.setEmail(email);
        postsPojo.setGender(gender);
        postsPojo.setStatus(status);

        response = given()
                .header("Authorization", "Bearer " + "600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Content-Type", "application/json")
                .when()
                .body(postsPojo)
                .post("/users")
                .then();
        response.log().all();
        response.statusCode(201);

        userId = response.extract().path("id");
        System.out.println("id created in POST method: " + userId);//debug purpose
        System.out.println("*****************************************************");
        System.out.println("User created with id :"+userId);
        System.out.println("*****************************************************");

    }

    @Test(priority = 2)
    public void verifyUser() {
        System.out.println("id captured in GET method: " + userId);//debug purpose

        given()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200);
        System.out.println("*****************************************************");
        System.out.println("Fetching user details for id :"+userId);
        System.out.println("*****************************************************");

    }

    @Test(priority = 3)
    public void updateUser() {
       // System.out.println("id captured in PATCH method: " + userId);//debug purpose
        UserPojo userPojo = new UserPojo();
        userPojo.setName(nameForUpdate);
        userPojo.setEmail(emailForUpdate);
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("id", userId)
                .when()
                .body(userPojo)
                .patch("users/{id}")
                .then()
                .statusCode(200);
        response.log().all();
        System.out.println("*****************************************************");
        System.out.println("Updated user for user id :"+userId);
        System.out.println("*****************************************************");


    }

    @Test(priority = 4)
    public void deleteUser() {
        System.out.println("id captured in DELETE method: " + userId);//debug purpose

        given()
                .pathParam("id", userId)
                .header("Authorization", "Bearer " + "600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(204);
        System.out.println("*****************************************************");
        System.out.println("User deleted for id :"+userId);
        System.out.println("*****************************************************");

    }

    @Test(priority = 5)
    public void verifyUserIsDeleted() {

        given()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404);
        System.out.println("*****************************************************");
        System.out.println("User with user id :"+userId+ "is not found.");
        System.out.println("*****************************************************");

    }


}
