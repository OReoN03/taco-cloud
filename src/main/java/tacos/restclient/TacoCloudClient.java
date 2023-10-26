package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.domain.Ingredient;
import tacos.domain.Taco;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {
    private RestTemplate rest;
    private Traverson traverson;

    public TacoCloudClient(RestTemplate rest, Traverson traverson) {
        this.rest = rest;
        this.traverson = traverson;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("https://localhost:8443/ingredients/{id}", Ingredient.class, ingredientId);
    }

    /*public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject("https://localhost:8443/ingredients/{id}", Ingredient.class, urlVariables);
    }*/

    /*public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("https://localhost:8443/ingredients/{id}")
                .build(urlVariables);
        return rest.getForObject(url, Ingredient.class);
    }*/

    /*public Ingredient getIngredientById(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                rest.getForEntity("https://localhost:8443/ingredients/{id}", Ingredient.class, ingredientId);
        log.info("Fetched time: {}", responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }*/

    public void updateIngredient(Ingredient ingredient) {
        rest.put("https://localhost:8443/ingredients/{id}", ingredient, ingredient.getId());
    }

    public void delete(Ingredient ingredient) {
        rest.delete("https://localhost:8443/ingredients/{id}", ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject("https://localhost:8443/ingredients/{id}", ingredient, Ingredient.class);
    }

    /*public URI createIngredient(Ingredient ingredient) {
        return rest.postForLocation("https://localhost:8443/ingredients/{id}", ingredient);
    }*/

    /*public Ingredient createIngredient(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                rest.postForEntity("https://localhost:8443/ingredients/{id}", ingredient, Ingredient.class);
        log.info("New source created at {}", responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }*/

    public Iterable<Ingredient> getAllIngredientsWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>(){};

        CollectionModel<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);
        Collection<Ingredient> ingredients = ingredientRes.getContent();
        return ingredients;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();

        return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
    }

    public Iterable<Taco> getRecentTacosWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Taco>> tacoType =
                new ParameterizedTypeReference<CollectionModel<Taco>>(){};

        CollectionModel<Taco> tacoRes =
                traverson
                        .follow("tacos")
                        .follow("recents")
                        .toObject(tacoType);
        Collection<Taco> tacos = tacoRes.getContent();
        // Alternatively, list the two paths in the same call to follow()
        /*
        CollectionModel<Taco> tacoRes =
                traverson
                        .follow("tacos", "recents")
                        .toObject(tacoType);
        */
        return tacos;
    }
}