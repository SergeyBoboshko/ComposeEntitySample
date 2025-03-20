package io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters

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

@Entity(tableName = "accum_reg_my_payments")
@MigrationEntityCE(migrationVersion = 7)
@ObjectGeneratorCE(type = GeneratorType.AccumulationRegister, label = "Utility Payments")
data class AccumRegMyPayments(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @FormFieldCE(label = "@@date_label",placeHolder="@@date_placeholder", type = FieldTypeHelper.DATE_TIME)
    override var period: Long,
    @FormFieldCE(render=true, label = "Registrator ID",placeHolder="Registrator ID", type = FieldTypeHelper.NUMBER)
    override var registratorID: Long,
    @FormFieldCE(render=true, label = "String ID",placeHolder="String ID", type = FieldTypeHelper.NUMBER)
    override var stringID: Long,
    @FormFieldCE(render=true, label = "Registrator type",placeHolder="Registrator type", type = FieldTypeHelper.NUMBER)
    override var registratorType: Int,
    @FormFieldCE(render=true, label = "Transaction type",placeHolder="Transaction type", type = FieldTypeHelper.TEXT)
    override var transactionType: TransactionType,
    @FormFieldCE(related = true, type = FieldTypeHelper.SELECT,relatedEntityClass = RefAddressesEntity::class, label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId:Long,
    @FormFieldCE(render=true, label = "Zone",placeHolder="Zone", type = FieldTypeHelper.NUMBER)
    var zoneId: Long,
    @FormFieldCE(render=true, label = "Amount",placeHolder="Amount", type = FieldTypeHelper.DECIMAL)
    var amount: Double): CommonAccumRegisterEntity(id,period,registratorID,stringID,registratorType, transactionType)