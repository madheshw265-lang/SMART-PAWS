```
🐾 SMART PAWS

## GPS-Based Smart Safety Belt for Dogs

SMART PAWS is a **GPS-enabled wearable safety system** designed to help pet owners monitor a dog's location and detect when the dog moves outside a predefined safe area.

The system is built around an **ESP32 microcontroller** and integrates a **NEO-6M GPS receiver, 0.96-inch OLED display, active buzzer, and push button** for real-time positioning, geofence monitoring, local status indication, and safety alerts.

An **Android companion application** provides a mobile interface for monitoring the device.

---

## 📌 Project Overview

SMART PAWS continuously monitors the device's GPS position and compares it with a user-defined **home location**.

The current prototype uses a **10-meter geofence radius**.

When the device remains within the configured boundary, the system reports a **SAFE** state. When the calculated distance exceeds the geofence radius, the system changes to an **OUTSIDE** state and activates the local buzzer alert.

### System Architecture

```text
                       GPS SATELLITES
                              │
                              ▼
                    ┌─────────────────┐
                    │     NEO-6M      │
                    │   GPS RECEIVER  │
                    └────────┬────────┘
                             │
                             │ UART
                             ▼
                    ┌─────────────────┐
                    │      ESP32      │
                    │  MAIN CONTROLLER│
                    │                 │
                    │ GPS Processing  │
                    │ Distance Check  │
                    │ Geofence Logic  │
                    └───────┬─────────┘
                            │
              ┌─────────────┼─────────────┐
              │             │             │
              ▼             ▼             ▼
       ┌────────────┐ ┌────────────┐ ┌────────────┐
       │   OLED     │ │   BUZZER   │ │   BUTTON   │
       │  DISPLAY   │ │   ALERT    │ │ SET HOME   │
       └────────────┘ └────────────┘ └────────────┘
                            │
                            │ Wi-Fi
                            ▼
                    ┌─────────────────┐
                    │  PHONE HOTSPOT  │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │  ANDROID APP    │
                    │                 │
                    │ GPS Status      │
                    │ Distance        │
                    │ Geofence Status  │
                    │ Location        │
                    └─────────────────┘
```

---

## ⭐ Key Features

| Feature                  | Description                                |
| ------------------------ | ------------------------------------------ |
| 📍 Live GPS              | Real-time positioning using NEO-6M         |
| 🛰️ Satellite Monitoring | GPS fix and satellite status               |
| 📏 Geofencing            | 10-meter configurable safety boundary      |
| 🏠 Home Location         | Set reference position using push button   |
| 🔊 Local Alert           | Buzzer activates when outside the boundary |
| 🖥️ OLED Display         | Displays GPS and safety information        |
| 📱 Android App           | Mobile monitoring interface                |
| 📶 Wi-Fi                 | Communication through phone hotspot        |
| 📊 Distance Monitoring   | Calculates distance from home              |
| 🐕 Wearable Design       | Intended for pet safety applications       |
| 🧪 Hardware Validation   | Tested using the physical prototype        |

---

## 🔄 How It Works

```
```

```
             POWER ON
                 │
                 ▼
        INITIALIZE ESP32
                 │
                 ▼
         INITIALIZE GPS
                 │
                 ▼
       SEARCH FOR GPS FIX
                 │
                 ▼
          GPS FIX VALID?
           /          \
         NO            YES
         │              │
         │              ▼
         │       READ GPS POSITION
         │              │
         │              ▼
         │       SET HOME LOCATION
         │              │
         │              ▼
         │       CALCULATE DISTANCE
         │              │
         │              ▼
         │       COMPARE WITH 10 m
         │              │
         │        ┌──────┴──────┐
         │        │             │
         │      ≤ 10 m         > 10 m
         │        │             │
         │        ▼             ▼
         │      SAFE         OUTSIDE
         │                        │
         │                        ▼
         │                  BUZZER ALERT
         │
         └────────── CONTINUOUS MONITORING
