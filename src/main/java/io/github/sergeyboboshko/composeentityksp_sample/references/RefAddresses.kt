package io.github.sergeyboboshko.composeentityksp_sample.references

import android.os.Parcelable
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.SaveOperationTypes
import io.github.sergeyboboshko.composeentity.daemons.SimpleAlertDialog
import io.github.sergeyboboshko.composeentity.daemons.SimpleDataPickerDialog
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentityksp_sample.MyApplication1
import io.github.sergeyboboshko.composeentityksp_sample.details.RefAddressDetailsEntity

import kotlinx.android.parcel.Parcelize

//******************** Entity --------------------------
@ObjectGeneratorCE(type = GeneratorType.Reference, hasDetails = true, detailsEntityClass = RefAddressDetailsEntity::class
, beforeSave = "RefAddressHelper.beforeSave")
@Parcelize
@Entity(tableName = "ref_adresses")
data class RefAddressesEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @FormFieldCE(render=true, label = "@@date_label", placeHolder = "@@date_placeholder",type = FieldTypeHelper.DATE_TIME, renderInList = false)
    override var date: Long,
    @FormFieldCE(label = "@@name_label",placeHolder = "@@name_placeholder", type = FieldTypeHelper.TEXT
        , positionOnForm = 1, useForOrder = true
    , onEndEditing = "RefAddressHelper.onNameChanged")
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @FormFieldCE(onChange = "RefAddressHelper.showZip",  label = "@@zipCode_label", type = FieldTypeHelper.NUMBER, placeHolder = "@@zipCode_placeholder", positionOnForm = 5,
        condition = "RefAddressHelper.zipCondition", renderInList = false)
    var zipCode: String,
    @FormFieldCE(render=true, label = "@@city_label", type = FieldTypeHelper.TEXT, placeHolder = "@@city_placeholder", positionOnForm = 4, useForOrder = true,
        condition = "RefAddressHelper.cityCondition")
    var city:String,
    @FormFieldCE(onEndEditing = "RefAddressHelper.showAddress", label = "@@address_label", type = FieldTypeHelper.TEXT, placeHolder = "@@address_placeholder", positionOnForm = 2, useForOrder = true)
    var address:String,
    @FormFieldCE(render=true, label = "@@houseNumber_label", type = FieldTypeHelper.NUMBER, placeHolder = "@@houseNumber_placeholder", positionOnForm = 3, useForOrder = true, renderInList = false)
    var houseNumber: Int,
    @FormFieldCE(render=true, label = "@@houseBlock_label", type = FieldTypeHelper.TEXT, placeHolder = "@@houseBlock_placeholder", positionOnForm = 1, renderInList = false)
    var houseBlock: String,
    @FormFieldCE(render=true, label = "@@apartmentNumber_label", type = FieldTypeHelper.NUMBER, placeHolder = "@@apartmentNumber_placeholder", renderInList = false)
    var apartmentNumber: Int
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}

object RefAddressHelper{

    fun beforeSave(sot:SaveOperationTypes,viewModel: RefAddressesEntityViewModel,ui:RefAddressesEntityUI){
        if (sot==SaveOperationTypes.SAVE){
            var date = viewModel.getField("date") as String
            if (date=="null"||date.isBlank()) date="0"
            var rdate = date.toLong()
            viewModel.updateField("city","Saved "+ SimpleDataPickerDialog(0).convertMillisToDate(rdate))
        }
        else{
            Toast.makeText (MyApplication1.appContext,"Another operation",Toast.LENGTH_SHORT).show()
        }
    }


    fun onNameChanged(currentValue:Any,vm:_BaseFormVM,ui: RefAddressesEntityUI){
        val address = vm._formData["address"]
        if (address!=null) {
            if (address.isBlank()) {
                vm._formData["address"] = currentValue as String
                //vm.updateView()
            }
        }
        var date = vm.getField("date") as String
        if (date=="null"||date.isBlank()) date="0"
        var rdate = date.toLong()
        vm.updateField("date",rdate+60000)
        vm.updateView()
    }

    fun showZip(currentValue:Any,vm:_BaseFormVM,ui: RefAddressesEntityUI){
        val cv = currentValue.toString()
        if (cv.isBlank())
            vm.fieldDescriptions["city"]?.labelStyleList= TextStyle(color = Color.Red)
        else
            vm.fieldDescriptions["city"]?.labelStyleList= TextStyle(color = Color.Green)
    }

    fun showAddress (currentValue:Any,vm:_BaseFormVM,ui: RefAddressesEntityUI){
        Toast.makeText(MyApplication1.appContext,"Address= "+currentValue,Toast.LENGTH_SHORT).show()
    }

    fun zipCondition ():FieldValidator{
        return object : FieldValidator {
            override var errorMessage = "Field Zip Code must be 5 symbols"
            override fun isValid(value: Any) :Boolean {
               val len = value.toString().length
               val v = value.toString()
               if( value.toString().length == 5){
                   return true
            }
                else{
                   errorMessage="Field Zip Code must be 5 symbols, but found $len [$v]"
                   return false
               }
            }
        }
    }

    fun cityCondition ():FieldValidator{
        return object : FieldValidator {
            override var errorMessage = "Field Sity must be 2 or more symbols"
            override fun isValid(value: Any) = value.toString().length>1
        }
    }
}