package br.com.meli.desafiospring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class DesafioSpringApplicationTests {

    @Test
    void contextLoads() {
        LocalDate ldata= LocalDate.of(1981,2,6);
        System.out.println(ldata.toString());
    }

}
