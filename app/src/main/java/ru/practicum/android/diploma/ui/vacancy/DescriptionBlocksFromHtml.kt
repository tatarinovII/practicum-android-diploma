package ru.practicum.android.diploma.ui.vacancy

sealed class DescriptionBlocksFromHtml {
    data class Header(val text: String, val level: Int) : DescriptionBlocksFromHtml()
    data class SmallHeader(val text: String, val level: Int) : DescriptionBlocksFromHtml()
    data class ListItem(val items: List<String>) : DescriptionBlocksFromHtml()
    data class Text(val content: String) : DescriptionBlocksFromHtml()
}
