package com.challenge.dogbreeds.database.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = SubBreedEntity.TABLE_NAME, primaryKeys = ["id", "parentBreedId"])
class SubBreedEntity(
    override val id: String,
    val parentBreedId: String,
    val name: String,
    @Embedded val image : ImageEmbedded = ImageEmbedded(null, null)
) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "subBreed"
    }
}