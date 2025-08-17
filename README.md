# OportuniaApp - Android CLEAN Architecture

## Overview

OportuniaApp is a demonstration project for computer science students to learn CLEAN Architecture in
Android development.
Mobile application connecting students with internship opportunities and companies with emerging talent, implementing Clean Architecture and modern Android best practices.

# Skills

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-0175C2?style=for-the-badge&logo=jetpack-compose)
![Hilt](https://img.shields.io/badge/DI-Hilt-430098?style=for-the-badge)


## 🚀 Key Features

### 👨🎓 For Students
- Customizable profile with skills and experience
- Advanced search with filters (location, industry, etc.)
- Integrated application system
- Real-time notifications

### � For Companies
- Job post creation and management
- Candidate dashboard
- Integrated evaluation system

## 🏗️ Technical Architecture

### Layer Diagram
Presentation (UI) → Domain ← Data
↑ ↑
ViewModels Use Cases
↑ ↑
Composable Repositories

text

### Technology Stack
| Layer            | Technologies                                                                 |
|------------------|-----------------------------------------------------------------------------|
| **Presentation** | Jetpack Compose, ViewModel, Navigation Component, Material 3               |
| **Domain**       | Use Cases, Domain Models, Coroutines                                       |
| **Data**         | Retrofit, Room, DataStore, Hilt, Gson                                      |

## ⚙️ Configuration

### Requirements
- Android Studio Flamingo (2022.2.1)+
- SDK Android API 26+
- Kotlin 1.8.20+
- Error handling verification

## Best Practices Demonstrated

- Single Responsibility Principle
- Interface segregation
- Result-based error handling
- Coroutine test practices with `TestDispatcher`

- Single Responsibility Principle
- Interface segregation
- Result-based error handling
- Coroutine test practices with `TestDispatcher`
