package de.lukasneugebauer.nextcloudcookbook.recipe.presentation.download

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.lukasneugebauer.nextcloudcookbook.R
import de.lukasneugebauer.nextcloudcookbook.core.presentation.components.DefaultOutlinedTextField
import de.lukasneugebauer.nextcloudcookbook.core.presentation.components.HideBottomNavigation
import de.lukasneugebauer.nextcloudcookbook.core.presentation.ui.theme.NextcloudCookbookTheme
import de.lukasneugebauer.nextcloudcookbook.core.util.UiText
import de.lukasneugebauer.nextcloudcookbook.destinations.RecipeDetailScreenDestination

@Destination
@Composable
fun DownloadRecipeScreen(
    navigator: DestinationsNavigator,
    viewModel: DownloadRecipeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    HideBottomNavigation()

    uiState.recipeId?.let { id ->
        LaunchedEffect(id) {
            navigator.navigate(RecipeDetailScreenDestination(id))
        }
    }

    Scaffold(
        topBar = {
            RecipeDownloadTopBar {
                navigator.navigateUp()
            }
        },
    ) { innerPadding ->
        DownloadRecipeScreen(
            url = uiState.url,
            onDownloadClick = { viewModel.importRecipe() },
            onUrlChange = { viewModel.updateUrl(it) },
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(top = dimensionResource(id = R.dimen.padding_m)),
            error = uiState.error,
        )
    }
}

@Composable
private fun DownloadRecipeScreen(
    url: String,
    onDownloadClick: () -> Unit,
    onUrlChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: UiText? = null,
) {
    Column(
        modifier = modifier,
    ) {
        DefaultOutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_m))
                    .padding(bottom = dimensionResource(id = R.dimen.padding_xs)),
            label = { Text(text = "Recipe URL") },
            errorText = error?.asString(),
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
            keyboardActions =
                KeyboardActions(
                    onDone = { },
                ),
            singleLine = true,
            colors =
                TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                ),
        )
        Button(
            onClick = onDownloadClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_m)),
            enabled = url.isNotEmpty(),
        ) {
            Text(text = "Download")
        }
    }
}

@Composable
private fun RecipeDownloadTopBar(onNavIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Download recipe",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavIconClick) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.common_back),
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
    )
}

@Preview
@Composable
private fun DownloadRecipeScreenPreview(modifier: Modifier = Modifier) {
    NextcloudCookbookTheme {
        DownloadRecipeScreen(
            url = "https://example.com/recipe",
            onDownloadClick = {},
            onUrlChange = {},
        )
    }
}
