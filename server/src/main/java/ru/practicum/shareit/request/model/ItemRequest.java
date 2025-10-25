package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Table(name = "requests")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @NotNull
    private String description;

    @Column(name = "create_date")
    private LocalDateTime created;
}
