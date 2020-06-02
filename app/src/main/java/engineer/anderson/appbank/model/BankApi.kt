package engineer.anderson.appbank.model

import io.reactivex.rxjava3.core.Single
import okhttp3.Call
import retrofit2.http.*

interface BankApi {

//    @GET("https://bank-app-test.herokuapp.com/api/login")
//    fun getUser():Single<UserBank>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("user") user:String,
        @Field("password") password:String
    ): Single<LoginResponse>


    @GET("statements/{id}")
    fun getStatements(@Path("id") id: Int?) : Single<StatementResponse>
}