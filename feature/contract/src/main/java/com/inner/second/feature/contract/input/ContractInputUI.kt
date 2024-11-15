package com.inner.second.feature.contract.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inner.second.core.designsystem.R
import com.inner.second.core.designsystem.theme.TextFieldPurple
import com.inner.second.core.model.ContractFormInputType
import com.inner.second.core.model.DateDuration
import com.inner.second.core.util.toFormattedString
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun ContractInputItem(
    title: String,
    inputType: ContractFormInputType,
    onItemChange: ((Any) -> Unit) = {},
) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )

    Spacer(modifier = Modifier.height(12.dp))

    when (inputType) {
        is ContractFormInputType.Text -> ContractInputText(
            onTextChange = onItemChange
        )

        is ContractFormInputType.Price -> ContractInputPrice(
            onPriceChange = onItemChange
        )

        is ContractFormInputType.RadioButton -> ContractInputRadioButton(
            selections = inputType.options,
            onSelectionChange = onItemChange,
        )

        is ContractFormInputType.DateDuration -> ContractInputDateDuration(
            onDurationChange = onItemChange,
        )

        is ContractFormInputType.MultiText -> ContractInputMultiText(
            onMultiTextChange = onItemChange
        )
    }
}

@Composable
fun ContractInputText(
    defaultText: String = "",
    onTextChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf(defaultText) }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChange(it)
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = text,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = false,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TextFieldPurple,
                    unfocusedBorderColor = TextFieldPurple,
                ),
            )
        }
    )
}

@Composable
fun ContractInputPrice(
    defaultPrice: Long = 0,
    onPriceChange: (Long) -> Unit,
) {
    var price by remember { mutableLongStateOf(defaultPrice) }
    val displayPrice: String = remember(price) {
        NumberFormat.getInstance().format(price)
    }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = displayPrice,
        onValueChange = {
            if (it.isNotEmpty()) {
                val number = it.replace(",", "").toLong()
                price = if (number < 0) 0 else number
                onPriceChange(price)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = displayPrice,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = false,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TextFieldPurple,
                    unfocusedBorderColor = TextFieldPurple,
                ),
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContractInputRadioButton(
    selections: List<String>,
    onSelectionChange: (String) -> Unit,
) {
    var selectedOption by remember { mutableStateOf(selections.first()) }

    LaunchedEffect(key1 = selectedOption) {
        onSelectionChange(selectedOption)
    }

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Center
    ) {
        selections.forEach { selection ->
            Row(
                modifier = Modifier.clickable(interactionSource = null, indication = null) {
                    selectedOption = selection
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selection == selectedOption,
                    onClick = {
                        selectedOption = selection
                    }
                )
                Text(
                    text = selection,
                    modifier = Modifier.padding(end = 20.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun ContractInputDateDuration(
    defaultDuration: DateDuration = DateDuration(),
    onDurationChange: (DateDuration) -> Unit,
) {
    var duration by remember { mutableStateOf(defaultDuration) }
    val interactionSource = remember { MutableInteractionSource() }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = duration) {
        onDurationChange(duration)
    }

    if (showDatePickerDialog) {
        ContractInputDateDurationPicker(
            dateDuration = duration,
            onDateRangeSelected = { duration = it },
            onDismiss = { showDatePickerDialog = false },
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = duration.startDate.toFormattedString(),
            onValueChange = { /* do nothing */ },
            modifier = Modifier
                .weight(1f)
                .clickable(interactionSource = null, indication = null) {
                    showDatePickerDialog = true
                },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.End
            ),
            enabled = false,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = duration.startDate.toFormattedString(),
                    innerTextField = innerTextField,
                    enabled = false,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TextFieldPurple,
                        unfocusedBorderColor = TextFieldPurple,
                        disabledBorderColor = TextFieldPurple,
                    ),
                    prefix = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar_outlined),
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black,
                            contentDescription = "icon"
                        )
                    },
                )
            }
        )

        Text(
            text = "~",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        BasicTextField(
            value = duration.endDate.toFormattedString(),
            onValueChange = { /* do nothing */ },
            modifier = Modifier
                .weight(1f)
                .clickable(interactionSource = null, indication = null) {
                    showDatePickerDialog = true
                },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.End
            ),
            enabled = false,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = duration.endDate.toFormattedString(),
                    innerTextField = innerTextField,
                    enabled = false,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TextFieldPurple,
                        unfocusedBorderColor = TextFieldPurple,
                        disabledBorderColor = TextFieldPurple,
                    ),
                    prefix = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar_outlined),
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black,
                            contentDescription = "icon"
                        )
                    },
                )
            }
        )
    }
}