```

---

## 📐 Geofence Logic

The home position acts as the center of the safety boundary.

```
```

```
                    OUTSIDE
                       ▲
                       │
              ┌─────────────────┐
              │                 │
              │    10 METERS    │
              │                 │
              │       ●         │
              │      HOME       │
              │                 │
              └─────────────────┘
                       │
                       ▼
                    OUTSIDE
```

### Decision Logic

```
```

```
Distance ≤ 10 m
      │
      ▼
    SAFE


Distance > 10 m
      │
      ▼
   OUTSIDE
      │
      ▼
 BUZZER ALERT
```

---

## 🏠 Home Location

The push button is used to establish the current GPS position as the home/reference location.

### Process

1.  Power on the SMART PAWS device. 
2.  Wait for a valid GPS fix. 
3.  Position the device at the desired home location. 
4.  Press the home-position button. 
5.  The ESP32 stores the current GPS position. 
6.  The stored position becomes the center of the geofence. 
7.  The system continuously calculates the distance from the home position. 

---

## 🛰️ GPS Positioning

SMART PAWS uses the **NEO-6M GPS receiver** to obtain live positioning information.

The GPS receiver provides:

-  Latitude 
-  Longitude 
-  Satellite count 
-  GPS fix status 
-  Position updates 

The ESP32 communicates with the GPS module through UART.

### GPS Connection

```
```

```
NEO-6M TX  → ESP32 GPIO16
NEO-6M RX  → ESP32 GPIO17
NEO-6M VCC → ESP32 3.3V
NEO-6M GND → ESP32 GND
```

The GPS coordinates are obtained from the physical GPS receiver during operation and are not hard-coded into the system.

---

# 🔌 Hardware

## Components

| Component              | Function               |
| ---------------------- | ---------------------- |
| ESP32 Dev Module       | Main controller        |
| NEO-6M GPS             | Live GPS positioning   |
| 0.96" OLED             | Local status display   |
| Active Buzzer          | Geofence alert         |
| Push Button            | Set home location      |
| Jumper Wires           | Electrical connections |
| Breadboard             | Prototype assembly     |
| Battery / Power Source | Portable operation     |

---

## 🔧 ESP32 Pin Configuration

| Component           | Connection | ESP32 Pin |
| ------------------- | ---------- | --------- |
| NEO-6M GPS          | TX         | GPIO 16   |
| NEO-6M GPS          | RX         | GPIO 17   |
| OLED                | SDA        | GPIO 21   |
| OLED                | SCL        | GPIO 22   |
| Buzzer              | Positive   | GPIO 25   |
| Push Button         | Signal     | GPIO 27   |
| GPS                 | VCC        | 3.3V      |
| OLED                | VCC        | 3.3V      |
| GPS / OLED / Buzzer | GND        | GND       |

---

# 🖥️ OLED Display

The 0.96-inch I2C OLED provides local feedback from the device.

Example:

```
```

```
SMART PAWS

GPS  : FIXED
SAT  : 10

DIST : 5.0 m
STATUS: SAFE
```

When the device moves outside the configured geofence:

```
```

```
SMART PAWS

GPS  : FIXED

STATUS: OUTSIDE
ALERT : ACTIVE
```

The OLED allows the device status to be checked locally without depending on the Android application.

---

# 🔊 Alert System

The active buzzer is connected to:

```
```

```
ESP32 GPIO25
```

The ESP32 controls the buzzer according to the geofence state.

```
```

```
              GPS POSITION
                    │
                    ▼
            DISTANCE CALCULATION
                    │
             ┌──────┴──────┐
             │             │
          ≤ 10 m         > 10 m
             │             │
             ▼             ▼
           SAFE         OUTSIDE
                           │
                           ▼
                     BUZZER ALERT
```

---

# 📱 Android Application

SMART PAWS includes an Android companion application for mobile monitoring.

### Application Functions

-  Device monitoring 
-  GPS status 
-  Satellite information 
-  Current distance 
-  Geofence status 
-  Current latitude 
-  Current longitude 
-  Home location 
-  Map-based location 
-  Safety status 

Android source:

```
```

```
android/
└── SMART_PAWS_Android/
```

---

# 📶 Connectivity

The current prototype uses a **mobile phone hotspot** for Wi-Fi connectivity.

```
```

```
        ┌───────────────┐
        │     ESP32     │
        └───────┬───────┘
                │
                │ Wi-Fi
                ▼
        ┌───────────────┐
        │ Phone Hotspot │
        └───────┬───────┘
                │
                ▼
        ┌───────────────┐
        │  Android App  │
        └───────────────┘
