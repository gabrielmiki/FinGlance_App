package miki.learn.finglance

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.display.SignInState
import miki.learn.finglance.ui.display.WelcomeViewModel
import miki.learn.finglance.ui.display.StockViewModel
import miki.learn.finglance.ui.screen.compact.CompactDetailScreen
import miki.learn.finglance.ui.screen.compact.CompactLogInScreen
import miki.learn.finglance.ui.screen.compact.CompactPortfolioScreen
import miki.learn.finglance.ui.screen.compact.CompactProfileScreen
import miki.learn.finglance.ui.screen.compact.CompactSearchScreen
import miki.learn.finglance.ui.screen.compact.CompactSignUpScreen
import miki.learn.finglance.ui.screen.compact.CompactWelcomeScreen
import miki.learn.finglance.ui.screen.medium.MediumLogInScreen
import miki.learn.finglance.ui.screen.medium.MediumPortfolioScreen
import miki.learn.finglance.ui.screen.medium.MediumSignUpScreen
import miki.learn.finglance.ui.screen.medium.MediumWelcomeScreen
import miki.learn.finglance.ui.screen.utils.StatisticsData
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.display.DetailViewModel
import miki.learn.finglance.ui.display.LogInViewModel
import miki.learn.finglance.ui.display.ProfileViewModel
import miki.learn.finglance.ui.display.SignUpViewModel
import miki.learn.finglance.ui.screen.medium.MediumSearchScreen
import miki.learn.finglance.ui.screen.medium.MediumProfileScreen

@Composable
fun WelcomeScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    onLogInChoice: () -> Unit,
    onSignUpChoice: () -> Unit,
    gooleLogInClick: () -> Unit
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactWelcomeScreen(
                onSignUpChoice = onSignUpChoice,
                onLogInChoice = onLogInChoice,
                googleLogInClick = gooleLogInClick,
                facebookLogInClick = {}
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumWelcomeScreen(
                googleLogInClick = gooleLogInClick,
                facebookLogInClick = {},
                onLogInClick = onLogInChoice,
                onSignUpClick = onSignUpChoice
            )
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}

@Composable
fun LogInScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    uiState: SignInState,
    navigateBackWelcomeScreen: () -> Unit,
    logInViewModel: LogInViewModel,
    navigateToPortfolio: () -> Unit
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactLogInScreen(
                uiState = uiState,
                navigateBackWelcomeScreen = navigateBackWelcomeScreen,
                logInViewModel = logInViewModel,
                navigateToPortfolio = navigateToPortfolio
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumLogInScreen(
                navigateBackWelcome = navigateBackWelcomeScreen
            )
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}

@Composable
fun SignUpScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    uiState: SignInState,
    navigateBackWelcomeScreen: () -> Unit,
    signInViewModel: SignUpViewModel,
    navigateToPortfolio: () -> Unit
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactSignUpScreen(
                uiState = uiState,
                navigateBackWelcomeScreen = navigateBackWelcomeScreen,
                signInViewModel = signInViewModel,
                navigateToPortfolio = navigateToPortfolio
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumSignUpScreen(
                navigateBackWelcome = navigateBackWelcomeScreen
            )
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}

@Composable
fun PortfolioScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    profileImage: Int,
    onLogOut: () -> Unit,
    userData: UserData?,
    navigateToItem: (String) -> Unit,
    viewModel: PortfolioViewModel,
    navigateToPortfolioEdit: () -> Unit
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactPortfolioScreen(
                profileImage = profileImage,
                onLogOut = onLogOut,
                userData = userData,
                navigateToItem = navigateToItem,
                viewModel = viewModel,
                navigateToPortfolioEdit = navigateToPortfolioEdit
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumPortfolioScreen(
                profileImage = profileImage,
                userData = userData,
                onLogOut = onLogOut,
                portfolioViewModel = viewModel,
                navigateToItem = navigateToItem
            )
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}

@Composable
fun SearchScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    viewModel: StockViewModel,
    navigateToDetail: (String) -> Unit,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel,
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactSearchScreen(
                viewModel = viewModel,
                navigateToDetail = navigateToDetail,
                navigateToItem = navigateToItem,
                portfolioViewModel = portfolioViewModel
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumSearchScreen(
                viewModel = viewModel,
                navigateToDetail = navigateToDetail,
                navigateToItem = navigateToItem,
                portfolioViewModel = portfolioViewModel
            )
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}

@Composable
fun DetailScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    detailViewModel: DetailViewModel,
    portfolioViewModel: PortfolioViewModel,
    navigateBackPortfolioScreen: () -> Unit,
    navigateToItem: (String) -> Unit,
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactDetailScreen(
                detailViewModel = detailViewModel,
                navigateBackPortfolioScreen = navigateBackPortfolioScreen,
                navigateToItem = navigateToItem,
                portfolioViewModel = portfolioViewModel
            )
        }
        WindowWidthSizeClass.Medium -> {
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}

@Composable
fun ProfileScreenSizeAdaptor(
    windowSize: WindowWidthSizeClass,
    viewModel: WelcomeViewModel,
    userData: UserData?,
    onLogOut: () -> Unit,
    navigateToItem: (String) -> Unit,
    portfolioViewModel: PortfolioViewModel,
    profileViewModel: ProfileViewModel
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactProfileScreen(
                userData = userData,
                onSignOut = onLogOut,
                navigateToItem = navigateToItem,
                portfolioViewModel = portfolioViewModel,
                profileViewModel = profileViewModel
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumProfileScreen(
                userData = userData,
                onSignOut = onLogOut,
                navigateToItem = navigateToItem,
                portfolioViewModel = portfolioViewModel
            )
        }
        WindowWidthSizeClass.Expanded -> {

        }
    }
}