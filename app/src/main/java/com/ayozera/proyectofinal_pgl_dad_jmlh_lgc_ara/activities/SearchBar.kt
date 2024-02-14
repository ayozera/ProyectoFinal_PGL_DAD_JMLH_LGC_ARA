package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


@Composable
fun SearchBar(navController: NavHostController, appMainViewModel: AppMainViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu(navController = navController, appMainViewModel)
        },
        content = {
            PruebaSubirImagen()
        }
    )
}


@Composable
fun PruebaSubirImagen() {

    var isUpLoading by remember { mutableStateOf(false) }
    val img: Bitmap = BitmapFactory.decodeResource(
        Resources.getSystem(),
        android.R.drawable.ic_menu_report_image
    )
    val bitmap = remember { mutableStateOf(img) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        if (it != null) {
            bitmap.value = it
        }
    }

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = it?.let { it2 ->
                ImageDecoder.createSource(context.contentResolver, it2)
            }
            bitmap.value = source?.let { it1 ->
                ImageDecoder.decodeBitmap(it1)
            }!!
        }
    }

    Column {
        Image(
            bitmap = bitmap.value.asImageBitmap(),
            contentDescription = "Imagen",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(200.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
        )
    }

    Box(
        modifier = Modifier.padding(top = 280.dp, start = 260.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.round_add_box_24),
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .size(50.dp)
                .padding(10.dp)
                .clickable { showDialog = true }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
    ) {
        Button(
            onClick = {
                isUpLoading = true
                bitmap.value.let { bitmap ->
                    FirebaseApp.initializeApp(context)
                    uploadImageToFirebase(bitmap, context as ComponentActivity) { success ->
                        isUpLoading = success
                        if (success) {
                            Toast.makeText(context, "Imagen subida", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    isUpLoading = false
                }
            },
            colors = ButtonDefaults.buttonColors(
                Color.Blue
            )
        ) {
            Text(text = "Subir imagen")
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp),
    ) {
        if (showDialog) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                Column(
                    modifier = Modifier.padding(start = 60.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.game_search),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                launcher.launch()
                                showDialog = false
                            }
                    )
                    Text(
                        text = "Camara",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.padding(30.dp))
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.dados),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                launcherGallery.launch("image/*")
                                showDialog = false
                            }
                    )
                    Text(text = "Galeria", color = MaterialTheme.colorScheme.primary)
                }
                Column(
                    modifier = Modifier.padding(start = 50.dp, bottom = 80.dp)
                ) {
                    Text(
                        text = "X",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { showDialog = false }
                    )
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        if (isUpLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }
}

fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback: (Boolean) -> Unit) {
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("images/${bitmap}")

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageData = baos.toByteArray()

    imageRef.putBytes(imageData)
        .addOnSuccessListener {
            callback(true)
        }
        .addOnFailureListener {
            callback(false)
        }
}
