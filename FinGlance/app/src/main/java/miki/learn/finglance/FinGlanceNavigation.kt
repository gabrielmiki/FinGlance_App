package miki.learn.finglance

import android.content.Context
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import miki.learn.finglance.ui.display.WelcomeViewModel
import miki.learn.finglance.ui.display.StockViewModel
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.display.DetailViewModel
import miki.learn.finglance.ui.display.LogInViewModel
import miki.learn.finglance.ui.display.ProfileViewModel
import miki.learn.finglance.ui.display.SignInState
import miki.learn.finglance.ui.utils.PreviewData

enum class FinGlanceScreen {
    WELCOME,
    LOGIN,
    SIGNUP,
    PORTFOLIO,
    STOCK_EDIT,
    SEARCH,
    DETAIL,
    PROFILE
}

@Composable
fun FinGlanceApp(
    windowSize: WindowWidthSizeClass,
    applicationContext: Context,
    googleLogInClick: () -> Unit,
    onLogOut: () -> Unit,
    userData: UserData?,
    signInViewModel: WelcomeViewModel,
    signInUiState: SignInState,
    profileViewModel: ProfileViewModel,
    logInViewModel: LogInViewModel
) {

    val navController: NavHostController = rememberNavController()

    val stockViewModel: StockViewModel =
        viewModel(factory = StockViewModel.Factory)
    val companyInfoViewModel: DetailViewModel =
        viewModel(factory = DetailViewModel.Factory)
    val portfolioViewModel = viewModel<PortfolioViewModel>()

    NavHost(
        navController = navController,
        startDestination = FinGlanceScreen.WELCOME.name
    ) {

        composable(FinGlanceScreen.WELCOME.name) {

            WelcomeScreenSizeAdaptor(
                windowSize = windowSize,
                onLogInChoice = {
                    navController.navigate(FinGlanceScreen.LOGIN.name)
                },
                onSignUpChoice = {
                    navController.navigate(FinGlanceScreen.SIGNUP.name)
                },
                gooleLogInClick = {
                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                }
            )
        }

        composable(FinGlanceScreen.LOGIN.name) {
            LogInScreenSizeAdaptor(
                windowSize = windowSize,
                uiState = signInUiState,
                navigateBackWelcomeScreen = {
                    navController.navigate(FinGlanceScreen.WELCOME.name)
                },
                logInViewModel = logInViewModel,
                navigateToPortfolio = {}
            )
        }

//        composable(FinGlanceScreen.SIGNUP.name) {
//            SignUpScreenSizeAdaptor(
//                windowSize = windowSize,
//                uiState = signInUiState,
//                navigateBackWelcomeScreen = {
//                    navController.navigate(FinGlanceScreen.WELCOME.name)
//                },
//                signInViewModel = signInViewModel
//            )
//        }

        composable(FinGlanceScreen.PORTFOLIO.name) {
//            PortfolioScreenSizeAdaptor(
//                windowSize = windowSize,
//                statisticsData = PreviewData.statisticsData,
//                profileImage = R.drawable.ic_launcher_background,
//                onLogOut = {
//                    onLogOut
//                    navController.navigate(FinGlanceScreen.WELCOME.name)
//                           },
//                userData = userData,
//                navigateToItem = { navController.navigate(it) },
//                viewModel = portfolioViewModel,
//                navigateToPortfolioEdit = {}
//            )
        }

        composable(FinGlanceScreen.SEARCH.name) {
            SearchScreenSizeAdaptor(
                windowSize = windowSize,
                viewModel = stockViewModel,
                navigateToDetail = {
                    companyInfoViewModel.getDetails(it)
                    navController.navigate(FinGlanceScreen.DETAIL.name)
                },
                navigateToItem = { navController.navigate(it) },
                portfolioViewModel = portfolioViewModel
            )
        }

        composable(FinGlanceScreen.DETAIL.name) {
            DetailScreenSizeAdaptor(
                windowSize = windowSize,
                detailViewModel = companyInfoViewModel,
                portfolioViewModel = portfolioViewModel,
                navigateBackPortfolioScreen = {},
                navigateToItem = {}
            )
        }

        composable(FinGlanceScreen.PROFILE.name) {
            ProfileScreenSizeAdaptor(
                windowSize = windowSize,
                viewModel = signInViewModel,
                userData = userData,
                onLogOut = {
                    onLogOut
                    navController.navigate(FinGlanceScreen.WELCOME.name)
                },
                navigateToItem = { navController.navigate(it) },
                portfolioViewModel = portfolioViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}