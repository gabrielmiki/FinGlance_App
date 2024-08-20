package miki.learn.finglance.ui.screen.medium

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinGlanceTheme
import miki.learn.finglance.R
import miki.learn.finglance.ui.screen.compact.SocialMediaLogin

@Composable
fun MediumWelcomeScreen(
    modifier: Modifier = Modifier,
    googleLogInClick: () -> Unit,
    facebookLogInClick: () -> Unit,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .drawBehind {

                val radius = size.height * .6F

                drawCircle(
                    color = Color(0xFF757680),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = .95F * size.height),
                    alpha = .2F,
                )
                drawCircle(
                    color = Color(0xFF45464F),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = 1.05F * size.height),
                    alpha = .2F
                )
                drawCircle(
                    color = Color(0xFF001849),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = 1.15F * size.height),
                )

            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            IconAndTitle(Modifier.weight(2F))
            Spacer(modifier = Modifier.weight(1F))
            Buttons(
                modifier = Modifier.weight(1F),
                googleLogInClick = googleLogInClick,
                facebookLogInClick = facebookLogInClick,
                onLogInClick = onLogInClick,
                onSignUpClick = onSignUpClick
            )
        }
    }
}

@Composable
fun Buttons(
    modifier: Modifier = Modifier,
    googleLogInClick: () -> Unit,
    facebookLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLogInClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = 40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            Button(
                onClick = onLogInClick,
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
            ) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
            OutlinedButton(
                onClick = onSignUpClick,
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Or continue with",
            style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimary)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialMediaMediumLogin(
                icon = R.drawable.google,
                text = "Google",
                onLogInClick = googleLogInClick
            )
            Spacer(modifier = Modifier.width(5.dp))
            SocialMediaMediumLogin(
                icon = R.drawable.facebook,
                text = "Facebook",
                onLogInClick = facebookLogInClick
            )
        }
    }
}

@Composable
fun IconAndTitle(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Text(
            text = "FinGlance",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "A glance of your finance",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF757680),
        )
    }
}

@Composable
fun SocialMediaMediumLogin(
    @DrawableRes icon: Int,
    text: String,
    onLogInClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        modifier = modifier
            .height(60.dp)
            .width(250.dp)
            .clip(RoundedCornerShape(2.dp)),
        onClick = onLogInClick
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(25.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(Color.Black)
        )

    }

}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun CompactWelcomeScreenPreview() {
    FinGlanceTheme {
        MediumWelcomeScreen(
            googleLogInClick = {},
            facebookLogInClick = {},
            onLogInClick = {},
            onSignUpClick = {}
        )
    }
}