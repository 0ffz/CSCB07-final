# Purpose of models

Models are meant to represent exactly how the data in our database looks like. This lets us read/write values by creating Java classes instead of writing piece by piece.

There are some UiState versions of these classes which contain information that is more relevant to the UI. (for example we don't have an email in the UserModel since it's used as a key, but this is useful information in the UI so we include it in UserUiState).
