#include <WiFi.h>
#include <WebServer.h>
#include <WiFiClient.h>
#include <WiFiAP.h>

WebServer server(80);
const char* ssid = "Connect_AP";
const char* password = "seongwoo";

void handleRoot()
{
  server.send(200, "text/plain", "Ready");
}

void handleGet()
{
  if (server.hasArg("data")) 
  {
    String data = server.arg("data");
    Serial.println("Data: " + data);
  }
  server.send(200, "text/plain", "Data Received");
}

void handlePost() 
{
  server.send(200, "text/plain", "Processing Data");
}

void handleUpload() 
{
  HTTPUpload& upload = server.upload();
  if (upload.status == UPLOAD_FILE_START) 
  {
    Serial.println("Receiving data:");
  }
  else if (upload.status == UPLOAD_FILE_WRITE) 
  {
    Serial.write(upload.buf, upload.currentSize);
  }
  else if (upload.status == UPLOAD_FILE_END) 
  {
    server.send(200, "text/plain", "Data: ");
  }
}

void setup() 
{
  Serial.begin(115200);
  Serial.println("\n * Creating AP");
  WiFi.mode(WIFI_AP);
  WiFi.softAP(ssid, password);
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);
  server.begin();
  /*Serial.print("* AP Created with IP Gateway ");
  Serial.println(WiFi.softAPIP());*/
  
  server.on("/", handleRoot);
  server.on("/get", HTTP_GET, handleGet);
  server.on("/post", HTTP_POST, handlePost, handleUpload);
  server.begin();
}

void loop() {
  
  server.handleClient();
}