```

This provides local connectivity between the ESP32 device and the Android application.

For independent remote tracking without a nearby phone hotspot, the system would require a cellular communication solution such as LTE, LTE-M, or NB-IoT.

---

# 🧪 Real Hardware Validation

SMART PAWS has been tested using the **physical hardware prototype**.

The following test cases document the observed geofence states.

---

## 🟢 SAFE — 0 Meters

The device is positioned at or very close to the configured home location.

[SMART PAWS Safe 0m](docs/media/safe_0m.jpg)

**Observed condition**

```
```

```
Distance : Approximately 0 m
Status   : SAFE
```

---

## 🟡 MOVEMENT — 5 Meters

The device is moved away from the home position while remaining inside the 10-meter geofence.

[SMART PAWS Movement 5m](docs/media/move_5m.jpg)

**Observed condition**

```
```

```
Distance : Approximately 5 m
Status   : SAFE
```

---

## 🔴 OUTSIDE — 12 Meters

The device is moved beyond the configured 10-meter geofence boundary.

[SMART PAWS Outside 12m](docs/media/unsafe_12m.jpg)

**Observed condition**

```
```

```
Distance : Approximately 12 m
Status   : OUTSIDE
Alert    : ACTIVE
```

---

# 📸 Prototype Test Results

| Test             | Distance | Geofence State | Alert |
| ---------------- | -------- | -------------- | ----- |
| Home Position    | \~0 m    | SAFE           | OFF   |
| Inside Boundary  | \~5 m    | SAFE           | OFF   |
| Outside Boundary | \~12 m   | OUTSIDE        | ON    |

These tests demonstrate the basic operation of the GPS-based geofence using the physical SMART PAWS prototype.

---

# 🎥 Prototype Video

A video of the physical SMART PAWS prototype is included in the repository.

### ▶️ [Watch the SMART PAWS Prototype Video](docs/media/Smart_paws_01.mp4)

```
```

```
docs/media/Smart_paws_01.mp4
```

---

# 📂 Project Structure

```
```

```
SMART-PAWS/
│
├── android/
│   └── SMART_PAWS_Android/
│       ├── app/
│       ├── gradle/
│       ├── build.gradle.kts
│       ├── settings.gradle.kts
│       ├── gradlew
│       ├── gradlew.bat
│       └── README.md
│
├── docs/
│   ├── media/
│   │   ├── safe_0m.jpg
│   │   ├── move_5m.jpg
│   │   ├── unsafe_12m.jpg
│   │   └── Smart_paws_01.mp4
│   │
│   └── README.md
│
├── esp32/
│   └── ESP32 Firmware
│
├── .gitignore
│
└── README.md
```

---

# 💻 Technology Stack

### Embedded System

-  ESP32 
-  C/C++ 
-  Arduino IDE 
-  Arduino ESP32 Core 
-  TinyGPSPlus 
-  Adafruit GFX Library 
-  Adafruit SSD1306 
-  ArduinoJson 

### Android

-  Kotlin 
-  Android SDK 
-  Firebase 
-  Google Services 

---

# 🛠️ Development Environment

```
```

```
Arduino IDE : 2.3.10
ESP32 Core  : 3.3.12
```

The ESP32 was connected through USB during firmware development, programming, and serial monitoring.

---

# 📡 GPS Validation

The GPS receiver was independently tested before integrating the complete geofence system.

A successful hardware test produced:

```
```

```
GPS Characters : 8400+
GPS Fix        : VALID
Satellites     : 10
Latitude       : Valid
Longitude      : Valid
```

This confirmed communication between the NEO-6M GPS receiver and the ESP32.

---

# ⚙️ Operating Sequence

```
```

```
1. Power ON
      ↓
