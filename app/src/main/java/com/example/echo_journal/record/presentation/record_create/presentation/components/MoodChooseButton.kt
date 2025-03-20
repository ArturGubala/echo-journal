package com.example.echo_journal.record.presentation.record_create.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echo_journal.R
import com.example.echo_journal.core.domain.MoodUi
import com.example.echo_journal.ui.theme.MoodUndefined95
import com.example.echo_journal.ui.theme.PalettesSecondary70

@Composable
fun MoodChooseButton(
    mood: MoodUi?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (mood == null) {
        Box (
            modifier = modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MoodUndefined95, CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.choose_mood),
                tint = PalettesSecondary70
            )
        }
    } else {
        Image(
            painter = painterResource(id = mood.resId),
            contentDescription = stringResource(R.string.choose_mood),
            modifier = Modifier.height(32.dp).clickable { onClick() },
            contentScale = ContentScale.FillHeight
        )
    }
}
