package miki.learn.finglance.ui.screen.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Facebook
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import miki.learn.finglance.domain.model.UserData

@Composable
fun ProfileHeader(
    userData: UserData?,
    modifier: Modifier = Modifier,
    @DrawableRes profileImage: Int,
    onLogOut: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            //        TODO("Check if this is the way to use images taken from the Database")
            if(userData?.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1F)
                        .clip(RoundedCornerShape(15.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = profileImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1F)
                        .clip(RoundedCornerShape(15.dp))
                    )
            }
            //        TODO("Think in what to put here")
            Box(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row {
                    val icons = listOf(Icons.Rounded.Facebook, Icons.Rounded.Share, Icons.Sharp.AddCircle, Icons.Sharp.ExitToApp)
                    icons.forEach {icon ->
                        IconButton(onClick = onLogOut) {
                            Icon(icon, contentDescription = null)
                        }
                    }
                }
            }

        }
    }
}