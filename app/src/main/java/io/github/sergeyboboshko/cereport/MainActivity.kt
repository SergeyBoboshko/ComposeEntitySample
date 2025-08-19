package io.github.sergeyboboshko.cereport

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.sergeyboboshko.cereport.daemons.InitialDataPrompt
import io.github.sergeyboboshko.cereport.daemons.MyGlobalValues
import io.github.sergeyboboshko.cereport.data.AppDatabase
import io.github.sergeyboboshko.cereport.screens.MainPage
import io.github.sergeyboboshko.cereport.screens.OtherNavigation
import io.github.sergeyboboshko.cereport.screens.ScaffoldTopCommon
import io.github.sergeyboboshko.cereport.screens.WelcomeScreen
import io.github.sergeyboboshko.cereport.daemons.MyTestViewModel
import io.github.sergeyboboshko.cereport.ui.theme.ComposeEntitySampleTheme
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.InitComposableEntityVariables
import io.github.sergeyboboshko.composeentity.daemons.InitComposeEntityColors
import io.github.sergeyboboshko.composeentity.daemons.MainViewModel
import io.github.sergeyboboshko.composeentity.daemons.SelfNavigation
import io.github.sergeyboboshko.composeentity.daemons.screens.BottomCommonBar
import io.github.sergeyboboshko.composeentity_ksp.base.CeDatabaseVersion
import io.github.sergeyboboshko.composeentity_ksp.registerGlobalEntities


@CeDatabaseVersion(version = 1)
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*** Define database
        val appDb = AppDatabase(this)
        database = appDb.writableDatabase
        GlobalContext.database = database
        registerGlobalEntities()
        //***************************
        GlobalContext.mainViewModel=viewModel
        //For example initial seeding data
        MyGlobalValues.testViewModel = MyTestViewModel()

        enableEdgeToEdge()
        setContent {
            //****************************************************
            //use only in this queue
            InitComposableEntityVariables()//works only before GlobalContext.init(this)
            GlobalContext.init(this)
            InitComposeEntityColors()//has sense only after GlobalContext.init(this)
            //*****************************************************************
            var navController = rememberNavController()
            GlobalContext.mainViewModel?.navController = navController
            InitialDataPrompt()
            //GlobalContext.context = this
            Log.d("S_TEST","GlobalContext.mainViewModel = ${GlobalContext.mainViewModel}")
            ComposeEntitySampleTheme(darkTheme = GlobalContext.darkMode) {
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
                            OtherNavigation()

                        }
                    }
                }
            }
        }
    }
}