package com.challenge.dogbreeds.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = BreedEntity.TABLE_NAME)
class BreedEntity (
    @PrimaryKey
    override val id: String,
    val name: String,
    var urlImage : String?
    ) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "breed"
    }
}