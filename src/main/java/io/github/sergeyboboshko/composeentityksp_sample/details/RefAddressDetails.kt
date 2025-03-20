package io.github.sergeyboboshko.composeentityksp_sample.details

import android.os.Parcelable
import android.widget.Toast
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentityksp_sample.MyApplication1
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity

import kotlinx.android.parcel.Parcelize
@MigrationEntityCE(migrationVersion = 4)
@ObjectGeneratorCE(type = GeneratorType.Details)
@Parcelize
@Entity(tableName = "ref_adress_details",
    foreignKeys = [
        ForeignKey(
            entity = RefAddressesEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RefAddressDetailsEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @FormFieldCE(related = true, type = FieldTypeHelper.TEXT, render = false, relatedEntityClass = RefAddressesEntity::class)
    override var parentId: Long,
    @FormFieldCE(onChange = "RefAddressDetailsEntityHelper.onUtilityChange",
        onEndEditing = "RefAddressDetailsEntityHelper.onUtilityEndEditing",
        related = true, type = FieldTypeHelper.SELECT,relatedEntityClass = RefUtilitiesEntity::class, label = "@@utility_label", placeHolder = "@@utility_placeholder")
    var utilityId: Long
) : CommonDetailsEntity(id, parentId = parentId), Parcelable

object RefAddressDetailsEntityHelper{
    fun onUtilityChange (currValue:RefUtilitiesEntity,vm:_BaseFormVM,ui:RefAddressDetailsEntityUI){
        Toast.makeText(MyApplication1.appContext,"Utility= "+currValue,Toast.LENGTH_SHORT).show()
    }

    fun onUtilityEndEditing (currValue:Any,vm:_BaseFormVM,ui:RefAddressDetailsEntityUI){
        Toast.makeText(MyApplication1.appContext,"Utility Text= "+currValue,Toast.LENGTH_SHORT).show()
    }
}