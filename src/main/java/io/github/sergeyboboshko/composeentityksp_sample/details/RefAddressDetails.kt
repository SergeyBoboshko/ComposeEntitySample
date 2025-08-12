package io.github.sergeyboboshko.composeentityksp_sample.details

import android.os.Parcelable
import android.widget.Toast

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeAutoMigration
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.MyApplication1
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity



//@CeMigrationEntity(migrationVersion = 4)
@CeGenerator(type = GeneratorType.Details)

@CeEntity(
    tableName = "ref_adress_details")
@CeCreateTable(tableName = "ref_adress_details")
data class RefAddressDetailsEntity(
    override var id: Long,
    @CeField(
        related = true,
        type = FieldTypeHelper.TEXT,
        render = false,
        relatedEntityClass = RefAddressesEntity::class
    )
    override var parentId: Long,
    @CeField(
        onChange = "RefAddressDetailsEntityHelper.onUtilityChange",
        onEndEditing = "RefAddressDetailsEntityHelper.onUtilityEndEditing",
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefUtilitiesEntity::class,
        label = "@@utility_label",
        placeHolder = "@@utility_placeholder"
    )
    var utilityId: Long,

    @CeField(
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefMeters::class,
        label = "@@meter_label",
        placeHolder = "@@meter_placeholder"
    )
    var meterId: Long
) : CommonDetailsEntity(id, parentId = parentId)

object RefAddressDetailsEntityHelper {
    fun onUtilityChange(
        currValue: RefUtilitiesEntity,
        vm: _BaseFormVM,
        ui: RefAddressDetailsEntityUI
    ) {
        Toast.makeText(MyApplication1.appContext, "Utility= " + currValue, Toast.LENGTH_SHORT)
            .show()
    }

    fun onUtilityEndEditing(currValue: Any, vm: _BaseFormVM, ui: RefAddressDetailsEntityUI) {
        Toast.makeText(MyApplication1.appContext, "Utility Text= " + currValue, Toast.LENGTH_SHORT)
            .show()
    }
}


