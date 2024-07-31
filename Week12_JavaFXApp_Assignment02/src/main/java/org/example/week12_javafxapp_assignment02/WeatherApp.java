package org.example.week12_javafxapp_assignment02;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApp extends Application {

    private TextField cityTextField;
    private TextArea resultTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        cityTextField = new TextField();
        cityTextField.setPromptText("Enter city name");

        Button fetchButton = new Button("Get Weather");
        fetchButton.setOnAction(e -> fetchWeatherData());

        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        VBox vbox = new VBox(10, cityTextField, fetchButton, resultTextArea);
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchWeatherData() {
        String city = cityTextField.getText().trim();
        if (city.isEmpty()) {
            showError("City name cannot be empty");
            return;
        }

        String apiKey = "api_key"; // Replace with your API key
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseWeatherData)
                .exceptionally(e -> {
                    showError("Failed to fetch data: " + e.getMessage());
                    return null;
                });