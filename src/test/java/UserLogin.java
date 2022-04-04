import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserLogin {

    String ExcelSheetPath = "C:\\Users\\shredeshpande\\IdeaProjects\\RestAssuredMiniAssignment\\XLsheets\\DataBase.xlsx";
    String ExcelSheetName = "database";


    private  static final String LOG_FILE = "log4j.properties";

    // TO ADD LOGGING IN OUR PROGRAM
    private static Logger log  = LogManager.getLogger(UserLogin.class);


    @Test(priority = 2)
    public void user_Login_and_Validation() throws IOException {

        log.info("LOGGING IN");
        int rowCount = javaUtility.getRowCount(ExcelSheetPath, ExcelSheetName);


        for (int i = 1; i <=rowCount; i++) {


            String email = javaUtility.getCellvalue(ExcelSheetPath, ExcelSheetName, i, 1);
            String password = javaUtility.getCellvalue(ExcelSheetPath, ExcelSheetName, i, 2);


            log.info("Entered id and password");

            Map bodyParameters = new LinkedHashMap();

            bodyParameters.put("email", email);
            bodyParameters.put("password", password);

            Gson gson = new Gson();
            String json = gson.toJson(bodyParameters, LinkedHashMap.class);

            Response response = (Response) given()
                    .baseUri("https://api-nodejs-todolist.herokuapp.com")
                    .basePath("/user/login").
                    contentType("application/json").
                    body(json).
                    when().
                    post().
                    then().statusCode(200).extract();

            System.out.println(response.asString());

            log.info("Successfully logged in");


            JSONObject arr = new JSONObject(response.asString());

            assertThat(arr.getJSONObject("user").get("email"),equalTo(email));

            if(arr.getJSONObject("user").get("email").equals(email))
            {
                System.out.println("login credentials verified");
                log.info("login credentials verified");
            }
            else
            {
                System.out.println("login credentials mismatched");
                log.info("login credentials mismatched");
            }



        }



    }

}