package io.github.sergeyboboshko.composeentityksp_sample.documents

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity_ksp.base.DocumentDescriberCE
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPayments
import io.github.sergeyboboshko.composeentityksp_sample.daemons.DocTypes
import io.github.sergeyboboshko.composeentityksp_sample.informationregisters.InfoRegMyNotifications
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity

import kotlinx.parcelize.Parcelize
//Outstanding Invoice document
//******************** Entity --------------------------
@ObjectGeneratorCE(type = GeneratorType.Document, label = "Utility Bill")
@Parcelize
@Entity(tableName = "doc_payments_invoice")
@DocumentDescriberCE(
    infoRegisters=[InfoRegMyNotifications::class],
    accumulationRegistersExpense = [AccumRegMyPayments::class],
    documentType = DocTypes.paymentsInvoice
)
data class DocPaymentsinvoiceEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @FormFieldCE(label = "@@date_label",placeHolder="@@date_placeholder", type = FieldTypeHelper.DATE_TIME)
    override var date: Long,
    @FormFieldCE(label = "@@number_label",placeHolder="@@number_placeholder", type = FieldTypeHelper.NUMBER)
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    @FormFieldCE(related = true, type = FieldTypeHelper.SELECT,relatedEntityClass = RefAddressesEntity::class, label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId:Long,
    @FormFieldCE(label = "@@describe_label",placeHolder="@@describe_placeholder", type = FieldTypeHelper.TEXT)
    var describe: String?
) : CommonDocumentEntity(id, date, number, isPosted, isMarkedForDeletion), Parcelable