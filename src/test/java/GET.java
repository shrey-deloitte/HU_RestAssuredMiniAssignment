//import org.asynchttpclient.Response;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  io.restassured.response.Response;

import java.io.File;

public class GET {

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
        Response response =  given().
                when().
                get("https://jsonplaceholder.typicode.com/posts").

        then().extract().response();

        JsonPath obj=new JsonPath(response.asString());
        assertThat(obj.get("title"),is());

    }

    @Test
    public  void Put(){
        File jsonData=new File("src//test//resources//putdata.json");

        given().
                baseUri("https://reqres.in/api").
                body(jsonData).
                header("Content-Type","application/json").
        when().
                put("/users").
        then().
                statusCode(200);
    }

}
