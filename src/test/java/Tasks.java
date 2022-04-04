import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
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
import static org.testng.Assert.assertTrue;

public class Tasks {

    private  static final String LOG_FILE = "log4j.properties";

    // TO ADD LOGGING IN OUR PROGRAM
    private static Logger log  = LogManager.getLogger(Tasks.class);


    String ExcelFilePath = "C:\\Users\\shredeshpande\\IdeaProjects\\RestAssuredMiniAssignment\\XLsheets\\tasks.xlsx";
    String ExcelSheetName = "Sheet1";

    boolean validating_tasks =false;

    @Test(priority = 3)
    public void addTasks() throws IOException {

        log.info("Adding Tasks");
        int rowCount = javaUtility.getRowCount(ExcelFilePath, ExcelSheetName);

        System.out.println(rowCount);
        String Login_From_Token = javaUtility.Tokens.get(0);


        for (int i = 1; i <= rowCount; i++) {
            String Task = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, i, 0);


            Map bodyParameters = new LinkedHashMap();

            bodyParameters.put("description", Task);


            Gson gson = new Gson();
            String json = gson.toJson(bodyParameters, LinkedHashMap.class);

            System.out.println(json);

            Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + Login_From_Token).header("content-type", "application/json").
                    body(json)
                    .when()
                    .post()
                    .then()
                    .statusCode(201)
                    .extract().response();



            JSONObject arr = new JSONObject(response.asString());
            System.out.println();


            System.out.println(arr.getJSONObject("data").get("description"));

            if(arr.getJSONObject("data").get("description").equals(Task))
            {
                validating_tasks =true;
            }
            log.info("task added");

            System.out.println(response.asString());
        }

        System.out.println(validating_tasks);
    }

    @Test(priority =4)
    public void pagination() throws IOException {

        log.info("Validating paging");

        int num = 2;
        for(int j = 0; j<3; j++){

            String token = javaUtility.Tokens.get(0);
            Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("?limit="+num)
                    .then()
                    .statusCode(200).extract().response();

            JSONObject arr = new JSONObject(response.asString());

            int Count = (int)arr.get("count");

            if (num == Count && num==2){
                System.out.println();
                log.info("pagination with limit 2 checked");
                num = 5;
            }
            if (num == Count && num==5){
                System.out.println("pagination with limit 5 checked");
                log.info("pagination with limit 5 checked");
                num = 10;
            }
            if (num== Count && num==10) {
                System.out.println("pagination with limit 10 checked");
                log.info("pagination with limit 10 checked");
                assertTrue(true);
            }
        }


    }
    @Test(priority = 5, dependsOnMethods = "addTasks")
    public void validating_Tasks() throws IOException {

        log.info("Validating tasks");
        String Unique_id = javaUtility.ID.get(0);

        int rowCount = javaUtility.getRowCount(ExcelFilePath, ExcelSheetName);

        for (int i = 1; i <= rowCount; i++) {
            String Task = javaUtility.getCellvalue(ExcelFilePath, ExcelSheetName, i, 0);
            {
                String Login_From_Token = javaUtility.Tokens.get(0);
                Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                        header("Authorization", "Bearer " + Login_From_Token).header("content-type", "application/json")
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().response();


                JsonPath j = new JsonPath(response.asString());



                assertThat(j.getString("data[0].owner"), equalTo(Unique_id));

                if(j.getString("data[0].owner").equals(Unique_id) && validating_tasks)
                {   System.out.println("Task"+i+" added validated");
                    log.info("Task"+i+" added validated");
                }
                else{
                    System.out.println("Task not added");
                    log.info("Task not added");}
            }

        }
    }
}