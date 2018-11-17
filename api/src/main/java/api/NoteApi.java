package api;

import model.CardRepository;
import model.entities.Card;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Optional;
import java.util.function.Function;

@Path("api")
public class NoteApi {

    @Inject
    private CardRepository cardRepository;

    @GET
    @Produces("application/json")
    @Path("card/{id}")
    public String card(@PathParam("id") long id) {
        Optional<Card> card = cardRepository.getCard(id);
        return card.map(card2Json).orElse("bad bad"); // TODO return 404
    }

    private Function<Card, String> card2Json = card -> {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        card.getAnswerAlternatives().forEach(arrayBuilder::add);
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("id", card.getId())
            .add("question", card.getQuestion())
            .add("answers", arrayBuilder.build());
        JsonObject jsonObject = objectBuilder.build();
        String jsonString = null;
        try (Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(jsonObject);
            jsonString = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (jsonString != null) ? jsonString : "";
    };
}
