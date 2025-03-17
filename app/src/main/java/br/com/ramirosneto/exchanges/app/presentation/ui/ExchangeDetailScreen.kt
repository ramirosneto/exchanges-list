package br.com.ramirosneto.exchanges.app.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.ramirosneto.exchanges.app.presentation.model.ExchangeDTO
import br.com.ramirosneto.exchanges.app.presentation.viewmodel.ExchangeViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExchangeDetailScreen(exchangeId: String, navController: NavController) {
    val viewModel: ExchangeViewModel = koinViewModel()
    val exchanges by viewModel.exchanges.collectAsState()
    val exchange = exchanges.find { it.id == exchangeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exchange Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        exchange?.let {
            ExchangeDetailContent(innerPadding, exchange = it)
        }
    }
}

@Composable
fun ExchangeDetailContent(innerPadding: PaddingValues, exchange: ExchangeDTO) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = innerPadding.calculateTopPadding())
    ) {
        Text(text = exchange.name.orEmpty(), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ID: ${exchange.id}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Volume 1 Day USD: ${exchange.volume1dayUSD}")
    }
}
