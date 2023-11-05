# StoryApp

# Submission 1
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

- [x] Add any other recommended features or improvements as needed.



# SUBMISSION

## Display Map
- [x] Implement a new map page that correctly displays the locations of stories with markers or icons.
  - [x] Retrieve story data with latitude and longitude via the "location" parameter in the API.
  - [x] Ensure that the map accurately displays the locations of the stories.

## Paging List
- [x] Implement a story list using Paging 3 correctly.
  - [x] Ensure that stories are loaded and displayed as expected. 

## Make Testing
- [x] Implement unit tests for functions in the ViewModel that retrieve Paging data.
  - [x] Test scenarios for successful data loading.
    - [x] Ensure data is not null.
    - [x] Verify that the data count matches expectations.
    - [x] Confirm that the first returned data is correct.
  - [x] Test scenarios for no story data.
    - [x] Ensure that the returned data count is zero.

# Saran Submission 2
- [x] Ensure clean code formatting.
  - [x] Remove unused comments and code.
  - [x] Maintain proper code indentation.
  - [x] Remove unused imports.
- [x] Provide information during API interactions:
  - [x] Loading indicator while fetching data.
  - [x] Error information when requests fail.
  - [x] Informative message when no data is available.
- [x] Use a custom map style for Google Maps.
- [ ] Use Paging 3 with RemoteMediator
- [] Add an optional feature to input the current GPS location using a checkbox or switch when adding a story.
- [x] Implement Android Architecture Components (at least ViewModel and LiveData) correctly on all pages containing business logic.
- [x] Implement UI testing and idling resources for one of the following scenarios:
  - [x] Ensure the **login** and logout processes are working as expected.
  - [ ] Ensure the story addition process functions correctly.
