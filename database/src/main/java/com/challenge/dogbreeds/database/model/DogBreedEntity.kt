package com.challenge.dogbreeds.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = DogBreedEntity.TABLE_NAME)
class DogBreedEntity (
    @PrimaryKey
    override val id: String,
    val name: String,
    @Embedded val image : ImageEmbedded = ImageEmbedded(null, null)
    ) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "breed"
    }
}


data class BreedWithSubBreedsRelation(
    @Embedded val breed: DogBreedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentBreedId"
    )
    val subBreedEntities: List<SubBreedEntity>
)