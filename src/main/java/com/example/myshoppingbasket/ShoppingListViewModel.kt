package com.example.shoppingmate

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myshoppingbasket.ShoppingItem
import com.example.myshoppingbasket.ShoppingListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.Console

class ShoppingListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingListState())
    val shoppingListState: StateFlow<ShoppingListState> = _uiState.asStateFlow()
    var userEntry by mutableStateOf("")

    fun itemExists(itemName: String) {
        userEntry = itemName
        _uiState.update { currentState ->
            currentState.copy(
                error = null,
                isExistingItem = false
            )
        }
        run breaking@{
            _uiState.value.shoppingItems.forEach {
                if (it.itemName == itemName) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = "Item already exists in the cart",
                            isExistingItem = true
                        )
                    }
                    return@breaking
                }
            }
        }
    }

    fun addToList(item: String) {
        if (!_uiState.value.isExistingItem) {
            var items: MutableList<ShoppingItem> = _uiState.value.shoppingItems.toMutableList()
            items.add(ShoppingItem(item, true))
            _uiState.update { currentState ->
                currentState.copy(
                    shoppingItems = items
                )
            }
            updateUserEntry("")
        }
    }

    fun addToBasket(shoppingItem: ShoppingItem) {
        var items: MutableList<ShoppingItem> = _uiState.value.shoppingItems.toMutableList()
        run breaking@{
            items.forEach {
                if (it.itemName == shoppingItem.itemName) {
                    it.addedToBasket = true
                    return@breaking
                }
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                shoppingItems = items
            )
        }
    }

    fun removeFromBasket(shoppingItem: ShoppingItem) {
        var items: MutableList<ShoppingItem> = _uiState.value.shoppingItems.toMutableList()
        run breaking@{
            items.forEach {
                if (it.itemName == shoppingItem.itemName) {
                    it.addedToBasket = false
                    return@breaking
                }
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                shoppingItems = items
            )
        }
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        var items: MutableList<ShoppingItem> = _uiState.value.shoppingItems.toMutableList()
        items.remove(shoppingItem)
        _uiState.update { currentState ->
            currentState.copy(
                shoppingItems = items.toList()
            )
        }
    }

    fun updateUserEntry(name: String) {
        userEntry = name
    }

}