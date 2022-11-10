#include "WiFi.h"

#define LED_BUILTIN 22
#define SWT_PIN 21
#define WIFI_NETWORK "My Network"
#define WIFI_PASSWORD "030322"
#define WIFI_TIMEOUT_MS 20000

int led_st = LOW;
int swt_st;
int last_swt_st;

void connectToWiFi()
{
  Serial.print("Connecting to WiFi");
  WiFi.mode(WIFI_AP);
}

void setup() 
{
  Serial.begin(9600);
  connectToWiFi();
  pinMode(SWT_PIN, INPUT_PULLUP);
  pinMode(LED_BUILTIN, OUTPUT);

  swt_st = digitalRead(SWT_PIN);

  
}

void loop() 
{
  last_swt_st = swt_st; //마지막 상태를 저장
  swt_st = digitalRead(SWT_PIN);

  if(last_swt_st == HIGH && swt_st == LOW)
  {
    Serial.println("The button is pressed");
    led_st = !led_st;
    digitalWrite(LED_BUILTIN, led_st);
  }
}
