package me.vertx.server;

import com.google.inject.Guice;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import me.vertx.server.config.ApplicationModule;
import me.vertx.server.config.Server;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public final class Main {

    private final Vertx vertx;
    private final Server server;
    private final DeploymentOptions deploymentOptions;

    @Inject
    public Main(Vertx vertx, Server server, DeploymentOptions deploymentOptions) {
        this.vertx = vertx;
        this.server = server;
        this.deploymentOptions = deploymentOptions;
    }

    public static void main(String... argv) {

        var injector = Guice.createInjector(new ApplicationModule());
        var bootstrap = injector.getInstance(Main.class);
        bootstrap.start();
    }

    void start() {
        Clock clock = Clock.systemUTC();
        Instant start = clock.instant();

        server.init(vertx, vertx.getOrCreateContext());

        vertx.deployVerticle(server, deploymentOptions);

        Instant started = clock.instant();

        System.out.printf("Vertex running\nStarted in: %dms %dns\n",
                Duration.between(start, started).toMillis(),
                Duration.between(start, started).toNanosPart()
        );
    }

}
