package io.github.sergeyboboshko.composeentityksp_sample.references

import android.os.Parcelable
import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator


@CeGenerator(type = GeneratorType.Reference
    , label = "The Types of Meters")

@CeEntity(tableName="ref_typesofmeters")
@CeCreateTable("ref_typesofmeters")
data class RefTypesOfMeters (
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean
): CommonReferenceEntity(id,date,name,isMarkedForDeletion){
    override fun toString(): String {
        return "$id: $name"
    }
}