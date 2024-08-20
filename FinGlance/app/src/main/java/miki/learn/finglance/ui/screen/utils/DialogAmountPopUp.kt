package miki.learn.finglance.ui.screen.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import miki.learn.finglance.domain.model.PortfolioStock
import miki.learn.finglance.ui.display.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAmountPopUp(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    portfolioStock: PortfolioStock,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .fillMaxWidth(0.95F)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Do you want to add ${portfolioStock.symbol} to your Portfolio?",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                )

                Divider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp, horizontal = 8.dp)
                ) {
                    Text(text = portfolioStock.name)
                    Text(text = portfolioStock.price.toString())
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = detailViewModel.stockPortfolioAmount,
                        onValueChange = { detailViewModel.updatePortfolioStockAmount(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }

                Row(
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Button(
                        onClick = onConfirmClick,
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.weight(1F).padding(end = 5.dp)
                    ) {
                        Text(text = "Add")
                    }

                    Button(
                        onClick = onDismissClick,
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }

        }

    }

}