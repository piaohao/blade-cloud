package org.piaohao.blade.cloud.registry.center;

import com.blade.Blade;

public class Bootstrap {

    public static void main(String[] args) {
        Blade.me()
                .listen(8080)
                .get("/", (request, response) -> response.text("hello blade cloud!"))
                .start(Bootstrap.class, args);
    }
}
