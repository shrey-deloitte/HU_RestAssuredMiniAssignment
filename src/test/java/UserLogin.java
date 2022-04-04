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

    String Path_Of_Excel_File = "C:\\Users\\shredeshpande\\IdeaProjects\\RestAssuredMiniAssignment\\XLsheets\\DataBase.xlsx";
    String SHEET_NAME_INSIDE_THE_EXCEL = "database";


    private  static final String LOG_FILE = "log4j.properties";

    // TO ADD LOGGING IN OUR PROGRAM
    private static Logger log  = LogManager.getLogger(UserLogin.class);


    @Test(priority = 2)
    public void user_Login_and_Validation() throws IOException {

        log.info("LOGGING IN");
        int rowCount = javaUtility.getRowCount(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL);


        for (int i = 1; i <=rowCount; i++) {


            String email = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 1);
            String password = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 2);


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
                    then().statusCode(201).extract();

            System.out.println(response.asString());

            log.info("Successfully logged in");


            //Validating Credentials
            JSONObject arr = new JSONObject(response.asString());
            //System.out.println(arr.get("token"));
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