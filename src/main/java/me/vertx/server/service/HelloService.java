package me.vertx.server.service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HelloService {
    private final NameFormatter nameFormatter;

    @Inject
    public HelloService(NameFormatter nameFormatter) {
        this.nameFormatter = nameFormatter;
    }

    public String sayHelloTo(String who) {
        return String.format("Hello %s!", nameFormatter.format(who));
    }
}
