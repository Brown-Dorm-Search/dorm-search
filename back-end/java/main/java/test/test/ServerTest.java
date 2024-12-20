package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;


import DormRoom.IDormRoom;
import Filtering.FilteringCache;
import Filtering.IDormFilter;
import Filtering.Node_KDTree.KDTreeWrapper;
import Parsing.RoomParser;
import Server.FilteringHandler;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class ServerTest {

  @BeforeAll
  public static void setup_before_everything() {
    // Set the Spark port number to an arbitrary available port
    Spark.port(0);

    // Remove logging spam during tests
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  @BeforeEach
  public void setup() throws IOException {

    // Parse the data when the server is initialized
    RoomParser parser = new RoomParser("data/PartialDataset.csv");
    List<IDormRoom> dormRoomList = parser.getRooms();
    // Initialize the filtering backend with a KDTreeWrapper
    IDormFilter filter = new KDTreeWrapper(dormRoomList);
    FilteringCache cache = new FilteringCache(filter);

    // Map the routes for the handlers
    Spark.get("/filter", new FilteringHandler(cache));

    // Initialize Spark
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    // Unmap the routes and stop the server
    Spark.unmap("/filter");
    Spark.awaitStop();
  }

  /**
   * Helper to start a connection to a specific API endpoint/params
   *
   * @param apiCall the call string, including endpoint
   * @return the connection for the given URL, just after connecting
   * @throws IOException if the connection fails
   */
  private static HttpURLConnection tryRequest(String apiCall) throws IOException {
    // Configure the connection
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
    clientConnection.setRequestMethod("GET");
    clientConnection.connect();
    return clientConnection;
  }

  @Test
  public void testInvalidCampusLocationWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=InvalidLocation&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for campusLocation: InvalidLocation"));
    connection.disconnect();
  }

  @Test
  public void testInvalidIsSuiteWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=invalid&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for isSuite: invalid"));
    connection.disconnect();
  }

  @Test
  public void testInvalidHasKitchenWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=unknown&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for hasKitchen: unknown"));
    connection.disconnect();
  }

  @Test
  public void testInvalidBathroomTypeWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Luxury&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for bathroomType: Luxury"));
    connection.disconnect();
  }

  @Test
  public void testNegativeMinRoomSizeWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=-10&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("success"));
    connection.disconnect();
  }

  @Test
  public void testNonNumericMaxRoomSizeWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=abc&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for maxRoomSize: abc"));
    connection.disconnect();
  }

  @Test
  public void testInvalidRoomCapacityWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=Seven&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for roomCapacity: Seven"));
    connection.disconnect();
  }

  @Test
  public void testNonNumericFloorNumberWithAllOtherParamsValid() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=First");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("Invalid value for floorNumber: First"));
    connection.disconnect();
  }

  @Test
  public void testEmptyCampusLocation() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("campusLocation parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyIsSuite() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("isSuite parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyHasKitchen() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("hasKitchen parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyBathroomType() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("bathroomType parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyMinRoomSize() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("minRoomSize parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyMaxRoomSize() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("maxRoomSize parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyRoomCapacity() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("roomCapacity parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testEmptyFloorNumber() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("floorNumber parameter is missing or empty"));
    connection.disconnect();
  }

  @Test
  public void testDefaultValuesForAllParameters() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=all&isSuite=all&hasKitchen=all&bathroomType=all&minRoomSize=all&maxRoomSize=all&roomCapacity=all&floorNumber=all");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("result"));
    assertTrue(responseBody.contains("success"));
    connection.disconnect();
  }

  @Test
  public void testValidParameters() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("result"));
    assertTrue(responseBody.contains("success"));
    connection.disconnect();
  }

  @Test
  public void testValidParametersWithCommaSeparatedValues() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen,WristonQuad&isSuite=true,false&hasKitchen=true,false&bathroomType=Private,Communal&minRoomSize=100&maxRoomSize=300&roomCapacity=One,Two&floorNumber=1,2");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("result"));
    assertTrue(responseBody.contains("success"));
    connection.disconnect();
  }

  @Test
  public void testLargeRoomSizeRange() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=0&maxRoomSize=1000000&roomCapacity=One&floorNumber=1");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("result"));
    assertTrue(responseBody.contains("success"));
    connection.disconnect();
  }

  @Test
  public void testMultipleFloors() throws IOException {
    HttpURLConnection connection = tryRequest("filter?campusLocation=MainGreen&isSuite=true&hasKitchen=true&bathroomType=Private&minRoomSize=100&maxRoomSize=300&roomCapacity=One&floorNumber=1,2,3,4,5,6,7,8,9,10");
    assertEquals(200, connection.getResponseCode());
    String responseBody = new Buffer().readFrom(connection.getInputStream()).readUtf8();
    assertTrue(responseBody.contains("result"));
    assertTrue(responseBody.contains("success"));
    connection.disconnect();
  }














}
