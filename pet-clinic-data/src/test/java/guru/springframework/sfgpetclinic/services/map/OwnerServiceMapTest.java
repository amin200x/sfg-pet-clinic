package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {
    OwnerServiceMap ownerServiceMap;
   public String smith = "Smith";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetServiceMap(), new PetTypeServiceMap());
        ownerServiceMap.save(Owner.builder().lastName(smith).id(100L).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(100L);
        assertNotEquals(null, owner);
        assertEquals(100L, owner.getId());
    }

    @Test
    void save() {
        Owner owner = Owner.builder().id(2L).build();
        Owner savedOwner = ownerServiceMap.save(owner);
        assertEquals(2L, savedOwner.getId());
    }
    @Test
    void saveNoId() {
        Owner owner = Owner.builder().build();
        Owner savedOwner = ownerServiceMap.save(owner);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(100L);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(100L));
        assertEquals(0, ownerServiceMap.findAll().size());
    }


    @Test
    void findByLastName() {
        Owner smithObj = ownerServiceMap.findByLastName(smith);
        assertEquals(100L, smithObj.getId());
    }
}