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
//    val subBreeds: List<String> = emptyList()
    ) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "breed"
    }
}

@Entity(tableName = SubBreedEntity.TABLE_NAME)
class SubBreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val breadId: String,
    val name: String,
    val parentBreed: String,
) : IEntity {
    companion object {
        const val TABLE_NAME = "subBreed"
    }
}

@Entity(tableName = ImageEntity.TABLE_NAME)
data class ImageEntity(
    @PrimaryKey
    override val id: String, //id breed
    var urlImage : String?,
    var statusFailImage : Boolean? = null,
): BaseEntity() {
    companion object {
        const val TABLE_NAME = "image"
    }
}


data class BreedWithSubBreeds(
    @Embedded val breed: DogBreedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentBreed"
    )
    val subBreedEntitiys: List<SubBreedEntity>
)