@Composable
fun ContractInputDateDurationPicker(
    dateDuration: DateDuration,
    onDateRangeSelected: (DateDuration) -> Unit,
    onDismiss: () -> Unit,
) {
    val stringBuilder = remember { StringBuilder() }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = dateDuration.startDate.toInstant(ZoneOffset.UTC)
            .toEpochMilli(),
        initialSelectedEndDateMillis = dateDuration.endDate.toInstant(ZoneOffset.UTC)
            .toEpochMilli(),
    )

    val headlineText: String = remember(
        dateRangePickerState.selectedStartDateMillis,
        dateRangePickerState.selectedEndDateMillis
    ) {
        stringBuilder.clear()

        dateRangePickerState.selectedStartDateMillis?.let {
            stringBuilder.append(
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it), ZoneOffset.UTC
                )
                    .toFormattedString()
            )
        }
        stringBuilder.append(" - ")
        dateRangePickerState.selectedEndDateMillis?.let {
            stringBuilder.append(
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it), ZoneOffset.UTC
                )
                    .toFormattedString()
            )
        }

        stringBuilder.toString()
    }

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        DateDuration(
                            startDate = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(
                                    dateRangePickerState.selectedStartDateMillis!!
                                ),
                                ZoneOffset.UTC
                            ),
                            endDate = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(
                                    dateRangePickerState.selectedEndDateMillis!!
                                ),
                                ZoneOffset.UTC
                            )
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            showModeToggle = false,
            modifier = Modifier.fillMaxWidth(),
            title = @Composable {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    text = "날짜 범위 선택",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            },
            headline = @Composable {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = headlineText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        )
    }
}

@Composable
fun ContractInputMultiText(
    defaultMultiText: List<String> = listOf(""),
    onMultiTextChange: (List<String>) -> Unit,
) {
    val multiText = remember { defaultMultiText.toMutableStateList() }
    val interactionSource = remember { MutableInteractionSource() }

    fun onAddButtonClick() = multiText.add("")
    fun onRemoveButtonClick(index: Int) {
        if (multiText.size < 2) return
        multiText.removeAt(index)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            multiText.forEachIndexed { index, text ->
                key("MultiText_$index") {
                    BasicTextField(
                        value = text,
                        onValueChange = {
                            multiText[index] = it
                            onMultiTextChange(multiText.toList())
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        enabled = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        decorationBox = @Composable { innerTextField ->
                            OutlinedTextFieldDefaults.DecorationBox(
                                value = text,
                                innerTextField = innerTextField,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = interactionSource,
                                contentPadding = PaddingValues(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = TextFieldPurple,
                                    unfocusedBorderColor = TextFieldPurple,
                                ),
                                suffix = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_remove_outlined),
                                        contentDescription = "removeIcon",
                                        modifier = Modifier.clickable(
                                            interactionSource = null, indication = null
                                        ) { onRemoveButtonClick(index) },
                                        tint = TextFieldPurple,
                                    )
                                },
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.clickable(
                    interactionSource = null, indication = null
                ) { onAddButtonClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "항목 추가",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "addIcon",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContractInputTextPreview() {
    ContractInputText(
        defaultText = "Text",
        onTextChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ContractInputPricePreview() {
    ContractInputPrice(
        defaultPrice = 1000000,
        onPriceChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ContractInputRadioButtonPreview() {
    ContractInputRadioButton(
        selections = listOf("option1", "option2"),
        onSelectionChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ContractInputDateDurationPreview() {
    ContractInputDateDuration(
        defaultDuration = DateDuration(),
        onDurationChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ContractInputDateDurationPickerPreview() {
    ContractInputDateDurationPicker(
        dateDuration = DateDuration(),
        onDateRangeSelected = {},
        onDismiss = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ContractInputMultiTextPreview() {
    ContractInputMultiText(
        defaultMultiText = listOf("text1", "text2", "text3"),
        onMultiTextChange = { _ -> },
    )
}