#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiAP.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

#define LED_PIN 22
#define SWT_PIN 21
#define LED_BUILTIN 2

int led_st = LOW;
int swt_st, last_swt_st;
unsigned long lastt = 0;

const char *ssid = "APMode";
const char *password = "nevergiveup";

const char* serverName = "<https://data.mongodb-api.com/app/application-0-mvnjh/endpoint/RestServer>"; // change to my server name.

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
      String response = http.getString();
      Serial.println(httpResponseCode);
      Serial.println(response);
      Serial.println("Data uploaded.");
      digitalWrite(LED_BUILTIN, HIGH);
      delay(1000);
      digitalWrite(LED_BUILTIN, LOW);
    }
    else
    {
      Serial.println("ERROR : Couldn't upload Data.");
    }
    http.end();
}

void setup() 
{  
  pinMode(SWT_PIN, INPUT);
  pinMode(LED_PIN, OUTPUT);

  Serial.begin(115200);
  WiFi.mode(WIFI_AP);
  Serial.println();
  Serial.println("Configuring access point...");
  
  WiFi.softAP(ssid, password);
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);
  server.begin();
  
  Serial.println("Server started");

  digitalWrite(LED_BUILTIN, HIGH);  
  //pinMode(LED_BUILTIN, OUTPUT);
}

void loop()
{

  unsigned long startmil = millis();
  unsigned long stopmil = millis();

  unsigned long NotPressedTime = startmil - lastt;
  swt_st = digitalRead(SWT_PIN);
  
  if(swt_st == HIGH)
  {
    digitalWrite(LED_PIN, HIGH);
  }
  else
  {
    digitalWrite(LED_PIN, LOW);
  }

  if(last_swt_st != swt_st)
  {
    lastt = stopmil;
    NotPressedTime = startmil - lastt;
    last_swt_st = swt_st;
  }
   WiFiClient client = server.available();   // listen for incoming clients

  
  doc["sensors"]["time_of_Button"] = NotPressedTime/1000;
  Serial.println(NotPressedTime/1000);
  POSTData();
  delay(500);
}
