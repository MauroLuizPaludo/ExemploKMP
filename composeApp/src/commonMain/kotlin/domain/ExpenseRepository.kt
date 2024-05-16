package domain

import Model.Expense
import Model.ExpenseCategory

interface ExpenseRepository {

    fun getAllExpense(): List<Expense>

    fun addExpense(expense: Expense)

    fun editExpense(expense: Expense)

    fun getCategories(): List<ExpenseCategory>

    suspend fun deleteExpense(id: Long)

}