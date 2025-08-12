package io.github.sergeyboboshko.composeentityksp_sample.details


import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity



@CeGenerator(type = GeneratorType.Details, label = "The Zones")
@CeEntity(tableName = "ref_meter_details")
@CeCreateTable(tableName = "ref_meter_details")
class DetailsMeterEntity(
    override var id: Long,
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefMeterZonesEntity::class, extName = "zone", type = FieldTypeHelper.SELECT
        , label = "@@zone_label", placeHolder = "@@zone_placeholder", positionOnForm = 5, useForOrder = true)
    var zoneId:Long,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 10)
    var describe:String
): CommonDetailsEntity(id,parentId) {

}