package com.housewise.core.network

import com.housewise.HousewiseApp
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Fetch token from global SessionManager
        val token = HousewiseApp.sessionManager.fetchAuthToken()

        if (!token.isNullOrEmpty()) {
            // Your API response already includes "Bearer " in the string, 
            // so we pass it directly to the Authorization header.
            requestBuilder.addHeader("Authorization", token)
        }

        return chain.proceed(requestBuilder.build())
    }
}