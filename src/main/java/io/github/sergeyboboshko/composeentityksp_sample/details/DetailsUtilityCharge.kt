package io.github.sergeyboboshko.composeentityksp_sample.details


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity


import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE

import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeIgnore
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntity
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntityExt
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAmount

@CeGenerator(type = GeneratorType.Details, label = "The Details Charge",
    renderCaption = false)

@CeEntity(tableName = "details_utility_charge")
@CeCreateTable(tableName = "details_utility_charge")
class DetailsUtilityCharge(
    
    override var id: Long,
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefUtilitiesEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
        , label = "@@utility_label", placeHolder = "@@utility_placeholder", positionOnForm = 1, useForOrder = true,
        onChange = "DetailsUtilityPaymentHelper.onUtilityEdited")
    var utilityId:Long,
    @CeField(related = true, relatedEntityClass = RefMeters::class,
        extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder",
        positionOnForm = 1, useForOrder = true)
    var meterId:Long,
    @CeField(related = true, relatedEntityClass = RefMeterZonesEntity::class,
        extName = "zone", type = FieldTypeHelper.SELECT
        , label = "@@zone_label", placeHolder = "@@zone_placeholder",
        positionOnForm = 1, useForOrder = true)
    
    var zoneId:Long,
    @CeField(label = "@@amount_label", placeHolder = "@@amount_placeholder",type= FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT)
    var describe:String,

    
    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",
        type= FieldTypeHelper.DECIMAL,
        condition = "DetailsUtilityPaymentHelper.meterReadingCondition")
    var meterR:Double


): CommonDetailsEntity(id,parentId) {
    @CeField(
        placeHolder = "Last reading:",
        renderInList = false,
        renderInAddEdit = true,
        type = FieldTypeHelper.COMPOSABLE,
        //customComposable = "io.github.sergeyboboshko.cereport.daemons.DetailsPaymentDocumentsHelper.LastReading"
        customComposable = "DetailsUtilityPaymentHelper.LastReading"
    )
    @CeIgnore
    var lastReading: String = ""
}

object DetailsUtilityPaymentHelper{
    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: DetailsUtilityChargeUI){
        val current = AppGlobalCE.docPaymentsinvoiceEntityViewModel.anyItem as DocPaymentsinvoiceEntityExt//take addressId from current document rendering on screen
        val addrID = current.address?.id
        //find the meter linked to utility and fill meter field
        val sqlText = "SELECT * FROM ref_adress_details WHERE utilityId = ? AND parentId=?" //get dependency between utility and meter in address details table
        val params = arrayOf((currentValue as RefUtilitiesEntity).id,addrID)//current value contents id of object repredents utility in table
        //if field is related, currentValue is not of simple types, but Ext class of the current entity
        AppGlobalCE.forSQLViewModel.viewModelScope.launch(Dispatchers.IO) {
            val cursor = AppGlobalCE.forSQLViewModel.repository.generateResultFromReport(sqlText, params as Array<Any>)
            if (cursor.moveToFirst()) {
                val meterIdIndex = cursor.getColumnIndex("meterId")
                if (meterIdIndex != -1) {
                    val meterId = cursor.getString(meterIdIndex)
                    vm.updateField("meterId",meterId)//set new value linked to Utility
                    vm.updateView()//say to form VM update elements on itself
                }
            }
            cursor.close() // не забудь закрити курсор
        }
    }

    var lastMeterReadings:Float = 0f
    fun meterReadingCondition(): FieldValidator {
        return object : FieldValidator {
            override var errorMessage = "Meter reading must be equals or biggest then previous one"
            override fun isValid(value: Any):Boolean{
                val a= value.toString().toFloatOrNull()?:0f
                return a>=lastMeterReadings
            }
        }
    }

    @Composable
    fun LastReading(vm: _BaseFormVM, formType: FormType? = null) {
        val currElement = vm.anyItem as? DetailsUtilityChargeExt
        val parent = currElement?.parent
        val lastReading = remember { mutableStateOf<Float?>(null) }

        if (currElement == null || parent == null) {
            Text("No previous readings")
            return
        }
        // Function to update the value
        fun updateReading(utilityId: Long?, meterId: Long?, period: Long?) {
            val query =
                "SELECT MAX(meterR) as lastReading FROM accum_reg_my_payments WHERE addressId=? AND utilityId=? AND meterId=? and period < ?"
            val params = arrayOf(parent.addressId, utilityId ?: 0L, meterId ?: 0L, period?:0L)

            AppGlobalCE.forSQLViewModel.viewModelScope.launch(Dispatchers.IO) {
                val cursor = AppGlobalCE.forSQLViewModel.repository.generateResultFromReport(
                    query, params as Array<Any>
                )
                cursor.use {
                    if (it.moveToFirst()) {
                        val value = it.getFloat(it.getColumnIndexOrThrow("lastReading"))
                        lastReading.value = value
                        lastMeterReadings = value
                    } else {
                        lastReading.value = null
                        lastMeterReadings = 0f
                    }
                }
            }
        }

        // Observe changes only in Add/Edit mode
        if (formType == null) {
            var utility = vm.formData["utilityId"] ?: "0"
            var meter = vm.formData["meterId"] ?: "0"
            var period = parent.date

            if (utility.equals("")) utility = "0"
            if (meter.equals("")) meter = "0"
            //if (period.equals("")) period = "0"

            LaunchedEffect(utility, meter) {
                val utilityId = utility?.toLongOrNull()
                val meterId = meter?.toLongOrNull()
                val period =  period//.toLongOrNull()
                updateReading(utilityId, meterId,period)
            }
        } else {
            LaunchedEffect(currElement.meter?.id) {
                val utilityId = currElement.utility?.id
                val meterId = currElement.meter?.id
                val period = currElement.parent?.date
                updateReading(utilityId, meterId,period)
            }
        }

        if (lastReading.value != null) {
            Text("Last reading: ${lastReading.value}")
        } else {
            Text("No previous readings 2")
        }
    }
}