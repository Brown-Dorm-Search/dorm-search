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

