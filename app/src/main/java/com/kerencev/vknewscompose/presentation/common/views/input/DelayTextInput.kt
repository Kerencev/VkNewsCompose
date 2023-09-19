package com.kerencev.vknewscompose.presentation.common.views.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kerencev.vknewscompose.ui.theme.Black500
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.LightGray
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DelayTextInput(
    modifier: Modifier = Modifier,
    onSearch: (text: String) -> Unit,
    delay: Long = 800L,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = LightBlue,
        unfocusedBorderColor = LightGray,
        disabledBorderColor = LightGray,
        focusedLabelColor = LightBlue,
        unfocusedLabelColor = Black500,
        disabledLabelColor = Black500,
        focusedLeadingIconColor = LightBlue,
        unfocusedLeadingIconColor = Black500,
        disabledLeadingIconColor = Black500,
        cursorColor = LightBlue,
        selectionColors = TextSelectionColors(
            handleColor = Black500,
            backgroundColor = Black500
        )
    )
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(searchText) {
        searchJob?.cancel()
        searchJob = launch {
            delay(delay)
            onSearch(searchText)
        }
    }

    BaseTextInput(
        modifier = modifier,
        value = searchText,
        onValueChange = { searchText = it },
        label = label,
        leadingIcon = leadingIcon,
        colors = colors
    )
}