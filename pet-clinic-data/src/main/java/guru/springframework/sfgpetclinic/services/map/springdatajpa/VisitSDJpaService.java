package guru.springframework.sfgpetclinic.services.map.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitSDJpaService implements VisitService {
    private final VisitRepository visitRepository;
    private final PetRepository petRepository;

    public VisitSDJpaService(VisitRepository visitRepository, PetRepository petRepository) {
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Visit save(Visit visit) {
        Visit savedVisit = new Visit();
        try {
            visit.setPet(petRepository.findById(visit.getPet().getId()).get());
            if (visit.getPet() != null && visit.getPet().getOwner() != null) {
                if (visit.getId() == null) {
                    savedVisit = visitRepository.save(visit);
                } else {
                    Optional<Visit> optionalVisit = visitRepository.findById(visit.getId());
                    if (optionalVisit.isPresent()) {
                        Visit updateVisit = optionalVisit.get();
                        updateVisit.setDate(visit.getDate());
                        updateVisit.setDescription(visit.getDescription());
                        savedVisit = visitRepository.save(updateVisit);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return savedVisit;
    }

    @Override
    public void delete(Visit visit) {
        visitRepository.delete(visit);
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }
}
