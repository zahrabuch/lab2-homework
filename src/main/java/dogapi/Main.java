package dogapi;

import java.util.List;

public class Main {

    public static void main(String[] args) throws BreedFetcher.BreedNotFoundException {
        String breed = "hound";
        BreedFetcher breedFetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());
        int result = getNumberOfSubBreeds(breed, breedFetcher);
        System.out.println(breed + " has " + result + " sub breeds");

        breed = "cat";
        result = getNumberOfSubBreeds(breed, breedFetcher);
        System.out.println(breed + " has " + result + " sub breeds");
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * @param breed the name of the dog breed
     * @param breedFetcher the breedFetcher to use
     * @return the number of sub breeds. Zero should be returned if there are no sub breeds
     * returned by the fetcher
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) throws BreedFetcher.BreedNotFoundException {

        try{
            List<String> breedsList = breedFetcher.getSubBreeds(breed);
            return breedsList.size();
        }catch(BreedFetcher.BreedNotFoundException e){

            return -1;
        }
    }
}