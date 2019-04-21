//导入arduino库
#include <SoftwareSerial.h>
#include<SPI.h>
#include<MFRC522.h>
#include <MsTimer2.h>
#include <ArduinoJson.h>
#include<dht11.h>

/* Typical pin layout used:
   -----------------------------------------------------------------------------------------
               MFRC522      Arduino       Arduino   Arduino    Arduino          Arduino
               Reader/PCD   Uno/101       Mega      Nano v3    Leonardo/Micro   Pro Micro
   Signal      Pin          Pin           Pin       Pin        Pin              Pin
   -----------------------------------------------------------------------------------------
   RST/Reset   RST          9             5         D9         RESET/ICSP-5     RST
   SPI SS      SDA(SS)      10            53        D10        10               10
   SPI MOSI    MOSI         11 / ICSP-4   51        D11        ICSP-4           16
   SPI MISO    MISO         12 / ICSP-1   50        D12        ICSP-1           14
   SPI SCK     SCK          13 / ICSP-3   52        D13        ICSP-3           15
*/
//定义针脚

SoftwareSerial  mySerial(2,3);  //RX=2,TX=3

#define Switch_1 5   //总开关
//#define Switch_2 4   //门禁
#define Switch_3 4   //宿舍开关
//#define Switch_4 2   //窗户

#define DoorB A1    //IB1
#define DoorA A2    //IA1
#define WindowB A3  //IB2
#define WindowA A4  //IA2

       
#define RAIN_PIN 6    //雨滴传感器
#define FIND_PIN 7    //人体红外传感器
#define DHT_PIN 8     //温湿度传感器
#define RST_PIN 9     //读卡器重置
#define SS_PIN 10     //晶片选择针脚
#define MQ7_PIN A0    //一氧化碳

#define ID 0xA2             //设备号
#define ALL_CLOSE 11     //宿舍总开关
#define ALL_OPEN 22
#define OPEN_DOOR 33    //RFID控制门禁
#define CLOSE_LIGHT 44       //关灯
#define OPEN_LIGHT 55        //开灯
#define CLOSE_WINDOW 66     //下雨关窗
#define OPEN_WINDOW 77      //开窗
#define HAVE_PERSON 88      //检测是否有人
#define READ_DHT 99       //查询温湿度


//实例化库类
MFRC522 rfid(SS_PIN, RST_PIN);
dht11 DHT11;



//存储接收到的命令
int Command = 0x00;

String serviceData = "";

int person_result = 0x00;

int rain_result = 0x00;

//默认关闭全部开关及关窗
int all_status = 0x00;
int light_status = 0x00;
int window_status = 0x00;

unsigned int MQ7 = 0;
int MQ7ppm = 0;

int warnCount = 0;    //警报次数
boolean openWindow = true;
boolean closeWindow = true;
boolean rain = true;

void setup() {

  Serial.begin(9600);
  mySerial.begin(9600);

  SPI.begin(); // 初始化SPI总线
  rfid.PCD_Init(); // 初始化 MFRC522

  //IO初始化
  pinMode(RAIN_PIN, INPUT);
  pinMode(FIND_PIN, INPUT);
  pinMode(DHT_PIN,  INPUT);
  pinMode(MQ7_PIN, INPUT);

  pinMode(Switch_1, OUTPUT);    //接NC
//  pinMode(Switch_2, OUTPUT);    //接NO
  pinMode(Switch_3, OUTPUT);    //接NC
//  pinMode(Switch_4, OUTPUT);    //接NO

  //门禁
  pinMode(DoorA,OUTPUT);    
  pinMode(DoorB,OUTPUT);    

  //窗户
  pinMode(WindowA,OUTPUT);     
  pinMode(WindowB,OUTPUT);     

  //使esp8266有足够的时间连上服务器
  delay(5000);

  Serial.print("MAC_");
  Serial.println(ID, HEX);

  mySerial.print("MAC_");
  mySerial.println(ID, HEX);

  MsTimer2::set(3600000, onTimer); //设置中断，每隔一段时间进入中断服务程序
  MsTimer2::start();         //开始计时
}


