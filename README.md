# Print Example using the Clover Android SDK
## **Pre-reqs**
 - Be familiar with Android Studio projects
 - Know how to design [custom layouts][1]

## **General Overview**
 1. Create a resource layout for the receipt
    - Add a ScrollView with a Linear Layout inside (needs to be a Scroll View to print elements off screen)
    - Give the Linear Layout an ID, this will be what we insert the receipt elements into and what we ultimately print
 2. In your print activity, inflate the layout from the last step
    - Use the LayoutInflater (if printing from another activity) and attach the receipt to a viewgroup
    - Grab the receipt area and store it in a View
 3. Print the receipt
    - Use an AsyncTask
    - Build the print job and broadcast the request

## Future revisions
 - Target specific printers

 [1]: http://kb4dev.com/tutorial/android-layout/how-to-build-dynamic-layouts-in-android 