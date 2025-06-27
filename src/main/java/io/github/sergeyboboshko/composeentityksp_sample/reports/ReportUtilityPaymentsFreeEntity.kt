package io.github.sergeyboboshko.composeentityksp_sample.reports

import android.database.Cursor
import android.location.Address
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.emptyCursor
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.reports.base.ReportEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeFormField
import io.github.sergeyboboshko.composeentity_ksp.base.CeObjectGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeReport
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel
import io.github.sergeyboboshko.composeentityksp_sample.R
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPayments
import io.github.sergeyboboshko.composeentityksp_sample.free.FreeReportUtilityPaymentsEntityResult
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity

//створюємо єнтіті для зберігання налаштувань фильтра. Фільтр буде включати в себе адресу та розмір залишку
@Entity(tableName = "rep_utilitypayments_free_settings")
@ObjectGeneratorCE(type = GeneratorType.ReportCursor,label="Free Grouping Balance/Overpayment", generationLevel = GenerationLevel.VIEW_MODEL, hasDetails = true, detailsEntityClass = FreeReportUtilityPaymentsEntityResult::class)
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


public class ReportUtilityPaymentsFreeEntityUI() : RefUI() {
    override var viewModel: _BaseFormVM =
        io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE.reportUtilityPaymentsFreeEntityViewModel as _BaseFormVM

    @Composable
    override fun MainScreenList() {
        initMe()
        io.github.sergeyboboshko.composeentity.daemons.GlobalContext.mainViewModel?.anyUI = this
        io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList(viewModel as io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel, "Free Grouping Balance/Overpayment List", formList, this)
    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {
        initMe()
        val bs =  io.github.sergeyboboshko.composeentity.reportscursor.base.ReportCursorSettingsButtonsSet(viewModel as io.github.sergeyboboshko.composeentity.reportscursor.base._ReportViewModel)
        io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference(viewModel = viewModel as io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel, isNew = isNew, caption1 = "Free Grouping Balance/Overpayment", bs, emptyEntity = ReportUtilityPaymentsFreeEntity(0L, "", "", 0L, 0.0))

    }

    @Composable
    override fun ViewScreen(id: Long) {
        initMe()
        PerfectViewScreen(id = id, caption = "Free Grouping Balance/Overpayment", true, false)
    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        val vm = viewModel as io.github.sergeyboboshko.composeentity.reportscursor.base._ReportViewModel

        // Виконуємо запит лише один раз
        LaunchedEffect(Unit) { vm.generateReport() }

        val cursor = vm.resFlow.collectAsState(emptyCursor())

        // Зберігаємо UI між перерендерами
        val resUI = remember { io.github.sergeyboboshko.composeentityksp_sample.free.FreeReportUtilityPaymentsEntityResultUI() }

        // Оновлюємо курсор тільки при його зміні
        LaunchedEffect(cursor.value) { resUI.viewModelFree.setCursor(cursor.value as Cursor) }

        resUI.refreshList = false
        cursor.value?.let { resUI.MainScreenList() }
    }

    @Composable
    override fun initMe() {
        if (!viewModel.IsInitialized) {
            viewModel.showStandartFields()
            var filterFields= mutableMapOf<String, _BaseDescribeFormElement>()
            //** describe
            viewModel.fieldDescriptions["describe"] =
                io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField(
                    fieldName = "describe",
                    fieldType = io.github.sergeyboboshko.composeentity.daemons.FieldType.TEXT,
                    label = androidx.compose.ui.res.stringResource(id = R.string.describe_label),
                    placeholder = androidx.compose.ui.res.stringResource(id = R.string.describe_placeholder),
                    useForSort = false,
                    renderInAddEdit = true,
                    renderInView = true,
                    renderInList=true,
                    onChange = {},
                    onEndEditing = {}
                ) as _BaseDescribeFormElement
            //--------------------------

            // address
            val addressField =
                io.github.sergeyboboshko.composeentity.daemons.CommonDescribeSelectField<io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity, io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntityExt, io.github.sergeyboboshko.composeentity.daemons.BaseFormVM<io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity, io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntityExt>>(
                    fieldName = "addressId",
                    fieldType = io.github.sergeyboboshko.composeentity.daemons.FieldType.SELECT,
                    label = androidx.compose.ui.res.stringResource(id = R.string.address_label),
                    placeholder = androidx.compose.ui.res.stringResource(id = R.string.address_placeholder),
                    emptyEntity = RefAddressesEntity(0L, System.currentTimeMillis(), "", false, "", "", "", 0, "", 0),
                    useForSort = false,
                    renderInAddEdit = true,
                    renderInView = true,
                    renderInList=true,
                    onChange = {},
                    onEndEditing = {},
                    viewModel = io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE.refAddressesEntityViewModel
                )
            addressField.extName = "address"
            filterFields[addressField.fieldName]=addressField
            //--------------------------

            //** amount
            filterFields["amount"]=
                io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField(
                    fieldName = "amount",
                    fieldType = io.github.sergeyboboshko.composeentity.daemons.FieldType.DECIMAL,
                    label = androidx.compose.ui.res.stringResource(id = R.string.amount_label),
                    placeholder = androidx.compose.ui.res.stringResource(id = R.string.amount_placeholder),
                    useForSort = false,
                    renderInAddEdit = true,
                    renderInView = true,
                    renderInList=true,
                    onChange = {},
                    onEndEditing = {}
                ) as _BaseDescribeFormElement
            //--------------------------
            //add wrapped in a filter fields to form
            for ((name,value) in filterFields){
                var curr = io.github.sergeyboboshko.composeentity.daemons.ConditionField(
                    state = false,
                    condition = io.github.sergeyboboshko.composeentity.daemons.FilterOperation.Equals(0),
                    field = value,//це поле що загортається в фільтр
                    fieldName = value.fieldName,
                    fieldType = io.github.sergeyboboshko.composeentity.daemons.FieldType.FILTER,
                    label=value.label,
                    placeholder = value.placeholder,
                    options = value.options,
                    optionsExt = value.optionsExt,
                    renderInAddEdit = value.renderInAddEdit,
                    renderInList=value.renderInList,
                    renderInView = value.renderInView
                )
                viewModel.fieldDescriptions[name] =  curr as _BaseDescribeFormElement
            }
            viewModel.fieldDescriptions["conditions"] = io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField(
                fieldName="conditions",
                fieldType = io.github.sergeyboboshko.composeentity.daemons.FieldType.TEXT,
                label = "Conditions",
                placeholder = "Conditions"
            ) as _BaseDescribeFormElement
            var a = viewModel.fieldDescriptions["conditions"]
            a?.renderInAddEdit = true
            viewModel.IsInitialized = true
        }
    }
}
