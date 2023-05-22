package com.example.koinandktortutorial.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.koinandktortutorial.ui.MainActivityViewModel
import com.example.koinandktortutorial.ui.MovieListViewModel
import com.example.koinandktortutorial.ui.ScreenMessage
import com.example.koinandktortutorial.ui.ShowLoading
import com.example.koinandktortutorial.ui.state.MovieUIState
import com.example.koinandktortutorial.ui.state.UIState
import com.example.koinandktortutorial.ui.theme.KoinAndKtorTutorialTheme
import com.example.koinandktortutorial.ui.theme.Purple700
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    KoinAndKtorTutorialTheme {

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            TopBar(navController) {
                NavHost(navController = navController, startDestination = "mainScreen") {
                    composable("mainScreen") {
                        val viewModel = getViewModel<MainActivityViewModel>()

                        val result by viewModel.uiState.collectAsState()
                        when (val value = result) {
                            is UIState.OnDisplayData -> {
                                val list = value.data ?: emptyList()
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp)
                                ) {
                                    item {
                                        Text(
                                            text = "List of the user",
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    items(list) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = it.name)
                                            Text(text = "ID: ${it.id}")
                                        }
                                    }
                                    item {
                                        Button(
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = Purple700,
                                                contentColor = Color.White
                                            ),
                                            onClick = {
                                                // Navigation to another screen with Ktor as http client
                                                navController.navigate("ktorWithKoinScreen")
                                            }) {
                                            Text(
                                                text = "Koin with KTor Setup",
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )
                                        }

                                    }
                                }
                            }
                            is UIState.OnError -> {
                                ScreenMessage(value.message ?: "Something went wrong")
                            }
                            else -> {
                                ShowLoading()
                            }
                        }
                    }
                    composable("ktorWithKoinScreen") {
                        KoinWithKtorScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun KoinWithKtorScreen(navController: NavHostController) {

    val viewModel = getViewModel<MovieListViewModel>()
    val result by viewModel.ui2ndScreenState.collectAsState()
    when (val value = result) {
        is MovieUIState.OnDisplayData -> {
            val list = value.data ?: emptyList()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                item {
                    Text(
                        text = "List of the movies",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                items(list) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = it.movieName)
                        Text(text = "ID: ${it.movieId}")
                    }
                }
                item {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple700,
                            contentColor = Color.White
                        ),
                        onClick = {
                            // Navigation to another screen with Ktor as http client
                            viewModel.submitPostRequest()
                        }) {
                        Text(
                            text = "Send Post Method",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        is MovieUIState.OnSuccessRequest -> {

            ScreenMessage("Success post request. Reference no: ${value.data}")
        }
        is MovieUIState.OnError -> {
            ScreenMessage(value.message ?: "Something went wrong")
        }
        else -> {
            ShowLoading()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopBar(navController: NavHostController, function: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                backgroundColor = Color.Magenta,
                title = {
                    Text(
                        text = "Koin and KTor Tutorials",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        function()
    }
}