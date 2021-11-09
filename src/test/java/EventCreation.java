import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class EventCreation {


    String token = "Bearer a4368ad0-fe9f-4901-a985-c7a6581acf82";
    String eventName = System.getProperty("eventName") + System.currentTimeMillis();
    String updatedTitle = System.getProperty("updatedTitle") + System.currentTimeMillis();
    String docID = "";
    String baseUri = System.getProperty("s4_base_url");

    //given - all input details
    //when - submit the api - resource, http method
    //then - validate the response

    public void getTemplateDetail() {
        //getTemplate()
        given().log().all().queryParam("user", System.getProperty("user"))
                .queryParam("passwordAdapter", System.getProperty("passwordAdapter"))
                .queryParam("realm", "s4svall-2")
                .queryParam("ids", System.getProperty("templateDocumentInternalId"))
                .header("Content-Type", "application/json").header("Authorization", token)
                .when().get(baseUri+"/Sourcing/sourcing-event/v2/events/templates")
                .then().log().all().assertThat().statusCode(200);

    }

    public void createEvent() {
        String createEvent = given().log().all().queryParam("user", System.getProperty("user"))
                .queryParam("passwordAdapter", System.getProperty("passwordAdapter"))
                .queryParam("realm", "s4svall-2")
                .header("Content-Type", "application/json").header("Authorization", token)
                .body("{\n" +
                        "  \"title\":\"" + eventName + "\",\n" +
                        "  \"description\": \"Event creation from API\",\n" +
                        "  \"templateDocumentInternalId\": \"WS3555676\",\n" +
                        "  \"isTest\": false,\n" +
                        "  \"currency\": \"USD\",\n" +
                        "  \"baseLanguage\": \"en\"\n" +
                        "}")
                .when().post(baseUri+"/Sourcing/sourcing-event/v2/events")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(createEvent);
        docID = js.getString("payload.internalId");

    }

    public void updateEvent() {
        //updateEvent()
        given().log().all().queryParam("user", System.getProperty("user"))
                .queryParam("passwordAdapter", System.getProperty("passwordAdapter"))
                .pathParam("docID", docID)
                .queryParam("realm", "s4svall-2")
                .header("Content-Type", "application/json").header("Authorization", token)
                .body("{\n" +
                        "  \"title\": \"" + updatedTitle + "\",\n" +
                        "  \"description\": \"Event creation from API\",\n" +
                        "  \"internalId\": \"" + docID + "\",\n" +
                        "  \"isTest\": false,\n" +
                        "  \"currency\": \"USD\",\n" +
                        "  \"baseLanguage\": \"en\",\n" +
                        "  \"requester\": \"customersupportadmin\",\n" +
                        "  \"passwordAdapter\": \"PasswordAdapter1\",\n" +
                        "  \"requestDate\": \"2021-03-26T22:44:30.917Z\",\n" +
                        "  \"closeDate\": \"2031-03-26T22:44:30.917Z\"\n" +
                        "}")
                .when().put(baseUri+"/Sourcing/sourcing-event/v2/events/{docID}")
                .then().log().all().assertThat().statusCode(200);
    }
}


