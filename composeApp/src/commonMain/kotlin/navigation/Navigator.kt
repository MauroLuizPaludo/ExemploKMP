package navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import data.ExpenseManager
import data.ExpenseRepoImpl
import getColorsTheme
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.viewmodel.viewModel
import presentaction.ExpensesViewModel
import ui.ExpensesChartScreen
import ui.ExpensesDetailScreen
import ui.ExpensesScreen

@Composable
fun Navigation(navigator: Navigator) {

    val colors = getColorsTheme()


    ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))


    val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
        ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
    }


    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState = uiState, onExpenseClick = { expense ->
                navigator.navigate("/addExpenses/${expense.id}")
            }) { expenseToDelete ->
                viewModel.deleteExpense(expenseToDelete.id)
            }
        }

        scene(route = "/addExpenses/{id}?") { backStackEntry ->
            val idFromPath = backStackEntry.path<Long>("id")
            val expenseToEditOrAdd = idFromPath?.let { id -> viewModel.getExpenseWithID(id) }

            ExpensesDetailScreen(
                expenseToEdit = expenseToEditOrAdd,
                categoryList = viewModel.getCategories()
            ) { expense ->
                if (expenseToEditOrAdd == null) {
                    viewModel.addExpense(expense)
                } else {
                    viewModel.editExpense(expense)
                }
                navigator.popBackStack()
            }
        }
        scene(route = "/chart") {
            ExpensesChartScreen(
            )
        }
    }
}


// }

//  val viewModel = koinViewModel(ExpensesViewModel::class) { parametersOf() }
// val viewModel = viewModel(modelClass = ExpensesViewModel::class)
// {
/*


@Composable
fun Navigation(navigator: Navigator) {
    val colors = getColorsTheme()
    val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
        ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
    }
    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState) { expense ->
                navigator.navigate("/addExpenses/${expense.id}")
            }
        }
        scene(route = "/addExpenses/{id}") { backStackEntry ->
            val idFromPath = backStackEntry.path<Long>("id")
            val expenseToEditOrAdd = idFromPath?.let { id -> viewModel.getExpenseWithID(id) }

            ExpensesDetailScreen(expenseToEdit = expenseToEditOrAdd, categoryList = viewModel.getCategories()){ expense ->
                if(expenseToEditOrAdd == null) {
                    viewModel.addExpense(expense)
                } else {
                    viewModel.editExpense(expense)
                }
                navigator.popBackStack()

            }
        }
    }
}
and ends with */
