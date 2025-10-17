package dogapi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {

    private int callsMade = 0;
    private BreedFetcher breedFetcher;
    private Map<String, List<String>> cache = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.breedFetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {

        if (cache.containsKey(breed)) {
            return cache.get(breed);
        }
        try {
            cache.put(breed, breedFetcher.getSubBreeds(breed));
            callsMade++;
            return cache.get(breed);

        } catch(BreedNotFoundException e){
            callsMade++;
            throw e;
        }

    }

    public int getCallsMade() {
        return callsMade;
    }
}