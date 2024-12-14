package src.Server;

import static spark.Spark.after;

import java.util.ArrayList;
import java.util.List;
import spark.Spark;
import src.DormRoom.IDormRoom;
import src.Filtering.FilteringCache;
import src.Filtering.IDormFilter;
import src.Filtering.Node_KDTree.KDTreeWrapper;

/**
 * The {@code Server} class represents the main entry point for the backend of the dorm room filtering system.
 * It initializes and manages the web server and its endpoints. The server uses the Spark Java framework
 * to handle HTTP requests and responses, and it integrates with the filtering backend for dorm room searches.
 */
public class Server {
  /**
   * Constructs and starts a new {@code Server} instance on the specified port, using the given list
   * of dorm rooms to initialize the filtering backend.
   *
   * @param dormRoomList the list of {@link IDormRoom} instances used to initialize the filtering system
   */
  public Server(List<IDormRoom> dormRoomList) {
    // Set the server port
    Spark.port(3232);

    // Configure CORS headers
    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    // Initialize the filtering backend with a KDTreeWrapper
    IDormFilter filter = new KDTreeWrapper(dormRoomList);
    FilteringCache cache = new FilteringCache(filter);

    // Set up endpoint handlers
    Spark.get("/filter", new FilteringHandler(cache));

    // Start the server and wait for initialization
    Spark.init();
    Spark.awaitInitialization();

    // Log the server start message
    System.out.println("Server started at http://localhost:3232");
  }

  /**
   * The main method for starting the server. This method creates a default server instance with
   * no dorm rooms and runs it on port 3232.
   *
   * @param args the command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Create an empty list of dorm rooms
    List<IDormRoom> dormRoomList = new ArrayList<>();

    // Instantiate and start the server
    Server server = new Server(dormRoomList);
    System.out.println("Server has been activated");
  }
}
