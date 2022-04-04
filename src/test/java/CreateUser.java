import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUser {


    String ExcelFilePath = "C:\\Users\\shredeshpande\\IdeaProjects\\RestAssuredMiniAssignment\\XLsheets\\DataBase.xlsx";
    String ExcelSheetName = "database";


    private  static final String LOG_FILE = "log4j.properties";

    // TO ADD LOGGING IN OUR PROGRAM
    private static Logger log  = LogManager.getLogger(CreateUser.class);

    Properties properties = new Properties();


    @Test(priority = 1)
    public void create_user() throws IOException {

        log.info("User credentials fetched");

        int rowCount = javaUtility.getRowCount(ExcelFilePath, ExcelSheetName);

        for (int i = 1; i <=rowCount; i++) {
            String name = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, i, 0);
            String email = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, i, 1);
            String password = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, i, 2);
            String age = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, i, 3);

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
                    then().statusCode(201).extract();

            log.info("Account registered");
            System.out.println(response.asString());

            //Validating Credentials
            JSONObject arr = new JSONObject(response.asString());
            System.out.println();
            assertThat(arr.getJSONObject("user").get("email"),equalTo(email));

            if(arr.getJSONObject("user").get("email").equals(email))
            {
                System.out.println("User login credentials matched");
                log.info("User login credentials matched");
            }
            else
            {
                System.out.println("INVALID CREDENTIAL EMAIL DOES NOT MATCHED");
                log.info("INVALID CREDENTIAL EMAIL DOES NOT MATCHED");
            }

            //STORING THE TOKENS OF USERS WHICH WE ARE REGISTERING
            javaUtility.Tokens.add((String) arr.get("token"));

            log.info("Stored tokens");

            javaUtility.ID.add((String) arr.getJSONObject("user").get("_id"));
            log.info("Stored ID");

        }

    }


}