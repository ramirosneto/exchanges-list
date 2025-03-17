package br.com.ramirosneto.exchanges.app.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.ramirosneto.exchanges.app.presentation.model.ExchangeDTO
import br.com.ramirosneto.exchanges.app.presentation.viewmodel.ExchangeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.FloatingActionButton as FloatingActionButton1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeListScreen(navController: NavController) {
    val viewModel: ExchangeViewModel = koinViewModel()
    val exchanges by viewModel.exchanges.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isAnimating) {
        viewModel.refreshExchanges()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Exchanges") },
                actions = {
                    IconButton(onClick = { viewModel.refreshExchanges() }) {
                        Icon(Icons.Filled.Refresh, "Refresh")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (exchanges.isNotEmpty()) {
                FloatingActionButton1(onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }) {
                    Icon(Icons.Filled.KeyboardArrowUp, "Back to Top")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }

                error.isNullOrEmpty().not() -> {
                    ErrorScreen(error.orEmpty()) {
                        viewModel.refreshExchanges()
                    }
                }

                exchanges.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(16.dp),
                        state = listState
                    ) {
                        items(exchanges.size) { index ->
                            val exchange = exchanges[index]
                            ExchangeItem(exchange) {
                                navController.navigate("exchangeDetail/${exchange.id}")
                            }
                        }
                    }
                }

                else -> {
                    Text(text = "Nenhuma exchange encontrada!")
                }
            }
        }
    }
}

@Composable
fun ExchangeItem(exchange: ExchangeDTO, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("ExchangeItem")
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exchange.name.orEmpty(), style = MaterialTheme.typography.bodyLarge)
            Text(text = "ID: ${exchange.id}")
            Text(text = "Volume 1 USD: ${exchange.volume1dayUSD}")
        }
    }
}

@Composable
fun ErrorScreen(message: String, onClick: () -> Unit) {
    Column {
        Text(text = message)
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { onClick() }) {
            Icon(
                Icons.Filled.Refresh, "Refresh"
            )
        }
    }
}
