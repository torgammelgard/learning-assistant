package api;

import model.CollectionRepository;

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
    name = "api_collection_servlet",
    urlPatterns = {"/api/collection"}
)
@RequestScoped
public class ApiCollectionServlet extends HttpServlet {

    @Inject
    private CollectionRepository collectionRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        List<String> collectionNames = collectionRepository.getCollectionNames();
        collectionNames.forEach(printWriter::write);
    }
}
