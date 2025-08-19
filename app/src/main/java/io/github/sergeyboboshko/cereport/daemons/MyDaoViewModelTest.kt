package io.github.sergeyboboshko.cereport.daemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sergeyboboshko.cereport.details.DetailsPersonInfo
import io.github.sergeyboboshko.cereport.references.RefGroupes
import io.github.sergeyboboshko.cereport.references.RefGroupesDao
import io.github.sergeyboboshko.cereport.references.RefPersones
import io.github.sergeyboboshko.cereport.references.RefPersonesDao
import io.github.sergeyboboshko.cereport.references.RefPersonesExt

import io.github.sergeyboboshko.composeentity.core.CEQuery
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.db.DependenciesProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class MyTestViewModel() : ViewModel() {
    val dao: RefPersonesDao = DependenciesProvider.refPersonesDao
    val groupeDao: RefGroupesDao = DependenciesProvider.refGroupesDao

    private val _items = MutableStateFlow<List<RefPersones>>(emptyList())
    val items: StateFlow<List<RefPersones>> = _items

    private val _itemsExt = MutableStateFlow<List<RefPersonesExt>>(emptyList())
    val itemsExt: StateFlow<List<RefPersonesExt>> = _itemsExt

    fun loadAll() {
        // Запит усіх записів
        viewModelScope.launch {
            dao.query(CEQuery("SELECT * FROM person")).collect { list ->
                _items.value = list
            }
        }
    }
    fun loadAllExt() {
        // Запит усіх записів
        viewModelScope.launch {
            dao.queryExt().collect { list ->
                _itemsExt.value = list
            }
        }
    }

    fun runTestChanges() {
        viewModelScope.launch {
            //groups insert
            groupeDao.insert(
                RefGroupes(
                    id = 1, // id 0 - щоб автоінкремент спрацював
                    date = System.currentTimeMillis(),
                    name = "VIP Groupe",
                    isMarkedForDeletion = false
                )
            )

            groupeDao.insert(
                RefGroupes(
                    id = 2, // id 0 - щоб автоінкремент спрацював
                    date = System.currentTimeMillis(),
                    name = "Common Groupe",
                    isMarkedForDeletion = false
                )
            )

            // 2. Insert
            dao.insert(
                RefPersones(
                    id = 1, // id 0 - щоб автоінкремент спрацював
                    date = System.currentTimeMillis(),
                    name = "Inserted item",
                    isMarkedForDeletion = false,
                    5,
                    1
                )
            )
            dao.insert(
                RefPersones(
                    id = 2, // id 0 - щоб автоінкремент спрацював
                    date = System.currentTimeMillis(),
                    name = "Inserted Serhij",
                    isMarkedForDeletion = false,
                    6,
                    2
                )
            )

            //details seeding
            val detailsDao = AppGlobalCE.detailsPersonInfoViewModel.repository.dao
            detailsDao.insert(
                DetailsPersonInfo(
                    id = 1,
                    parentId = 1,
                    describe = "Good persone",
                    date = System.currentTimeMillis()
                )
            )
            detailsDao.insert(
                DetailsPersonInfo(
                    id = 2,
                    parentId = 1,
                    describe = "Getting good",
                    date = System.currentTimeMillis()
                )
            )
            detailsDao.insert(
                DetailsPersonInfo(
                    id = 3,
                    parentId = 2,
                    describe = "Amazing men",
                    date = System.currentTimeMillis()
                )
            )
            detailsDao.insert(
                DetailsPersonInfo(
                    id = 4,
                    parentId = 2,
                    describe = "CE Greator, terminator",
                    date = System.currentTimeMillis()
                )
            )
            // Після змін перезавантажуємо список
            loadAllExt()
            loadAll()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            dao.exec(CEQuery("DELETE FROM person"))
            loadAllExt()
            loadAll()
        }
    }
}

