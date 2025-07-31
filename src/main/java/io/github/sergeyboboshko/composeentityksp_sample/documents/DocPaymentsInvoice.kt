package io.github.sergeyboboshko.composeentityksp_sample.documents

import android.os.Parcelable
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.Form
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.mainCustomStack
import io.github.sergeyboboshko.composeentity.details.base._DetailsViewModel
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity.documents.base.DocUI
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeDocumentDescriber
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentityksp_sample.MyApplication1
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPayments
import io.github.sergeyboboshko.composeentityksp_sample.alerts.CleanAndRefillDialodue
import io.github.sergeyboboshko.composeentityksp_sample.daemons.DocTypes
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsUtilityCharge
import io.github.sergeyboboshko.composeentityksp_sample.informationregisters.InfoRegMyNotifications
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import kotlinx.coroutines.launch

import kotlinx.parcelize.Parcelize

//Outstanding Invoice document
//******************** Entity --------------------------
@CeGenerator(
    type = GeneratorType.Document,
    label = "@@utility_bill",
    hasDetails = true,
    detailsEntityClass = DetailsUtilityCharge::class,
    renderCaption = false,
    composableTopForm = "UtilityChargeHelper.Caption"
)
@Parcelize
@Entity(tableName = "doc_payments_invoice")
@CeDocumentDescriber(
    //infoRegisters=[InfoRegMyNotifications::class],
    accumulationRegistersExpense = [AccumRegMyPayments::class],
    documentType = DocTypes.paymentsInvoice
)
data class DocPaymentsinvoiceEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @CeField(
        label = "@@date_label",
        placeHolder = "@@date_placeholder",
        type = FieldTypeHelper.DATE_TIME
    )
    override var date: Long,
    @CeField(
        label = "@@number_label",
        placeHolder = "@@number_placeholder",
        type = FieldTypeHelper.NUMBER
    )
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    @CeField(
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label",
        placeHolder = "@@address_placeholder"
    )
    var addressId: Long,
    @CeField(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT
    )
    var describe: String?
) : CommonDocumentEntity(id, date, number, isPosted, isMarkedForDeletion), Parcelable {
    @Ignore
    @CeField(
        label = "-",
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "UtilityChargeHelper.FillDetails",
        renderInList = false,
        renderInAddEdit = false
    )
    var fillDetails: String = ""
}

object UtilityChargeHelper {
    @Composable
    fun FillDetails(vm: _BaseFormVM, formType: FormType? = null) {
        var showDialogue by remember { mutableStateOf(false) }
        val currentDoc = vm.anyItem as DocPaymentsinvoiceEntityExt
        var refresher by remember { mutableStateOf(true) }
        LaunchedEffect(refresher) {
            AppGlobalCE.detailsUtilityChargeViewModel.refreshAll()
            AppGlobalCE.detailsUtilityChargeViewModel.refreshAllExt()
        }

        if (showDialogue) {
            CleanAndRefillDialodue(
                onConfirm = {
                    //Toast.makeText(MyApplication1.appContext,"We Filling Details",Toast.LENGTH_SHORT).show()
                    val sqlDelete = "DELETE FROM details_utility_charge WHERE parentId = ?"
                    val sqlInsert = """
                        INSERT INTO details_utility_charge (
                                parentId, utilityId, meterId, amount, describe, meterR
                            )
                            SELECT ?, utilityId, 0, 0.0, "auto", 0.0
                            FROM ref_adress_details
                         WHERE parentId = ?
                        """.trimIndent()

                    AppGlobalCE.forSQLViewModel.viewModelScope.launch {
                        AppGlobalCE.forSQLViewModel.repository.execSQL(
                            sqlDelete,
                            arrayOf(currentDoc.link.id)
                        )
                        AppGlobalCE.forSQLViewModel.repository.execSQL(
                            sqlInsert,
                            arrayOf(currentDoc.link.id, currentDoc.link.addressId)
                        )
                        //!Impotant! repost doc   !Impotant!   !Impotant!   !Impotant!
                        val accumUIs = (mainCustomStack.peek() as DocUI).regs
                        val infoUIs = (mainCustomStack.peek() as DocUI).infoRegs
                        AppGlobalCE.docPaymentsinvoiceEntityViewModel.onPost(
                            regs = accumUIs,
                            infoRegs = infoUIs,
                            AppGlobalCE.detailsUtilityChargeViewModel as _DetailsViewModel
                        )
                        //-----------------------------------------------------------------
                        refresher = !refresher
                    }

                    showDialogue = false

                },
                onDismiss = {
                    Toast.makeText(MyApplication1.appContext, "DISMISS", Toast.LENGTH_SHORT).show()
                    showDialogue = false
                }
            )
        } else {
            TextButton(onClick = { showDialogue = true }) {
                Text("Fill Utilities By Address")
            }
        }
    }

    @Composable
    fun Caption(ui: BaseUI, vm: _BaseFormVM, form: Form?, caption: String) {
        Row {
            when {
                form?.formType == FormType.LIST -> {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Smile!")
                }

                form?.formType == FormType.ENTITY_VIEW -> {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Smile!")
                }

                else -> {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Smile!")
                }
            }
            Text("   It's me: $caption")
        }
    }
}