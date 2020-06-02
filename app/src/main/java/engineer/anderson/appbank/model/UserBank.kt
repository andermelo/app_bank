package engineer.anderson.appbank.model

import com.google.gson.annotations.SerializedName

data class UserBank(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("name")
    val userName: String?,

    @SerializedName("bankAccount")
    val userBankAccount: String?,

    @SerializedName("agency")
    val userAgency: String?,

    @SerializedName("balance")
    val userBalance: String?
)