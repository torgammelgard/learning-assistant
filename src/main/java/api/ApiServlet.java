package api;

import com.mongodb.client.MongoCollection;
import model.DBSource;
import org.bson.Document;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(
    name = "api_servlet",
    urlPatterns = {"/api"},
    initParams = {@WebInitParam(name = "myParam", value = "foo_value")}
)
public class ApiServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //MongoCollection<Document> s = DBSource.getMongoDatabase().getCollection("countries");
        System.out.print("Fooooo");
        List<String> collectionNames = DBSource.getCollectionNames();

        try(PrintWriter out = resp.getWriter()) {
            collectionNames.forEach(out::write);
        }
        //resp.getWriter().write("The web init param is : " + myParam);

    }

    private String myParam = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.myParam = config.getInitParameter("myParam");
    }

}
