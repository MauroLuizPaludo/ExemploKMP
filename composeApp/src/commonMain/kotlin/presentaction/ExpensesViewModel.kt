package presentaction

import Model.Expense
import Model.ExpenseCategory
import domain.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class ExpensesUiState{
    object Loading : ExpensesUiState()
    data class Success(val expenses: List<Expense>, val total: Double): ExpensesUiState()
    data class Error(val message: String) : ExpensesUiState()
}
class ExpensesViewModel(private val repo: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            updateExpenseList()
        }
    }

    private suspend fun updateExpenseList(){
        try{
            val expenses = repo.getAllExpense()
            _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
        }catch (e: Exception){
            _uiState.value = ExpensesUiState.Error(e.message ?: "Ocorreu um erro")
        }
    }

    fun addExpense(expense: Expense){
        viewModelScope.launch {
            try{
                repo.addExpense(expense)
                updateExpenseList()
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocorreu um erro")
            }
        }
    }

    fun editExpense(expense: Expense){
        viewModelScope.launch {
            try{
                repo.editExpense(expense)
                updateExpenseList()
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocorreu um erro")
            }
        }
    }

    fun deleteExpense(id: Long){
        viewModelScope.launch {
            try{
                repo.deleteExpense(id)
                updateExpenseList()
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocorreu um erro")
            }
        }
    }

    fun getExpenseWithID(id: Long) : Expense? {
       return (_uiState.value as? ExpensesUiState.Success)?.expenses?.firstOrNull {it.id == id}
    }

    fun getCategories() : List<ExpenseCategory> {
        return repo.getCategories()
    }
}