package dogapi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {
        String dogAPIurl = String.format("https://dog.ceo/api/breed/%s/list", breed.toLowerCase());


        try{
            Request request = new Request.Builder()
                    .url(dogAPIurl)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            // 2. Parse the response
            //if (!response.isSuccessful()) {
              //  throw new BreedNotFoundException("API request failed with code: " + response.code());
            //}

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            // 3. Check for error status
            if (!json.getString("status").equals("success")) {
                throw new BreedNotFoundException("Breed not found: " + breed);
            }
            // 4. Extract sub-breeds
            JSONArray message = json.getJSONArray("message");
            List<String> subBreeds = new ArrayList<>();
            for (int i = 0; i < message.length(); i++) {
                subBreeds.add(message.getString(i));
            }

            return subBreeds;

        } catch (IOException e) {
            throw new BreedNotFoundException("Network error: " + e.getMessage());
            //} catch (Exception e) {
            //   throw new BreedNotFoundException("Unexpected error: " + e.getMessage());
            //}

            // TODO Task 1: Complete this method based on its provided documentation
            //      and the documentation for the dog.ceo API. You may find it helpful
            //      to refer to the examples of using OkHttpClient from the last lab,
            //      as well as the code for parsing JSON responses.

        }   // return statement included so that the starter code can compile and run.
    }
}