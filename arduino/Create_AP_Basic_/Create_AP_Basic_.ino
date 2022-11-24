#include <WiFi.h>

const char* ssid = "Connect_AP";
const char* password = "seongwoo";

void setup() 
{
  Serial.begin(115200);
  Serial.println("\n * Creating AP");
  WiFi.mode(WIFI_AP);
  WiFi.softAP(ssid, password);
  Serial.print("* AP Created with IP Gateway ");
  Serial.println(WiFi.softAPIP());

}

void loop() 
{

}
