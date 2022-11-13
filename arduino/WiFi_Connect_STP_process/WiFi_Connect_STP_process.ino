#include "WiFi.h"
#include <Time.h>

#define LED_BUILTIN 22
#define SWT_PIN 21
#define WIFI_NETWORK "My Network" // The name of network
#define WIFI_PASSWORD "030322" //password of network
#define WIFI_TIMEOUT_MS 20000
/*장치가 wifi에 연결을 실패 했을 때 ESP를 재시작하거나 
 * 절전 모드를 이용할 때쓰인다. 20초가 된다. */

int led_st = LOW;
int swt_st;
int last_swt_st;
int count_sec = 0;

void connectToWiFi()
{
  Serial.print("Connecting to WiFi"); //set the mode of wifi chip
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_NETWORK, WIFI_PASSWORD);

  unsigned long startAttemptTime = millis(); 
  /* millis - 업타임을 반환하는 함수, 
   *  At startAttemptTime, The uptime that ESP32 will start a connect.*/
  while(WiFi.status() != WL_CONNECTED && millis() - startAttemptTime < WIFI_TIMEOUT_MS)
  {
    Serial.print(".");
    delay(100);
  }

  if(WiFi.status() != WL_CONNECTED) //if Wifi isn't connected
  {
    Serial.println("Failed"); // take action (Its up to my battery) - 절전 모드 혹은 리부팅
  }
  else // if connected
  {
    Serial.print("Connected");
    Serial.println(WiFi.localIP());
  }
}
/* wifi의 모드에는 두가지 종류가 있다.
 * 1. STA (or station mode) -> Use exist Wifi.
 * 2. AP (Accesss point mode) -> Create own Wifi. Useful if someone else 
 * can figure it.
 * 
 */

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
  if(last_swt_st == HIGH && swt_st == LOW || last_swt_st == LOW && swt_st == HIGH)
  {
    
  }
}
