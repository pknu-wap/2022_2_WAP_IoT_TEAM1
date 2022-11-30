#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiAP.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

/*
 * IPAddress 를 써도 된다. 일단 지금 당장은 필요 없을 것으로 예상됨.
 */

#define LED_PIN 4
#define SWT_PIN 21
#define LED_BUILTIN 2

int led_st = LOW;
int swt_st, last_swt_st;
unsigned long lastt = 0;

const char *ssid = "APMode";
const char *password = "nevergiveup";

const char* ntpServer = "pool.ntp.org"; // Change to my ntpserver.
const char* serverName = "<mongo-server-endpoing-url>"; // change to my server name.

StaticJsonDocument<500> doc;
WiFiServer server(80);

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
  
   WiFiClient client = server.available();   // listen for incoming clients

  if (client) 
  {                                         // if you get a client,
    Serial.println("New Client.");          // print a message out the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) 
    {                                       // loop while the client's connected
      if (client.available()) 
      {                                     // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        if (c == '\n') 
        {                                   // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) 
          {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
            
            if(last_swt_st != swt_st)
            {
              lastt = stopmil;
              NotPressedTime = startmil - lastt;
              last_swt_st = swt_st;
            }

            client.print("The led doesn't changed for");
            client.println(NotPressedTime/1000);
            client.print("seconds.");

            // The HTTP response ends with another blank line:
            client.println();
            // break out of the while loop:
            break;
          } 
          else 
          {    // if you got a newline, then clear currentLine:
            currentLine = "";
          }
        } 
        
        else if (c != '\r') 
        {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }

        // Check to see if the client request was "GET /H" or "GET /L":
       /* if (currentLine.endsWith("GET /H")) 
        {
          digitalWrite(LED_BUILTIN, HIGH);               // GET /H turns the LED on
        
        }
        if (currentLine.endsWith("GET /L")) 
        {
          digitalWrite(LED_BUILTIN, LOW);                // GET /L turns the LED off
        
        }
        */
      }
    }
    doc["sensors"]["time"] = NotPressedTime;
    // close the connection:
    client.stop();
    Serial.println("Client Disconnected.");
    POSTData();
  }
}

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
