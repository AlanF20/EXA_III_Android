package com.example.exa_ii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exa_ii.ui.theme.EXA_IITheme

class MainGuitarsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EXA_IITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun MainBody(navController: NavController , modifier: Modifier = Modifier, viewModel: GuitarViewModel){
    Main(navController = navController,viewModel = viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(modifier: Modifier = Modifier, viewModel: GuitarViewModel, navController: NavController){
    val state = viewModel.state

    Scaffold (topBar = {
        TopAppBar(title = { Text(text = "Listado de guitarras")})
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_guitars") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar guitarra")
            }
        }){
            if(state.guitarList.isEmpty()){
                NoGuitarsLayout(padding = it)
            }else{
                ListOfGuitars(pad = it, viewModel = viewModel)
            }
    }
}

@Composable
fun NoGuitarsLayout(modifier: Modifier = Modifier, padding: PaddingValues = PaddingValues(0.dp)){
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No hay guitarras en este momento. Para añadir una haga click en el boton de +.",
            modifier = Modifier,
            textAlign = TextAlign.Center,
        )
    }
}
@Composable
fun GuitarCard(modifier: Modifier = Modifier, guitar: Guitar, viewModel: GuitarViewModel){
    var openAlertDeleteDialog by remember {
        mutableStateOf(false)
    }
    ElevatedCard(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .width(350.dp)
            .height(260.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
        ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            text = guitar.model,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = guitar.brand,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.chida),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(120.dp)
                .background(color = Color.Transparent),

        )
        Text(
            text = "$${guitar.price}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Editar")
            }
            IconButton(onClick = { openAlertDeleteDialog = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
            }
            when {
                openAlertDeleteDialog -> {
                    AlertDialogExample(
                        onDismissRequest = {
                            openAlertDeleteDialog = false
},
                        onConfirmation = {
                            viewModel.deleteGuitar(guitar)
                            openAlertDeleteDialog = false
                        },
                        dialogTitle = "Eliminar guitarra",
                        dialogText = "Esta seguro de eliminar a la guitarra?.",
                        icon = Icons.Default.Delete
                    )
                }
            }
        }
    }
}
@Composable
fun ListOfGuitars(modifier:Modifier= Modifier, pad: PaddingValues = PaddingValues(0.dp), viewModel: GuitarViewModel){
    val state = viewModel.state

    Column(modifier = Modifier
        .padding(pad)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(state.guitarList){
                GuitarCard(guitar = it, viewModel = viewModel)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
