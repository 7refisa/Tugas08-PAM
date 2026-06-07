package com.example.myprofileapp.data

// Data class yang menyimpan semua UI state profil
// Setiap perubahan dibuat lewat copy() agar immutable
data class ProfileUiState(
    val name: String = "Refi Ikhsanti",
    val role: String = "Informatics Student",
    val bio: String = "Mahasiswa Teknik Informatika ITERA semester 6 yang passionate di bidang UI/UX Design dan Data. Aktif berorganisasi dan saat ini sedang mendalami Artificial Intelligence.",
    val email: String = "refi.123140126@student.itera.ac.id",
    val phone: String = "+62 85840438525",
    val location: String = "Lampung Selatan",
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false
)
