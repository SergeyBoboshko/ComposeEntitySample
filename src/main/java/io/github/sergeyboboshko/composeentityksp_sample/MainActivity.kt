package io.github.sergeyboboshko.composeentityksp_sample

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalConfig
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.InitComposableEntityVariables
import io.github.sergeyboboshko.composeentity.daemons.InitComposeEntityColors
import io.github.sergeyboboshko.composeentity.daemons.MainViewModel
import io.github.sergeyboboshko.composeentity.daemons.SelfNavigation
import io.github.sergeyboboshko.composeentity.daemons.SettingsScreen
import io.github.sergeyboboshko.composeentity.daemons.dbtransfer.DatabaseFunctions
import io.github.sergeyboboshko.composeentity.daemons.screens.BottomCommonBar

import io.github.sergeyboboshko.composeentity_ksp.base.CeDatabaseVersion
import io.github.sergeyboboshko.composeentity_ksp.base.Generated
import io.github.sergeyboboshko.composeentity_ksp.db.DependenciesProvider
import io.github.sergeyboboshko.composeentity_ksp.registerGlobalEntities
import io.github.sergeyboboshko.composeentityksp_sample.daemons.changeDefaultPalettes
import io.github.sergeyboboshko.composeentityksp_sample.daemons.initialLocales
import io.github.sergeyboboshko.composeentityksp_sample.data.AppDatabase
import io.github.sergeyboboshko.composeentityksp_sample.samples.CarouselExample_MultiBrowse
import io.github.sergeyboboshko.composeentityksp_sample.screens.AppSettings
import io.github.sergeyboboshko.composeentityksp_sample.screens.MainPage
import io.github.sergeyboboshko.composeentityksp_sample.screens.ScaffoldTopCommon
import io.github.sergeyboboshko.composeentityksp_sample.ui.theme.ComposeEntityTheme
import kotlin.getValue


@CeDatabaseVersion(version = 1)
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    private lateinit var database: SQLiteDatabase
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDb = AppDatabase(this)
        database = appDb.writableDatabase
        GlobalContext.database = database
        registerGlobalEntities()
        GlobalContext.mainViewModel=viewModel
        //GlobalContext.dropdownMenyStyle= DropdownMenuStyles.TILES
        enableEdgeToEdge()
        setContent {
            GlobalConfig.showTransferDB = true
            //****************************************************
            //використати тільки в такій послідовності
            InitComposableEntityVariables()//має сенс тільки до GlobalContext.init(this)
            GlobalContext.init(this)
            changeDefaultPalettes()
            InitComposeEntityColors()//має сенс тільки після GlobalContext.init(this)
            LaunchedEffect(Unit) {initialLocales() }

            //*****************************************************************
            var navController = rememberNavController()
            GlobalContext.mainViewModel?.navController = navController
            //GlobalContext.context = this
            Log.d("S_TEST","GlobalContext.mainViewModel = ${GlobalContext.mainViewModel}")
            ComposeEntityTheme(darkTheme = GlobalContext.darkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()
                    .background(color = GlobalColors.currentPalette.background),
                    topBar = {
                        ScaffoldTopCommon()
                    },
                    bottomBar = { BottomCommonBar() }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = GlobalColors.currentPalette.background)
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                MainPage("MainList")
                            }

                            composable(route = "selfNav/{form}",
                                arguments = listOf(
                                    navArgument("form") { type = NavType.StringType }
                                )) {
                                //NavigationTargets.current = "selfNav"
                                val form =
                                    navController.currentBackStackEntry?.arguments?.getString("form")
                                SelfNavigation(form ?: "")
                            }

                            composable (route="settings"){
                                SettingsScreen(Generated.CeDatabaseVersion, DependenciesProvider as DatabaseFunctions)
                            }

                            composable(route="bluetoothtransfer") {

                            }

                            composable (route="locate_settings"){ AppSettings() }
                            composable(route = "carousel_sample") {

                            CarouselExample_MultiBrowse() }
                        }
                    }
                }
            }
        }
    }
}