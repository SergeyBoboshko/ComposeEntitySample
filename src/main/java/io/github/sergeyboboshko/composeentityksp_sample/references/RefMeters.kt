package io.github.sergeyboboshko.composeentityksp_sample.references

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsMeterEntity
import kotlinx.android.parcel.Parcelize

@CeGenerator(type = GeneratorType.Reference
    , label = "The Meters", hasDetails = true, detailsEntityClass = DetailsMeterEntity::class)
@Parcelize
@Entity(tableName="ref_meters")
//@CeMigrationEntity(11)
data class RefMeters(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,

    @CeField(related = true, type = FieldTypeHelper.SELECT, label = "Meter's type", placeHolder = "Select type of the meter"
        , relatedEntityClass = RefTypesOfMeters::class, extName = "type")
    var type:Long
): CommonReferenceEntity(id,date,name,isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}