package com.test.drone.infraestructure.medication;

import com.test.drone.core.base.ServiceBaseImpl;
import com.test.drone.core.exception.DroneException;
import com.test.drone.core.file.ImageUploader;
import com.test.drone.core.filter.SearchCriteria;
import com.test.drone.core.filter.SearchOperation;
import com.test.drone.domain.drone.Drone;
import com.test.drone.domain.medication.IMedicationRepository;
import com.test.drone.domain.medication.IMedicationService;
import com.test.drone.domain.medication.Medication;
import com.test.drone.domain.medication.MedicationSpecification;
import com.test.drone.domain.medication.Medication_;
import com.test.drone.web.medication.dto.MedicationDTO;
import com.test.drone.web.medication.dto.MedicationResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MedicationServiceImpl extends ServiceBaseImpl<Medication, Integer, MedicationDTO, MedicationResource, MedicationFactory, IMedicationRepository>
        implements IMedicationService {

    private final ImageUploader uploader;

    protected MedicationServiceImpl(IMedicationRepository repository, MedicationFactory factory,
                                    MedicationValidationService validator, ImageUploader uploader) {
        super(repository, factory, validator);
        this.uploader = uploader;
    }

    @Override
    public Medication save(MedicationDTO dto, MultipartFile file) throws DroneException {
        Medication medication = factory.from(dto);

        validator.validateForCreate(medication);

        medication.setImage(uploader.updateImage(file, "", dto.getImage()));

        return repository.save(medication);
    }

    @Override
    public Medication update(MedicationDTO dto, MultipartFile file) throws DroneException {
        Medication medication = factory.from(dto);

        validator.validateForUpdate(medication);

        String image = medication.getImage();
        medication.setImage(uploader.updateImage(file, image, image));

        return repository.save(medication);
    }

    @Override
    public Page<Medication> getMedicationsToLoad(Set<Integer> medications) {
        List<SearchCriteria> criteria = new ArrayList<>() {{
            add(SearchCriteria.builder()
                    .field(Medication_.ID)
                    .operator(SearchOperation.IN)
                    .value(medications)
                    .build()
            );
        }};

        return findAll(new MedicationSpecification(criteria).toSpecification(), Pageable.unpaged());
    }

    @Override
    public void setDroneForMedications(Drone drone, Set<Integer> medications) {
        repository.setDroneForMedications(drone, medications);
    }
}