void loop() {

  DHT11.read(DHT_PIN);

  Command = 0x00;      //防止重复触发
  serviceData = "";

  //查询串口是否收到数据
  while (mySerial.available() > 0) {
    serviceData += char(mySerial.read());
    delay(5);     //使缓冲区足够时间接收数据
  }
  if (serviceData.length() > 0) {
    Serial.println(serviceData);
    Switch_Command(serviceData);
  }



  handle(all_status, light_status, window_status);
  
  IsRFID();

  IsFire();

  //检测是否有人
  IsHavePerson(person_result);

  //检测是否下雨
  IsRain(rain_result);

}

void handle(int all_status, int light_status, int window_status) {
  if (all_status == 0x00) {
    digitalWrite(Switch_1, HIGH);       //关闭全部开关
  } else if (all_status == 0x01) {
    digitalWrite(Switch_1, LOW);          //打开全部开关
  }
  if (light_status == 0x00) {
    digitalWrite(Switch_3, HIGH);         //关灯
  } else if (light_status == 0x01) {
    digitalWrite(Switch_3, LOW);          //开灯
  }
  if (window_status == 0x00) {
    if(closeWindow){
      //关窗，电机正转
      digitalWrite(WindowA,HIGH);
      digitalWrite(WindowB,LOW);
      delay(300);
      digitalWrite(WindowB,HIGH);
      closeWindow = false;
    }
  } else if (window_status == 0x01) {
      if(openWindow){
        //开窗，电机反转
        digitalWrite(WindowA,LOW);
        digitalWrite(WindowB,HIGH);
        delay(300);
        digitalWrite(WindowB,LOW);
        openWindow = false;
        rain = true;
      }
    
  }
}

void Switch_Command(String data)
{
//  Serial.println(data);
  DynamicJsonBuffer jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(data);
  String type = root["Type"].as<String>();

  if (type == "Status") {
    all_status = root[String("all")];
    light_status = root[String("light")];
    window_status = root[String("window")];
    handle(all_status, light_status, window_status);
  }
  if (type == "Command") {
    String code = root[String("Code")];
    Command = code.toInt();
  }

  switch (Command)
  {
    case READ_DHT:
      onTimer();
      break;

    case ALL_CLOSE:
      digitalWrite(Switch_1, HIGH);
      all_status = 0x00;
      sendResult();
      break;

    case ALL_OPEN:
      digitalWrite(Switch_1, LOW);
      all_status = 0x01;
      sendResult();
      break;

    case OPEN_DOOR:

      //开门，反转电机
      digitalWrite(DoorA,LOW);
      digitalWrite(DoorB,HIGH);
      delay(250);
      digitalWrite(DoorB,LOW);
      //开灯
      digitalWrite(Switch_3, LOW);
      light_status = 0x01;
      sendResult();
      delay(3000);
      //电机正转
      digitalWrite(DoorA,HIGH);
      digitalWrite(DoorB,LOW);
      delay(250);
      digitalWrite(DoorB,HIGH);
      break;

    case CLOSE_LIGHT:
      person_result = CLOSE_LIGHT;
      digitalWrite(Switch_3, HIGH);
      light_status = 0x00;
      sendResult();
      break;

    case OPEN_LIGHT:
      person_result = OPEN_LIGHT;
      digitalWrite(Switch_3, LOW);
      light_status = 0x01;
      sendResult();
      break;

    case OPEN_WINDOW:
      rain_result = OPEN_WINDOW;
      window_status = 0x01;
      rain = true;
      sendResult();
      break;

    case CLOSE_WINDOW:
      rain_result = CLOSE_WINDOW;
      window_status = 0x00;
      sendResult();
      break;

    case HAVE_PERSON:
      int result = digitalRead(FIND_PIN);
      String have = "{\"ID\":";
      have += ID;
      have += ",\"Type\":\"person\",\"Result\":";
      have += result;
      have += "}";
      Serial.println(have);
      mySerial.println(have);
      break;

  }
}

void IsRFID(void)
{
  //找卡
  if (rfid.PICC_IsNewCardPresent())
  {
    //验证UID是否可读
    if (rfid.PICC_ReadCardSerial())
    {
      String uid = "{\"ID\":";
      uid += ID;
      uid += ",\"Type\":\"cardId\",\"Card\":";
      //获取UID
      for (int i = 0; i < 4; i++)
      {
        uid += rfid.uid.uidByte[i];
      }
      uid += "}";
      Serial.println(uid);
      mySerial.println(uid);
 
    }

    //使放置在读卡区的IC卡进入休眠状态，不再重复读卡
    rfid.PICC_HaltA();

    //停止读卡模块编码
    rfid.PCD_StopCrypto1();
  }
}

