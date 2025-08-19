package io.github.sergeyboboshko.cereport.references

import io.github.sergeyboboshko.cereport.details.DetailsPersonInfo
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigration
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@CeEntity(tableName = "person")
@CeGenerator(type = GeneratorType.Reference, generationLevel = GenerationLevel.UI,
    hasDetails = true, detailsEntityClass = DetailsPersonInfo::class)
@CeCreateTable(tableName = "person")

data class RefPersones(
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @CeField(type = FieldTypeHelper.NUMBER, label = "Age", placeHolder = "Select Age")
    var age:Int,
    @CeField(related = true, relatedEntityClass = RefGroupes::class, type = FieldTypeHelper.SELECT, extName = "groupe",
        label = "Groupe", placeHolder = "Select Groupe of the person")
    var groupeId: Long
): CommonReferenceEntity(id, date, name, isMarkedForDeletion){
    override fun toString(): String {
        return "$id: $name"
    }
}