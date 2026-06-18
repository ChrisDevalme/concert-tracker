package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByConcertYear(int year);

    List<Concert> findByTicketPriceLessThanEqual(double maxPrice);

    List<Concert> findByTicketPriceBetween(double minPrice, double maxPrice);

    @Query(" SELECT c FROM Concert c WHERE LOWER(c.artist.name) LIKE LOWER(CONCAT('%', :artistName, '%'))")
    List<Concert> findByArtistName(@Param("artistName") String artistName);

    @Query("SELECT c FROM Concert c WHERE LOWER(c.venue.name) LIKE LOWER(CONCAT('%', :venueName, '%'))")
    List<Concert> findByVenueName(@Param("venueName") String venueName);

    @Query(" SELECT c FROM Concert c WHERE LOWER(c.venue.city) = LOWER(:city)")
    List<Concert> findByCity(@Param("city") String city);

    @Query(" SELECT c FROM Concert c WHERE c.ticketPrice <= :maxPrice AND c.concertYear >= :earliestYear")
    List<Concert> advancedSearch(@Param("maxPrice") double maxPrice, @Param("earliestYear") int earliestYear);

}