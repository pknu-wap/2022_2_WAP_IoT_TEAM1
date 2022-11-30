void setup()
{
  Serial.begin(9600);
}

void loop()
{
  Serial.print("millis = "); 
  Serial.println(millis());
  unsigned long previousTime = millis();
  unsigned long elTime = millis() - previousTime;


  delay(333);
}
