package data

import Model.Expense
import Model.ExpenseCategory

object ExpenseManager {

    private  var currentId = 1L

    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 3000.0,
            category = ExpenseCategory.COFFEE,
            description = "3 Corações"
        ),
        Expense(
            id = currentId++,
            amount = 6000.0,
            category = ExpenseCategory.PARTY,
            description = "Bloco CAmaleão - Bell Marques"
        ),
        Expense(
            id = currentId++,
            amount = 3000.00,
            category = ExpenseCategory.SNACKS,
            description = "Biscoito"
        ),
        Expense(
            id = currentId++,
            amount = 200.00,
            category = ExpenseCategory.PARTY,
            description = "Farofa da GKay"
        ),
        Expense(
            id = currentId++,
            amount = 10000.00,
            category = ExpenseCategory.CAR,
            description = "BYD DOUPHIN"
        ),
        Expense(
            id = currentId++,
            amount = 3000.00,
            category = ExpenseCategory.HOUSE,
            description = "LIMPEZA"
        ),
        Expense(
            id = currentId++,
            amount = 2000.00,
            category = ExpenseCategory.OTHER,
            description = "LIMPEZA"
        ),
        Expense(
            id = currentId++,
            amount = 1000.0,
            category = ExpenseCategory.OTHER,
            description = "SERVIÇOS PISCINA"
        ),
    )

    fun addNewExpense(expense: Expense){

        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun deleteExpense(id: Long){
        val index = fakeExpenseList.indexOfFirst{
            it.id == id
        }
        fakeExpenseList.removeAt(index)
    }

    fun editExpense(expense: Expense){
        val index = fakeExpenseList.indexOfFirst{
            it.id == expense.id
        }
        if (index != -1){
            fakeExpenseList[index] = fakeExpenseList[index].copy(
                amount = expense.amount,
                category = expense.category,
                description = expense.description
            )
        }
    }

    fun getCategories(): List<ExpenseCategory>{
        return listOf(
            ExpenseCategory.PARTY,
            ExpenseCategory.COFFEE,
            ExpenseCategory.SNACKS,
            ExpenseCategory.CAR,
            ExpenseCategory.HOUSE,
            ExpenseCategory.OTHER,
        )
    }


}