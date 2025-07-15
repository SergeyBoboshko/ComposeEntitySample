package io.github.sergeyboboshko.composeentityksp_sample.references


import android.os.Parcelable
import android.util.Log

import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RawQuery

import androidx.sqlite.db.SupportSQLiteQuery

import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.MyViewModel

import io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference
import io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList
import io.github.sergeyboboshko.composeentity.daemons.RenderViewScreen
import io.github.sergeyboboshko.composeentity.daemons.SuperTopDAO
import io.github.sergeyboboshko.composeentity.daemons.SuperTopRepository

import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI

import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet

import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

import androidx.compose.ui.text.style.TextDirection
import androidx.room.AutoMigration

import io.github.sergeyboboshko.composeentity.references.base.TopReferenceViewModel
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE

import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

//******************** Entity --------------------------
@CeGenerator(type = GeneratorType.Reference, generationLevel = GenerationLevel.UI, label = "Meter Zones")
//@CeMigrationEntity (8)
@Parcelize
@Entity(tableName = "ref_meter_zones")
data class RefMeterZonesEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    var addressId: Long
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}
