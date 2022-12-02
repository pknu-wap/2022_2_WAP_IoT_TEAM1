#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiAP.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

#define LED_BUILTIN 2
#define echoPin 21
#define trigPin 22

const char* ssid = "APMode2";
const char* password = "nevergiveup";

const char* serverName = "<https://data.mongodb-api.com/app/application-0-mvnjh/endpoint/RestServer>";

StaticJsonDocument<500> doc;
WiFiServer server(80);

void POSTData()
{
    HTTPClient http;

    http.begin(serverName);
    http.addHeader("Content-Type", "application/json");

    String json;
    serializeJson(doc, json);

    Serial.println(json);
    int httpResponseCode = http.POST(json);
    Serial.println(httpResponseCode);

    if(httpResponseCode == 200)
    {
      Serial.println("Data uploaded.");
      digitalWrite(LED_BUILTIN, HIGH);
      delay(1000);
      digitalWrite(LED_BUILTIN, LOW);
    }
    else
    {
      Serial.println("ERROR : Couldn't upload Data.");
    }
}


void setup() 
{
  Serial.begin(115200);
  WiFi.mode(WIFI_AP);
  Serial.println();
  Serial.println("Configuring access point...");

  WiFi.softAP(ssid, password);
  IPAddress myIP = WiFi.softAPIP();
  Serial.println(myIP);
  server.begin();

  Serial.println("Server started");

  digitalWrite(LED_BUILTIN, HIGH);
  //pinMode(LED_BUILTIN, OUTPUT);

  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  
}

void loop() 
{
  float duration, distance;

  digitalWrite(trigPin, HIGH);
  delay(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = ((float)(340 * duration) / 10000);

  Serial.print(distance);
  Serial.println("cm");
  delay(500);
  
  WiFiClient client = server.available();

  doc["sensors"]["Ultrasonic"] = distance;
  Serial.println(distance);
  POSTData();
  delay(500);
}
