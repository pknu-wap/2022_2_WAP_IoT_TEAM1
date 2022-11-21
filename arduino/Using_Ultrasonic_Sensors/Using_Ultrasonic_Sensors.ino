int echoPin = 12;
int trigPin = 13;

void setup() 
{
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
}

void loop() 
{
  float duration, distance;

  digitalWrite(trigPin, HIGH);
  delay(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH); // echoPin 이 HIGH를 유지한 시간을 저장한다.
  distance = ((float)(340 * duration) / 10000 ) / 2; // HIGH였을 때 시간(초음파가 보냈다가 다시 들어온 시간)을 가지고 거리를 계산한다.

  Serial.print(distance);
  Serial.println("cm");
  delay(500);
}
