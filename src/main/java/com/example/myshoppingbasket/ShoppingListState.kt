package com.example.myshoppingbasket

data class ShoppingListState(
    val shoppingItems: List<ShoppingItem> = emptyList(),
    val error: String? = null,
    val isExistingItem: Boolean = false
)
