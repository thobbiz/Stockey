package com.example.project_umbrella.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.project_umbrella.ui.screens.inventory.AddProductViewModel
import com.example.project_umbrella.ui.screens.inventory.EditProductViewModel
import com.example.project_umbrella.ui.screens.inventory.InventoryScreenViewModel
import com.example.project_umbrella.UmbrellaApplication
import com.example.project_umbrella.ui.screens.inventory.ProductDetailsViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for AddProductViewModel
        initializer {
            AddProductViewModel(umbrellaApplication().container.productsRepository)
        }

        // Initializer for EditProductViewModel
        initializer {
            EditProductViewModel(
                this.createSavedStateHandle(),
                umbrellaApplication().container.productsRepository
            )
        }

        // Initializer for Inventory Screen
        initializer {
            InventoryScreenViewModel(
                umbrellaApplication().container.productsRepository
            )
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ProductDetailsViewModel(
                this.createSavedStateHandle(),
                umbrellaApplication().container.productsRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [UmbrellaApplication].
 */
fun CreationExtras.umbrellaApplication(): UmbrellaApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as UmbrellaApplication)