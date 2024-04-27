import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MainMenuScreen() {
    var itens by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    var searchQuery by remember { mutableStateOf("") }
    var newItemName by remember { mutableStateOf("") }
    var newItemQuantidade by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Pesquisar") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            singleLine = true
        )
        Button(onClick = {showDialog = true }, modifier = Modifier.align(Alignment.End).padding(16.dp)){
            Text(text = "Adicionar Item", fontSize = 23.sp)
        }
        if(showDialog){
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    editIndex = -1
                },
                title = {
                    Text(if(editIndex >= 0) "Editar Item" else "Adicionar Item", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                },
                text = {
                    Column {
                        TextField(
                            value = newItemName,
                            onValueChange = { newItemName = it },
                            label = { Text("Nome do Item") },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        TextField(
                            value = newItemQuantidade,
                            onValueChange = { newItemQuantidade = it },
                            label = { Text("Quantidade") },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if(newItemName.isNotBlank()){
                            if(editIndex >= 0){
                                itens = itens.toMutableList().also {
                                    it[editIndex] = newItemName to (newItemQuantidade.toIntOrNull() ?: 1)
                                }
                            } else {
                                itens = itens + (newItemName to (newItemQuantidade.toIntOrNull() ?: 1))
                            }
                            newItemName= ""
                            newItemQuantidade = ""
                            showDialog = false
                            editIndex = -1
                        }
                    }){
                        Text("ok")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }){
                        Text("Cancelar")
                    }
                }
            )
        }

        val filteredItens = itens.filter { it.first.contains(searchQuery, ignoreCase = true) }

        if(filteredItens.isEmpty()){
            Text("Não foram encontrados resultados", modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp))
        } else{
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Row (modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)){
                    Text("Nome do item", Modifier.weight(3f))
                    Text("Quantidade", Modifier.weight(7f))
                }
                filteredItens.forEachIndexed { index, (name, quantity) ->
                    Row (modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(name, Modifier.weight(6f))
                        Text(quantity.toString(), Modifier.weight(2f))
                        Row {
                            Button(
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant), //deixa o botão com tom azul claro
                                onClick = {
                                    editIndex = index
                                    newItemName = name
                                    newItemQuantidade = quantity.toString()
                                    showDialog = true
                                }
                            ) { Text("Editar") }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.error),
                                onClick = {
                                    itens = itens.filterIndexed{i, _ -> i != index}
                                    if (editIndex == index){
                                        editIndex = -1 //reseta o índice de edição ao remover o item
                                    }
                                }
                            ) { Text("Apagar") }
                        }
                    }
                }
            }
        }
    }
}