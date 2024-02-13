package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.CommentsViewModel


@Composable
fun GameDescription(navController: NavHostController) {}
@Preview
@Composable
fun GameDescriptionPrueba(){
    var comments = DataUp.getComments(LocalContext.current)
    val commentsViewModel = remember { CommentsViewModel() }
    val gameName = "Cluedo"
    val gameArt = R.drawable.cluedo
    val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam in dolor in enim vulputate accumsan. Fusce euismod arcu vitae odio hendrerit, vel dapibus justo vulputate. Integer cursus accumsan felis, a cursus elit. Sed sit amet hendrerit elit. Sed tincidunt vestibulum risus, vel vulputate purus efficitur ac. Praesent et vulputate odio. Nunc euismod risus a augue lacinia, in pulvinar turpis hendrerit. Nam sed quam quis sem fermentum suscipit. Nullam varius purus eu nisl malesuada, in dictum leo tristique. In hac habitasse platea dictumst. Nullam nec nunc justo."
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ){
        GameHeader(gameName, gameArt)
        Description(description)
        WriteReview(commentsViewModel)
        ReviewList(comments)
    }
}

@Composable
fun GameHeader(gameName: String, gameArt: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameName,
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
            fontWeight = FontWeight.ExtraBold
        )
        Image(
            painter = painterResource(
                id = gameArt
            ),
            contentDescription = "Game Art",
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun Description(description: String) {
    var showdescription by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 10.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
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
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
@Composable
fun WriteReview(viewModel: CommentsViewModel) {
    var userReview by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            value = userReview,
            onValueChange = { userReview = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(
            onClick = { viewModel.addComment("user",userReview)
                      userReview = ""
            },
            modifier = Modifier
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.primaryContainer),

        ) {
            Text(
                text = "Publish",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun reviewBox(comments: Comment) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    MaterialTheme.shapes.large
                )
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp, 10.dp)
        ) {
            Text(
                text = comments.user,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = comments.comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = comments.date,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
    }


@Composable
fun ReviewList(comments: List<Comment>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(comments) { comment ->
            reviewBox(comment)
        }
    }
}
