#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiAP.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

/*
 * IPAddress 를 써도 된다. 일단 지금 당장은 필요 없을 것으로 예상됨.
 */

#define LED_PIN 22
#define SWT_PIN 21
#define LED_BUILTIN 2

int led_st = LOW;
int swt_st, last_swt_st;
unsigned long lastt = 0;

const char *ssid = "APMode";
const char *password = "nevergiveup";

const char* ntpServer = "pool.ntp.org"; // Change to my ntpserver.
const char* serverName = "<https://data.mongodb-api.com/app/application-0-mvnjh/endpoint/jihoon>"; // change to my server name.

StaticJsonDocument<500> doc;
WiFiServer server(80);

void POSTData()
{
  if(WiFi.status() == WL_CONNECTED)
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
}

void setup() 
{  
  pinMode(SWT_PIN, INPUT);
  pinMode(LED_PIN, OUTPUT);

  Serial.begin(115200);
  Serial.println();
  Serial.println("Configuring access point...");
  
  WiFi.softAP(ssid, password);
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);
  server.begin();
  
  Serial.println("Server started");
  configTime(0, 0, ntpServer);

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

  
  doc["sensors"]["time"] = NotPressedTime/1000;
  Serial.println(NotPressedTime/1000);
  POSTData();
}
