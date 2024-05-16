package data

import Model.Expense
import Model.ExpenseCategory
import domain.ExpenseRepository

data class ExpenseRepoImpl(private val expenseManager: ExpenseManager): ExpenseRepository {
    override fun getAllExpense(): List<Expense>{
        return ExpenseManager.fakeExpenseList
    }

    override fun addExpense(expense: Expense){
        ExpenseManager.addNewExpense(expense)
    }

    override fun editExpense(expense: Expense){
       ExpenseManager.editExpense(expense)
    }

    override fun getCategories(): List<ExpenseCategory>{
        return ExpenseManager.getCategories()
    }

    override suspend fun deleteExpense(id: Long){
        return ExpenseManager.deleteExpense(id)
    }
}
