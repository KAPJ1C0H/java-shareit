package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    void deleteById(long id);

    List<Item> findByOwnerId(long ownerId);

    @Query("SELECT i FROM Item i WHERE i.request.id = :requestId")
    List<Item> findAllByRequest(@Param("requestId") long requestId);

    List<Item> findAllByRequestIdIn(List<Long> requestIds);

    @Query("from Item as it " +
            "where " +
            "it.available = true " +
            "and " +
            "(upper(it.name) like upper(concat('%',?1,'%')) or upper(it.description) like upper(concat('%',?1,'%')))")
    List<Item> searchAvailableByText(String text);
}
