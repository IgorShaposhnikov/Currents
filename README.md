# 🌊 Currents — Offline-First News Reader

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-blue?logo=kotlin)](https://kotlinlang.org/)
[![Android SDK](https://img.shields.io/badge/Android-SDK_24+-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Compose-Material3-4285F4?logo=android)](https://developer.android.com/compose)

**Currents** is a clean, "modern", and accessible mobile news reader for Android, designed to keep you informed even in conditions of unstable connectivity (such as subways or remote areas). 

Most modern news applications are bloated with tracking scripts, heavy ads, and constant redirects to external browsers. Furthermore, they fail to function the moment you lose internet access. *Currents* was built as a lightweight, user-focused alternative. It features an integrated web rendering engine, local offline storage, and built-in text-to-speech synthesis for eyes-free listening.

The project is designed using native Android development patterns and serves as an intermediate software product submission.

> [!NOTE]
> The name **Currents** refers both to ocean currents (the flow of water) and current events (the flow of global news).

## ✨ Features

The application is built around accessibility, performance, and offline-first usability:

- 📰 **Global News Feed:** Real-time top headlines fetched dynamically from the News API.
- 💾 **Hybrid Offline Library:** Save full articles to a local SQLite database (via Room) with a single click. Bookmarked articles remain fully readable even without an internet connection.
- 🗣️ **Voice Companion (TTS):** Integrated Android `TextToSpeech` engine that reads article headlines and summaries aloud, providing an eyes-free, accessible alternative to reading.
- 🌐 **Immersive WebView:** Open and read full articles directly inside the application, ensuring a cohesive user experience without distracting redirects to external web browsers.
- 🛡️ **Secure API Storage:** Out-of-the-box configuration that prevents API keys from being leaked in public version-controlled repositories.

## 🛠 Tech Stack & Architecture

The application is built using a modern **MVVM (Model-View-ViewModel)** architectural pattern. It strictly separates the presentation layer from the data management layer, ensuring the codebase is testable, clean, and easily extensible.

**Frontend & Presentation:**
* **Kotlin** — Language of implementation.
* **Jetpack Compose** — Modern declarative UI toolkit using Material 3 guidelines.
* **Navigation Compose** — Type-safe, declarative route-based screen navigation.
* **Coil** — Fast, asynchronous, Kotlin-first image loading library for Jetpack Compose.

**Data & Network Layer:**
* **Retrofit 2 & OkHttp** — Type-safe REST client. Implements custom headers and automated request interceptors for API key handling.
* **Room Database (SQLite)** — Object-relational mapping library used as the local persistence layer. Stores article metadata for offline reading.
* **Kotlin Coroutines & Flow** — Reactive asynchronous programming to emit state changes (`UiState.Loading`, `UiState.Success`, `UiState.Error`) seamlessly from ViewModel to UI.

**Security:**
* **Local Build Configuration:** Implements automatic Gradle scripts to extract API keys from a git-ignored `local.properties` file, injecting them dynamically into the `BuildConfig` class at compile-time.

## 🚀 Quick Start

To build and run the application locally, you will need a free API key from [News API](https://newsapi.org/).

1. Clone the repository:
   ```bash
   git clone https://github.com/igorshaposhnikov/currents.git
   cd currents
   ```
2. Open the project in **Android Studio** (Koala or newer recommended).
3. Create a `local.properties` file in the root directory (if it does not exist) and add your API key:
   ```properties
   NEWS_API_KEY="your_api_key_here"
   ```
4. Build the project to generate the `BuildConfig` class.
5. Run the app on an Android Emulator or a physical device (API 24+).

## 🗺 Roadmap

The project is structured in iterative development phases.

### Phase 1: Foundation & Network Layer (Completed)
- [x] Set up base project architecture (Kotlin, Gradle configuration).
- [x] Configure secure compile-time API key integration using `BuildConfig`.
- [x] Map News API DTO structures (`NewsResponse`, `ArticleDto`, `SourceDto`).
- [x] Configure Retrofit client with logging interceptors.

### Phase 2: Offline Caching & Local DB (Completed)
- [x] Configure Room Database, Entity schema (`BookmarkEntity`), and DAOs.
- [x] Implement the `NewsRepository` layer to orchestrate local and remote data.
- [x] Design reactive UI states (`UiState.Loading`, `UiState.Success`, `UiState.Error`).
- [x] Implement the offline "Bookmarks" tab.

### Phase 3: Core UI & Special Features (Completed)
- [x] Implement modern Material 3 `NewsCard` list using Jetpack Compose.
- [x] Implement in-app `WebView` rendering inside `AndroidView`.
- [x] Integrate system-level `TextToSpeech` (TTS) playback control on the details screen.
- [x] Add pull-to-refresh and network error retry logic.

### Phase 4: Refactoring & Verification (Current Status: Completed 🚧)
- [x] Refactor navigation mapping to safely encode/decode URI links.
- [x] Verify offline fallback state behaviors.
- [x] Prepare documentation and analysis of analog solutions.

### Phase 5: Future Enhancements (Planned)
- [ ] Add categories filtering tabs (e.g., Tech, Business, Science) on the main screen.
- [ ] Implement search functionality inside the list.
- [ ] Add dark mode support and custom theme color palettes.
