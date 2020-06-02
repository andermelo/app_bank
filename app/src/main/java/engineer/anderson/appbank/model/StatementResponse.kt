package engineer.anderson.appbank.model

data class StatementResponse(
    val error: ErrorApi?,
    val statementList: List<Statement>?
)