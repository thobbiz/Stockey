package com.example.project_umbrella.ui.screens.inventory

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_umbrella.R
import com.example.project_umbrella.ui.AppViewModelProvider
import com.example.project_umbrella.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object EditProductDestination : NavigationDestination {
    override val route = "product_edit"
    override val titleRes = R.string.edit_product
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineSCope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            EditProductTopAppBar(
                title = stringResource(EditProductDestination.titleRes),
                navigateBack = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EditProductBody(
            productUiState = viewModel.productUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveEditClick = {
                coroutineSCope.launch {
                    viewModel.updateProduct()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun EditProductBody(
    productUiState: ProductUiState,
    onItemValueChange: (ProductInfo) -> Unit,
    onSaveEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(60.dp),
        modifier = modifier.padding(20.dp)
    ) {
        EditProductForm(
            productInfo = productUiState.productInfo,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveEditClick,
            enabled = productUiState.isInfoValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff0081f7),
                contentColor = Color.White,
                disabledContainerColor = Color(0xff3B5975)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.save_edit),  style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun EditProductForm(
    productInfo: ProductInfo,
    modifier: Modifier = Modifier,
    onValueChange: (ProductInfo) -> Unit = {},
    enabled: Boolean = true
) {
    val locale = Locale("en", "NG")
    val currencyNG = Currency.getInstance(locale)
    val symbolNG = currencyNG.getSymbol(locale).trim()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        OutlinedTextField(
            value = productInfo.unitCostPrice,
            onValueChange = { onValueChange(productInfo.copy(unitCostPrice = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            placeholder = { Text(stringResource(R.string.cost_price),  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = Color(0xff34373c),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Unspecified, RoundedCornerShape(12.dp))
                .height(50.dp),
            leadingIcon = { Text(symbolNG) },
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
                unfocusedBorderColor = Color(0xff34373c),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Unspecified, RoundedCornerShape(12.dp))
                .height(50.dp),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = productInfo.unit,
            onValueChange = { onValueChange(productInfo.copy(unit = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text( text = stringResource(R.string.measurement_unit),  style = MaterialTheme.typography.bodySmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xffcbc7c7),
                unfocusedBorderColor = Color(0xff34373c),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(5.dp, Color.Unspecified, RoundedCornerShape(12.dp))
                .height(50.dp),
            enabled = enabled,
            singleLine = true
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductTopAppBar(
    title: String,
    navigateBack: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp, start = 10.dp)
    ) {
        IconButton(onClick = navigateBack, modifier = Modifier.padding(0.dp)) {
            Icon(
                imageVector = Filled.Clear,
                contentDescription = stringResource(R.string.back_button),
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}
