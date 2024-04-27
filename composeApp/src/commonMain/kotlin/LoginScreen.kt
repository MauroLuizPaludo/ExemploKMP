import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onLogin: () -> Unit) {

    val users = mapOf("mariazinha" to "123456", "ronaldo" to "fenomeno")

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var alertText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            Header()
            Spacer(modifier = Modifier.height(16.dp))

            val usernameError = UsernameField(username) { username = it }
            Spacer(modifier = Modifier.height(16.dp))

            val passwordError = PasswordField(password) { password = it }
            Spacer(modifier = Modifier.height(16.dp))

            LoginButton(username, password, users, usernameError, passwordError, onLogin) {
                showDialog = true;
                alertText = it
            }

            if (showDialog) {
                ShowDialog({
                    showDialog = false
                }, alertText)
            }
        }
    }
}

@Composable
fun Header() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "Curso KMP", fontSize = 30.sp)
    }
}

@Composable
fun UsernameField(username: String, onValueChange: (String) -> Unit): String {
    var usernameError by remember { mutableStateOf("") }
    TextField(
        value = username,
        onValueChange = {
            onValueChange(it)
            usernameError =
                if (it.length < 3) "O nome de usuário deve ter pelo menos 3 caracteres" else ""
        },
        label = { Text("Usuário") },
        modifier = Modifier.fillMaxWidth(0.8f),
        singleLine = true,
        isError = usernameError.isNotEmpty()
    )
    Text(text = usernameError, color = Color.Red)
    return usernameError
}

@Composable
fun PasswordField(password: String, onValueChange: (String) -> Unit): String {
    var passwordError by remember { mutableStateOf("") }
    TextField(
        value = password,
        onValueChange = {
            onValueChange(it)
            passwordError =
                if (it.length < 6) "A senho deve ter pelo menos 6 caracteres" else ""
        },
        label = { Text("Senha") },
        modifier = Modifier.fillMaxWidth(0.8f),
        singleLine = true,
        isError = passwordError.isNotEmpty()
    )
    Text(text = passwordError, color = Color.Red)
    return passwordError
}


@Composable
fun LoginButton(
    username: String,
    password: String,
    users: Map<String, String>,
    usernameError: String,
    passwordError: String,
    onLogin: () -> Unit,
    showDialog: (String) -> Unit
) {
    Button(onClick = {
        when {
            username.isEmpty() || password.isEmpty() -> showDialog("Informe login e senha")
            usernameError.isNotEmpty() || passwordError.isNotEmpty() -> showDialog("Verifique os campos")
            users[username] != password -> showDialog("Informações incorretas")
            else -> {
                onLogin()
            }
        }

    }) {
        Text("Entrar")
    }
}

@Composable
fun ShowDialog(onDismiss: () -> Unit, message: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Ok")
            }
        },
        text = { Text(message) }
    )
}


