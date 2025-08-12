package io.github.sergeyboboshko.composeentityksp_sample.screens

import io.github.sergeyboboshko.composeentityksp_sample.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.ButtonDisplayMode
import io.github.sergeyboboshko.composeentity.daemons.buttons.ClassicButtons
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.IconAligment
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.daemons.navigate
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPaymentsUI
import io.github.sergeyboboshko.composeentityksp_sample.daemons.startInitializator
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsUtilityChargeUI
import io.github.sergeyboboshko.composeentityksp_sample.details.RefAddressDetailsEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocActualPaymentsEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.informationregisters.InfoRegMyNotificationsUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMetersUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefTypesOfMetersUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.reports.ReportUtilityPaymentsFreeEntityUI


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainPage(form: String) {
    val references = LocalizationManager.getTranslation("references")
    val documents = LocalizationManager.getTranslation("documents")
    LazyColumn {

        item {
            FlowRow(Modifier.padding(all = 2.dp)) {
                StyledButton(
                    onClick = {
                        GlobalContext.mainViewModel?.navController?.navigate(
                            SelfNav.getMainScreen(),
                            RefAddressesEntityUI()
                        )
                    }) {
                    Row {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.signpost_24px),
                            contentDescription = "Address", modifier = Modifier.size(48.dp)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth()
                        ) {
                            Text(
                                "Addresses",
                                style = MaterialTheme.typography.titleLarge,
                                color = GlobalColors.currentPalette.text
                            )
                            Divider(color = Color.Gray, thickness = 1.dp)
                            Text(
                                text = "The your addresses have tracking",
                                style = MaterialTheme.typography.titleSmall,
                                color = GlobalColors.currentPalette.buttonText
                            )
                        }
                    }
                }
                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(), ui = RefUtilitiesEntityUI() as BaseUI,
                    commonText = "Utilities", subText = "You appartments services",
                    icon = ImageVector.vectorResource(id = R.drawable.wifi_home_24px),
                    iconAligment = IconAligment.RIGHT,
                    iconSize = 48.dp
                )
                //*** doc Payments
                ClassicButtons.NavigationButton(
                    SelfNav.getMainScreen(),
                    DocPaymentsinvoiceEntityUI() as BaseUI,
                    "Invoice",
                    null
                )
                ClassicButtons.NavigationButton(
                    SelfNav.getMainScreen(),
                    DocActualPaymentsEntityUI() as BaseUI,
                    "Payment",
                    null
                )

                //----------------
                ClassicButtons.IconNavigationButton(
                    commonText = "Notifications", subText = "", icon = Icons.Default.Info,
                    iconSize = 24.dp, routePath = SelfNav.getMainScreen(),
                    ui = InfoRegMyNotificationsUI() as BaseUI
                )
                ClassicButtons.IconNavigationButton(
                    "Payments", "", Icons.Default.ShoppingCart, routePath = SelfNav.getMainScreen(),
                    ui = AccumRegMyPaymentsUI() as BaseUI
                )
                ClassicButtons.IconNavigationButton(
                    "Details", "", Icons.Default.Menu, routePath = SelfNav.getMainScreen(),
                    ui = RefAddressDetailsEntityUI() as BaseUI
                )
                HorizontalDivider()
//                ClassicButtons.NavigationButton(
//                    SelfNav.getMainScreen(),
//                    ReportUtilityPaymentsEntityUI() as BaseUI,
//                    "Some Test Report",
//                    "Some test report describe"
//                )
                ClassicButtons.NavigationButton(
                    SelfNav.getMainScreen(),
                    ReportUtilityPaymentsFreeEntityUI() as BaseUI,
                    "Some FREE Report",
                    "Some test FREE report"
                )
                HorizontalDivider()
                ClassicButtons.IconNavigationButton(
                    commonText = "Types of Meters",
                    icon = Icons.Default.List,
                    routePath = SelfNav.getMainScreen(),
                    ui = RefTypesOfMetersUI() as BaseUI,
                    subText = ""
                )
                ClassicButtons.IconNavigationButton(
                    commonText = "Meter Zones",
                    icon = Icons.Default.List,
                    routePath = SelfNav.getMainScreen(),
                    ui = RefMeterZonesEntityUI() as BaseUI,
                    subText = ""
                )
                ClassicButtons.IconNavigationButton(
                    commonText = "Meters",
                    icon = Icons.Default.List,
                    routePath = SelfNav.getMainScreen(),
                    ui = RefMetersUI() as BaseUI,
                    subText = ""
                )
                ClassicButtons.IconNavigationButton(
                    commonText = "Invoice Details",
                    icon = Icons.Default.List,
                    routePath = SelfNav.getMainScreen(),
                    ui = DetailsUtilityChargeUI() as BaseUI,
                    subText = ""
                )
                StyledButton(
                    icon = Icons.Default.Face,
                    displayMode = ButtonDisplayMode.IconOnly,
                    onClick = { startInitializator() }
                )
                StyledButton(
                    icon = Icons.Default.Settings,
                    displayMode = ButtonDisplayMode.IconOnly,
                    onClick = { GlobalContext.mainViewModel?.navController?.navigate(route = "locate_settings") }
                )
                PoweredByCEComposable()
            }
        }
    }
}