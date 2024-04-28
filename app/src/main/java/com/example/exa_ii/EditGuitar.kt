package com.example.exa_ii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.exa_ii.ui.theme.EXA_IITheme

class EditGuitar : ComponentActivity() {
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditGuitarBody(navController: NavController, viewModel: GuitarViewModel, guitarId: Int){

    viewModel.getGuitar(guitarId)
    val state = viewModel.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Editar guitarra") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back" )
                } })
        },
    ) {
        if (state.selectedGuitar.model.isEmpty()){
            Text(text = "Cargando...")
        }else{
            GuitarEditForm(pad = it, viewModel, navController, state.selectedGuitar!!)
        }
    }
}


@Composable
fun GuitarEditForm(pad: PaddingValues = PaddingValues(0.dp), viewModel: GuitarViewModel, navController: NavController, guitarInfo: Guitar){

    var openAlertDialog by remember {
        mutableStateOf(false)
    }
    var model by remember {
        mutableStateOf(guitarInfo.model)
    }
    var brand by remember {
        mutableStateOf(guitarInfo.brand)
    }
    var price by remember {
        mutableDoubleStateOf(guitarInfo.price)
    }
    var selectedType by remember { mutableStateOf(GuitarType.SIX_STRING) }
    var showMenu by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .padding(pad)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = model,
            onValueChange = {model = it},
            label = { Text(text = "Modelo")}
        )
        OutlinedTextField(
            value = brand,
            onValueChange = { brand = it },
            label = { Text("Marca") }
        )
        OutlinedTextField(
            value = price.toString(),
            onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
            label = { Text(guitarInfo.price.toString()) }
        )
        Box{
            Text(
                text = "Click aqui para seleccionar el tipo de guitarra",
                modifier = Modifier
                    .clickable { showMenu = true }
                    .padding(10.dp)
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                content = {
                    DropdownMenuItem(onClick = { selectedType = GuitarType.SIX_STRING; showMenu = false }, text = {
                        Text("Seis cuerdas")
                    })
                    DropdownMenuItem(onClick = { selectedType = GuitarType.TWELVE_STRING; showMenu = false }, text = {
                        Text("Doce cuerdas")
                    })
                }
            )
        }

        Button(onClick = {
            val guitar = Guitar(id = guitarInfo.id, brand = brand, price = price, model = model, type = selectedType)
            viewModel.updateGuitar(guitar)
            openAlertDialog = true
        }) {
            Text(text = "Editar")
        }
        when {
            openAlertDialog -> {
                AlertDialogExample(
                    onDismissRequest = {
                        navController.popBackStack()
                        openAlertDialog = false
                    },
                    onConfirmation = {
                        navController.popBackStack()
                        openAlertDialog = false
                    },
                    dialogTitle = "Informacion",
                    dialogText = "Guitarra editada exitosamente.",
                    icon = Icons.Default.Info
                )
            }
        }
    }
}