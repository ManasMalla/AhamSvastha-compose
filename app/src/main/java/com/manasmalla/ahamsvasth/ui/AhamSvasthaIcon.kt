/*
 * Copyright © 2023 Manas Malla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.manasmalla.ahamsvasth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manasmalla.ahamsvasth.R
import com.manasmalla.ahamsvasth.ui.theme.AhamSvasthaTheme

@Preview
@Composable
fun AhamSvasthaIcon(){
    Box {
        Icon(
            painter = painterResource(id = R.drawable.ic_lotus_bottom_layer),
            contentDescription = null,
            modifier = Modifier.size(128.dp).wrapContentHeight(Alignment.Top).padding(top = 12.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_lotus_top_layer),
            contentDescription = null,
            modifier = Modifier.size(128.dp).wrapContentHeight(Alignment.Top).padding(top = 12.dp),
            tint = MaterialTheme.colorScheme.primaryContainer
        )
        Icon(
            imageVector = Icons.Rounded.SelfImprovement,
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun AhamSvasthaIconPreview(){
    AhamSvasthaTheme {
        AhamSvasthaIcon()
    }
}