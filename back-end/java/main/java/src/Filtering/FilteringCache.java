package src.Filtering;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.jetbrains.annotations.NotNull;
import src.DormRoom.IDormRoom;

/**
 * The {@code FilteringCache} class provides a caching layer for dorm room filtering operations.
 * It associates a set of filtering criteria with the resulting set of filtered {@link IDormRoom}
 * instances, allowing repeated queries with the same criteria to be served from the cache rather
 * than recomputing the results every time.
 *
 * <p>This class uses a Guava {@link LoadingCache}, which automatically loads data when an entry
 * is missing. The loading function is provided by an {@link IDormFilter} instance that executes
 * the filtering logic. The cached results can significantly reduce computation costs for
 * frequently repeated filters, improving overall performance.</p>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * IDormFilter filter = ...; // An implementation of the filter interface
 * FilteringCache cache = new FilteringCache(filter);
 * FilteringCriteria criteria = ...; // Some criteria
 * List<DormRoom> rooms = cache.getFilteredDormList(criteria); // Cached or computed
 * }</pre>
 */
public class FilteringCache {

  /**
   * A loading cache mapping {@link FilteringCriteria} to {@link Set} of {@link IDormRoom}.
   * Each time a new FilteringCriteria key is requested, the cache uses the {@link IDormFilter}
   * to produce the corresponding filtered set of dorm rooms.
   */
  private final LoadingCache<FilteringCriteria, Set<IDormRoom>> cache;

  /**
   * Constructs a new {@code FilteringCache} using the provided {@link IDormFilter} for data loading.
   *
   * @param filter an {@link IDormFilter} implementation used to filter dorm rooms based on criteria
   * @throws NullPointerException if {@code filter} is null
   */
  public FilteringCache(IDormFilter filter) {
    if (filter == null) {
      throw new NullPointerException("IDormFilter cannot be null.");
    }
    this.cache = this.cacheBuilder(filter);
  }

  /**
   * Builds the {@link LoadingCache} instance that caches the association between
   * {@link FilteringCriteria} and the resulting set of filtered {@link IDormRoom}s.
   *
   * <p>This method is only intended to be called once by the constructor. The returned cache
   * uses the provided {@link IDormFilter} to load dorm room data on cache misses.</p>
   *
   * @param filter the filter used to load {@link IDormRoom} lists for given {@link FilteringCriteria}
   * @return a newly created {@link LoadingCache} instance
   */
  private LoadingCache<FilteringCriteria, Set<IDormRoom>> cacheBuilder(IDormFilter filter) {
    return CacheBuilder.newBuilder()
        .build(
            new CacheLoader<>() {
              @Override
              public @NotNull Set<IDormRoom> load(@NotNull FilteringCriteria filteringCriteria) {
                return filter.filterDormList(filteringCriteria);
              }
            }
        );
  }

  /**
   * Retrieves a set of {@link IDormRoom}s that match the specified {@link FilteringCriteria}.
   * If the criteria have been requested before, the cached result is returned. Otherwise,
   * the associated {@link IDormFilter} is used to compute and store the result in the cache.
   *
   * @param filteringCriteria the criteria specifying which dorm rooms to retrieve
   * @return a set of dorm rooms matching the provided criteria
   * @throws ExecutionException if the computation (filtering) threw an exception
   * @throws NullPointerException if {@code filteringCriteria} is null
   */
  public Set<IDormRoom> getFilteredDormList(FilteringCriteria filteringCriteria) throws ExecutionException {
    if (filteringCriteria == null) {
      throw new NullPointerException("FilteringCriteria cannot be null.");
    }
    return this.cache.get(filteringCriteria);
  }
}
