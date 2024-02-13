package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R


@Composable
fun SignUp(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        SingUpHeader()
        SingUpBody()
    }

}
@Composable
fun SingUpHeader() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
    ) {
        Text(
            text = "Turn & points",
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 25.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.dados),
            contentDescription = "Logo de dados",
            modifier = Modifier.padding(start = 25.dp)
        )
    }
}

@Composable
fun SingUpBody() {
    var textUser by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textPass by remember { mutableStateOf("") }
    var textPassRepeat by remember { mutableStateOf("") }
    // val keys = DataUp.credentialLoader(LocalContext.current)

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sign Up",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondaryContainer)
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Pick a username",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary)
            TextField(
                value = textUser,
                placeholder = { Text("UserName") },
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Add your email",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            TextField(
                value = textEmail,
                placeholder = { Text("email@example.com") },
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Pick a password",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            TextField(
                value = textPass,
                placeholder = { Text("Password") },
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Repeat your password",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            TextField(
                value = textPassRepeat,
                placeholder = { Text("Password") },
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextButton(
                onClick = {
                },
                modifier = Modifier
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.primary),

                ) {
                Text(
                    text = "Sign Up",
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
}