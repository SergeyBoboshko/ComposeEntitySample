package io.github.sergeyboboshko.composeentityksp_sample.migrations

import androidx.room.migration.AutoMigrationSpec
import io.github.sergeyboboshko.composeentity_ksp.base.CeAutoMigration

@CeAutoMigration(from = 1, to = 2)
class CeAutoMigration1

@CeAutoMigration(from = 2, to = 3)
class CeAutoMigration2

@CeAutoMigration(from = 3, to = 4)
class CeAutoMigration3: AutoMigrationSpec {

}