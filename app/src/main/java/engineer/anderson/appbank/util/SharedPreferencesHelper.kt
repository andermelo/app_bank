package engineer.anderson.appbank.util

import android.content.Context
import engineer.anderson.appbank.model.UserBank

class SharedPreferencesHelper private constructor(private val context: Context){
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("userId", -1) != -1

        }

    val user: UserBank
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return UserBank(
                sharedPreferences.getInt("userId", -1),
                sharedPreferences.getString("userName","null"),
                sharedPreferences.getString("bankAccount","null"),
                sharedPreferences.getString("agency", "null"),
                sharedPreferences.getString("balance", "6.0")

            )
        }

    fun saveUser(user: UserBank) {

        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("userId", user.userId)
        editor.putString("userName", user.userName)
        editor.putString("bankAccount", user.userBankAccount)
        editor.putString("agency", user.userAgency)
        editor.putString("balance", user.userBalance)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPreferencesHelper? = null
        @Synchronized
        fun getInstance(context: Context): SharedPreferencesHelper {
            if (mInstance == null) {
                mInstance = SharedPreferencesHelper(context)
            }
            return mInstance as SharedPreferencesHelper
        }
    }

}