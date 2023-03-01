package uz.uzkass.smartpos.supply.android.coreui.datetime

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@SuppressLint("SimpleDateFormat")
@Composable
fun DateTimePickerDialog(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit = {},
) {
    var textValue by remember {
        mutableStateOf("")
    }

    val date = Calendar.getInstance()

    val tempDate = remember {
        Calendar.getInstance()
    }
    val dialogTime = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute ->
            tempDate.set(
                tempDate.get(Calendar.YEAR),
                tempDate.get(Calendar.MONTH),
                tempDate.get(Calendar.DAY_OF_MONTH),
                hour,
                minute
            )
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
            val timeTex = parser.format(tempDate.time)
            textValue = timeTex
            onValueChange(timeTex)
        },
        date.get(Calendar.HOUR_OF_DAY),
        date.get(Calendar.MINUTE),
        true,
    )

    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            tempDate.set(year,month,dayOfMonth)
            dialogTime.show()
        },
        date.get(Calendar.YEAR),
        date.get(Calendar.MONTH),
        date.get(Calendar.DAY_OF_MONTH),
    )
    Column(modifier = modifier) {
        LabelText(label = label)
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall4dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.Transparent, shape = LocalShapes.current.small8Dp)
                .clickable {
                    dialog.show()
                }
                .border(
                    width = 1.dp,
                    color = SupplyTheme.colors.textFieldBorder,
                    shape = LocalShapes.current.small8Dp
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = textValue
            )
            FillAvailableSpace()
            Image(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null
            )
        }

    }
}