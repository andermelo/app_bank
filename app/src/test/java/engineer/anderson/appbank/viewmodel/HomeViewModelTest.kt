package engineer.anderson.appbank.viewmodel

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import engineer.anderson.appbank.model.BankApiService
import engineer.anderson.appbank.model.ErrorApi
import engineer.anderson.appbank.model.Statement
import engineer.anderson.appbank.model.StatementResponse
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class HomeViewModelTest {

    private lateinit var bankApiService: BankApiService

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp(){
        bankApiService = Mockito.mock(BankApiService::class.java)
        homeViewModel = HomeViewModel()
    }

    @Test
    fun testWithNetwork(){
        var id : Int = 0

        val errorApi : ErrorApi = ErrorApi(55,"Deu Ruim!")

        val statement1 = Statement("Pagamento", "Conta de luz","2020-05-15",-50.0)
        val statement2 = Statement("TED Recebida", "Joao Alfredo","2020-04-23",745.03)
        val statement3 = Statement("DOC Recebida", "Victor Silva","2020-03-25",745.03)
        val statementList = arrayListOf<Statement>(statement1,statement2,statement3)

        Mockito.`when`(bankApiService.getStatements(id)).thenReturn(Single.just(StatementResponse(errorApi,statementList)))
        bankApiService.getStatements(id)
        verify(bankApiService, times(1)).getStatements(id)

    }


}