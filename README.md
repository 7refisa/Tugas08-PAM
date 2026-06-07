# Tugas Praktikum Minggu 7 — Local Data Storage

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Deskripsi

Aplikasi **Notes App** berbasis Compose Multiplatform yang diperbarui untuk mendukung **Local Data Storage** (Penyimpanan Data Lokal) menggunakan **SQLDelight** untuk database SQLite (CRUD & Search) dan **Multiplatform Settings** untuk menyimpan preferensi pengguna (Tema & Urutan Catatan) secara persisten.

---

## Fitur yang Diimplementasikan

- **SQLDelight Database (CRUD & Search):**
  - **Tambah catatan:** Disimpan secara permanen ke database SQLite lokal.
  - **Edit catatan:** Memperbarui data catatan yang sudah ada berdasarkan ID.
  - **Hapus catatan:** Fitur baru untuk menghapus catatan langsung dari detail screen.
  - **Toggle favorit:** Menyimpan status favorit secara persisten.
  - **Fitur Pencarian (Search Bar):** Pencarian catatan secara reaktif berdasarkan judul atau konten langsung dari database.
- **Multiplatform Settings (Preferensi Pengguna):**
  - **Tema Dinamis:** Pilihan Mode Gelap (Dark Mode) persisten yang dapat diaktifkan di halaman Profil dan langsung diterapkan ke seluruh aplikasi.
  - **Pengurutan (Sort Order):** Pilihan pengurutan catatan yang disimpan secara persisten:
    - *By Date Newest First* (Terbaru Dahulu — default)
    - *By Date Oldest First* (Terlama Dahulu)
- **Arsitektur Offline-First & Reactive:**
  - Menggunakan `Flow` dari SQLDelight dan Multiplatform Settings untuk sinkronisasi data UI secara *real-time* menggunakan `NotesViewModel` dan `ProfileViewModel`.

---

## Struktur Folder

```
commonMain/kotlin/com/example/myprofileapp/
│
├── data/
│   ├── local/
│   │   ├── DatabaseDriverFactory.kt # Expect factory driver database
│   │   ├── SettingsFactory.kt       # Expect factory Settings
│   │   └── SettingsManager.kt       # Pengelola preferensi (Theme & Sort)
│   │
│   ├── Note.kt                      # Model data catatan
│   └── NoteRepository.kt            # Repository terintegrasi database & seeding
│
├── viewmodel/
│   ├── NotesViewModel.kt            # Mengelola StateFlow catatan, search, & sort
│   └── ProfileViewModel.kt          # Mengelola StateFlow profil & theme
│
├── screens/
│   ├── NoteListScreen.kt            # List catatan dengan Search Bar & Sort Dropdown
│   ├── NoteDetailScreen.kt          # Detail catatan + Hapus + Favorit + Edit
│   ├── AddEditNoteScreen.kt         # Form tambah & edit catatan
│   ├── FavoritesScreen.kt           # Menampilkan list favorit secara reaktif
│   └── ProfileScreen.kt             # Profil + Switch Mode Gelap (Persisten)
│
├── components/
│   └── NoteComponents.kt            # Komponen UI reusable (NoteCard, TopBar, dll.)
│
├── utils/
│   └── DateTimeUtils.kt             # Expect formatter tanggal multiplatform
│
└── App.kt                           # Root: Scaffold, NavHost, & MaterialTheme dinamis
```

---

## Skema Database (SQLDelight)

Skema tabel didefinisikan dalam file [Note.sq](file:///composeApp/src/commonMain/sqldelight/com/example/myprofileapp/db/Note.sq):

```sql
CREATE TABLE NoteEntity (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    isFavorite INTEGER AS kotlin.Boolean NOT NULL DEFAULT 0,
    createdAt INTEGER NOT NULL
);
```

---

## Passing Arguments & Routing

| Route | Argument | Tipe | Keterangan |
|-------|----------|------|------------|
| `note_detail/{noteId}` | `noteId` | `NavType.LongType` | ID catatan (dari database) yang ditampilkan |
| `edit_note/{noteId}` | `noteId` | `NavType.LongType` | ID catatan (dari database) yang diedit |

---

## Screenshot & Demo

*(Akan ditambahkan dokumentasi/screenshot/video demonstrasi oleh pengguna)*

---

## Teknologi & Library Utama

| Komponen | Library |
|----------|---------|
| **UI Framework** | Compose Multiplatform |
| **Navigasi** | `org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10` |
| **Database Lokal** | `app.cash.sqldelight:runtime:2.0.1` |
| **Database Driver** | Android Driver, SQLite JDBC Driver (JVM), Native Driver (iOS) |
| **Key-Value Settings**| `com.russhwolf:multiplatform-settings:1.1.1` (FlowSettings) |
| **State Management** | Kotlin Coroutines Flows & MVVM ViewModels |
| **Target Platform** | Android |

---

## Dependency Baru yang Ditambahkan

```kotlin
// composeApp/build.gradle.kts
plugins {
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    // SQLDelight
    implementation(libs.sqldelight.runtime)
    implementation(libs.sqldelight.coroutines)
    
    // Multiplatform Settings
    implementation(libs.multiplatformSettings)
    implementation(libs.multiplatformSettings.coroutines)
}
```

---

## Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio Koala atau yang lebih baru
3. Sync Gradle (SQLDelight akan otomatis membuat interface database)
4. Jalankan di emulator atau device Android (min. API 26)