2. ESP32 initializes
      ↓
3. GPS initializes
      ↓
4. Search for satellites
      ↓
5. Obtain valid GPS fix
      ↓
6. Set home location
      ↓
7. Start geofence monitoring
      ↓
8. Calculate current distance
      ↓
9. Compare with 10 m radius
      ↓
10. Display status on OLED
      ↓
11. Activate buzzer if outside
      ↓
12. Continue monitoring
```

---

# 🧠 Technical Approach

The system uses GPS coordinates to determine the current position of the device.

The ESP32 receives latitude and longitude from the NEO-6M module and compares the current position against the stored home position.

The calculated distance determines whether the device is:

```
```

```
SAFE
```

or

```
```

```
OUTSIDE
```

The resulting state is reflected through:

-  OLED display 
-  Buzzer alert 
-  Android application 

---

# ⚠️ Engineering Considerations

GPS accuracy can vary depending on environmental conditions.

Factors that can affect positioning include:

-  Number of visible satellites 
-  Satellite geometry 
-  Buildings 
-  Trees 
-  Indoor environments 
-  Antenna placement 
-  Multipath effects 
-  Atmospheric conditions 

Because the current geofence radius is only 10 meters, GPS position variation near the boundary may affect the geofence state.

Future firmware versions can incorporate GPS filtering and boundary hysteresis to improve stability.

---

# 🚧 Current Limitations

-  GPS accuracy depends on environmental conditions. 
-  A 10-meter boundary can be sensitive to GPS fluctuations. 
-  Wi-Fi connectivity currently depends on a nearby phone hotspot. 
-  GPS reception can be poor indoors. 
-  Battery operating time depends on the selected power system. 
-  The prototype does not currently use independent cellular communication. 

---

# 🚀 Future Development

## Hardware

-  Custom PCB 
-  Compact wearable enclosure 
-  Waterproof enclosure 
-  Improved GPS antenna placement 
-  Rechargeable battery management 
-  Battery-level monitoring 
-  Low-power operating modes 

## Communication

-  4G/LTE 
-  LTE-M 
-  NB-IoT 
-  LoRa for suitable deployments 

## GPS

-  GPS filtering 
-  Position smoothing 
-  Boundary hysteresis 
-  Improved geofence reliability 
-  Location history 
-  Route tracking 

## Android Application

-  Real-time map tracking 
-  Location history 
-  Multiple pet profiles 
-  Battery monitoring 
-  Geofence configuration 
-  Alert history 
-  Emergency contact management 
-  Improved background monitoring 

---

# 🔐 Safety

SMART PAWS is intended as a supplementary pet-monitoring system.

It should not be considered a replacement for responsible supervision of animals.

Before real-world deployment, the system should be tested under different:

-  GPS conditions 
-  Network conditions 
-  Battery levels 
-  Weather conditions 
-  Movement patterns 
-  Geofence boundary conditions 

---

# 📊 Project Status

| Feature               | Status        |
| --------------------- | ------------- |
| ESP32 Hardware        | ✅ Implemented |
| NEO-6M GPS            | ✅ Implemented |
| GPS Fix Detection     | ✅ Tested      |
| Live GPS Coordinates  | ✅ Tested      |
| OLED Display          | ✅ Implemented |
| Buzzer Alert          | ✅ Implemented |
| Home Position Button  | ✅ Implemented |
| 10 m Geofence         | ✅ Implemented |
| Android Application   | ✅ Available   |
| Real Hardware Testing | ✅ Completed   |
| Prototype Photos      | ✅ Added       |
| Prototype Video       | ✅ Added       |

---

# 📁 Repository

### GitHub

[**SMART-PAWS Repository**](https://github.com/madheshw265-lang/SMART-PAWS)

---

# 👨‍💻 Author

## Madhesh G

Computer Science & Engineering

GitHub: [@madheshw265-lang](https://github.com/madheshw265-lang)

---

# 📜 License

This project is intended for engineering development, experimentation, learning, and further research.

A suitable open-source license can be added as the project develops.
