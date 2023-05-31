import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Load extends Simulation {
    String baseUrl = "http://localhost:8080";

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(baseUrl)
            .contentTypeHeader("application/x-www-form-urlencoded")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    public Load() {
        setUp(
                scn1().injectOpen(
                        constantUsersPerSec(10).during(30)
                ),
                scn2().injectOpen(
                        constantUsersPerSec(10).during(30)
                ),
                scn3().injectOpen(
                        constantUsersPerSec(10).during(30)
                ),
                scn4().injectOpen(
                        constantUsersPerSec(10).during(30)
                )
        ).protocols(httpProtocol);
    }

    private ScenarioBuilder scn1() {
        return scenario("Scenario 1")
                .exec(http("Login")
                        .post("/login")
                        .formParam("username", "test123")
                        .formParam("password", "123")
                        .check(status().is(200)))
                .exec(http("View Post 3")
                        .get("/posts/3")
                        .check(status().is(200)))
                .exec(http("Logout")
                        .get("/logout")
                        .check(status().is(200)));
    }

    private ScenarioBuilder scn2() {
        return scenario("Scenario 2")
                .exec(http("Login")
                        .post("/login")
                        .formParam("username", "test123")
                        .formParam("password", "123")
                        .check(status().is(200)))
                .exec(http("View Post 3")
                        .get("/posts/3")
                        .check(status().is(200)))
                .exec(http("Go to dashboard")
                        .get("/dashboard")
                        .check(status().is(200)))
                .exec(http("View Post 2")
                        .get("/posts/2")
                        .check(status().is(200)))
                .exec(http("Leave Comment in post 2")
                        .post("/posts/2/comment")
                        .formParam("comment", "This is a test comment")
                        .check(status().is(200)))
                .exec(http("Logout")
                        .get("/logout")
                        .check(status().is(200)));
    }

    private ScenarioBuilder scn3() {
        return scenario("Scenario 3")
                .exec(http("Login")
                        .post("/login")
                        .formParam("username", "test123")
                        .formParam("password", "123")
                        .check(status().is(200)))
                .exec(http("Open Add new post page")
                        .get("/posts/add")
                        .check(status().is(200)))
                .exec(http("Create Post")
                        .post("/posts/add")
                        .formParam("title", "My New Post")
                        .formParam("description", "Some text")
                        .formParam("body", "Some body")
                        .check(status().is(200)))
                .exec(http("Logout")
                        .get("/logout")
                        .check(status().is(200)));
    }

    private ScenarioBuilder scn4() {
        return scenario("Scenario 4")
                .exec(http("Login")
                        .post("/login")
                        .formParam("username", "test123")
                        .formParam("password", "123")
                        .check(status().is(200)))
                .exec(http("View profile")
                        .get("/profile")
                        .check(status().is(200)))
                .exec(http("Update Profile")
                        .post("/profile")
                        .formParam("firstName", "John")
                        .formParam("lastName", "Doe")
                        .formParam("email", "john.doe@example.com")
                        .formParam("age", "21")
                        .formParam("address", "Some address")
                        .formParam("website", "https://example.com")
                        .formParam("gender", "Male")
                        .check(status().is(200)))
                .exec(http("Logout")
                        .get("/logout")
                        .check(status().is(200)));
    }
}