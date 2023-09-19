package com.kerencev.vknewscompose.presentation.common.views.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.kerencev.vknewscompose.ui.theme.Black500
import com.kerencev.vknewscompose.ui.theme.LightBlue
import com.kerencev.vknewscompose.ui.theme.LightGray

@Composable
fun BaseTextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
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
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colors.onPrimary),
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = leadingIcon,
        colors = colors,
    )
}