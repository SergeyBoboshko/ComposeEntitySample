package io.github.sergeyboboshko.composeentityksp_sample.documents

import android.os.Parcelable
import android.widget.Toast
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
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
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
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsActualPayment
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import kotlinx.coroutines.launch

import kotlinx.parcelize.Parcelize
//Outstanding Invoice document
//******************** Entity --------------------------
@CeGenerator(type = GeneratorType.Document, label = "Utility Payment", hasDetails = true, detailsEntityClass = DetailsActualPayment::class)
@Parcelize
@Entity(tableName = "doc_actual_payment")
@CeDocumentDescriber(
    //infoRegisters=[InfoRegMyNotifications::class],
    accumulationRegistersIncome = [AccumRegMyPayments::class],
    documentType = DocTypes.utilityPayments
)
//@CeMigrationEntity(migrationVersion = 4)
data class DocActualPaymentsEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @CeField(label = "@@date_label",placeHolder="@@date_placeholder", type = FieldTypeHelper.DATE_TIME)
    override var date: Long,
    @CeField(label = "@@number_label",placeHolder="@@number_placeholder", type = FieldTypeHelper.NUMBER)
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    @CeField(related = true, type = FieldTypeHelper.SELECT,relatedEntityClass = RefAddressesEntity::class, label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId:Long,
    @CeField(label = "@@describe_label",placeHolder="@@describe_placeholder", type = FieldTypeHelper.TEXT)
    //@ColumnInfo(defaultValue = "")
    var describe: String
) : CommonDocumentEntity(id, date, number, isPosted, isMarkedForDeletion), Parcelable{
    @Ignore
    @CeField(
        label = "-",
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "UtilityPaymentHelper.FillDetails",
        renderInList = false,
        renderInAddEdit = false
    )
    var fillDetails: String = ""
}

object UtilityPaymentHelper {
    @Composable
    fun FillDetails(vm: _BaseFormVM, formType: FormType? = null) {
        var showDialogue by remember { mutableStateOf(false) }
        val currentDoc = vm.anyItem as DocActualPaymentsEntityExt
        var refresher by remember { mutableStateOf(true) }
        LaunchedEffect(refresher) {
            AppGlobalCE.detailsActualPaymentViewModel.refreshAll()
            AppGlobalCE.detailsActualPaymentViewModel.refreshAllExt()
        }

        if (showDialogue) {
            CleanAndRefillDialodue(
                onConfirm = {
                    //Toast.makeText(MyApplication1.appContext,"We Filling Details",Toast.LENGTH_SHORT).show()
                    val sqlDelete = "DELETE FROM details_actual_payment WHERE parentId = ?"
                    val sqlInsert = """
                        INSERT INTO details_actual_payment (
                                parentId, utilityId, meterId, amount, describe, meterR,zoneId
                            )
                            SELECT ?, utilityId, 0, 0.0, "auto", 0.0,0
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
                        AppGlobalCE.docActualPaymentsEntityViewModel.onPost(
                            regs = accumUIs,
                            infoRegs = infoUIs,
                            AppGlobalCE.detailsActualPaymentViewModel as _DetailsViewModel)
                        //-----------------------------------------------------------------
                        refresher=!refresher
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
}