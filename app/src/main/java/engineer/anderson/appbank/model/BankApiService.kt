package engineer.anderson.appbank.model

import android.content.pm.SigningInfo
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class BankApiService : BankApi {
    private val BASE_URL = "https://bank-app-test.herokuapp.com/api/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(BankApi::class.java)

    override fun userLogin(user: String, password: String): Single<LoginResponse> {
        return api.userLogin(user,password)
    }

    override fun getStatements(id: Int?): Single<StatementResponse> {
        return api.getStatements(id)
    }

}