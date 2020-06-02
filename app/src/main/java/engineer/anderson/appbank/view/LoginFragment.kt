package engineer.anderson.appbank.view

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import engineer.anderson.app_bank.view.LoginFragmentDirections
import engineer.anderson.appbank.R
import engineer.anderson.appbank.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {

    private lateinit var viewModel : LoginViewModel
    //private var userId = 0
    private val regex ="(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-])".toRegex()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        editText.setText("test_user")
        editText2.setText("Test@1")

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        button.setOnClickListener {

            val user = editText.text.toString().trim()
            val password = editText2.text.toString().trim()

            if (user.isEmpty()){
                editText.error = "usuario obrigatorio"
                editText.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                editText2.error = "senha obrigatoria"
                editText2.requestFocus()
                return@setOnClickListener
            }

            if(regex.find(password) == null){
                editText2.error = "senha possui pelo menos uma letra maiuscula, um caracter especial e um caracter alfanumérico"
                editText2.requestFocus()
                return@setOnClickListener
            }

            viewModel.userLogin(view,user,password)



        }

        if (viewModel.loginAuto(view)){
            //userId = SharedPreferencesHelper.getInstance(view.context).user.userId
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragment2ToHomeFragment2())
        }

        observeViewModel()

    }

    private fun observeViewModel(){
        viewModel.loading.observe(viewLifecycleOwner, Observer {isLoading ->
            isLoading?.let {
                loadingViewLogin.visibility = if(it) View.VISIBLE else View.GONE
                if (it){
                    //listError.visibility = View.GONE
                    button.isEnabled = false
                }
            }
        })
    }


}

