package io.github.sergeyboboshko.composeentityksp_sample.daemons

import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.details.base.DetailsViewModel
import io.github.sergeyboboshko.composeentity.details.base._DetailsViewModel
import io.github.sergeyboboshko.composeentity.documents.base.RegMoveElement
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentityksp_sample.accumulationregisters.AccumRegMyPayments
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsActualPayment
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsMeterEntity
import io.github.sergeyboboshko.composeentityksp_sample.details.DetailsUtilityCharge
import io.github.sergeyboboshko.composeentityksp_sample.details.RefAddressDetailsEntity
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocActualPaymentsEntity
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocActualPaymentsEntityExt
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntity
import io.github.sergeyboboshko.composeentityksp_sample.documents.DocPaymentsinvoiceEntityExt
import io.github.sergeyboboshko.composeentityksp_sample.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.composeentityksp_sample.references.RefMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefTypesOfMeters
import io.github.sergeyboboshko.composeentityksp_sample.references.RefUtilitiesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun startInitializator() {
    val meterZoneVM = AppGlobalCE.refMeterZonesEntityViewModel
    val meterTypesVM = AppGlobalCE.refTypesOfMetersViewModel
    val metersVM = AppGlobalCE.refMetersViewModel
    val detailsMeterVM = AppGlobalCE.detailsMeterEntityViewModel
    val utilityVM = AppGlobalCE.refUtilitiesEntityViewModel
    val addressVM = AppGlobalCE.refAddressesEntityViewModel
    val detailsAddressVM = AppGlobalCE.refAddressDetailsEntityViewModel
    val expenseDocVM = AppGlobalCE.docPaymentsinvoiceEntityViewModel


    val period = System.currentTimeMillis()

    meterZoneVM.viewModelScope.launch (Dispatchers.IO) {
        //Looper.prepare()
        meterZoneVM.simpleInsert(RefMeterZonesEntity(1, 0, LocalizationManager.getTranslation("nozones"), false, 0))
        meterZoneVM.simpleInsert(RefMeterZonesEntity(2, 0, LocalizationManager.getTranslation("dayzone"), false, 0))
        meterZoneVM.simpleInsert(RefMeterZonesEntity(3, 0, LocalizationManager.getTranslation("nightzone"), false, 0))
        meterZoneVM.simpleInsert(RefMeterZonesEntity(4, 0, LocalizationManager.getTranslation("eveningzone"), false, 0))

        meterTypesVM.simpleInsert(RefTypesOfMeters(1, 0, LocalizationManager.getTranslation("gasMeterType"), false))
        meterTypesVM.simpleInsert(RefTypesOfMeters(2, 0, LocalizationManager.getTranslation("waterMeterType"), false))
        meterTypesVM.simpleInsert(RefTypesOfMeters(3, 0, LocalizationManager.getTranslation("electricityMeterType"), false))

        metersVM.simpleInsert(RefMeters(1, 0, LocalizationManager.getTranslation("gasMeter"), false, 1))
        metersVM.simpleInsert(RefMeters(2, 0, LocalizationManager.getTranslation("waterMeter"), false, 2))
        metersVM.simpleInsert(RefMeters(3, 0, LocalizationManager.getTranslation("electricityMeter"), false, 3))
        metersVM.simpleInsert(RefMeters(4, 0, LocalizationManager.getTranslation("electricityMeter2"), false, 3))

        delay(1000)
        //(detailsMeterVM as _DetailsViewModel).setHatID(1,"")
        detailsMeterVM.simpleInsert(DetailsMeterEntity(1, 1, 1, ""))
        //(detailsMeterVM as _DetailsViewModel).setHatID(2,"")
        detailsMeterVM.simpleInsert(DetailsMeterEntity(2, 2, 1, ""))
        //(detailsMeterVM as _DetailsViewModel).setHatID(3,"")
        detailsMeterVM.simpleInsert(DetailsMeterEntity(3, 3, 2, ""))
        detailsMeterVM.simpleInsert(DetailsMeterEntity(4, 3, 3, ""))
        //(detailsMeterVM as _DetailsViewModel).setHatID(4,"")
        detailsMeterVM.simpleInsert(DetailsMeterEntity(5, 4, 2, ""))
        detailsMeterVM.simpleInsert(DetailsMeterEntity(6, 4, 4, ""))
        detailsMeterVM.simpleInsert(DetailsMeterEntity(7, 4, 3, ""))

        utilityVM.simpleInsert(RefUtilitiesEntity(1, 0, LocalizationManager.getTranslation("gasDelivery"), false, ""))
        utilityVM.simpleInsert(RefUtilitiesEntity(2, 0, LocalizationManager.getTranslation("water"), false, ""))
        utilityVM.simpleInsert(RefUtilitiesEntity(3, 0, LocalizationManager.getTranslation("electricity"), false, ""))
        utilityVM.simpleInsert(RefUtilitiesEntity(4, 0, LocalizationManager.getTranslation("sewerage"), false, ""))
        utilityVM.simpleInsert(RefUtilitiesEntity(5, 0, LocalizationManager.getTranslation("wasteRemoval"), false, ""))
        utilityVM.simpleInsert(RefUtilitiesEntity(6, 0, LocalizationManager.getTranslation("television"), false, ""))
        utilityVM.simpleInsert(RefUtilitiesEntity(7, 0, LocalizationManager.getTranslation("electricity2"), false, ""))

        addressVM.simpleInsert(RefAddressesEntity(1, 0, LocalizationManager.getTranslation("mydowntownaddress"), false, "10020", "mycity", "mystreet", 14, "", 12))
        addressVM.simpleInsert(RefAddressesEntity(2, 0, LocalizationManager.getTranslation("mycountrysideaddress"), false, "10333", "myvillage", "mystreet2", 63, "", 0))
        //delay(2000)
        //(detailsAddressVM as _DetailsViewModel).setHatID(1,"")
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(1, 1, 1, 1))
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(2, 1, 2, 2))
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(3, 1, 3, 3))
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(4, 1, 4, 0))
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(5, 1, 5, 0))
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(6, 1, 6, 0))
        //(detailsAddressVM as _DetailsViewModel).setHatID(2,"")
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(7, 2, 2, 2))
        //(detailsAddressVM as _DetailsViewModel).setHatID(7,"")
        detailsAddressVM.simpleInsert(RefAddressDetailsEntity(8, 2, 3, 4))

        val doc = DocPaymentsinvoiceEntity(1, period, 1, true, false, 1, "")
        expenseDocVM.simpleInsert(doc)
        //delay(2000)
        val detailsExpense = AppGlobalCE.detailsUtilityChargeViewModel

        //(detailsExpense as _DetailsViewModel).setHatID(1,"")
        detailsExpense.simpleInsert(DetailsUtilityCharge(1, 1, 1, 1, 1, 10.0, "", 10000.0))
        detailsExpense.simpleInsert(DetailsUtilityCharge(2, 1, 2, 2, 1, 123.0, "", 902.0))
        detailsExpense.simpleInsert(DetailsUtilityCharge(3, 1, 3, 3, 0, 201.0, "", 32005.0))
        detailsExpense.simpleInsert(DetailsUtilityCharge(4, 1, 4, 0, 0, 20.0, "", 0.0))
        detailsExpense.simpleInsert(DetailsUtilityCharge(5, 1, 5, 0, 0, 35.0, "", 0.0))
        detailsExpense.simpleInsert(DetailsUtilityCharge(6, 1, 6, 0, 0, 50.0, "", 0.0))

        val paymentsVM = AppGlobalCE.accumRegMyPaymentsViewModel

        paymentsVM.simpleInsert(AccumRegMyPayments(1, period, 1, 1, DocTypes.paymentsInvoice, TransactionType.EXPENSE, 1, 1, 1, 1, -10.0, -10000.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(2, period, 1, 2, DocTypes.paymentsInvoice, TransactionType.EXPENSE, 1, 2, 2, 1, -123.0, -902.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(3, period, 1, 3, DocTypes.paymentsInvoice, TransactionType.EXPENSE, 1, 3, 3, 0, -201.0, -32005.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(4, period, 1, 4, DocTypes.paymentsInvoice, TransactionType.EXPENSE, 1, 0, 4, 0, -20.0, 0.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(5, period, 1, 5, DocTypes.paymentsInvoice, TransactionType.EXPENSE, 1, 0, 5, 0, -35.0, 0.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(6, period, 1, 6, DocTypes.paymentsInvoice, TransactionType.EXPENSE, 1, 0, 6, 0, -50.0, 0.0))

        val payDocVM = AppGlobalCE.docActualPaymentsEntityViewModel

        val doc2 = DocActualPaymentsEntity(1, period, 1, true, false, 1, "")
        payDocVM.simpleInsert(doc2)
        //delay(2000)
        val detailsPay = AppGlobalCE.detailsActualPaymentViewModel
        //(detailsPay as _DetailsViewModel).setHatID(1,"")
        detailsPay.simpleInsert(DetailsActualPayment(1, 1, 1, 1, 1, 10.0, "", 10000.0))
        detailsPay.simpleInsert(DetailsActualPayment(2, 1, 2, 2, 1, 123.0, "", 902.0))
        detailsPay.simpleInsert(DetailsActualPayment(3, 1, 3, 3, 0, 201.0, "", 32005.0))
        detailsPay.simpleInsert(DetailsActualPayment(4, 1, 4, 0, 0, 20.0, "", 0.0))
        detailsPay.simpleInsert(DetailsActualPayment(5, 1, 5, 0, 0, 35.0, "", 0.0))
        detailsPay.simpleInsert(DetailsActualPayment(6, 1, 6, 0, 0, 50.0, "", 0.0))

        paymentsVM.simpleInsert(AccumRegMyPayments(7, period, 1, 1, DocTypes.utilityPayments, TransactionType.INCOME, 1, 1, 1, 1, 11.0, 10000.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(8, period, 1, 2, DocTypes.utilityPayments, TransactionType.INCOME, 1, 2, 2, 1, 121.0, 902.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(9, period, 1, 3, DocTypes.utilityPayments, TransactionType.INCOME, 1, 3, 3, 0, 202.0, 32005.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(10, period, 1, 4, DocTypes.utilityPayments, TransactionType.INCOME, 1, 0, 4, 0, 23.0, 0.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(11, period, 1, 5, DocTypes.utilityPayments, TransactionType.INCOME, 1, 0, 5, 0, 39.0, 0.0))
        paymentsVM.simpleInsert(AccumRegMyPayments(12, period, 1, 6, DocTypes.utilityPayments, TransactionType.INCOME, 1, 0, 6, 0, 55.0, 0.0))

        withContext(Dispatchers.Main) {
            Toast.makeText(GlobalContext.context, "Done!", Toast.LENGTH_SHORT).show()
        }
    }
}

//    suspend fun waitForMyTurn(myNumber:Int,counter:Int){
//        for (i in 0..60){
//            if (counter>=myNumber) {break} else{
//                delay(1000)
//            }
//    }
//}

fun initialLocales() {
    LocalizationManager.addLocalization("uk", mapOf(
        "nozones" to "Без зони",
        "dayzone" to "День",
        "nightzone" to "Ніч",
        "eveningzone" to "Вечір",
        "gasMeterType" to "Газові лічильники",
        "waterMeterType" to "Лічильники води",
        "electricityMeterType" to "Електролічильники",
        "gasMeter" to "Газовий лічильник",
        "waterMeter" to "Лічильник води",
        "electricityMeter" to "Лічильник електроенергії",
        "electricityMeter2" to "Лічильник електроенергії 2",
        "gasDelivery" to "Постачання газу",
        "water" to "Вода",
        "electricity" to "Електроенергія",
        "sewerage" to "Каналізація",
        "wasteRemoval" to "Вивіз сміття",
        "television" to "Телебачення",
        "electricity2" to "Електроенергія 2",
        "mydowntownaddress" to "Моя адреса в центрі",
        "mycountrysideaddress" to "Моя сільська адреса"
    ))

    LocalizationManager.addLocalization("en", mapOf(
        "nozones" to "No zone",
        "dayzone" to "Day",
        "nightzone" to "Night",
        "eveningzone" to "Evening",
        "gasMeterType" to "Gas meters",
        "waterMeterType" to "Water meters",
        "electricityMeterType" to "Electricity meters",
        "gasMeter" to "Gas meter",
        "waterMeter" to "Water meter",
        "electricityMeter" to "Electricity meter",
        "electricityMeter2" to "Electricity meter 2",
        "gasDelivery" to "Gas delivery",
        "water" to "Water",
        "electricity" to "Electricity",
        "sewerage" to "Sewerage",
        "wasteRemoval" to "Waste removal",
        "television" to "Television",
        "electricity2" to "Electricity 2",
        "mydowntownaddress" to "My downtown address",
        "mycountrysideaddress" to "My countryside address"
    ))

    LocalizationManager.addLocalization("es", mapOf(
        "nozones" to "Sin zona",
        "dayzone" to "Día",
        "nightzone" to "Noche",
        "eveningzone" to "Tarde",
        "gasMeterType" to "Medidores de gas",
        "waterMeterType" to "Medidores de agua",
        "electricityMeterType" to "Medidores eléctricos",
        "gasMeter" to "Medidor de gas",
        "waterMeter" to "Medidor de agua",
        "electricityMeter" to "Medidor eléctrico",
        "electricityMeter2" to "Medidor eléctrico 2",
        "gasDelivery" to "Suministro de gas",
        "water" to "Agua",
        "electricity" to "Electricidad",
        "sewerage" to "Alcantarillado",
        "wasteRemoval" to "Recolección de basura",
        "television" to "Televisión",
        "electricity2" to "Electricidad 2",
        "mydowntownaddress" to "Mi dirección en el centro",
        "mycountrysideaddress" to "Mi dirección rural"
    ))

    LocalizationManager.addLocalization("hi", mapOf(
        "nozones" to "कोई ज़ोन नहीं",
        "dayzone" to "दिन",
        "nightzone" to "रात",
        "eveningzone" to "शाम",
        "gasMeterType" to "गैस मीटर",
        "waterMeterType" to "पानी के मीटर",
        "electricityMeterType" to "बिजली के मीटर",
        "gasMeter" to "गैस मीटर",
        "waterMeter" to "पानी का मीटर",
        "electricityMeter" to "बिजली का मीटर",
        "electricityMeter2" to "बिजली का मीटर 2",
        "gasDelivery" to "गैस आपूर्ति",
        "water" to "पानी",
        "electricity" to "बिजली",
        "sewerage" to "मलजल निकासी",
        "wasteRemoval" to "कचरा हटाना",
        "television" to "टेलीविजन",
        "electricity2" to "बिजली 2",
        "mydowntownaddress" to "मेरा शहर का पता",
        "mycountrysideaddress" to "मेरा ग्रामीण पता"
    ))
}

