package engineer.anderson.appbank.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import engineer.anderson.appbank.view.LoginFragmentDirections
import engineer.anderson.appbank.model.BankApiService
import engineer.anderson.appbank.model.LoginResponse
import engineer.anderson.appbank.model.UserBank
import engineer.anderson.appbank.util.SharedPreferencesHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel : ViewModel(){

    private val bankApiService = BankApiService()
    private val disposable = CompositeDisposable()
    val userLiveData = MutableLiveData<UserBank>()
    val loading = MutableLiveData<Boolean>()

    fun userLogin(view:View?,user:String,pass:String){
        loading.value = true
        disposable.add(
            bankApiService.userLogin(user,pass)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<LoginResponse>(){
                    override fun onError(e: Throwable?) {
                        loading.value = false
                        e?.printStackTrace()
                        if (view != null) {
                            Toast.makeText(view.context, "Deu ruim :(", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onSuccess(t: LoginResponse?) {
                        if (t != null) {
                            loading.value = false
                            userLiveData.value = t.userAccount
                            if (view != null) {
                                SharedPreferencesHelper.getInstance(view.context).saveUser(t.userAccount!!)
                            }
                            if (view != null) {
                                Navigation.findNavController(view).navigate(
                                    LoginFragmentDirections.actionLoginFragment2ToHomeFragment2(
                                    t.userAccount!!.userId))
                            }
                        }
                    }
                })
        )

    }

    fun loginAuto(view: View): Boolean{
        loading.value = SharedPreferencesHelper.getInstance(view.context).isLoggedIn
        return SharedPreferencesHelper.getInstance(view.context).isLoggedIn
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}