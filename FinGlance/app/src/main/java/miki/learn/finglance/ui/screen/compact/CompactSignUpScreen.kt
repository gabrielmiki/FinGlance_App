package miki.learn.finglance.ui.screen.compact

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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import miki.learn.finglance.R
import miki.learn.finglance.ui.display.SignInState
import miki.learn.finglance.ui.display.SignUpViewModel

@Composable
fun CompactSignUpScreen(
    uiState: SignInState,
    modifier: Modifier = Modifier,
    navigateBackWelcomeScreen: () -> Unit,
    signInViewModel: SignUpViewModel,
    navigateToPortfolio: () -> Unit
) {

    DisplaySignUpScreen(
        modifier = modifier,
        navigateBackWelcomeScreen = navigateBackWelcomeScreen,
        signInViewModel = signInViewModel,
        navigateToPortfolio = navigateToPortfolio
    )

}

@Composable
fun DisplaySignUpScreen(
    modifier: Modifier = Modifier,
    navigateBackWelcomeScreen: () -> Unit,
    signInViewModel: SignUpViewModel,
    navigateToPortfolio: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .drawBehind {

                val radius = size.height / 2

                drawCircle(
                    color = Color(0xFF001849),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = size.height * .8F),
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
                        titleResource = R.string.signup_text,
                        navigateBackWelcomeScreen = navigateBackWelcomeScreen
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
            TextFieldsAndButton(
                modifier = Modifier.weight(7F),
                signInViewModel = signInViewModel,
                navigateToPortfolio = navigateToPortfolio
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsAndButton(
    modifier: Modifier = Modifier,
    signInViewModel: SignUpViewModel,
    navigateToPortfolio: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {
        OutlinedTextField(
            value = signInViewModel.email,
            onValueChange = { signInViewModel.updateEmailDisplay(it) },
            label = {
                Text(
                    text = "Email",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = signInViewModel.password,
            onValueChange = { signInViewModel.updatePasswordDisplay(it) },
            label = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = {
                Text(
                    text = "Confirm your Password",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    signInViewModel.signUpUser()
                }
                navigateToPortfolio
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.signup_text))
        }
    }
}