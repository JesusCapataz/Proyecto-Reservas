package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Parcel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class ParcelRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private ParcelRepository parcelRepository;

    @BeforeEach
    void setUp() { parcelRepository.deleteAll(); }

    @Test
    void shouldFindParcelByCode() {
        // Given
        Parcel parcel = Parcel.builder().code("PKG-999").senderName("Juan").build();
        parcelRepository.save(parcel);

        // When
        Optional<Parcel> found = parcelRepository.findByCode("PKG-999");

        // Then
        assertThat(found).isPresent();
    }
}