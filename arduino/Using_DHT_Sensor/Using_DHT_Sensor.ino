#include <DHT.h>
#include <DHT_U.h>

#define DHTPIN 3
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

void setup() 
{
  Serial.begin(9600);
  dht.begin(9600);
}

void loop() 
{
  float hum = dht.readHumidity();
  float temp = dht.readTemperature();
  Serial.print("습도 : ");
  Serial.println(hum);

  Serial.print(" : ");
  Serial.println(temp);
  Serial.println("");
  delay(1000);
}
