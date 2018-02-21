# Print Example using the Clover Android SDK
## **Pre-requisites**
 - Be able to connect to a Clover Device via Android SDK (PritnerConnector)
 - Know how to design [custom layouts][1]
    - [Changing font sizes using pixels][2]

## **General Overview**
 1. Create a resource layout for the receipt
    - Add a ScrollView with a Linear Layout inside (needs to be a Scroll View to print elements off screen)
    - Give the Linear Layout an ID, this will be what we insert the receipt elements into and what we ultimately print
 2. In your print activity, inflate the layout from the last step (It has to be attached to a root in order to print)
    - Use the LayoutInflater (if printing from another activity) and attach the receipt to a viewgroup
    - Grab the whole receipt area and store it in a View
 3. Print the receipt
    - Use an AsyncTask
    - Specify the printer
    - Build the print job using ViewPrintJobBuilder and broadcast the request

## Future revisions
 - Target specific printers
 - Spruce up print screen
 - Use a table layout to organize line items

 [1]: http://kb4dev.com/tutorial/android-layout/how-to-build-dynamic-layouts-in-android 
 [2]: https://medium.com/@rayacevedo45/android-sp-vs-dp-ca50ad53162d