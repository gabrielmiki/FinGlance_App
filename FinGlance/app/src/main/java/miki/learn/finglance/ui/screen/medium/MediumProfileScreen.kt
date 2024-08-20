package miki.learn.finglance.ui.screen.medium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import miki.learn.finglance.R
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.screen.compact.ProfileSettings
import miki.learn.finglance.ui.screen.utils.ProfileHeader

@Composable
fun MediumProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel
) {

    Row(
        modifier = modifier.fillMaxSize()
    ) {
        MediumNavigationBar(
            viewModel = portfolioViewModel,
            navigateToItem = navigateToItem
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProfileHeader(
                profileImage = R.drawable.ic_launcher_background,
                modifier = Modifier.weight(1F),
                onLogOut = onSignOut,
                userData = userData
            )
//            ProfileSettings(
//                modifier = Modifier.weight(4F),
//                userData = userData
//            )
        }
    }
}