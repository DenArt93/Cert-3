package org.example.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.example.config.ConfigProvider;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private final ConfigProvider configProvider;
    private String token;

    public ApiClient() {
        this.configProvider = new ConfigProvider();
    }

    public void generateToken() throws IOException {
        String username = configProvider.getUser();
        String password = configProvider.getPassword();
        String tokenUrl = configProvider.getTokenUrl();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", password);

        String response = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
                .post(tokenUrl)
                .then()
                .extract()
                .asString();

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonResponse = (JSONObject) parser.parse(response);
            this.token = (String) jsonResponse.get("token");
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse token response.", e);
        }
    }

    public Response addBook(String filePath) throws IOException {
        String addBookUrl = configProvider.getAddBookUrl();

        File file = new File(filePath);
        String jsonPayload = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        return given()
                .header("Authorization", "Bearer " + this.token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .post(addBookUrl);
    }

    public void generateTokenAndAddBook(String filePath) throws IOException {
        generateToken();
        addBook(filePath);
    }
}
