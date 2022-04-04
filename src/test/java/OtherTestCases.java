import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OtherTestCases {



    private  static final String LOG_FILE = "log4j.properties";

    // TO ADD LOGGING IN OUR PROGRAM
    private static Logger log  = LogManager.getLogger(Tasks.class);

    @Test(priority = 5)
    public void registering_Same_user_again() throws IOException {
        String Path_Of_Excel_File = "C:\\Users\\shredeshpande\\IdeaProjects\\RestAssuredMiniAssignment\\XLsheets\\DataBase.xlsx";
        String SHEET_NAME_INSIDE_THE_EXCEL = "database";

        log.info("Registering same user again in order to verify the error");

        String name = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, 1, 0);
        String email = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, 1, 1);
        String password = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, 1, 2);
        String age = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, 1, 3);

        Map bodyParameters = new LinkedHashMap();

        bodyParameters.put("name", name);
        bodyParameters.put("email", email);
        bodyParameters.put("password", password);
        bodyParameters.put("age", age);

        Gson gson = new Gson();
        String json = gson.toJson(bodyParameters, LinkedHashMap.class);

        log.info("User name, email ,password and age added");

        Response response = (Response) given().baseUri("https://api-nodejs-todolist.herokuapp.com").basePath("/user/register").
                contentType("application/json").
                body(json).
                when().
                post().
                then().extract();

        log.info(response.asString());
        System.out.println(response.asString());

        String str = String.valueOf(response.statusCode());

        if(str.equals(400)){
            System.out.println("Expected Status Code should be 400 and status code is " + response.statusCode());
            log.info(response.statusCode());
            Assert.assertTrue(str.equals(400));
        }


    }


    @Test(priority = 6)
    public  void unregisteredLogin() throws IOException {

        String ExcelFilePath = "C:\\Users\\shredeshpande\\IdeaProjects\\RestAssuredMiniAssignment\\XLsheets\\UnregisteredData.xlsx";
        String ExcelSheetName = "Sheet1";

        log.info("Logging in with the wrong id and password");

        String email = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, 1, 0);
        String password = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, 1, 1);


        log.info("Entered id and password");

        Map bodyParameters = new LinkedHashMap();

        bodyParameters.put("email", email);
        bodyParameters.put("password", password);

        Gson gson = new Gson();
        String json = gson.toJson(bodyParameters, LinkedHashMap.class);



        Response response = (Response) given().baseUri("https://api-nodejs-todolist.herokuapp.com").basePath("/user/login").
                contentType("application/json").
                body(json).
                when().
                post().
                then().extract();

        log.info(response.asString());

        System.out.println(response.asString());
        String str = String.valueOf(response.statusCode());

        if(str.equals(400)){
            System.out.println("Expected Status Code should be 400 and status code is " + response.statusCode());
            log.info(response.statusCode());
            Assert.assertTrue(str.equals(400));
        }

    }


    @Test(priority = 7) //Adding Tassk
    public void wrongRequestBody()
    {
        String Getting_Token = javaUtility.Tokens.get(0);

        String Wrong_request_Body = "SENDING INVALID DATA"; //Not a json object

        log.info("Trying to add task but with the wrong request body");

        Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                header("Authorization", "Bearer " + Getting_Token).header("content-type", "application/json").
                body(Wrong_request_Body)
                .when()
                .post()
                .then()
                //.statusCode(201)
                .extract().response();

        log.info(response.asString());

        System.out.println(response.asString());
        String str = String.valueOf(response.statusCode());

        if(str.equals(400)){
            System.out.println("Expected Status Code should be 400 and status code is " + response.statusCode());
            log.info(response.statusCode());
            Assert.assertTrue(str.equals(400));
        }



    }



}