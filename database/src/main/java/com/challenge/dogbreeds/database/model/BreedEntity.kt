package com.challenge.dogbreeds.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = BreedEntity.TABLE_NAME)
class BreedEntity (
    @PrimaryKey
    override val id: String,
    val name: String,
    var urlImage : String?,
    var mainBreed : String?
    ) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "breed"
    }
}


data class BreedWithSubBreeds(
    @Embedded val breed: BreedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "mainBreed"
    )
    val subBreeds: List<BreedEntity>
)