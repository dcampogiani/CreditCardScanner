package com.danielecampogiani.creditcardscanner

data class CardDetails(
    val owner: String?,
    val number: String?,
    val expirationMonth: String?,
    val expirationYear: String?
)