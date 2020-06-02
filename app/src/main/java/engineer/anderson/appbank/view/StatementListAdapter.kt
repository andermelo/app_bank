package engineer.anderson.app_bank.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import engineer.anderson.appbank.R
import engineer.anderson.appbank.model.Statement
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_statement.view.*

class StatementListAdapter(val statementList : ArrayList<Statement>): RecyclerView.Adapter<StatementListAdapter.StatementViewHolder>() {

    class StatementViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    fun updateStatementList(newStatementList: List<Statement>){
        statementList.clear()
        statementList.addAll(newStatementList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_statement, parent, false)
        return  StatementViewHolder(view)
    }

    override fun getItemCount() = statementList.size

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        holder.view.titulo.text = statementList[position].title
        holder.view.descricao.text = statementList[position].desc
        holder.view.data.text = statementList[position].date
        holder.view.valor.text = statementList[position].value.toString()
    }

}