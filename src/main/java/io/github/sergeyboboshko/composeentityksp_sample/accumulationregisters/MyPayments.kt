package io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.accumulationregisters.base.CommonAccumRegisterEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity

@Entity(tableName = "accum_reg_my_payments")
//@MigrationEntityCE(migrationVersion = 7)
@ObjectGeneratorCE(type = GeneratorType.AccumulationRegister, label = "Utility Payments", resources = "amount, meterR MAX")
data class AccumRegMyPayments(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @FormFieldCE(
        label = "@@date_label",
        placeHolder = "@@date_placeholder",
        type = FieldTypeHelper.DATE_TIME
    )
    override var period: Long,
    @FormFieldCE(
        render = true,
        label = "Registrator ID",
        placeHolder = "Registrator ID",
        type = FieldTypeHelper.NUMBER
    )
    override var registratorID: Long,
    @FormFieldCE(
        render = true,
        label = "String ID",
        placeHolder = "String ID",
        type = FieldTypeHelper.NUMBER
    )
    override var stringID: Long,
    @FormFieldCE(
        render = true,
        label = "Registrator type",
        placeHolder = "Registrator type",
        type = FieldTypeHelper.NUMBER
    )
    override var registratorType: Int,
    @FormFieldCE(
        render = true,
        label = "Transaction type",
        placeHolder = "Transaction type",
        type = FieldTypeHelper.TEXT
    )
    override var transactionType: TransactionType,
    @FormFieldCE(
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label",
        placeHolder = "@@address_placeholder"
    )
    var addressId: Long,
    @FormFieldCE(
        related = true,
        relatedEntityClass = RefUtilitiesEntity::class,
        extName = "utility",
        type = FieldTypeHelper.SELECT,
        label = "@@meter_label",
        placeHolder = "@@meter_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    @ColumnInfo(defaultValue = "0")
    var meterId: Long,//NEW in 16
    @FormFieldCE(
        related = true,
        relatedEntityClass = RefMeters::class,
        extName = "meter",
        type = FieldTypeHelper.SELECT,
        label = "@@utility_label",
        placeHolder = "@@utility_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    @ColumnInfo(defaultValue = "0")
    var utilityId: Long,//NEW in 2

    @FormFieldCE(render = true, label = "Zone", placeHolder = "Zone", type = FieldTypeHelper.SELECT,
    related = true, relatedEntityClass = RefMeterZonesEntity::class)
    var zoneId: Long,

    @FormFieldCE(
        render = true,
        label = "Amount",
        placeHolder = "Amount",
        type = FieldTypeHelper.DECIMAL
    )
    var amount: Double,
    @ColumnInfo(defaultValue = "0")
    @FormFieldCE(
        label = "@@meter_reading_label",
        placeHolder = "@@meter_reading_placeholder",
        type = FieldTypeHelper.DECIMAL
    )
    var meterR: Double//NEW in 16
) : CommonAccumRegisterEntity(
    id, period, registratorID, stringID, registratorType, transactionType)


