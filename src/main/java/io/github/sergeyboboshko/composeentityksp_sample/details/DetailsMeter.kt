package io.github.sergeyboboshko.composeentityksp_sample.details

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(type = GeneratorType.Details, label = "The Zones")
@Parcelize
@Entity(tableName = "ref_meter_details",
    foreignKeys = [
        ForeignKey(
            entity = RefMeters::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
//@MigrationEntityCE(migrationVersion = 12)
class DetailsMeterEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var parentId: Long,
    @FormFieldCE(related = true, relatedEntityClass = RefMeterZonesEntity::class, extName = "zone", type = FieldTypeHelper.SELECT
        , label = "@@zone_label", placeHolder = "@@zone_placeholder", positionOnForm = 5, useForOrder = true)
    var zoneId:Long,
    @FormFieldCE(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 10)
    var describe:String
): CommonDetailsEntity(id,parentId), Parcelable {

}