package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData
import data.ExpenseManager
import kotlin.random.Random

@Composable
fun ExpensesChartScreen() {
    var expenses = ExpenseManager.fakeExpenseList
    val dados = mutableListOf<PieChartData>()

    for ((name, expenses) in expenses.groupBy { it.category.name }) {
        dados.add(PieChartData(
            partName = name,
            data =   expenses.sumOf { it.amount },
            color = Color(gerarCorRandomica()),))
    }

    DonutChart(
        modifier = Modifier.fillMaxSize(),
        pieChartData = dados,
        centerTitle = "Despesas",
        centerTitleStyle = TextStyle(color = Color(0xFF071952)),
        outerCircularColor = Color.LightGray,
        innerCircularColor = Color.Gray,
        ratioLineColor = Color.LightGray,
    )
}

fun gerarCorRandomica(): Int {
    val r = Random.nextInt(256)
    val g = Random.nextInt(256)
    val b = Random.nextInt(256)

    return (255 shl 24) or (r shl 16) or (g shl 8) or b
}
