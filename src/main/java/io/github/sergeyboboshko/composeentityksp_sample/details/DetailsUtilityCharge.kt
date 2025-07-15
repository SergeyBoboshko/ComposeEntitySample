package io.github.sergeyboboshko.composeentityksp_sample.details

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity
import kotlinx.android.parcel.Parcelize
import java.time.temporal.TemporalAmount

@CeGenerator(type = GeneratorType.Details, label = "The Details Charge")
@Parcelize
@Entity(tableName = "details_utility_charge",
    foreignKeys = [
        ForeignKey(
            entity = DocPaymentsinvoiceEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
//@CeMigrationEntity(migrationVersion = 16)
class DetailsUtilityCharge(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefUtilitiesEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
        , label = "@@utility_label", placeHolder = "@@utility_placeholder", positionOnForm = 1, useForOrder = true)
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
    @ColumnInfo(defaultValue = "0")
    var zoneId:Long,
    @CeField(label = "@@amount_label", placeHolder = "@@amount_placeholder",type= FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT)
    var describe:String,

    @ColumnInfo(defaultValue = "0")
    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",type= FieldTypeHelper.DECIMAL)
    var meterR:Double

//    @ColumnInfo(defaultValue = "0")
//    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",type= FieldTypeHelper.DECIMAL)
//    var meterReading:Double
): CommonDetailsEntity(id,parentId), Parcelable {

}