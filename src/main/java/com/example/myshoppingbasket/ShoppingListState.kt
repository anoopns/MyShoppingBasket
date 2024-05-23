package com.example.myshoppingbasket

data class ShoppingListState(
    val shoppingItems: MutableList<ShoppingItem> = ArrayList(),
    val error: String? = null,
    val isExistingItem: Boolean = false
)
