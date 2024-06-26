package com.example.myshoppingbasket

import com.example.shoppingmate.ShoppingListViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShoppingListScreen(
    shoppingListViewModel: ShoppingListViewModel = viewModel()
) {
    val uiState by shoppingListViewModel.shoppingListState.collectAsState()

    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Text(
            text = "Add item to the shopping list",
            modifier = Modifier
                .padding(8.dp)
        )
        Row(
        ) {
            AddToListLayout(
                onTextChange = { shoppingListViewModel.itemExists(it) },
                uiState.isExistingItem,
                onButtonClick = { shoppingListViewModel.addToList(it) },
                shoppingListViewModel.userEntry,
                uiState.shoppingItems,
                onDelete = { shoppingListViewModel.deleteShoppingItem(it) },
                addToBasket = { shoppingListViewModel.addToBasket(it) },
                removeFromBasket = { shoppingListViewModel.removeFromBasket(it) }
            )
        }

    }
}

@Composable
fun AddToListLayout(
    onTextChange: (String) -> Unit,
    isExistingItem: Boolean,
    onButtonClick: (String) -> Unit,
    userEntry: String,
    shoppingItems: List<ShoppingItem>,
    onDelete: (ShoppingItem) -> Unit,
    addToBasket: (ShoppingItem) -> Unit,
    removeFromBasket: (ShoppingItem) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            value = userEntry,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onTextChange,
            label = {
                if (isExistingItem) {
                    Text(
                        text = "Item already exists in the basket",
                        color = Color.Red
                    )
                } else {
                    Text(
                        text = "Enter item",
                        color = Color.Black
                    )
                }
            })

        Button(
            onClick = {
                onButtonClick(userEntry)
            }
        ) {
            Text(
                text = "Add to basket"
            )
        }
        MyBasketLayout(shoppingItems, onDelete, addToBasket, removeFromBasket)
    }

}


@Composable
fun MyBasketLayout(
    shoppingItems: List<ShoppingItem>,
    onDelete: (ShoppingItem) -> Unit,
    addToBasket: (ShoppingItem) -> Unit,
    removeFromBasket: (ShoppingItem) -> Unit,
    modifier: Modifier = Modifier
) {
        LazyColumn {
            itemsIndexed(shoppingItems) { index, item ->
                val deleteItem = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Delete")
                    }
                }
                var fontColor = Color.Blue
                var textValue: String = "AddToBasket"
                if(item.addedToBasket) {
                    fontColor = Color.Green
                    textValue = "RemoveFromBasket"
                }

                val addRemoveToBasket = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = fontColor,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(textValue)
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(Dp(50f))
                ) {
                    Text(
                        text = item.itemName,
                        Modifier.weight(0.85f),
                        )
                    ClickableText(
                        text = addRemoveToBasket, onClick = {
                            if(textValue === "AddToBasket") {
                                addToBasket(item)
                            } else{
                                removeFromBasket(item)
                            }

                        },
                        modifier = Modifier.weight(0.15f)
                    )
                    ClickableText(
                        text = deleteItem, onClick = {
                            onDelete(item)
                        },
                        modifier = Modifier.weight(0.15f)
                    )
                }
            }
        }
}

