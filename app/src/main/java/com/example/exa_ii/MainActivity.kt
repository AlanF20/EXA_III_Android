package com.example.exa_ii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.exa_ii.ui.theme.EXA_IITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EXA_IITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val database = Room.databaseBuilder(this, GuitarDatabase::class.java, "guitar").build()
                    val guitarDao = database.guitarDao()
                    val viewModel = GuitarViewModel(guitarDao)
                    NavHost(
                        navController = navController,
                        startDestination = "guitars"
                    ){
                        composable("guitars"){
                            MainBody(navController = navController, viewModel = viewModel)
                        }
                        composable("add_guitars"){
                            AddGuitarBody(navController = navController, viewModel= viewModel)
                        }
                    }
                }
            }
        }
    }
}

