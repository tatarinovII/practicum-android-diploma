package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Black
import ru.practicum.android.diploma.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    height: Int = 72,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxSize()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = onClear,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close_24px),
                            contentDescription = stringResource(R.string.clear_query),
                            tint = Black
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_24px),
                        contentDescription = stringResource(R.string.search),
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 4.dp),
                        tint = Black
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = Blue
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Black
            )
        )
    }
}
