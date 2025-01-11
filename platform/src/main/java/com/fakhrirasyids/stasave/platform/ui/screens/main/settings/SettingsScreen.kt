package com.fakhrirasyids.stasave.platform.ui.screens.main.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.platform.ui.components.ButtonCard
import com.fakhrirasyids.stasave.platform.ui.components.PartialBottomSheet
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBanner(title = MainScreen.Settings.name)

        ButtonCard(
            modifier = modifier.padding(16.dp),
            title = "How to use",
            description = "Learn how to use this app",
            onClick = {
                showBottomSheet.value = true
            }
        )

        ButtonCard(
            modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            title = "Change Theme",
            description = "Change app theme appearance",
            onClick = {

            }
        )

        PartialBottomSheet(
            showBottomSheet = showBottomSheet,
            sheetState = sheetState,
            content = {
                TutorialContent()
            }
        )
    }
}

@Composable
fun TutorialContent() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_1),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_1_instructions_1),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_1),
            contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_1_instructions_1),
        )

        Text(
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_1_instructions_2),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_2),
            contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_1_instructions_2),
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_2),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_2_instructions_1),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_3),
            contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_2_instructions_1)
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_2_instructions_2),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Row {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_4),
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_2_instructions_2)
            )

            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_5),
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_2_instructions_2)
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_3),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_3_instructions_1),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_6),
            contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_3_instructions_1)
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_4),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_4_instructions_1),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Row {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_7),
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_4)
            )

            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.tutorial_8),
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.tutorial_title_4)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    StasaveTheme {
        SettingsScreen()
    }
}