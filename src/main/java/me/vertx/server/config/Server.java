package me.vertx.server.config;

import io.vertx.core.*;
import io.vertx.ext.web.Router;
import me.vertx.server.config.RouteController;

import javax.inject.Inject;
import javax.inject.Named;

public class Server extends AbstractVerticle {
    private final RouteController routeController;
    private final int port;

    @Inject
    public Server(RouteController routeController, @Named("serverPort") int port) {
        this.routeController = routeController;
        this.port = port;
    }

    /**
     * Start and complete after heavy Guice initialization.
     *
     * @param promise to complete.
     */
    @Override
    public void start(Promise<Void> promise) {
        Router router = Router.router(vertx);

        routeController.registerRoutes(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port)
                .onComplete(serverAsyncResult -> {
                    System.out.printf("HttpServer at port: %d\n", serverAsyncResult.result().actualPort());
                    promise.complete();
                });

    }

}
