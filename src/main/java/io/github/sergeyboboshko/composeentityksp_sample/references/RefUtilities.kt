package io.github.sergeyboboshko.composeentityksp_sample.references
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.BaseEntityExt
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons.WebPageViewer
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentityksp_sample.MyApplication1
import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(type = GeneratorType.Reference, beforeDelete = "beforeDeleteUtility")
@Parcelize
@Entity(tableName = "ref_utilities")
@MigrationEntityCE(2)
data class RefUtilitiesEntity (
    @PrimaryKey(autoGenerate = true)
    override var id:Long,
    override var date: Long,
    @FormFieldCE(render=true, label = "@@name_label", placeHolder = "@@name_placeholder",type = FieldTypeHelper.TEXT, positionOnForm = 5)
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @FormFieldCE(render=true, label = "@@describe_label", placeHolder = "@@describe_placeholder",type = FieldTypeHelper.TEXT, positionOnForm = 10)
    var describe : String

): CommonReferenceEntity(
    id,date,name, isMarkedForDeletion
), Parcelable{

    @Ignore
    @FormFieldCE(customComposable = "MyComposableTest", type = FieldTypeHelper.COMPOSABLE, positionOnForm = 15, renderInList=false, renderInAddEdit = false)
    var customField:String = "{}"

    override fun toString(): String {
        return "$id: $name"
    }
}

@Composable
fun MyComposableTest(vm: _BaseFormVM, formType: FormType? = null) {
    val entity = vm.anyItem as RefUtilitiesEntityExt;
    val url = entity.link.describe

    if (url.isNotEmpty()&&url.startsWith("http")) {
        WebPageViewer (url)
    }
    else{
        Text("If set url in describe, it will appears here")
        Text("Now: [$url]")
    }

}

fun beforeDeleteUtility (delList: List<Any>?,viewModel: RefUtilitiesEntityViewModel,ui: RefUtilitiesEntityUI):Boolean{
    for (item in delList ?: emptyList()) {
        val curr = item as RefUtilitiesEntityExt //RefUtilitiesEntity + Ext
        if(curr.link.describe.isNotBlank()){
            Toast.makeText(MyApplication1.appContext,"Can't delete course of describe is not empty but [${curr.link.describe}]",Toast.LENGTH_SHORT).show()
            return true
        }
    }
    return false
}