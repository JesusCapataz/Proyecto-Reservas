package com.proyecto.Proyecto.Reservas.domain.entities;

import com.proyecto.Proyecto.Reservas.domain.enums.ParcelStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parcels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String senderName;
    private String senderPhone;
    private String receiverName;
    private String receiverPhone;
    private BigDecimal price;
    private String proofPhotoUrl;
    private String deliveryOtp;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @ManyToOne
    @JoinColumn(name = "from_stop_id")
    private Stop fromStop;

    @ManyToOne
    @JoinColumn(name = "to_stop_id")
    private Stop toStop;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}