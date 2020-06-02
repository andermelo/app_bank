package engineer.anderson.appbank.model

data class LoginResponse (
    val error: ErrorApi?,
    val userAccount: UserBank?
)