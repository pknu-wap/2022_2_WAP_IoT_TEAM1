#include <SPI.h>
#include <WiFi101.h>
//
char ssid_AP[] = "Feather_WiFi";
String ssid_STA = "";      //  your network SSID (name)
String pass_STA = "";   // your network password
//
int status = WL_IDLE_STATUS;
//
void setup() {
  WiFi.setPins(8,7,4,2); // SPI pin setting
  Serial.begin(9600);
  delay(1000);
  //
  if (WiFi.status() == WL_NO_SHIELD) {
        Serial.println("WiFi shield not present");
        while (true);
  }
  //
  if (ssid_STA.length() != 0) {
    connect_STA();
  } else {
    Serial.println("SSID is not setted!");
  }
  //
  if (status != WL_CONNECTED) {
    connect_AP();
  }
}
//
void loop() {
  // put your main code here, to run repeatedly:
}
//
void connect_AP() {
  int count = 0;
  while (status != WL_AP_LISTENING) {
    count++;
    Serial.print(count);
    Serial.print(" Creating access point named: ");
    Serial.println(ssid_AP);
    //
    status = WiFi.beginAP(ssid_AP);
    //
    if (count >= 3) {
      Serial.println("Creating access point failed!");
      return;
    }
  }
}
//
void connect_STA() {
  int count = 0;
  while (status != WL_CONNECTED) {
    count++;
    Serial.print(count);
    Serial.print(" Attempting to connect to Network named: ");
    Serial.println(ssid_STA);                   // print the network name (SSID);
    //
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid_STA, pass_STA);
    //
    if (count >= 3) return;
    //
    // wait 2 seconds for connection:
    delay(2000);
  }
}
