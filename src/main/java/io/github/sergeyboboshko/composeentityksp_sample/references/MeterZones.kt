package io.github.sergeyboboshko.composeentityksp_sample.references


import android.os.Parcelable
import android.util.Log

import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.MyViewModel

import io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference
import io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList
import io.github.sergeyboboshko.composeentity.daemons.RenderViewScreen

import io.github.sergeyboboshko.composeentity.daemons.SuperTopRepository

import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI

import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet


import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

import androidx.compose.ui.text.style.TextDirection


import io.github.sergeyboboshko.composeentity.references.base.TopReferenceViewModel
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable

import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

//******************** Entity --------------------------
@CeGenerator(type = GeneratorType.Reference, generationLevel = GenerationLevel.UI, label = "Meter Zones")
@CeCreateTable(tableName = "ref_meter_zones")
@CeEntity(tableName = "ref_meter_zones")
data class RefMeterZonesEntity(
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    var addressId: Long
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion){
    override fun toString(): String {
        return "$id: $name"
    }
}
