package io.github.sergeyboboshko.composeentityksp_sample.free

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.sergeyboboshko.composeentity.daemons.BaseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.IconVector
import io.github.sergeyboboshko.composeentity.daemons.ReportField
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity_ksp.base.CeFree
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@ObjectGeneratorCE(type = GeneratorType.Free, label = "Payments Balance", generationLevel = GenerationLevel.UI)
@CeFree(fields = "fields", alternatingRowColors = true,withRowNumber = true)
data class FreeReportUtilityPaymentsEntityResult (
    override var id: Long,
    var period: Long,
    var registratorID: Long,
    var stringID: Long,
    var registratorType: Int,
    var transactionType: TransactionType,
    var addressId:Long,
    var zoneId: Long,
    var amount: Double
): BaseEntity {
    @Composable
    override fun StatusIcon() {
        Icons.Default.Build
    }
    @Composable
    override fun StatusVectosIcon(): IconVector {
        return IconVector(imageVector = Icons.Default.Build, contentDescription = "Setting", tint = Color.Cyan)
    }
}

fun fields ():List<ReportField>{
    return listOf(
        ReportField(name = "name", headerTitle = LocalizationManager.getTranslation("name"), headerTextColor = Color.DarkGray, headerBackgroundColor = Color.Yellow, type = FieldTypeHelper.TEXT)
    )
}