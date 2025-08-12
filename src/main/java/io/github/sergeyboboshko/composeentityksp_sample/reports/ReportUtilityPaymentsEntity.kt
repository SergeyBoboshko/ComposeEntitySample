package io.github.sergeyboboshko.composeentityksp_sample.reports

//import android.location.Address
//import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
//
//import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
//import io.github.sergeyboboshko.composeentity.reports.base.ReportEntity
//import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
//import io.github.sergeyboboshko.composeentity_ksp.base.CeReport
//import io.github.sergeyboboshko.composeentity_ksp.base.CeField
//import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
//import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
//import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
//import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel
//import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPayments
//import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
//
////створюємо єнтіті для зберігання налаштувань фильтра. Фільтр буде включати в себе адресу та розмір залишку
//@CeCreateTable("rep_utilitypayments_settings")
//@CeEntity(tableName = "rep_utilitypayments_settings")
//@CeGenerator(type = GeneratorType.Report,label="Balance/Overpayment", generationLevel = GenerationLevel.UI, hasDetails = true, detailsEntityClass = AccumRegMyPayments::class)
//@CeReport(resultEntity = AccumRegMyPayments::class,
//    query = """SELECT
//        id,
//        period,
//        registratorID,
//        stringID,
//        registratorType,
//        transactionType,
//        addressId,
//        zoneId,
//        SUM(CASE WHEN transactionType='EXPENSE' THEN -amount ELSE amount END) AS amount
//        FROM accum_reg_my_payments
//    """,
//    groups = """id,
//        period,
//        registratorID,
//        stringID,
//        registratorType,
//        transactionType,
//        addressId,
//        zoneId""")
////@CeMigrationEntity (9)
//data class ReportUtilityPaymentsEntity(
//    override var id: Long,
//    override var name:String = "Default Settings",
//    @CeField(type = FieldTypeHelper.TEXT,label="@@describe_label", placeHolder = "@@describe_placeholder")
//    override var describe:String = "Report Outstanding Balance/Overpayment",
//    @CeField(related = true,label="@@address_label", placeHolder = "@@address_placeholder", relatedEntityClass = RefAddressesEntity::class
//        , type = FieldTypeHelper.SELECT, wrapInFilter = true)
//    var addressId:Long,
//    @CeField(type = FieldTypeHelper.DECIMAL,label="@@amount_label", placeHolder = "@@amount_placeholder", wrapInFilter = true)
//    var amount:Double
//): ReportEntity(id,"addressId","amount",name,describe)