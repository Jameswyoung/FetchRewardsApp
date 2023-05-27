# FetchRewardsApp

Notes:

I designed and tested this application on a Pixel 6 Pro API 33 Android Studio Emulator. 

Upon starting up the app, the user will find themselves in a login screen. 

To log in enter "Fetch" into the username text field and "Rewards" into the password text field continue by clicking the "Login" button at the bottom of the screen. If the username or password is wrong, a message will appear next to the running dog that says "TRY AGAIN!". 

Once the user is logged in, there will be approximately a 17 minute download (estimated by my current computer and emulator) as the application database is filled with the JSON file data. During this time, a running dog gif will appear that says "Please wait While I fetch your items...". This download will only occur the first time the user enters the Item List screen. Once the JSON File has been downloaded, it will retain itself within the database and the download will not occur again. 

Once the download has been completed the running dog gif will dissapear and a recycler view will appear holding the filtered list of items. 

To further navigate the list of items, the user may enter a number between 1 and 4 and click the search button to filter the list by 'listID'. The user can reset the list to show all items by clicking the search button while the search text field is empty. If the user enters a value that matches none of these parameters, a message will appear below the text field that says "*Enter a value between 1 - 4*". 

