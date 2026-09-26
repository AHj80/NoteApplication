package com.ahj.noteapplication.core.common.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacerHeight(num: Int) = Spacer(Modifier.height(num.dp))


@Composable
fun SpacerWidth(num: Int) = Spacer(Modifier.width(num.dp))