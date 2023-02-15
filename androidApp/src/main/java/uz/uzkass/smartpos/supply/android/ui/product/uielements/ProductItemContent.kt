package uz.uzkass.smartpos.supply.android.ui.product.uielements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.uzkass.smartpos.supply.android.R

@Composable
fun ProductItemContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    subTitle: String,
    summa: String
) {

    Row(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .error(R.drawable.ic_profile_vector)
                .placeholder(R.drawable.ic_profile_vector)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Column() {
            Text(text = title)
            Text(text = subTitle)
        }

        Text(text = summa)

        Icon(painter = painterResource(id = R.drawable.ic_orders), contentDescription = null)

    }

}