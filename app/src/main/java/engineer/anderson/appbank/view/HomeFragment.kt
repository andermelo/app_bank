package engineer.anderson.app_bank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import engineer.anderson.appbank.R
import engineer.anderson.appbank.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var userUuid = 0
    private lateinit var viewModel : HomeViewModel
    private val statementListAdapter = StatementListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.refresh(view)
        viewModel.getUser(view)

        statementList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = statementListAdapter
        }

//        arguments?.let {
//            userUuid = HomeFragmentArgs.fromBundle(it).userUuid
//            //textView.text = userUuid.toString()
//        }

        refreshLayout.setOnRefreshListener {
            statementList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            refreshLayout.isRefreshing = false
            viewModel.refresh(view)
        }

        signoutImage.setOnClickListener{

            var alertDialog = AlertDialog.Builder(view.context)
            alertDialog.setTitle("Atenção!")
            alertDialog.setMessage("Pretende sair do applicativo?")
            alertDialog.setPositiveButton("Sim", { _, _ -> viewModel.logoutUser(view)})
            alertDialog.setNegativeButton("Não", { _, _ -> })
            alertDialog.show()
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {user ->
            user?.let {
                userName.text = user.userName
                account.text = user.userBankAccount + " / " + user.userAgency
                value.text = "R$"+user.userBalance.toString()+",00"
            }
        })

        viewModel.statements.observe(viewLifecycleOwner, Observer {statement ->
            statement?.let {
                statementList.visibility = View.VISIBLE
                statementListAdapter.updateStatementList(statement)
            }

        })

        viewModel.userLoadError.observe(viewLifecycleOwner, Observer {isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if (it){
                    listError.visibility = View.GONE
                    statementList.visibility = View.GONE
                }
            }
        })
    }

}
