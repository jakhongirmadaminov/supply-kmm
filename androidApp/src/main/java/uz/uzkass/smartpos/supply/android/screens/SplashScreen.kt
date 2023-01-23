package uz.uzkass.smartpos.supply.android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import uz.uzkass.smartpos.supply.android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkassa.smartpos.supply.library.MR

@Composable
fun SplashScreenView(
  onRussianClick: () -> Unit,
  onUzbekClick: () -> Unit,

  ) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(
        color = Color.White
      ),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Image(
      painter = painterResource(id = R.drawable.logo_smartpos),
      contentDescription = null
    )
    Spacer(modifier = Modifier.size(50.dp))
    Text(
      text = "Choose language",
      fontSize = 24.sp
    )
    Spacer(modifier = Modifier.size(8.dp))
    Text(
      text = stringResource(id = MR.strings.my_string.resourceId),
      fontSize = 16.sp
    )
    Spacer(modifier = Modifier.size(16.dp))
    Row {
      ColumnButtonImageText(
        modifier = Modifier.width(140.dp),
        painter = painterResource(id = R.drawable.ic_russian_flag),
        title = "Russian",
        onClick = onRussianClick
      )
      Spacer(modifier = Modifier.size(8.dp))
      ColumnButtonImageText(
        modifier = Modifier.width(140.dp),
        painter = painterResource(id = R.drawable.ic_uzbek_flag),
        title = "Uzbek",
        onClick = onUzbekClick
      )

    }
  }
}

@Composable
fun ColumnButtonImageText(
  modifier: Modifier,
  painter: Painter,
  title: String,
  onClick: () -> Unit,
) {
  Card(
    shape = RoundedCornerShape(8.dp), elevation = 4.dp
  ) {
    Column(
      modifier = modifier
        .clickable(role = Role.Button, onClick = onClick)
        .padding(vertical = 8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = painter,
        contentDescription = null,
      )
      Text(
        text = title,
        color = Color.Black,
        fontSize = 16.sp
      )
    }
  }
}

@Preview
@Composable
fun SplashPreview() {
  SplashScreenView(onRussianClick = { /*TODO*/ }) {
  }
}