void IsHavePerson(int result)
{
  if (digitalRead(FIND_PIN))    //有人返回1，无人返回0
  {
    if (result == CLOSE_LIGHT)       //实现有人也可关灯
    {
      digitalWrite(Switch_3, HIGH);
      int before = light_status;      
      light_status = 0x00;
      if (before != light_status) {
        //底层状态改变则通知服务器
        sendStatus(light_status, window_status);
      }
    } else {
      digitalWrite(Switch_3, LOW);
      int before = light_status;
      light_status = 0x01;
      if (before != light_status) {
        sendStatus(light_status, window_status);
      }
    }
  } else {
    digitalWrite(Switch_3, HIGH);
    int before = light_status;
    light_status = 0x00;
    if (before != light_status) {
      sendStatus(light_status, window_status);
    }
  }
}

void IsRain(int result)
{

  if (digitalRead(RAIN_PIN))    //无雨返回1，有雨返回0
  {
    if (result == CLOSE_WINDOW)       //实现无雨也可关窗
    {
      rain_result = 0x00;
      //关窗，电机正转
      digitalWrite(WindowA,HIGH);
      digitalWrite(WindowB,LOW);
      delay(300);
      digitalWrite(WindowB,HIGH);
      
      int before = window_status;
      window_status = 0x00;
      if (before != window_status) {
        sendStatus(light_status, window_status);
      }
    } else if (result == OPEN_WINDOW) {
      rain_result = 0x00;
      //开窗，电机反转
      digitalWrite(WindowA,LOW);
      digitalWrite(WindowB,HIGH);
      delay(300);
      digitalWrite(WindowB,LOW);
      rain = true;
      
      int before = window_status;
      window_status = 0x01;
      if (before != window_status) {
        sendStatus(light_status, window_status);
      }
    }
  } else {
      if( rain ){
        //关窗，电机正转
        digitalWrite(WindowA,HIGH);
        digitalWrite(WindowB,LOW);
        delay(300);
        digitalWrite(WindowB,HIGH);
        rain = false;
      }
      
    int before = window_status;
    window_status = 0x00;
    if (before != window_status) {
      sendStatus(light_status, window_status);
    }
  }

}

void IsFire(void) {

  //读取MQ7端口的电压值
  MQ7 = analogRead(MQ7_PIN);
  // MQ7-=>ppm
  if (MQ7 <= 48)
    MQ7ppm = 300;
  else if (MQ7 >= 1008)
    MQ7ppm = 10000;
  else
  {
    MQ7ppm = 10 * (MQ7 - 4);
  }
  int temp = DHT11.temperature;
  int hum = DHT11.humidity;
  String warning = "";
  warning += "{\"ID\":";
  warning += ID;
  warning += ",\"Type\":\"warning\"}";
//  Serial.println(MQ7ppm);
//  delay(3000);
//  Serial.println(temp);
//  Serial.println(hum);
  if(temp > 40 || hum < 50 || MQ7ppm >1500){
    warnCount++;
    Serial.println(warning);
    if(warnCount == 30){       //防止多次报警及误报
       mySerial.println(warning);
    }
  }
}



void onTimer()
{
  warnCount = 0;    //重置报警次数
  String sensor = "";
  sensor += "{\"ID\":";
  sensor += ID;
  sensor += ",\"Type\":\"sensorData\",\"Temp\":";
  sensor += DHT11.temperature;
  sensor += ",\"Hum\":";
  sensor += DHT11.humidity;
  sensor += "}";

  Serial.println(sensor);
  mySerial.println(sensor);
}

void sendStatus(int light_status, int window_status) {
  String Status = "{\"ID\":";
  Status += ID;
  Status += ",\"Type\":\"status\",\"light\":";
  Status += light_status;
  Status += ",\"window\":";
  Status += window_status;
  Status += "}";
  Serial.println(Status);
  mySerial.println(Status);
}

void sendResult() {
  String Result = "{\"ID\":";
  Result += ID;
  Result += ",\"Type\":\"result\",\"Result\":true}";
  Serial.println(Result);
  mySerial.println(Result);
}
