package com.whereismymotivation.ui.mentors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.image.OutlinedAvatar
import com.whereismymotivation.ui.common.preview.MentorPreviewParameterProvider
import com.whereismymotivation.ui.theme.AppTheme
import java.util.Locale

@Composable
fun Mentors(
    modifier: Modifier = Modifier,
    mentorViewModel: MentorsViewModel,
) {
    val mentors = mentorViewModel.mentors.collectAsState().value
    MentorsView(
        modifier = modifier.fillMaxSize(),
        mentors = mentors,
        selectMentor = { mentorViewModel.selectMentor(it) }
    )
}

@Composable
fun MentorsView(
    mentors: List<Mentor>,
    modifier: Modifier = Modifier,
    selectMentor: (Mentor) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            item(
                key = "LogoAppBar",
                span = StaggeredGridItemSpan.FullLine
            ) {
                LogoAppBar(title = stringResource(R.string.my_mentors))
            }
            items(mentors) { mentor ->
                MentorView(mentor, selectMentor)
            }
        },
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    )
}

@Composable
private fun MentorView(
    mentor: Mentor,
    selectMentor: (Mentor) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(4.dp),
        tonalElevation = 4.dp,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable(
                    onClick = { selectMentor(mentor) }
                )
                .semantics {
                    contentDescription = "featured"
                }
        ) {
            val (cover, avatar, occupation, name, steps, icon) = createRefs()
            NetworkImage(
                url = mentor.coverImgUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .constrainAs(cover) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )
            OutlinedAvatar(
                url = mentor.thumbnail,
                modifier = Modifier
                    .size(48.dp)
                    .constrainAs(avatar) {
                        centerHorizontallyTo(parent)
                        centerAround(cover.bottom)
                    }
            )
            Text(
                text = mentor.occupation.uppercase(Locale.getDefault()),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(occupation) {
                        centerHorizontallyTo(parent)
                        top.linkTo(avatar.bottom)
                    }
            )
            Text(
                text = mentor.name,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(name) {
                        centerHorizontallyTo(parent)
                        top.linkTo(occupation.bottom)
                    }
            )
            Text(
                text = mentor.title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .constrainAs(steps) {
                        centerHorizontallyTo(parent)
                        top.linkTo(name.bottom)
                    }
            )
        }
    }
}

@Preview(name = "MentorInfoPreview: Light")
@Composable
private fun MentorPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorView(
            modifier = Modifier,
            mentor = mentor,
            selectMentor = {}
        )
    }
}

@Preview(name = "MentorsPreview: Light")
@Composable
private fun MentorsPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorsView(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            selectMentor = {},
            mentors = listOf(
                mentor, mentor, mentor, mentor, mentor
            )
        )
    }
}