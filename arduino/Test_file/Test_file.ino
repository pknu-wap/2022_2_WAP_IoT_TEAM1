/*
  ESP32 Chuck Norris Jokes
  by R. Pelayo
  Tutorial: https://www.teachmemicro.com/esp32-restful-api
  
  V1.0 - 10/27/2020
*/

#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

//Provide your own WiFi credentials
const char* ssid = "<Your WiFi SSID>";
const char* password = "<Your WiFi Password>";
//String for storing server response
String response = "";
//JSON document
DynamicJsonDocument doc(2048);


void setup(void) {
  //For displaying the joke on Serial Monitor
  Serial.begin(9600);
  //Initiate WiFi connection
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.println("");

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print("WiFi connected with IP: ");
  Serial.println(WiFi.localIP());
}

void loop(void) {
  //Initiate HTTP client
  HTTPClient http;
  //The API URL
  String request = "https://api.chucknorris.io/jokes/random";
  //Start the request
  http.begin(request);
  //Use HTTP GET request
  http.GET();
  //Response from server
  response = http.getString();
  //Parse JSON, read error if any
  DeserializationError error = deserializeJson(doc, response);
  if(error) {
     Serial.print(F("deserializeJson() failed: "));
     Serial.println(error.f_str());
     return;
  }
  //Print parsed value on Serial Monitor
  Serial.println(doc["value"].as<char*>());
  //Close connection  
  http.end();
  //Wait two seconds for next joke
  delay(2000);
}
