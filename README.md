# StoryApp

# Authentication Pages
- [x] **Login Page**
  - [x] Email
  - [x] Password (hidden)

- [x] **Register Page**
  - [x] Name
  - [x] Email
  - [x] Password (hidden)

## Custom EditText View
  - [x] If the password length is less than 8 characters, display an error message directly within the 
        EditText without needing to switch forms or click a button.

## Data Storage and Preferences
- [x] Save session data and token in preferences.
  - [x] If already logged in, redirect to the homepage.
  - [x] If not logged in, redirect to the login page.

## Logout Feature
- [x] Implement a logout feature.
  - [x] Delete token and session information when logout button is pressed.

# List Story
- [x] Display a list of stories from the provided API.
  - [x] Show user's Name for each story.
  - [x] Display a photo for each story.
- [x] Implement a detailed view for each story item:
  - [x] Show user's Name.
  - [x] Display a photo.
  - [x] Include a description for each story.

# Add Story
- [x] Create a page for adding new stories with gallery image selection.
  - [x] Include a photo file selection.
  - [x] Add a story description input.
- [x] Provide an upload button to send data to the server.
- [x] After a successful upload, return to the list of stories with the most recent story at the top.

# Animations
- [x] Implement an animation in the application using one of the following types:
  - [x] Property Animation
  - [] Motion Animation
  - [x] Shared Element
- [x] Specify the type and location of the animation in the Student Note
  - [x] WelcomeActivity
  - [x] SignUpActivity
  - [x] LoginActivity
  - [x] MainActivity
  - [x] DetailActivity


# SARAN SUBMISSION
## Code Quality

- [x] Ensure clean code formatting.
  - [x] Remove unused comments and code.
  - [x] Maintain proper code indentation.
  - [x] Remove unused imports.

## Custom View - EditText

- [x] Create a custom EditText that displays an error if the email format is incorrect.

## Camera Integration

- [x] Add an option to capture images using the device's camera when adding a story.

## API Interaction

- [x] Ensure that the app waits for a successful or failed response before navigating to a new screen during registration, login, and story upload.

## App Flow

- [x] After successful login, pressing the back button on the home page should exit the app, not return to the login page.
- [x] After a story is uploaded, pressing the back button on the home page should exit the app, not return to the upload page.
- [x] After logging out, pressing the back button should exit the app, not return to the previous screen.

## Stack Widget

- [?] Implement a stack widget to display a list of stories.

## Localization

- [x] Add support for multi-language localization in the app.

## User Interaction with API

- [x] Display loading indicators when data is being fetched.
- [x] Show informative error messages when requests fail.
- [x] Provide user-friendly messages when no data is available.

## Architecture

- [x] Implement Android Architecture Components, including ViewModel and LiveData, correctly on all screens with business logic.

## Additional Improvements

- [] Add any other recommended features or improvements as needed.