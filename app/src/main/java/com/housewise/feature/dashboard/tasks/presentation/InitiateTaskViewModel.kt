package com.housewise.feature.dashboard.tasks.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// FIXED: Added photoCount and isExpanded states
data class InventorySubItem(
    val id: String,
    val name: String,
    val satisfaction: Int = 0, // 0 = none, 1 = sad, 2 = neutral, 3 = happy
    val comment: String = "",
    val photoCount: Int = 0,
    val isExpanded: Boolean = true // Default is expanded
)

class InitiateTaskViewModel : ViewModel() {
    // Structure: Category -> Item Name -> List of SubItems
    // e.g., "Living Room" -> "Chairs" -> [Chair-01, Chair-02]
    private val _inventoryData =
        MutableStateFlow<Map<String, Map<String, List<InventorySubItem>>>>(emptyMap())
    val inventoryData: StateFlow<Map<String, Map<String, List<InventorySubItem>>>> =
        _inventoryData.asStateFlow()

    fun addItem(category: String, itemName: String) {
        val currentData = _inventoryData.value.toMutableMap()
        val categoryData = currentData[category]?.toMutableMap() ?: mutableMapOf()
        val currentList = categoryData[itemName]?.toMutableList() ?: mutableListOf()

        // Create new ID (e.g., Chair-01)
        val newIndex = currentList.size + 1
        val safeName = if (itemName.endsWith("s")) itemName.dropLast(1) else itemName
        val newId = "$safeName-${newIndex.toString().padStart(2, '0')}"

        currentList.add(InventorySubItem(id = newId, name = newId))
        categoryData[itemName] = currentList
        currentData[category] = categoryData
        _inventoryData.value = currentData
    }

    // Removes the last item when clicking the "—" button
    fun removeItem(category: String, itemName: String) {
        val currentData = _inventoryData.value.toMutableMap()
        val categoryData = currentData[category]?.toMutableMap() ?: return
        val currentList = categoryData[itemName]?.toMutableList() ?: return

        if (currentList.isNotEmpty()) {
            currentList.removeAt(currentList.lastIndex)
            categoryData[itemName] = currentList
            currentData[category] = categoryData
            _inventoryData.value = currentData
        }
    }

    // FIXED: Removes a SPECIFIC item when clicking the trash can icon
    fun removeSubItem(category: String, itemName: String, subItemId: String) {
        val currentData = _inventoryData.value.toMutableMap()
        val categoryData = currentData[category]?.toMutableMap() ?: return
        val currentList = categoryData[itemName]?.toMutableList() ?: return

        currentList.removeAll { it.id == subItemId }
        categoryData[itemName] = currentList
        currentData[category] = categoryData
        _inventoryData.value = currentData
    }

    fun updateSubItem(category: String, itemName: String, updatedItem: InventorySubItem) {
        val currentData = _inventoryData.value.toMutableMap()
        val categoryData = currentData[category]?.toMutableMap() ?: return
        val currentList = categoryData[itemName]?.toMutableList() ?: return

        val index = currentList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            currentList[index] = updatedItem
            categoryData[itemName] = currentList
            currentData[category] = categoryData
            _inventoryData.value = currentData
        }
    }

    // FIXED: Increments the photo count for a specific item
    fun addPhotosToSubItem(category: String, itemName: String, subItemId: String, count: Int) {
        val currentData = _inventoryData.value.toMutableMap()
        val categoryData = currentData[category]?.toMutableMap() ?: return
        val currentList = categoryData[itemName]?.toMutableList() ?: return

        val index = currentList.indexOfFirst { it.id == subItemId }
        if (index != -1) {
            val item = currentList[index]
            currentList[index] = item.copy(photoCount = item.photoCount + count)
            categoryData[itemName] = currentList
            currentData[category] = categoryData
            _inventoryData.value = currentData
        }
    }

    fun getTotalItems(): Int {
        return _inventoryData.value.values.sumOf { categoryMap ->
            categoryMap.values.sumOf { it.size }
        }
    }
}