const int Button = 2;
const int LED = 3;
int Buttonstate = 0;

void setup()
{
  pinMode (Button, INPUT);
  pinMode (LED, OUTPUT);
}

void loop() 
{
  Buttonstate = digitalRead(Button);
  if(Buttonstate == HIGH)
  {
    digitalWrite(LED, HIGH);
  }
  else
  {
    digitalWrite(LED, LOW);
  }
}
