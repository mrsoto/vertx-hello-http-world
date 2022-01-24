package me.vertx.server.config;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import me.vertx.server.service.HelloService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RouteController {

    private final HelloService helloService;

    @Inject
    public RouteController(HelloService helloService) {
        this.helloService = helloService;
    }

    void registerRoutes(Router router) {
        router.route(HttpMethod.GET, "/ping").handler(this::ping);

        router.route().handler(ctx -> {
            ctx.put("x-requestId", String.valueOf(Math.random()));
            ctx.next();
        });

        router.route(HttpMethod.GET, "/hello/:name")
                .blockingHandler((ctx) -> {
                    String name = ctx.pathParam("name");
                    HttpServerResponse res = ctx.response();
                    res.putHeader("content-type", "text/plain");
                    res.putHeader("x-requestId", Optional.ofNullable(ctx.get("x-requestId"))
                            .map(String::valueOf)
                            .orElse("Unknown"));
                    res.end(helloService.sayHelloTo(name));
                });
    }

    private void ping(RoutingContext routingContext) {
        routingContext.response().end("Pong");
    }
}
