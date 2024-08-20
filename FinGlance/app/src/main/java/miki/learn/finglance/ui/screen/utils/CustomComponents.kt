package miki.learn.finglance.ui.screen.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    onValueUpdate: (String) -> Unit,
    value: String,
    labelText: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueUpdate(value) },
        label = {
            Text(
                text = labelText,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ) },
        textStyle = TextStyle(color = Color.White)
    )
}