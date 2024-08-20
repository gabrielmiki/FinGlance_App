package miki.learn.finglance.ui.screen.compact

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun CompactWelcomeScreen(
    modifier: Modifier = Modifier,
    onSignUpChoice: () -> Unit,
    onLogInChoice: () -> Unit,
    googleLogInClick: () -> Unit,
    facebookLogInClick: () -> Unit
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
                    center = Offset(x = size.width / 2, y = .9F * size.height),
                    alpha = .2F,
                )
                drawCircle(
                    color = Color(0xFF45464F),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = 1F * size.height),
                    alpha = .2F
                )
                drawCircle(
                    color = Color(0xFF001849),
                    radius = radius,
                    center = Offset(x = size.width / 2, y = 1.1F * size.height),
                )
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            IconAndTitle(Modifier.weight(2F))
            Spacer(modifier = Modifier.weight(.4F))
            Buttons(
                modifier = Modifier.weight(1.6F),
                onSignUpChoice = onSignUpChoice,
                onLogInChoice = onLogInChoice,
                googleLogInClick = googleLogInClick,
                facebookLogInClick = facebookLogInClick
            )
        }
    }
}

@Composable
fun Buttons(
    modifier: Modifier = Modifier,
    onSignUpChoice: () -> Unit,
    onLogInChoice: () -> Unit,
    googleLogInClick: () -> Unit,
    facebookLogInClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = 24.dp)
    ) {
        Button(
            onClick = onLogInChoice,
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = onSignUpChoice,
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Or continue with",
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimary)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialMediaLogin(
                icon = R.drawable.google,
                text = "Google",
                onLogInClick = googleLogInClick
            )
            Spacer(modifier = Modifier.width(5.dp))
            SocialMediaLogin(
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
fun SocialMediaLogin(
    @DrawableRes icon: Int,
    text: String,
    onLogInClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        modifier = modifier
            .height(30.dp)
            .width(125.dp)
            .clip(RoundedCornerShape(2.dp)),
        onClick = onLogInClick
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(Color.Black)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun CompactWelcomeScreenPreview() {
    FinGlanceTheme {
        CompactWelcomeScreen(
            onSignUpChoice = {},
            onLogInChoice = {},
            googleLogInClick = {},
            facebookLogInClick = {}
        )
    }
}