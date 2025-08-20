package com.example.project_umbrella.ui.screens.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_umbrella.R
import com.example.project_umbrella.ui.AppViewModelProvider
import com.example.project_umbrella.ui.navigation.NavigationDestination
import com.example.project_umbrella.ui.theme.ProjectumbrellaTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale


object AddProductDestination: NavigationDestination {
    override val route = "add_product"
    override val titleRes = R.string.add_product
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: AddProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AddProductTopAppBar(
                title = stringResource(R.string.add_new_product),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
    ) { innerPadding ->
        AddProductBody(
            productUiState = viewModel.productUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveProduct()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun AddProductBody(
    productUiState: ProductUiState,
    onItemValueChange: (ProductInfo) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(60.dp),
        modifier = modifier.padding(20.dp)
    ) {
        AddProductForm(
            productInfo = productUiState.productInfo,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = productUiState.isInfoValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff0081f7),
                contentColor = Color.White,
                disabledContainerColor = Color(0xff3B5975)
            ),
            shape = RoundedCornerShape(9.dp),
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text(text = stringResource(R.string.save),  style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun AddProductForm(
    productInfo: ProductInfo,
    modifier: Modifier = Modifier,
    onValueChange: (ProductInfo) -> Unit = {},
    enabled: Boolean = true
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        OutlinedTextField(
            value = productInfo.name,
            onValueChange = { onValueChange(productInfo.copy(name = it)) },
            placeholder = { Text(stringResource(id = R.string.product_name),  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,

            ),
            modifier = Modifier.fillMaxWidth().border(0.dp, Color.Unspecified, RoundedCornerShape(12.dp)).height(55.dp),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = productInfo.unitCostPrice,
            onValueChange = { onValueChange(productInfo.copy(unitCostPrice = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            placeholder = { Text(stringResource(R.string.cost_price),  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Unspecified, RoundedCornerShape(12.dp)).height(55.dp),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = productInfo.unitSellingPrice,
            onValueChange = { onValueChange(productInfo.copy(unitSellingPrice = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(stringResource(R.string.selling_price),  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Unspecified, RoundedCornerShape(12.dp)).height(55.dp),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = productInfo.quantity,
            onValueChange = { onValueChange(productInfo.copy(quantity = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("Quantity in Stock",  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Unspecified, RoundedCornerShape(12.dp)).height(55.dp),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = productInfo.measurementUnit,
            onValueChange = { onValueChange(productInfo.copy(measurementUnit = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text( text = stringResource(R.string.measurement_unit),  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Unspecified, RoundedCornerShape(12.dp)).height(55.dp),
            enabled = enabled,
            singleLine = true
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth(0.5f)
            .padding(start = 0.dp, end = 0.dp, top = 16.dp, bottom = 32.dp)
    ) {
        if (canNavigateBack) {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Filled.Clear,
                    contentDescription = stringResource(R.string.back_button),
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    ProjectumbrellaTheme {
        AddProductBody(productUiState = ProductUiState(
            ProductInfo(
                name = "Item name", unitCostPrice = "10.00", quantity = "5", unitSellingPrice = "20.00"
            )
        ), onItemValueChange = {}, onSaveClick = {})
    }
}