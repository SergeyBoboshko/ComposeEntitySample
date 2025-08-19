package io.github.sergeyboboshko.cereport.details

import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigration
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@CeGenerator(type = GeneratorType.Details, label = "@@persone_info", generationLevel = GenerationLevel.UI)
@CeEntity(
    tableName = "details_persone_info"
)
@CeCreateTable(tableName = "details_persone_info")
data class DetailsPersonInfo(
    override var id: Long,
    override var parentId: Long,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",
        type= FieldTypeHelper.TEXT,
        positionOnForm = 5)
    var describe:String,
    @CeField(type = FieldTypeHelper.DATE_TIME, label = "@@date_label", placeHolder = "@@date_placeholder")
    var date: Long
): CommonDetailsEntity(id,parentId) {

}