package api;

import model.CardRepository;
import model.entities.Card;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(
    name = "api_servlet",
    urlPatterns = {"/api/cards"}
)
@RequestScoped
public class ApiServlet extends HttpServlet {

    @Inject
    private CardRepository cardRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printer = resp.getWriter();
        List<Card> cards = cardRepository.getCards("countries"); // TODO
        cards.stream().map(Card::toString).map(s -> s.concat("\n")).forEach(printer::write);

    }
}
