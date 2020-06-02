package engineer.anderson.appbank.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import engineer.anderson.app_bank.view.HomeFragmentDirections
import engineer.anderson.appbank.model.BankApiService
import engineer.anderson.appbank.model.Statement
import engineer.anderson.appbank.model.StatementResponse
import engineer.anderson.appbank.model.UserBank
import engineer.anderson.appbank.util.SharedPreferencesHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.sql.StatementEvent

class HomeViewModel : ViewModel() {

    private val bankApiService = BankApiService()
    private val disposable = CompositeDisposable()

    val statements = MutableLiveData<List<Statement>>()
    val userLiveData = MutableLiveData<UserBank>()
    val userLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val userlogaded = MutableLiveData<Boolean>()

    fun getUser(view : View){
        userLiveData.value = SharedPreferencesHelper.getInstance(view.context).user
    }

    fun logoutUser(view : View){
        SharedPreferencesHelper.getInstance(view.context).clear()
        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragment2ToLoginFragment2())
    }

    fun refresh(view: View){
        loading.value = true
        disposable.add(
            bankApiService.getStatements(SharedPreferencesHelper.getInstance(view.context).user.userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<StatementResponse>(){
                    override fun onSuccess(t: StatementResponse?) {
                        if (t != null) {
                            statements.value = t.statementList
                            loading.value = false
                        }
                    }

                    override fun onError(e: Throwable?) {
                        loading.value = true
                        e?.printStackTrace()
                    }

                })
        )
    }

//    fun refresh(){
//        val statement1 = Statement("Pagamento", "Conta de luz","2020-05-15",-50.0)
//        val statement2 = Statement("TED Recebida", "Joao Alfredo","2020-04-23",745.03)
//        val statement3 = Statement("DOC Recebida", "Victor Silva","2020-03-25",745.03)
//
//        val statementList = arrayListOf<Statement>(statement1,statement2,statement3)
//
//        statements.value = statementList
//        userLoadError.value = false
//        loading.value = false
//    }

}