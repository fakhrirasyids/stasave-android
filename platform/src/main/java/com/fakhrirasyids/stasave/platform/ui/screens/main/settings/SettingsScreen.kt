package com.fakhrirasyids.stasave.platform.ui.screens.main.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.stasave.platform.ui.components.ButtonCard
import com.fakhrirasyids.stasave.platform.ui.components.PartialBottomSheet
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeViewModel
import com.fakhrirasyids.stasave.platform.utils.constants.PermissionConstants
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    val openDocumentTreeWhatsappLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            PermissionConstants.handleWhatsappFolderSelection(result, context, homeViewModel)
        }
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
            title = "Set Whatsapp Statuses Folder",
            description = "Change only if you encounter Error.",
            onClick = {
                PermissionConstants.getWhatsappFolderPermissions(openDocumentTreeWhatsappLauncher)
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