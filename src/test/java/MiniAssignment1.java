//import org.asynchttpclient.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  io.restassured.response.Response;

import java.io.File;

public class MiniAssignment1 {

    @Test
    public void getCallTest(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type","application/json").
        when().
                get("/posts").
        then().
                statusCode(200);
    }

    @Test
    public void validateUserID(){
        Response response =  given().
        when().
                get("https://jsonplaceholder.typicode.com/posts").

        then().extract().response();

        assertThat(response.path("[39].userId"),is(equalTo(4)));
    }

    @Test
    public void validateTitle(){
        File JsonData=new File("src/test/resources/JsonData.json");
        given().
                baseUri("https://jsonplaceholder.typicode.com/posts").
                header("Content-Type","application/json").
                when().
                get("https://jsonplaceholder.typicode.com/posts").
                then().
                statusCode(200).body("title[39]",equalTo("enim quo cumque"));


    }


    @Test
    public  void Put(){
        File JsonData=new File("src/test/resources/JsonData.json");

        given().
                baseUri("https://reqres.in/api").
                body(JsonData).
                header("Content-Type","application/json").
        when().
                put("/users").
        then().
                statusCode(200).body("name",equalTo("Arun")).body("job",equalTo("Manager"));
    }

}
