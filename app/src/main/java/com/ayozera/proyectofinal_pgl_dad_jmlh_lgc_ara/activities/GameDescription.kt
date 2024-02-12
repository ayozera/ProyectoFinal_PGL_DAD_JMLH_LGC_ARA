package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment

@Composable
fun GameDescription(navController: NavHostController) {
    Column {
        header()
        description()
        writeReview()
        reviewList(comments = ArrayList())
    }
}

@Composable
fun header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GameName",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Image(
            painter = painterResource(
                id = R.drawable.cluedo
            ),
            contentDescription = "Game Art",
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun description() {
    var showdescription by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Description",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = { showdescription = !showdescription }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = "Expand Description",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        if (showdescription) {
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam in dolor in enim vulputate accumsan. Fusce euismod arcu vitae odio hendrerit, vel dapibus justo vulputate. Integer cursus accumsan felis, a cursus elit. Sed sit amet hendrerit elit. Sed tincidunt vestibulum risus, vel vulputate purus efficitur ac. Praesent et vulputate odio. Nunc euismod risus a augue lacinia, in pulvinar turpis hendrerit. Nam sed quam quis sem fermentum suscipit. Nullam varius purus eu nisl malesuada, in dictum leo tristique. In hac habitasse platea dictumst. Nullam nec nunc justo.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun writeReview() {
    var userReview by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = userReview,
            onValueChange = { userReview = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Public",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun reviewBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Autor",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Review",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Date",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun reviewList(comments: ArrayList<Comment>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(comments) { comment ->
            reviewBox()
        }
    }
}
