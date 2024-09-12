package com.challenge.dogbreeds.database.model

interface IEntity {

}

abstract class BaseEntity : IEntity {
    abstract val id: String
}