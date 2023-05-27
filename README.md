# FetchRewardsApp

Notes:

Type anything in the login boxes to login. 

Currently, every time the application opens the database is cleared of all data. The data is then reuploaded. This is being done to test how the download goes for the user. In real use scenario, the download will only occur once the frirst time the user uses the app, then from then on it won't have to be done again. 

This JSON download, though it only happens once, is causing an ANR on the UI thread. After the data has been downlaoded to the database upon first use of the app, the application runs just fine. It is only causing an ANR the first time the app is being used on a phone and the phone has to donwload the 
complete JSON file. 

Most of the issues will be within the ItemListScreen controller, as that is where the download and AsyncTask methods are located. 

Currently, when the user logs on, the screen freezes and keeps telling them that the application is not working, even though in the background the Async Task doInBackground method is working on downloading the JSON objects. The progress bar is frozen in place during this time. 

What should be happening is once the AsyncTask starts, the pre task method shoud make the progress bar appear and begin to move, the DoInBackground Async method should then download the file in the background. Once the dounload is complete the AsyncPostTask should remove the progress bar. The code should then continue within the OnCreate method
to fill the GUI table with the database objects just donwloaded from the JSON File. 
