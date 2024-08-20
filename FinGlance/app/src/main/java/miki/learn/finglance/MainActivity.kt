package miki.learn.finglance

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.FinGlanceTheme
import kotlinx.coroutines.launch
import miki.learn.finglance.domain.model.UserData
import miki.learn.finglance.ui.display.WelcomeViewModel
import miki.learn.finglance.ui.display.DetailViewModel
import miki.learn.finglance.ui.display.LogInViewModel
import miki.learn.finglance.ui.display.PortfolioViewModel
import miki.learn.finglance.ui.display.ProfileViewModel
import miki.learn.finglance.ui.display.SignUpViewModel
import miki.learn.finglance.ui.display.StockViewModel
import miki.learn.finglance.ui.utils.PreviewData

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinGlanceTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val windowSize = calculateWindowSizeClass(this).widthSizeClass

                    val navController: NavHostController = rememberNavController()

                    val stockViewModel: StockViewModel =
                        viewModel(factory = StockViewModel.Factory)
                    val companyInfoViewModel: DetailViewModel =
                        viewModel(factory = DetailViewModel.Factory)
                    val portfolioViewModel: PortfolioViewModel =
                        viewModel(factory = PortfolioViewModel.Factory)
                    val welcomeViewModel: WelcomeViewModel =
                        viewModel(factory = WelcomeViewModel.Factory)
                    val profileViewModel: ProfileViewModel =
                        viewModel(factory = ProfileViewModel.Factory)
                    val logInViewModel: LogInViewModel =
                        viewModel(factory = LogInViewModel.Factory)
                    val signUpViewModel: SignUpViewModel =
                    viewModel(factory = SignUpViewModel.Factory)

                    val signInUiState by welcomeViewModel.uiState.collectAsStateWithLifecycle()
                    val logInUiState by logInViewModel.uiState.collectAsState()
                    val signUpUiState by signUpViewModel.uiState.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = FinGlanceScreen.WELCOME.name
                    ) {

                        composable(FinGlanceScreen.WELCOME.name) {

                            LaunchedEffect(key1 = Unit) {
                                if(welcomeViewModel.accountRepository.getSignedInUser() != null) {
                                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK)  {
                                        lifecycleScope.launch {
                                            val signInResult = welcomeViewModel.accountRepository.getGoogleSignInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            welcomeViewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = signInUiState.isSignInSuccessful) {
                                if(signInUiState.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.sign_in_was_successful),
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                                    welcomeViewModel.resetState()
                                }
                            }

                            WelcomeScreenSizeAdaptor(
                                windowSize = windowSize,
                                onLogInChoice = {
                                    navController.navigate(FinGlanceScreen.LOGIN.name)
                                },
                                onSignUpChoice = {
                                    navController.navigate(FinGlanceScreen.SIGNUP.name)
                                },
                                gooleLogInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = welcomeViewModel.accountRepository.signInIntent()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )

                        }

                        composable(FinGlanceScreen.LOGIN.name) {

                            LaunchedEffect(key1 = logInUiState.isSignInSuccessful) {
                                if(logInUiState.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.sign_in_was_successful),
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                                    logInViewModel.resetState()
                                }
                            }

                            LogInScreenSizeAdaptor(
                                windowSize = windowSize,
                                uiState = signInUiState,
                                navigateBackWelcomeScreen = {
                                    navController.popBackStack()
                                },
                                logInViewModel = logInViewModel,
                                navigateToPortfolio = {
                                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                                }
                            )
                        }

                        composable(FinGlanceScreen.SIGNUP.name) {

                            LaunchedEffect(key1 = signUpUiState.isSignInSuccessful) {
                                if(signUpUiState.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.sign_in_was_successful),
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                                    signUpViewModel.resetState()
                                }
                            }

                            SignUpScreenSizeAdaptor(
                                windowSize = windowSize,
                                uiState = signInUiState,
                                navigateBackWelcomeScreen = {
                                    navController.popBackStack()
                                },
                                signInViewModel = signUpViewModel,
                                navigateToPortfolio = {
                                    navController.navigate(FinGlanceScreen.PORTFOLIO.name)
                                }
                            )
                        }

                        composable(FinGlanceScreen.PORTFOLIO.name) {

                            LaunchedEffect(key1 = signUpUiState.isSignInSuccessful) {
                                signUpViewModel.storeUserInRealTimeDatabase()
                            }

                            LaunchedEffect(key1 = Unit) {
                                profileViewModel.getUserDataFromRealtimeDatabase()
                            }

                            PortfolioScreenSizeAdaptor(
                                windowSize = windowSize,
                                // statisticsData = PreviewData.statisticsData,
                                userData = welcomeViewModel.accountRepository.getSignedInUser(),
                                viewModel = portfolioViewModel,
                                profileImage = R.drawable.ic_launcher_background,
                                onLogOut = {
                                    lifecycleScope.launch {
                                        welcomeViewModel.accountRepository.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out_successfully),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    navController.popBackStack(FinGlanceScreen.WELCOME.name, inclusive = false)
                                },
                                navigateToItem = { navController.navigate(it) },
                                navigateToPortfolioEdit = {}
                            )
                        }

                        composable(FinGlanceScreen.STOCK_EDIT.name) {

                        }

                        composable(FinGlanceScreen.SEARCH.name) {

                            SearchScreenSizeAdaptor(
                                windowSize = windowSize,
                                viewModel = stockViewModel,
                                portfolioViewModel = portfolioViewModel,
                                navigateToDetail = {
                                    companyInfoViewModel.getDetails(it)
                                    navController.navigate(FinGlanceScreen.DETAIL.name)
                                },
                                navigateToItem = { navController.navigate(it) },
                            )

                        }

                        composable(FinGlanceScreen.DETAIL.name) {

                            DetailScreenSizeAdaptor(
                                windowSize = windowSize,
                                detailViewModel = companyInfoViewModel,
                                portfolioViewModel = portfolioViewModel,
                                navigateBackPortfolioScreen = {
                                    navController.navigate(
                                        FinGlanceScreen.PORTFOLIO.name
                                    )
                                },
                                navigateToItem = { navController.navigate(it) },
                            )

                        }

                        composable(FinGlanceScreen.PROFILE.name) {

                            LaunchedEffect(key1 = Unit) {
                                profileViewModel.getUserDataFromRealtimeDatabase()
                            }

                            ProfileScreenSizeAdaptor(
                                windowSize = windowSize,
                                userData = UserData(userName = "", profilePictureUrl = ""),
                                viewModel = welcomeViewModel,
                                profileViewModel = profileViewModel,
                                onLogOut = {
                                    lifecycleScope.launch {
                                        welcomeViewModel.accountRepository.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out_successfully),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    navController.popBackStack(FinGlanceScreen.WELCOME.name, inclusive = false)
                                },
                                navigateToItem = { navController.navigate(it) },
                                portfolioViewModel = portfolioViewModel,
                            )

                        }
                    }
                }
            }
        }
    }
}