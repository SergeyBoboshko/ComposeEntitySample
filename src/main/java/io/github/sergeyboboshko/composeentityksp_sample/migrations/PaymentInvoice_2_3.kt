package io.github.sergeyboboshko.composeentityksp_sample.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.composeentity_ksp.base.DatabaseMigration

@DatabaseMigration(version = 3)
object PaymentInvoice_2_3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для сутності DocPaymentsinvoiceEntity
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `doc_payments_invoice` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `date` INTEGER NOT NULL,
                `number` INTEGER NOT NULL,
                `isPosted` INTEGER NOT NULL,
                `isMarkedForDeletion` INTEGER NOT NULL,
                describe TEXT
            )
        """.trimIndent())
    }
}

@DatabaseMigration(version = 6)
object PaymentInvoice_5_6: Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE doc_payments_invoice ADD COLUMN addressId INTEGER NOT NULL DEFAULT 0")
    }
}