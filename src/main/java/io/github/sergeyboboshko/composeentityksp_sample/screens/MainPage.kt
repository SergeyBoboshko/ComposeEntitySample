package io.github.sergeyboboshko.composeentityksp_sample.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.ButtonDisplayMode
import io.github.sergeyboboshko.composeentity.daemons.ClassicButtons
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext.darkMode
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.daemons.navigate
import io.github.sergeyboboshko.composeentity.documents.base.DocumentsIconButton
import io.github.sergeyboboshko.composeentityksp_sample.MyApplication1
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPaymentsUI
import io.github.sergeyboboshko.composeentityksp_sample.details.RefAddressDetailsEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.informationregisters.InfoRegMyNotifications
import io.github.sergeyboboshko.composeentityksp_sample.informationregisters.InfoRegMyNotificationsUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.reports.ReportUtilityPaymentsEntityUI
import io.github.sergeyboboshko.composeentityksp_sample.reports.ReportUtilityPaymentsFreeEntityUI

import java.util.Locale


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainPage (form:String){
    val references = LocalizationManager.getTranslation("references")
    val documents = LocalizationManager.getTranslation("documents")

    FlowRow(Modifier.padding(all=2.dp)) {
        StyledButton(onClick = {
            //GlobalContext.mainViewModel?.anyUI = RefMeterZonesUI(GlobalContext.context) as BaseUI
            //Toast.makeText(MyApplication1.appContext,"Now I try go to ${RefAddressesEntityUI()}",Toast.LENGTH_SHORT).show()
            GlobalContext.mainViewModel?.navController?.navigate(
                SelfNav.getMainScreen(),
                RefAddressesEntityUI()
            )
        }) {
            Column/*(modifier = Modifier.width(IntrinsicSize.Max)) */{
                Text(
                    "Addresses",
                    style = MaterialTheme.typography.titleLarge, color = GlobalColors.currentPalette.text
                )
                Divider(color = Color.Gray, thickness = 1.dp)
                Text(
                    text = "The your addresses have tracking",
                    style = MaterialTheme.typography.titleSmall, color = GlobalColors.currentPalette.text
                )
            }
        }
        ClassicButtons.NavigationButton(SelfNav.getMainScreen(), RefUtilitiesEntityUI() as BaseUI,"Utilities",null)
        //*** doc Payments
        ClassicButtons.NavigationButton(SelfNav.getMainScreen(), DocPaymentsinvoiceEntityUI() as BaseUI,"Invoice",null)
        ClassicButtons.IconNavigationButton("Notifications","",Icons.Default.Info,SelfNav.getMainScreen(),
            InfoRegMyNotificationsUI() as BaseUI)
        ClassicButtons.IconNavigationButton("Payments","",Icons.Default.ShoppingCart,SelfNav.getMainScreen(),
            AccumRegMyPaymentsUI() as BaseUI)
        ClassicButtons.IconNavigationButton("Details","",Icons.Default.Menu,SelfNav.getMainScreen(),
            RefAddressDetailsEntityUI() as BaseUI)
        HorizontalDivider()
        ClassicButtons.NavigationButton(SelfNav.getMainScreen(), ReportUtilityPaymentsEntityUI() as BaseUI,"Some Test Report","Some test report describe")
        ClassicButtons.NavigationButton(SelfNav.getMainScreen(), ReportUtilityPaymentsFreeEntityUI() as BaseUI,"Some FREE Report","Some test FREE report")
    }
}