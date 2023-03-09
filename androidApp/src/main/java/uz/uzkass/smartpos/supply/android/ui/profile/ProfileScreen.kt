package uz.uzkass.smartpos.supply.android.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer4dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.destinations.CheckOldPinCodeScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileScreenState
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileViewModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = koinViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    ProfileScreenView(
        onPinCodeChangeClick = {
            navigator.navigate(CheckOldPinCodeScreenDestination)
        },
        onPasswordChangeClick = {
            navigator.navigate(NavGraphs.passwordReset)
        },
        screenState = screenState.value,
        onBackPressed = navigator::popBackStack
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ProfileScreenView(
    screenState: ProfileScreenState,
    onPinCodeChangeClick: () -> Unit,
    onPasswordChangeClick: () -> Unit,
    onBackPressed: () -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            ProfileScreenAppBar(
                title = "Профиль",
                onBackPressed = onBackPressed
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer24dp()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(screenState.avatarUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_profile_vector)
                        .error(R.drawable.ic_profile_vector)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                )
                Spacer16dp()
                Column() {
                    Text(
                        text = "${screenState.firstName} ${screenState.lastName}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = SupplyTheme.colors.subtitle1
                    )
                    Spacer4dp()
                    Text(
                        text = screenState.login,
                        style = SupplyTheme.typography.subtitle1,
                        color = SupplyTheme.colors.unSelected
                    )
                }

            }

            Spacer24dp()

            LabeledProfileText(
                label = stringResource(id = MR.strings.type_account.resourceId),
                value = screenState.firstName
            )

            LabeledProfileText(
                label = stringResource(id = MR.strings.company_name.resourceId),
                value = screenState.company
            )

            LabeledProfileText(
                label = stringResource(id = MR.strings.vat.resourceId),
                value = screenState.companyVat
            )

            LabeledProfileText(
                label = stringResource(id = MR.strings.activity_type.resourceId),
                value = screenState.activityType
            )


            FillAvailableSpace()

            SupplyFilledTextButton(
                text = "Изменить PIN-код",
                onClick = onPinCodeChangeClick
            )

            SupplyFilledTextButton(
                text = "Изменить пароль",
                onClick = onPasswordChangeClick
            )

        }

    }

}


@Composable
private fun ProfileScreenAppBar(
    title: String,
    onBackPressed: () -> Unit
) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onBackPressed
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = title
        )
    }
}


@Composable
private fun LabeledProfileText(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = SupplyTheme.typography.subtitle1,
            color = SupplyTheme.colors.unSelected
        )
        Spacer8dp()
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = SupplyTheme.colors.subtitle1
        )
    }
}

