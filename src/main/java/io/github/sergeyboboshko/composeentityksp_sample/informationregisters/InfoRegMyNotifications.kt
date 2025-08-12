package io.github.sergeyboboshko.composeentityksp_sample.informationregisters

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity.informationregisters.base.InfRegEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity

@CeEntity(tableName = "info_reg_my_notifications")
@CeCreateTable(tableName = "info_reg_my_notifications")
@CeGenerator(type = GeneratorType.InformationRegister,label="Notifications")
data class InfoRegMyNotifications(
    override var id: Long,
    @CeField(render=true, label = "@@date_label",placeHolder="@@date_placeholder", type = FieldTypeHelper.DATE_TIME)
    override var period: Long,
    @CeField(render=true, label = "Registrator ID",placeHolder="Registrator ID", type = FieldTypeHelper.NUMBER)
    override var registratorID: Long,
    @CeField(render=true, label = "String ID",placeHolder="String ID", type = FieldTypeHelper.NUMBER)
    override var stringID: Long,
    @CeField(render=true, label = "Registrator type",placeHolder="Registrator type", type = FieldTypeHelper.NUMBER)
    override var registratorType: Int,
    @CeField(render=true, label = "Transaction type",placeHolder="Transaction type", type = FieldTypeHelper.TEXT)
    override var transactionType: TransactionType,
    @CeField(related = true, type = FieldTypeHelper.SELECT,relatedEntityClass = RefAddressesEntity::class, label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId: Long,
    @CeField(render=true, label = "Amount",placeHolder="Amount", type = FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(render=true, label = "Caption",placeHolder="Caption", type = FieldTypeHelper.TEXT)
    var caption: String
) : InfRegEntity(id, period, registratorID, stringID, registratorType, transactionType)