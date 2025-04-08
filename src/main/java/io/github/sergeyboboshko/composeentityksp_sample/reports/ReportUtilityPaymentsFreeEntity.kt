package io.github.sergeyboboshko.composeentityksp_sample.reports

import android.location.Address
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.reports.base.ReportEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeFormField
import io.github.sergeyboboshko.composeentity_ksp.base.CeObjectGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeReport
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPayments
import io.github.sergeyboboshko.composeentityksp_sample.free.FreeReportUtilityPaymentsEntityResult
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity

//створюємо єнтіті для зберігання налаштувань фильтра. Фільтр буде включати в себе адресу та розмір залишку
@Entity(tableName = "rep_utilitypayments_free_settings")
@ObjectGeneratorCE(type = GeneratorType.ReportCursor,label="Free Grouping Balance/Overpayment", generationLevel = GenerationLevel.UI, hasDetails = true, detailsEntityClass = FreeReportUtilityPaymentsEntityResult::class)
@CeReport(resultEntity = FreeReportUtilityPaymentsEntityResult::class,
    query = """SELECT
        name,
        zoneId,
        SUM(CASE WHEN transactionType='EXPENSE' THEN -amount ELSE amount END) AS amount
        FROM accum_reg_my_payments
        LEFT JOIN ref_adresses
        ON accum_reg_my_payments.addressId = ref_adresses.id
    """,
    groups = """ 
        addressId,
        zoneId""")
//@MigrationEntityCE (10)
data class ReportUtilityPaymentsFreeEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var name:String = "Default Settings",
    @FormFieldCE(type = FieldTypeHelper.TEXT,label="@@describe_label", placeHolder = "@@describe_placeholder")
    override var describe:String = "Report Outstanding Balance/Overpayment",
    @FormFieldCE(related = true,label="@@address_label", placeHolder = "@@address_placeholder", relatedEntityClass = RefAddressesEntity::class
        , type = FieldTypeHelper.SELECT, wrapInFilter = true)
    var addressId:Long,
    @FormFieldCE(type = FieldTypeHelper.DECIMAL,label="@@amount_label", placeHolder = "@@amount_placeholder", wrapInFilter = true)
    var amount:Double
): ReportEntity(id,"addressId","amount",name,describe)