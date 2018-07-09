package org.piaohao.blade.cloud.registry.client;

import com.blade.Blade;

public class Bootstrap {

    public static void main(String[] args) {
        Blade.me()
                .get("/", (request, response) -> response.text("hello blade cloud!"))
                .start(Bootstrap.class, args);
    }
}
