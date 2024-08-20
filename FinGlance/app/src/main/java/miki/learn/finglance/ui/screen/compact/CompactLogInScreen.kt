package miki.learn.finglance.ui.screen.compact

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import miki.learn.finglance.R
import miki.learn.finglance.ui.display.LogInViewModel
import miki.learn.finglance.ui.display.SignInState

@Composable
fun CompactLogInScreen(
    uiState: SignInState,
    modifier: Modifier = Modifier,
    navigateBackWelcomeScreen: () -> Unit,
    logInViewModel: LogInViewModel,
    navigateToPortfolio: () -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState) {
        uiState.signInError?.let {error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    DisplayLogInScreen(
        modifier = modifier,
        navigateBackWelcomeScreen = navigateBackWelcomeScreen,
        logInViewModel = logInViewModel,
        navigateToPortfolio = navigateToPortfolio
    )
}

@Composable
fun DisplayLogInScreen(
    modifier: Modifier = Modifier,
    navigateBackWelcomeScreen: () -> Unit,
    logInViewModel: LogInViewModel,
    navigateToPortfolio: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .drawBehind {

                val radius = size.height / 2

                drawCircle(
                    color = Color(0xFF757680),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = size.height * .8F),
                    alpha = .2F,
                )
                drawCircle(
                    color = Color(0xFF45464F),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = size.height * .85F),
                    alpha = .2F
                )
                drawCircle(
                    color = Color(0xFF001849),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = size.height * .9F)
                )

            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(3F)
                    .fillMaxSize()
                    .drawBehind {
                        drawCircle(
                            color = Color(0xFF3158EF),
                            center = Offset(
                                x = ((.5 * size.width) * .3).toFloat(),
                                y = (-(.5 * size.width) * .1).toFloat()
                            ),
                            radius = (.5 * size.width).toFloat(),
                        )
                    },
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TitleAndBackButton(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(8.dp),
                        titleResource = R.string.login_text,
                        navigateBackWelcomeScreen = navigateBackWelcomeScreen
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
            Spacer(modifier = Modifier.weight(1F))
            TextFieldAndButton(
                modifier = Modifier.weight(6F),
                logInViewModel = logInViewModel,
                navigateToPortfolio = navigateToPortfolio                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAndButton(
    modifier: Modifier = Modifier,
    logInViewModel: LogInViewModel,
    navigateToPortfolio: () -> Unit
) {

    val corrotineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = 24.dp)
    ) {
        OutlinedTextField(
            value = logInViewModel.email,
            onValueChange = { logInViewModel.updateEmailDisplay(it) },
            label = {
                Text(
                    text = "Email or Phone Number",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = logInViewModel.password,
            onValueChange = { logInViewModel.updatePasswordDisplay(it) },
            label = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                corrotineScope.launch {
                    logInViewModel.logInUser()
                }
                navigateToPortfolio
                      },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.login_text))
        }
    }
}

@Composable
fun TitleAndBackButton(
    modifier: Modifier = Modifier,
    @StringRes titleResource: Int,
    navigateBackWelcomeScreen: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(onClick = navigateBackWelcomeScreen){
                Icon(
                    imageVector = Icons.Sharp.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = stringResource(id = R.string.back_button_text),
                    color = Color.White,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(titleResource),
            style = MaterialTheme.typography.displayMedium,
            color = Color.White
        )
    }
}