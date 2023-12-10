package com.marvinmielchen.lambo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LamboApplicationTests {

    @Test
    void contextLoads() {
        String source =
                """
                //this is a comment
                def crazy_function(x){
                    (number){
                        add number x
                    }
                    one
                };
                
                def add(x y){
                    x y
                };
                """;
        Lambo.run(source);
    }

}
