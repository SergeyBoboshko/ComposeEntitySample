package io.github.sergeyboboshko.cereport.screens

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.cereport.references.RefGroupesUI
import io.github.sergeyboboshko.cereport.references.RefPersonesUI
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.ButtonDisplayMode
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.IconAligment
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.buttons.ClassicButtons
import io.github.sergeyboboshko.composeentity.daemons.navigate

//import io.github.sergeyboboshko.composeentity.daemons.GlobalState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainPage (form:String){
    LazyColumn {

        item {
            FlowRow(Modifier.padding(all = 2.dp)) {
                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(), ui = RefPersonesUI() as BaseUI,
                    commonText = "Persones", subText = "Reference of persones",
                    icon = Icons.Default.Face,
                    iconAligment = IconAligment.RIGHT,
                    iconSize = 48.dp
                )
                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(), ui = RefGroupesUI() as BaseUI,
                    commonText = "Groupes", subText = "Groupe of persones",
                    icon = ImageVector.vectorResource(id = R.drawable.group_48px),
                    iconAligment = IconAligment.RIGHT,
                    iconSize = 48.dp
                )
            }
        }
        item {
            HorizontalDivider()
        }
        item {
            FlowRow(Modifier.padding(all = 2.dp)) {
                StyledButton(
                    displayMode = ButtonDisplayMode.IconAndText,
                    icon = ImageVector.vectorResource(id = R.drawable.emoji_people_48px),
                    iconContentDescription = "Welcome Screen",
                    onClick = { GlobalContext.mainViewModel?.navController?.navigate("welcome_screen")},
                    iconSize = 48.dp
                ) {
                    Text("Welcome Screen",
                        style = MaterialTheme.typography.titleLarge,
                        color = GlobalColors.currentPalette.text)
                }
            }
            StyledButton(icon = ImageVector.vectorResource(id = R.drawable.database_48px),
                displayMode = ButtonDisplayMode.IconOnly,
                iconContentDescription = "INIT Fill",
                onClick = {
                    //startInitializator()
                    GlobalContext.mainViewModel?.navController?.navigate("init_db")
                })
        }
        item {
            PoweredByCEComposable()
        }
    }
}