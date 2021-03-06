/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.puppyList
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage

class PuppyDetailActivity : AppCompatActivity() {

    private lateinit var puppy: Puppy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index = intent.getIntExtra("index", 0)
        puppy = puppyList[index]
        setContent {
            MyTheme {
                Detail()
            }
        }
    }

    @Composable
    fun Detail() {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Puppy Detail",
                                color = Color.White
                            )
                        }
                    )
                },
                content = {
                    PuppyDetail()
                }
            )
        }
    }

    @Composable
    fun PuppyDetail() {
        val selectImageIndex = remember { mutableStateOf(0) }
        val headImage = puppy.images[selectImageIndex.value]
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp, 6.dp, 16.dp, 6.dp)
        ) {
            HeadImage(headImage = headImage)
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                puppy.images.forEachIndexed { index, s ->
                    Thumbnail(index = index, selectImageIndex = selectImageIndex, s = s)
                }
            }
            Text(
                "My name is ${puppy.name}!",
                style = typography.h6,
            )

            LineSpace()

            Text(
                "Facts About Me",
                style = typography.h6,
            )

            Spacer(modifier = Modifier.height(12.dp))

            PuppyAbout(title = "Breed", value = puppy.breed)
            PuppyAbout(title = "Sex", value = puppy.sex)
            PuppyAbout(title = "Color", value = puppy.color)
            PuppyAbout(title = "Age", value = puppy.age)
            PuppyAbout(title = "Size", value = puppy.size)

            LineSpace()

            Text(
                "Animal Profile",
                style = typography.h6,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                puppy.profile,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
            )
        }
    }

    @Composable
    fun HeadImage(headImage: String) {
        val imageModifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
        CoilImage(
            data = headImage,
            contentDescription = "",
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }

    @Composable
    fun Thumbnail(index: Int, selectImageIndex: MutableState<Int>, s: String) {
        val alpha = if (index == selectImageIndex.value) 0.5f else 1.0f
        CoilImage(
            data = s,
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .padding(0.dp, 6.dp, 6.dp, 6.dp)
                .alpha(alpha)
                .clickable {
                    selectImageIndex.value = index
                }
                .clip(shape = RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
    }

    @Composable
    fun LineSpace() {
        Spacer(modifier = Modifier.height(6.dp))
        Divider(color = Color(238, 238, 238, 255))
        Spacer(modifier = Modifier.height(6.dp))
    }

    @Composable
    fun PuppyAbout(title: String, value: String) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                modifier = Modifier.width(70.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Spacer(modifier = Modifier.width(6.dp))

            Text(
                value,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
            )
        }
    }

    @Preview("Light Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun LightPreview() {
        MyTheme {
            Detail()
        }
    }

    @Preview("Dark Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun DarkPreview() {
        MyTheme(darkTheme = true) {
            Detail()
        }
    }
}
