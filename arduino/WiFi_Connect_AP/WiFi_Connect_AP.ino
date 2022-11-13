#include "WiFi.h"

#define LED_BUILTIN 22
#define SWT_PIN 21
#define WIFI_NETWORK "My Network"
#define WIFI_PASSWORD "030322"
#define WIFI_TIMEOUT_MS 20000

WiFiServer server(80); //Set web server port number to 80
String header; // Variable to store the HTTP request

String output26State = "off";
String output27State = "off";
// Auxiliar variables to store the current output state

#define output26 = 26;
#define output27 = 27; // Assign output variables to GPIO pins

int led_st = LOW;
int swt_st;
int last_swt_st;

void setup() 
{
  Serial.begin(115200);

  pinMode(output26, OUTPUT);//Initialize the output variables as outputs
  pinMode(output27, OUTPUT);

  digitalWrite(output26, LOW);
  digitalWrite(output27. LOW); //Set outputs to LOW

  Serial.print("Setting AP(Access Point)...");
  // Connect to Wi-Fi network with SSID and password
  WiFi.softAP(WIFI_NETWORK, WIFI_PASSWORD); 
  // Remove the password parameter, if you want the AP (Access Point) to be open

  IPAddress IP = WiFi.softAPIP();
  Serial.print("AP IP address : ");
  Serial.println(IP);

  server.begin();

  pinMode(SWT_PIN, INPUT_PULLUP);
  pinMode(LED_BUILTIN, OUTPUT);

  swt_st = digitalRead(SWT_PIN);

  
}

void loop() 
{
  WiFiClient client = server.available();   // Listen for incoming clients

  if (client) 
  {                             // If a new client connects,
    Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) {            // loop while the client's connected
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println("Connection: close");
            client.println();
            
            // turns the GPIOs on and off
            if (header.indexOf("GET /26/on") >= 0) {
              Serial.println("GPIO 26 on");
              output26State = "on";
              digitalWrite(output26, HIGH);
            } else if (header.indexOf("GET /26/off") >= 0) {
              Serial.println("GPIO 26 off");
              output26State = "off";
              digitalWrite(output26, LOW);
            } else if (header.indexOf("GET /27/on") >= 0) {
              Serial.println("GPIO 27 on");
              output27State = "on";
              digitalWrite(output27, HIGH);
            } else if (header.indexOf("GET /27/off") >= 0) {
              Serial.println("GPIO 27 off");
              output27State = "off";
              digitalWrite(output27, LOW);
            }
            
            // Display the HTML web page
            client.println("<!DOCTYPE html><html>");
            client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            client.println("<link rel=\"icon\" href=\"data:,\">");
            // CSS to style the on/off buttons 
            // Feel free to change the background-color and font-size attributes to fit your preferences
            client.println("<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}");
            client.println(".button { background-color: #4CAF50; border: none; color: white; padding: 16px 40px;");
            client.println("text-decoration: none; font-size: 30px; margin: 2px; cursor: pointer;}");
            client.println(".button2 {background-color: #555555;}</style></head>");
            
            // Web Page Heading
            client.println("<body><h1>ESP32 Web Server</h1>");
            
            // Display current state, and ON/OFF buttons for GPIO 26  
            client.println("<p>GPIO 26 - State " + output26State + "</p>");
            // If the output26State is off, it displays the ON button       
            if (output26State=="off") {
              client.println("<p><a href=\"/26/on\"><button class=\"button\">ON</button></a></p>");
            } else {
              client.println("<p><a href=\"/26/off\"><button class=\"button button2\">OFF</button></a></p>");
            } 
               
            // Display current state, and ON/OFF buttons for GPIO 27  
            client.println("<p>GPIO 27 - State " + output27State + "</p>");
            // If the output27State is off, it displays the ON button       
            if (output27State=="off") {
              client.println("<p><a href=\"/27/on\"><button class=\"button\">ON</button></a></p>");
            } else {
              client.println("<p><a href=\"/27/off\"><button class=\"button button2\">OFF</button></a></p>");
            }
            client.println("</body></html>");
            
            // The HTTP response ends with another blank line
            client.println();
            // Break out of the while loop
            break;
          } else { // if you got a newline, then clear currentLine
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }
      }
    }
    // Clear the header variable
    header = "";
    // Close the connection
    client.stop();
    Serial.println("Client disconnected.");
    Serial.println("");
  }

  
  last_swt_st = swt_st; //마지막 상태를 저장
  swt_st = digitalRead(SWT_PIN);

  if(last_swt_st == HIGH && swt_st == LOW)
  {
    Serial.println("The button is pressed");
    led_st = !led_st;
    digitalWrite(LED_BUILTIN, led_st);
  }
}
