package miki.learn.finglance.ui.screen.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinGlanceTheme
import miki.learn.finglance.R

data class StatisticsData(
    @DrawableRes val graph: Int,
    val type: String,
)

@Composable
fun PortfolioStatistics(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxSize()
        ) {
            DataCard(
                statisticsData = StatisticsData(
                    graph = R.drawable.ic_launcher_background,
                    "Return",
                ),
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(20.dp))
            DataCard(
                statisticsData = StatisticsData(
                    graph = R.drawable.ic_launcher_background,
                    "Return",
                ),
                modifier = Modifier.weight(1F)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxSize()
        ) {
            DataCard(
                statisticsData = StatisticsData(
                    graph = R.drawable.ic_launcher_background,
                    "Return",
                ),
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(20.dp))
            DataCard(
                statisticsData = StatisticsData(
                    graph = R.drawable.ic_launcher_background,
                    "Return",
                ),
                modifier = Modifier.weight(1F)
            )
        }
    }
}

@Composable
fun DataCard(
    statisticsData: StatisticsData,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier
                .weight(4F)
                .fillMaxSize()) {
                val chartData = listOf(
                    1.5,
                    1.75,
                    3.45,
                    2.25,
                    6.45,
                    3.35,
                    8.65,
                    0.15,
                    3.05,
                    4.25
                )
                LineChart(chartData, modifier = Modifier.fillMaxSize())
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize()
            ) {
                Text(
                    text = statisticsData.type,
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PortfolioStatisticsPreview() {
//    FinGlanceTheme {
//        val data = listOf(
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Returns"
//            ),
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Volatility"
//            ),
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Max Drawdown"
//            ),
//            StatisticsData(
//                graph = R.drawable.ic_launcher_background,
//                type = "Min Drawdown"
//            ),
//        )
//      PortfolioStatistics(data)
//    }
//}