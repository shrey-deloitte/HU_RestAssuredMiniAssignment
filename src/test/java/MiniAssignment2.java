import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.Matchers.*;

public class MiniAssignment2 {


    RequestSpecification requestSpecification;
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    ResponseSpecification responseSpecification;
    ResponseSpecBuilder responseSpecBuilder;


    @BeforeTest
    public void initialize(){

        requestSpecBuilder.setBaseUri("https://jsonplaceholder.typicode.com").
                addHeader("Content-Type","application/json");
        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());
        responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void statusCodeValidation(){
        responseSpecBuilder.expectStatusCode(200);
    }

    @Test
    public void contentTypeValidation(){
        responseSpecBuilder.expectContentType(ContentType.JSON);
    }


    @Test
    public void userIdValidation(){
        responseSpecification.body("[39].userId", Matchers.equalTo(4));
    }

    @Test
    public  void titleTypeValidation(){
        responseSpecification.body("title", Matchers.hasToString(""));
    }



}
