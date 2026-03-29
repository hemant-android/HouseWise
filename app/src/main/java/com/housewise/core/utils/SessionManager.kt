package com.housewise.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "housewise_prefs"
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val HAS_SEEN_ONBOARDING = "has_seen_onboarding"
    }

    // Save the token
    fun saveAuthToken(token: String) {
        prefs.edit { putString(USER_TOKEN, token) }
    }

    // Fetch the token
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    // Save User ID
    fun saveUserId(id: Int) {
        prefs.edit { putInt(USER_ID, id) }
    }

    fun fetchUserId(): Int {
        return prefs.getInt(USER_ID, -1) // Returns -1 if no ID is found
    }

    // --- First Name ---
    fun saveFirstName(name: String) {
        prefs.edit { putString(FIRST_NAME, name) }
    }

    fun fetchFirstName(): String? {
        return prefs.getString(FIRST_NAME, null)
    }

    // --- Last Name ---
    fun saveLastName(name: String) {
        prefs.edit { putString(LAST_NAME, name) }
    }

    fun fetchLastName(): String? {
        return prefs.getString(LAST_NAME, null)
    }

    // --- Onboarding State ---
    fun setOnboardingSeen(seen: Boolean) {
        prefs.edit { putBoolean(HAS_SEEN_ONBOARDING, seen) }
    }

    fun hasSeenOnboarding(): Boolean {
        return prefs.getBoolean(HAS_SEEN_ONBOARDING, false)
    }

    // --- Login State Helper ---
    fun isLoggedIn(): Boolean {
        // If the token exists and isn't empty, the user is logged in
        return !fetchAuthToken().isNullOrEmpty()
    }
    
    // Clear session on Logout
    fun clearSession() {
        prefs.edit { clear() }
    }
}