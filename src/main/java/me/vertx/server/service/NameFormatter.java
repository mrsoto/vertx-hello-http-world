package me.vertx.server.service;

import java.util.Locale;

public class NameFormatter {
    public String format(String name) {
        return name.toUpperCase(Locale.ROOT);
    }
}
