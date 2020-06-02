package engineer.anderson.appbank.viewmodel

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import engineer.anderson.appbank.model.BankApiService
import engineer.anderson.appbank.model.ErrorApi
import engineer.anderson.appbank.model.LoginResponse
import engineer.anderson.appbank.model.UserBank
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class LoginViewModelTest {

    private lateinit var bankApiService: BankApiService
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp(){
        bankApiService = Mockito.mock(BankApiService::class.java)
        loginViewModel = LoginViewModel()
    }

    @Test
    fun testWithNetwork(){
        val user : String = "andermelo"
        val pass : String = "54564"
        val userBank : UserBank
        val errorApi : ErrorApi
        userBank = UserBank(1,"Anderson","231231","1212121","545.00")
        errorApi = ErrorApi(55,"Deu Ruim!")

        Mockito.`when`(bankApiService.userLogin(user,pass)).thenReturn(Single.just(LoginResponse(errorApi,userBank)))
        bankApiService.userLogin(user,pass)
        verify(bankApiService, times(1)).userLogin(user,pass)
    }

}



