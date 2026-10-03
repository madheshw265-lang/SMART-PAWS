# SMART PAWS

## GPS-Based Dog Safety Belt with 10-Meter Geofencing

**Author:** Madhesh G

SMART PAWS is a real ESP32-based wearable prototype for location-aware dog safety. It combines a NEO-6M GPS receiver, ESP32 controller, OLED display, buzzer alert and Android application to detect when a dog moves outside a defined 10-meter safety boundary.

## System Architecture

```
GPS Satellites
      |
      v
  NEO-6M GPS
      |
     UART
      |
      v
     ESP32
      |
  +---+---+----------------+
  |       |                |
  v       v                v
 OLED   Buzzer       Phone Hotspot
                          |
                          v
                    Android App
```

## How It Works

### GPS Acquisition

The NEO-6M receives satellite signals through its antenna and decodes them into navigation data. The ESP32 reads the receiver output and processes latitude, longitude, satellite count and GPS fix status.

The antenna is the RF receiving element; the NEO-6M receiver is responsible for decoding the satellite navigation information.

### HOME Reference

A valid current GPS position can be stored as the HOME reference point.

### 10-Meter Geofence

| Condition | State |
|---|---|
| Distance <= 10 m | SAFE |
| Distance > 10 m | OUTSIDE |

When the state becomes OUTSIDE:
- OLED shows OUTSIDE
- Buzzer is activated
- Android status is updated

When the device returns within 10 m, the state returns to SAFE.

## Hardware

| Component | Purpose |
|---|---|
| ESP32 DevKit | Main controller and Wi-Fi communication |
| NEO-6M GPS | Live geographic position |
| 0.96-inch OLED | Local status/location display |
| Active buzzer | Audible boundary alert |
| Push button | HOME control |
| Prototype wiring | Hardware interconnection |
| USB-C power / portable supply | Prototype power |

## Pin Mapping

| Device | Signal | ESP32 Pin |
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
| Push button | Signal | GPIO27 |
| Push button | Other terminal | GND |

## OLED Status

Typical live operation:

```
SMART PAWS
LAT: <live latitude>
LON: <live longitude>
SAT: <satellite count>
DIST: <distance> m
SAFE
```

Outside the boundary:

```
SMART PAWS
DIST: <distance> m
OUTSIDE
ALERT
```

All position values are obtained from the live device.

## Android Connectivity

The prototype can use a smartphone hotspot as its local network:

```
Smartphone Hotspot
        |
        v
      ESP32
        |
        v
   Android App
```

The Android application can display device connection status, safety state, distance from HOME, GPS position, geofence radius and map/location information.

## Software Stack

### Embedded
- Arduino IDE / Arduino CLI
- C/C++
- ESP32 Arduino Core
- TinyGPSPlus
- Adafruit SSD1306
- Adafruit GFX Library
- ArduinoJson
- WiFi / WebServer

### Android
- Android Studio
- Kotlin
- Android SDK
- HTTP communication with ESP32

## Repository Structure

```
SMART-PAWS/
|
├── README.md
├── .gitignore
|
├── esp32/
|   └── SMART_PAWS/
|       └── SMART_PAWS.ino
|
├── android/
|   └── SMART_PAWS_Android/
|
├── hardware/
|   ├── wiring.md
|   └── bill-of-materials.md
|
└── docs/
    └── project-report.pdf
```

## Testing

### GPS Test

A valid GPS test should show:

```
Location valid: YES
Satellites: 8+
Latitude: <live value>
Longitude: <live value>
```

### Geofence Test

| Test Position | Expected Result |
|---|---|
| HOME | SAFE |
| 5 m from HOME | SAFE |
| 10 m from HOME | SAFE |
| More than 10 m | OUTSIDE + buzzer |
| Return within 10 m | SAFE |

### Android Test

1. Enable the smartphone hotspot.
2. Connect the ESP32 to the hotspot.
3. Note the ESP32 local IP.
4. Enter the IP in the Android app.
5. Test the connection.
6. Observe live status and distance.

## Engineering Considerations

GPS position is not perfectly static. Normal variation can occur because of satellite geometry, multipath and surrounding structures. A small 10-meter boundary can therefore require filtering or hysteresis for a production product.

This prototype uses a fixed 10-meter boundary for its intended operation.

For operation beyond local Wi-Fi coverage, a wide-area communication method such as cellular connectivity would be required.

## Future Enhancements

- GPS filtering and geofence hysteresis
- Rechargeable battery and low-power modes
- Weather-resistant enclosure
- Cellular connectivity for wide-area tracking
- Cloud location history
- Push notifications and SMS alerts
- Multiple safe zones
- Improved wearable mechanical design

## Author

**Madhesh G**

GitHub: [@madheshw265-lang](https://github.com/madheshw265-lang)

## License

For educational and prototype development use.
