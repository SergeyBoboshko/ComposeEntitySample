package io.github.sergeyboboshko.cereport.daemons

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope

import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InitialDataPrompt(
    context: Context = LocalContext.current
) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!InitialDataState.isFilled(context)) {
            showDialog = true
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Initial Data") },
            text = { Text("Do you want to fill the database with default values?") },
            confirmButton = {
                TextButton(onClick = {
                    MyGlobalValues.testViewModel.runTestChanges()
                    InitialDataState.markAsFilled(context)
                    showDialog = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    InitialDataState.markAsFilled(context)
                    showDialog = false
                    GlobalContext.mainViewModel?.navigateToHome()
                }) {
                    Text("No")
                }
            }
        )
    }


}

object InitialDataState {
    private const val PREF_NAME = "initial_data_pref"
    private const val KEY_FILLED = "initial_data_filled"

    fun isFilled(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_FILLED, false)
    }

    fun markAsFilled(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_FILLED, true).apply()
    }

    fun markAsUnFilled(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_FILLED, false).apply()
    }
}
