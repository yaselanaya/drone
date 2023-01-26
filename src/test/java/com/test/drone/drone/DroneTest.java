package com.test.drone.drone;

import com.test.drone.base.ICrudTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DroneTest implements ICrudTest {

    @Autowired
    private DroneImpl drone;

    @Test
    @Override
    public void findById() throws Exception {
        drone.findById();
    }

    @Test
    @Override
    public void findAllPaging() throws Exception {
        drone.findAllPaging();
    }

    @Test
    @Override
    public void save() throws Exception {
        drone.save();
    }

    @Test
    @Override
    public void update() throws Exception {
        drone.update();
    }
}
