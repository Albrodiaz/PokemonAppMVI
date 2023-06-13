package com.albrodiaz.pokemonappmvi.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    placeholder: String = "Search",
    onValueChange: (String) -> Unit
) {

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        maxLines = 1,
        textStyle = textStyle,
        onValueChange = onValueChange
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            placeholder = { Text(text = placeholder, style = textStyle) },
            visualTransformation = VisualTransformation.None,
            interactionSource = MutableInteractionSource(),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 8.dp, bottom = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = MaterialTheme.shapes.medium
        )
    }

}

