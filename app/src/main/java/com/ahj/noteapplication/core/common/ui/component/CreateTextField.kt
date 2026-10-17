package com.ahj.noteapplication.core.common.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor

@Composable
fun CreateTextField(
    state: TextFieldState,
    placeholder: String? = null,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isPasswordVisible: Boolean = true, // اگر false باشد، متن مخفی (Password) می‌شود
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    placeholderSize: Int = 16,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onKeyboardAction: KeyboardActionHandler? = null,
    inputTransformation: InputTransformation? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(20.dp),
    textAlign: TextAlign = TextAlign.Start,
    paddingValue: PaddingValues = PaddingValues(),
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = if (readOnly) Color.LightGray else MaterialTheme.colorScheme.secondary.copy(
        alpha = 0.5f
    ),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Red,
        errorContainerColor = Color.White,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    // برای شخصی سازی استایل ها
    val mergeStyle = LocalTextStyle.current.merge(textStyle)

    val lineLimits = if (singleLine) TextFieldLineLimits.SingleLine else TextFieldLineLimits.Default

    // ماسک کردن متن رمز عبور با الگوی جدید OutputTransformation
    val outputTransformation = if (!isPasswordVisible) {
        OutputTransformation {
            replace(0, length, "•".repeat(length))
        }
    } else {
        null
    }

    BasicTextField(
        state = state,
        modifier = modifier
            .padding(paddingValue),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergeStyle,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        interactionSource = interactionSource,
        decorator = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = state.text.toString(),
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                isError = isError,
                placeholder = {
                    if (placeholder !=null){
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = placeholderSize.sp,
                            textAlign = textAlign,
                            color = Color.Gray
                        )
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                shape = shape,
                colors = colors,
                contentPadding = PaddingValues(
                    start = 15.dp,
                    end = 15.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
            )
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRichTextField(
    state: RichTextState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    singleLine: Boolean = false, // برای ادیتور RichText معمولاً چندخطی مناسب‌تر است
    readOnly: Boolean = false,
    placeholderSize: Int = 16,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(20.dp),
    textAlign: TextAlign = TextAlign.Start,
    paddingValue: PaddingValues = PaddingValues(),
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = if (readOnly) Color.LightGray else MaterialTheme.colorScheme.secondary.copy(
        alpha = 0.5f
    ),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Red,
        errorContainerColor = Color.White,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val mergeStyle = LocalTextStyle.current.merge(textStyle)
    val textDigit = state.annotatedString.text.trim().firstOrNull()?.let {
            char ->
        char in '0'..'9' || char in '۰'..'۹'
    } ?: false
    val textDirection = if (textDigit) TextDirection.Ltr else TextDirection.Rtl

    BasicRichTextEditor(
        state = state,
        modifier = modifier.padding(paddingValue),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergeStyle.copy(textDirection = textDirection),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = state.toText(), // مقدار متنی ساده جهت تشخیص خالی بودن برای Placeholder
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                isError = isError,
                placeholder = {
                    if (state.annotatedString.text.isBlank()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = placeholderSize.sp,
                            textAlign = textAlign,
                            color = Color.Gray
                        )
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                shape = shape,
                colors = colors,
                contentPadding = PaddingValues(
                    start = 15.dp,
                    end = 15.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
            )
        }
    )
}