# dorm-search
A complete encyclopedia of Brown University dormitories for students to find their perfect match! We have created a secure website for Brown students to filter through the thousands of dorm rooms on campus in order to be prepared for the housing lottery and have all their dorm info in one place. 
# How to use
After signing in, (need to be a Brown University Student) you can navigate to the Home, About page, and Contact page. The Home page allows you to search and collect informaiton about Dorm rooms and buildings. The About page contains information about the project and who contributed. The Contact Page allows users to contact programmers about questions and inqueries.

**Developer Use**
In the case that you need to add more data to the dorm buildings and dorm rooms, you can do the following:
 * Adding Images:
    In frontend/public/assests there are image files. You need to replace or add images names that match with the path file names listed in InfoPage.tsx.
* Adding Dorm Building Information:
   You will need to add information in the InfoPage, the dataset in backend, and Backend DormBuildings. if creating new dorm buildings, will also need to add to the overlay.
 * Adding More Dorm Room Informaiton:
    You will need to add information to the dataset in the backend. If creating more options, will also need to add to several files, including Dropdownss, SatndardDorm, and SuiteDorm in the front end, and Dormrooms in the backend
 * Adding Map Information: 
    Will need to change the overlay and Tiledata.json accordingly. 

**Student Use**
Select options on the top panel and press "search" to display dorm buildings
that have matching dorms as per the search request. 

Click on a Dorm building on the map or the left panel to get information about the dorm building and its mathcing dorms. If you press the button at the top of the left pannel, you can see all dorms that match your search request 
in the right panel. 


# Design
**Backend File Structure**  
**Filtering Folder**

* Interface IFilter  
  * filter()  
* KDTreeGenerator  
  * KDTreeFilterer generateTree(List\<DormRoom\>)  
  * List\<DormRoom\> getDormRoomList()


  * Responsible for making a KDTree used for filtering  
  * Responsible for accessing the dorm room data  
    * This could just be from SQL, but it could also access the housing lottery Google Sheet if time permits.  
* KDTreeFilterer implements Filterer  
  * List\<DormRoom\> filter(List\<DormRoom\>, List\<Object\>)  
  * List\<DormRoom\> filter(List\<DormBuilding\>, List\<Object\>)

  * Given filter parameters this class filters the dorms list  
  * The List\<Object\> is a list of all the different attributes to filter on. Object is used because a user could need to split on a list of different options for a single filter. For example, one of the filters could be a building in Young O or Goddard.  
* FilteringCache(IFilter)  
  * List\<DormRoom\> getFilteredListOfDormRooms()  
    * Returns the filtered dorm room list  
  * private Cache generateCache()  
    * Generates the cache  
      

         
**Dorm Rooms Folder**

* Dorm Room Class   
  * Dorm Room Object  
    * int roomSize  
      * Square footage of the room  
    * Int  roomNumber  
      * The number of rooms within a certain building  
    * int floorNumber  
      * The number floor that the room is on  
    * BuildingName buildingName  
      * The name of the building that the room is in  
    * Int roomateNumber   
      * The number of roommates  
      * Could also be an enum  
    * boolean roomType   
      * Whether a room is a suite or not  
      * **Note:** There may be some complications here whether we split different rooms within a suite  
* Dorm Building Class  
  * Building object  
    * int numOfWashingMachines  
      * Taken from CSC Go and [\[Link\]](https://reslife.brown.edu/on-campus/community-living/residence-hall-service-rooms)  
    * Int totalBuildingOccupacy  
      * The maximum number of people who live in the building   
    * int year  
      * The year the dorm room was built  
    * int Longitude  
      * Approximate Longitude of the center of the building  
    * Int Latitude  
      * Approximate Latitude of the center of the building  
    * List\<int\> floorsWithElevatorAccess  
      * What floors have elevator access  
      * May need to have additional info (e.g. EvPol has elevator access through a different building)   
    * CampusLocation campusLocation   
      * Where the dorm is located around campus (Wriston Quad, Main Green, etc.)  
* Enum BuildingName  
  * An enum with every housing building on campus  
* Enum CampusLocation  
  * An enum with all the different general locations around campus

         
**Server Folder**

* Server  
  * public static void setupServer()  
    * Sets up the backend server  
  * public static void main(String\[\] args)  
    * Runs the server program  
* FilterHandler  
  * public Object void handle()  
    * Calls the entire program  
  * private static String toMoshiJson(Map\<String, Object\> map)  
    * Turns the filtered dorm list into a JSON, so it can be outputted by the API.


**Frontend Structure**  
**Map Folder**

* MapBox
    * Displays Mapbox
        * has Hover functionality
        * Has bounds
        * when map clicked, a variable keeps track to oepn associated dorm information
* Overlay 
    * Contains fill color information for two map layers
        * map layers are for all dorms and for searched dorm buildings
* Tiledata
    * json of layer

**Additonal Files in src Folder**

* About
    * leads to page about Programmers who worked on the project
* App
    * Contains instance of Navbar at Top of page
   * Contians instance of Home and About Page, accesible through routes
* Dropdowns
    * Dispalys mulitselect and scroll options as well as search button
    * Interfaces of Dorm Building names and json fetched by backend after search
* Home
    * Contains dropdown intannce at top of screen
        * gets information fetched from backend 
    * Panel contain holds the following:
        * Left Panel: Dorm buildings
        * Center Panel: MapBox instance || InfoPage instance
        * Right Panel: Multiple Instances of Suit and StandardDorm
* InfoPage
    * For the clicked Dorm building, displays building information
* Navbar
    * Links to Home, About, and Contact pages 
* StandardDorm
    * Displays qualities of a Dorm room in a container
* Suite
    * Displays qualities of a Suite in a container
    * Displays Dorm rooms of Suite in sub-containers

# Tests