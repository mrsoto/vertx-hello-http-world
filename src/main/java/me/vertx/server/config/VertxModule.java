package me.vertx.server.config;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

public class VertxModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(VertxOptions.class).toInstance(
                new VertxOptions()
                        .setMaxWorkerExecuteTime(20)
                        .setWarningExceptionTimeUnit(TimeUnit.SECONDS)
                        .setWorkerPoolSize(Runtime.getRuntime().availableProcessors() * 10)
        );

        bind(Key.get(Integer.class, Names.named("serverPort"))).toInstance(8080);

        bind(DeploymentOptions.class).toInstance(new DeploymentOptions());

    }

    @Provides
    @Singleton
    private Vertx vertxProvider(VertxOptions options) {
        return Vertx.vertx(options);
    }

}
