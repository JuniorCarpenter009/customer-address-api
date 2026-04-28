package com.oriontek.customer_api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootTest
class CustomerApiApplicationTests {

	@GetMapping("/api/test")
    public String test() {
        return "API funcionando correctamente";
    }

}
