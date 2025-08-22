package demo.parkingsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ParkingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingSystemApplication.class, args);
    }

}
