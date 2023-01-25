package com.test.drone.medication;

import com.test.drone.base.ICrudTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedicationTest implements ICrudTest {

    @Autowired
    private MedicationImpl medication;

    @Test
    @Override
    public void findById() throws Exception {
        medication.findById();
    }

    @Test
    @Override
    public void findAllPaging() throws Exception {
        medication.findAllPaging();
    }

    @Test
    @Override
    public void save() throws Exception {
        medication.save();
    }

    @Test
    @Override
    public void update() throws Exception {
        medication.update();
    }
}
