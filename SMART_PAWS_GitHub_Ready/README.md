# 🐾 SMART PAWS

## GPS-Based Dog Safety Belt with 10 m Geofencing

<p align="center">
  <img src="assets/smart-paws-banner.png" alt="SMART PAWS Project Banner" width="100%">
</p>

<p align="center">
  <b>ESP32 + NEO-6M GPS + OLED + Buzzer + Android App</b>
</p>

---

## 📌 Project Overview

**SMART PAWS** is an ESP32-based wearable dog safety prototype designed to monitor a dog's location within a defined safe area.

The system uses a **NEO-6M GPS module** for location data and an **ESP32** for processing and 10-meter geofence detection.

### Main Features

- 📍 GPS location monitoring
- 🏠 HOME location setting
- 📏 Fixed **10-meter geofence**
- 🟢 SAFE status
- 🔴 OUTSIDE status
- 🔊 Buzzer alert outside the safe zone
- 🖥️ OLED display
- 📱 Android application
- 📶 Phone-hotspot connectivity
- 🧪 Indoor simulation/demo mode

---

## 🎯 Problem Statement

A dog can move away from a permitted area without the owner immediately noticing.

SMART PAWS creates a HOME reference point and checks the dog's current position against a **10-meter safe boundary**. When the distance exceeds the boundary, the system provides an alert.

---

## 💡 System Flow

```text
NEO-6M GPS
     │
     │ Latitude / Longitude
     ▼
   ESP32
     │
     │ Calculate distance
     ▼
10 m Geofence
  ┌──┴──┐
  │     │
≤10 m  >10 m
  │     │
  ▼     ▼
SAFE  OUTSIDE
  │     │
  │   Buzzer
  │   + OLED
  │     │
  └──┬──┘
     ▼
 Android App
```

---

## 🧩 Hardware

| Component | Purpose |
|---|---|
| ESP32 Development Board | Main controller and Wi-Fi |
| NEO-6M GPS | Location data |
| 0.96" OLED | Status and distance display |
| Active Buzzer | Outside-zone alert |
| Push Button | HOME/demo control |
| Jumper Wires | Connections |
| Breadboard | Prototype assembly |
| USB Power Bank | Demo power |

---

## 🔌 Pin Connections

| Component | Pin | ESP32 |
|---|---|---|
| NEO-6M | TX | GPIO16 / RX2 |
| NEO-6M | RX | GPIO17 / TX2 |
| NEO-6M | VCC | 3V3 |
| NEO-6M | GND | GND |
| OLED | SDA | GPIO21 |
| OLED | SCL | GPIO22 |
| OLED | VCC | 3V3 |
| OLED | GND | GND |
| Buzzer | + | GPIO25 |
| Buzzer | - | GND |
| Button | Signal | GPIO27 |
| Button | Other side | GND |

---

## ⚙️ Working Principle

1. **GPS Acquisition** — NEO-6M provides latitude, longitude and satellite information.
2. **HOME Setting** — the current position is stored as the safe reference.
3. **Distance Calculation** — ESP32 calculates the distance from HOME.
4. **Geofence Decision**:
   - `Distance ≤ 10 m` → SAFE
   - `Distance > 10 m` → OUTSIDE
5. **Alert** — OUTSIDE activates the buzzer and updates the OLED.
6. **Android Monitoring** — ESP32 sends status over the phone hotspot.

---

## 📱 Android + Phone Hotspot

```text
Phone Hotspot
      │
      ▼
    ESP32
      │
      ▼
 Android App
```

The Android application can display:

- SAFE / OUTSIDE
- Distance from HOME
- Geofence radius
- Latitude / longitude
- Satellite information
- Demonstration movement controls

### Demo Sequence

```text
HOME / 0 m
    ↓
SAFE
    ↓
MOVE 5 m
    ↓
SAFE
    ↓
MOVE 12 m
    ↓
OUTSIDE + BUZZER
    ↓
RETURN HOME
    ↓
SAFE
```

---

## 🧪 Indoor Simulation Mode

GPS satellite acquisition can be difficult inside an exhibition hall.

SMART PAWS therefore has a **clearly labelled simulation mode** for demonstrations. It generates representative movement while demonstrating the same:

- 10 m geofence
- SAFE / OUTSIDE logic
- OLED display
- Buzzer alert
- Android application workflow

The simulation mode is not presented as live GPS data.

---

## 🧰 Software

- Arduino IDE / Arduino CLI
- C/C++
- TinyGPSPlus
- Adafruit SSD1306
- Adafruit GFX Library
- ArduinoJson
- ESP32 Wi-Fi
- WebServer
- Android / Kotlin

---

## 📂 Repository Structure

```text
SMART-PAWS/
│
├── README.md
├── .gitignore
│
├── assets/
│   └── smart-paws-banner.png
│
├── esp32/
│   ├── SMART_PAWS_GPS/
│   └── SMART_PAWS_DEMO/
│
├── android/
│   └── SMART_PAWS_Android/
│
├── simulation/
│   └── wokwi/
│
└── docs/
    ├── SMART_PAWS_Project_Report.pdf
    └── SMART_PAWS_Project_Report.docx
```

---

## 🧪 Testing

| Test | Expected Result |
|---|---|
| HOME / 0 m | SAFE |
| 5 m | SAFE |
| 10 m | SAFE |
| > 10 m | OUTSIDE + buzzer |
| Return within 10 m | SAFE |

Example OLED:

```text
SMART PAWS
LAT: 12.xxxxx
LON: 80.xxxxx
DIST: 5.0m
STATUS: SAFE
```

Outside:

```text
SMART PAWS
DIST: 12.0m
!! OUTSIDE !!
```

---

## 🚀 Future Enhancements

- Cloud location tracking
- Push notifications
- SMS alerts
- Cellular connectivity
- Rechargeable battery
- Waterproof enclosure
- Adjustable geofence
- Multiple safe zones
- GPS filtering
- Low-power operation

---

## 👨‍💻 Team

| Name | Register Number |
|---|---|
| **Madhesh G** | **412725104053** |
| **Jaanavin N** | **412725104032** |

**Department:** Computer Science and Engineering (CSE-1)  
**Project Exhibition:** 2026

---

## ⚠️ Prototype Note

SMART PAWS is an academic prototype. The 10-meter boundary is selected for the project demonstration. GPS accuracy can vary with satellite visibility and environmental conditions.

---

<p align="center">
  <b>🐾 SMART PAWS — Keeping Every Paw Inside the Safe Zone 🐾</b>
</p>
