package com.example.myshoppingbasket

import com.example.shoppingmate.ShoppingListViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel = viewModel()
) {
    val shoppingListUiState by shoppingListViewModel.shoppingListState.collectAsState()

    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add item to the shopping list",
            modifier = Modifier
                .padding(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AddToListLayout(
                onTextChange = { shoppingListViewModel.itemExists(it) },
                shoppingListUiState.isExistingItem,
                onButtonClick = { shoppingListViewModel.addToList(it) },
                shoppingListViewModel.userEntry,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
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

            Button(//modifier = Modifier.fillMaxWidth(),
                onClick = { onButtonClick(userEntry) }) {
                Text(
                    text = "Add to basket"
                )
            }
        }
    }
}