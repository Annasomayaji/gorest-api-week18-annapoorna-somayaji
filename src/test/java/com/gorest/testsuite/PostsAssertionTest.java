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

public class PostsAssertionTest extends TestBase {
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

    //            1. Verify the if the total record is 25
    @Test
    public void verifyTotalRecords() {
        List<Map<String, ?>> list = response.extract().path("");
        Assert.assertTrue(list.size() == 25);
    }

    //    2. Verify the if the title of id = 93997 is equal to ”Demitto conqueror atavus argumentum corrupti cohaero libero.”
    @Test
    public void verifyTitle() {
        response.body("findAll{it.id==93997}.title.get(0)", equalTo("Demitto conqueror atavus argumentum corrupti cohaero libero."));

    }

    // 3. Check the single user_id in the Array list (5914249)
    @Test
    public void verifySingleUserId(){
        response.body("user_id",hasItem(5914249));
    }

    //4. Check the multiple ids in the ArrayList (5914243, 5914202, 5914199)
    @Test
    public void verifyMultipleUserId(){
        response.body("user_id",hasItems(5914243,5914206,5914206));
    }

    //5. Verify the body of userid = 5914197 is equal “Desidero vorax adsum. Non confero clarus.
    //    Velut defessus acceptus. Alioqui dignissimos alter. Tracto vel sordeo. Vulpes curso tollo. Villa usus
    //    vos. Terreo vos curtus. Condico correptius praesentium. Curatio deripio attero. Tempus creptio
    //    tumultus. Adhuc consequatur undique. Adaugeo terminatio antiquus. Stultus ex temptatio. Autus
    //    acerbitas civitas. Comptus terminatio tertius. Utpote fugit voluptas. Sequi adulescens caecus.”

    @Test
    public void verifyBodyOfRecordByUserId(){
        response.body("findAll{it.user_id==5914184}.body",hasItem("Reiciendis voluptatum inflammatio. Desparatus autem delectatio. Cibo tantillus corona. Coma sumo tenuis. Perferendis quis strues. Terreo tam angulus. Similique utroque circumvenio. Omnis totus umquam. Praesentium totam admiratio. Crudelis dolor denego. Cito claudeo subiungo. Tandem armarium uxor. Similique decens eum. Vester decretum desino. Calcar recusandae condico. Itaque quibusdam acerbitas. Bellum despirmatio ultio."));
    }

}
