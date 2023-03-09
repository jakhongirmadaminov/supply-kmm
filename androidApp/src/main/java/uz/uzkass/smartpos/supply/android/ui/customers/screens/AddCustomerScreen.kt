package uz.uzkass.smartpos.supply.android.ui.customers.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import uz.uzkass.smartpos.supply.android.coreui.LabeledTextField
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun AddCustomerScreen() {

    AddCustomerScreenView()

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun AddCustomerScreenView() {
    var tinValue by remember {
        mutableStateOf("")
    }

    Scaffold(topBar = {

    }) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LabeledTextField(
                label = stringResource(id = MR.strings.vat.resourceId),
                valueState = tinValue,
                onValueChange = {
                    tinValue = it
                }
            )





        }
    }

}