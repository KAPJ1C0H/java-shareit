package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.enums.BookStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "booker")
    private User booker;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;
    @NotNull
    @Column(name = "start_time")
    private LocalDateTime start;
    @NotNull
    @Column(name = "end_time")
    private LocalDateTime end;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
