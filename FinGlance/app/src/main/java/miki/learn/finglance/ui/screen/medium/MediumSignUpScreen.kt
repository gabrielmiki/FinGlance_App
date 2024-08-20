package miki.learn.finglance.ui.screen.medium

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinGlanceTheme
import miki.learn.finglance.R

@Composable
fun MediumSignUpScreen(
    modifier: Modifier = Modifier,
    navigateBackWelcome: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .drawBehind {

                val radius = size.height * .6F

                drawCircle(
                    color = Color(0xFF001849),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = size.height * .95F)
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
                    .weight(1F)
                    .fillMaxSize()
                    .drawBehind {
                        drawCircle(
                            color = Color(0xFF3158EF),
                            center = Offset(
                                x = ((.4 * size.width) * .4).toFloat(),
                                y = (-(.4 * size.width) * .35).toFloat()
                            ),
                            radius = (.4 * size.width).toFloat(),
                        )
                    },
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TitleAndBackButton(
                        navigateBackWelcome = navigateBackWelcome,
                        titleResource = R.string.signup_text,
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(8.dp),
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
            TextFieldsAndButton(modifier = Modifier.weight(1F))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsAndButton(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TextFields()
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .width(300.dp)
                .height(80.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
fun TextFields(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        TextField()
        Spacer(modifier = Modifier.width(20.dp))
        TextField()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = "Lalaland",
            onValueChange = {},
            label = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.width(350.dp).height(100.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = "Lalaland",
            onValueChange = {},
            label = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.width(350.dp).height(100.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun CompactSignUpScreenPreview() {
    FinGlanceTheme {
        MediumSignUpScreen(
            navigateBackWelcome = {}
        )
    }
}