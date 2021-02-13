package guru.springframework.sfgpetclinic.services.map.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;
    private Owner returnedOwner;

    @BeforeEach
    void setUp() {
        returnedOwner = Owner.builder().id(1L).build();
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(Owner.builder().id(2L).build());
        owners.add(Owner.builder().id(3L).build());

        when(ownerSDJpaService.findAll()).thenReturn(owners);
        Set<Owner> ownersReturned = ownerSDJpaService.findAll();
        assertEquals(2, ownersReturned.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnedOwner));
        Owner owner = ownerSDJpaService.findById(1L);
        assertNotNull(owner);
        assertEquals(1L, owner.getId());

        verify(ownerRepository).findById(anyLong());

    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(returnedOwner);
        Owner owner = ownerSDJpaService.save(returnedOwner);

        assertNotNull(owner);

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnedOwner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository, atMostOnce()).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerSDJpaService.findByLastName(anyString()))
                .thenReturn(Owner.builder().id(100L).lastName(LAST_NAME).build());
        Owner owner = ownerSDJpaService.findByLastName(LAST_NAME);

        assertEquals(100L, owner.getId());
        assertEquals(LAST_NAME, owner.getLastName());

        verify(ownerRepository).findByLastName(anyString());
    }
}