package com.airsofter.airsoftermobile.register.data.network

import android.util.Log
import com.airsofter.airsoftermobile.login.data.network.requests.LoginRequest
import com.airsofter.airsoftermobile.register.data.network.requests.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.inject.Inject

class RegisterService @Inject constructor(private val registerClient: IRegisterClient){

    suspend fun registerUser(username: String, displayName: String, email: String, password: String): Boolean {
        return try {
            val hashedPassword = hashPassword(password) // Cifrar el password
            val registerRequest = RegisterRequest(username, displayName, email, hashedPassword)
            val response = registerClient.registerUser(registerRequest)

            // Verificar si la solicitud HTTP fue exitosa
            if (response.isSuccessful) {
                val registerResponse = response.body()
                // Verificar si el registro en el servidor fue exitoso
                registerResponse?.success ?: false
            } else {
                val registerResponse = response.body()
                Log.e("REGISTER", "Unsuccessful response: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            // Manejar errores de red o excepciones
            Log.e("REGISTER", "Error during register: ${e.message}", e)
            false
        }
    }


    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(Charsets.UTF_8)
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }

    suspend fun checkFieldAvailability(field: String, value: String): Boolean {
        return try {
            val response = registerClient.checkFieldAvailability(field, value)
            response.isSuccessful && response.body()?.available ?: false
        } catch (e: Exception) {
            Log.e("CHECK_AVAILABILITY", "Error checking field availability: ${e.message}", e)
            false
        }
    }

}