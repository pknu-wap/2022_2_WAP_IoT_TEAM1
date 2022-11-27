#include <WiFi.h>
#include <WiFiClient.h>

const char* ssid = "Connect_AP";
const char* password = "seongwoo";

#define echoPin 22 //Esp32 pin GPIO23 connected to Ultrasonic sensor's trig Pin.
#define trigPin 23 //Same.

void setup() 
{
  Serial.begin(115200);
  Serial.println("\n * Creating AP");
  WiFi.mode(WIFI_AP);
  WiFi.softAP(ssid, password);
  Serial.print("* AP Created with IP Gateway ");
  Serial.println(WiFi.softAPIP()); //Connect WiFi by AP Mode

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
  distance = ((float)(340 * duration)/10000);

  Serial.print(distance);
  Serial.println("cm");
  delay(500);  
}
