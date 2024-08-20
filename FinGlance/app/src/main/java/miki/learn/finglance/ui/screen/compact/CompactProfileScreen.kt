package miki.learn.finglance.ui.screen.compact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinGlanceTheme
import miki.learn.finglance.R
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.display.ProfileUiState
import miki.learn.finglance.ui.display.ProfileViewModel
import miki.learn.finglance.ui.screen.utils.CustomOutlinedTextField
import miki.learn.finglance.ui.screen.utils.ProfileHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel,
    profileViewModel: ProfileViewModel
) {

    val profileUiState by profileViewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            CompactNavigationBar(
                navigateToItem = navigateToItem,
                viewModel = portfolioViewModel
            )
        }
    ){innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ProfileHeader(
                profileImage = R.drawable.ic_launcher_background,
                modifier = Modifier.weight(1F),
                onLogOut = onSignOut,
                userData = userData
            )
            ProfileSettings(
                modifier = Modifier.weight(4F),
                profileUiState = profileUiState,
                onNameChange = { profileViewModel.onNameChange(it) },
                profileViewModel = profileViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettings(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    onNameChange: (String) -> Unit,
    profileViewModel: ProfileViewModel
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ) {
            OutlinedTextField(
                value = profileUiState.userData?.userName!!,
                onValueChange = { onNameChange(it) },
                label = {
                    Text(
                        text = "User Name",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ) },
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(.6F),
            )
            Button(
                onClick = { profileViewModel.onSaveNewUserData() },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth(.8F)
                    .height(50.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompactProfileScreenPreview() {
    FinGlanceTheme {
        //CompactProfileScreen()
    }
}