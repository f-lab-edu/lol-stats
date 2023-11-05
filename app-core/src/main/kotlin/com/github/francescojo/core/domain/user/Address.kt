package com.github.francescojo.core.domain.user

interface Address {
    val zipCode: String

    val streetAdr: String

    val detailAdr: String?
}
