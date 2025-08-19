package io.github.sergeyboboshko.cereport.references

import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@CeEntity(tableName = "groupe")
@CeGenerator(type = GeneratorType.Reference, generationLevel = GenerationLevel.UI)
@CeCreateTable(tableName = "groupe")
data class RefGroupes(
    override var id: Long,
    override var date: Long,
    @CeField(type = FieldTypeHelper.TEXT, label = "name")
    override var name: String,
    override var isMarkedForDeletion: Boolean
): CommonReferenceEntity(id, date, name, isMarkedForDeletion){
    override fun toString(): String {
        return "$id: $name"
    }
}

