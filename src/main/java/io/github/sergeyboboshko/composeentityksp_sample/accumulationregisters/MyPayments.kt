package io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters


import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.composeentity.accumulationregisters.base.CommonAccumRegisterEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity

@CeEntity(tableName = "accum_reg_my_payments")
@CeCreateTable(tableName = "accum_reg_my_payments")
@CeGenerator(type = GeneratorType.AccumulationRegister, label = "Utility Payments", resources = "amount, meterR MAX")
data class AccumRegMyPayments(
    override var id: Long,
    @CeField(
        label = "@@date_label",
        placeHolder = "@@date_placeholder",
        type = FieldTypeHelper.DATE_TIME
    )
    override var period: Long,
    @CeField(
        render = true,
        label = "Registrator ID",
        placeHolder = "Registrator ID",
        type = FieldTypeHelper.NUMBER
    )
    override var registratorID: Long,
    @CeField(
        render = true,
        label = "String ID",
        placeHolder = "String ID",
        type = FieldTypeHelper.NUMBER
    )
    override var stringID: Long,
    @CeField(
        render = true,
        label = "Registrator type",
        placeHolder = "Registrator type",
        type = FieldTypeHelper.NUMBER
    )
    override var registratorType: Int,
    @CeField(
        render = true,
        label = "Transaction type",
        placeHolder = "Transaction type",
        type = FieldTypeHelper.TEXT
    )
    override var transactionType: TransactionType,
    @CeField(
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label",
        placeHolder = "@@address_placeholder"
    )
    var addressId: Long,
    @CeField(
        related = true,
        relatedEntityClass = RefUtilitiesEntity::class,
        extName = "utility",
        type = FieldTypeHelper.SELECT,
        label = "@@meter_label",
        placeHolder = "@@meter_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )

    var meterId: Long,//NEW in 16
    @CeField(
        related = true,
        relatedEntityClass = RefMeters::class,
        extName = "meter",
        type = FieldTypeHelper.SELECT,
        label = "@@utility_label",
        placeHolder = "@@utility_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )

    var utilityId: Long,//NEW in 2

    @CeField(render = true, label = "Zone", placeHolder = "Zone", type = FieldTypeHelper.SELECT,
    related = true, relatedEntityClass = RefMeterZonesEntity::class)
    var zoneId: Long,

    @CeField(
        render = true,
        label = "Amount",
        placeHolder = "Amount",
        type = FieldTypeHelper.DECIMAL
    )
    var amount: Double,

    @CeField(
        label = "@@meter_reading_label",
        placeHolder = "@@meter_reading_placeholder",
        type = FieldTypeHelper.DECIMAL
    )
    var meterR: Double//NEW in 16
) : CommonAccumRegisterEntity(
    id, period, registratorID, stringID, registratorType, transactionType)


