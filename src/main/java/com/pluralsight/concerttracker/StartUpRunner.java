package com.pluralsight.concerttracker;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import com.pluralsight.concerttracker.service.ArtistService;
import com.pluralsight.concerttracker.service.ConcertService;
import com.pluralsight.concerttracker.service.PromoterService;
import com.pluralsight.concerttracker.service.VenueService;

@Component
public class StartUpRunner implements CommandLineRunner {

    private final ConcertService concertService;
    private final ArtistService artistService;
    private final VenueService venueService;
    private final PromoterService promoterService;

    @Autowired
    public StartUpRunner(ConcertService concertService, ArtistService artistService, VenueService venueService, PromoterService promoterService) {
        this.concertService = concertService;
        this.artistService = artistService;
        this.venueService = venueService;
        this.promoterService = promoterService;
    }

    @Override
    public void run(String... args) {
        seedData();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        displayMenu();
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                displayAllConcerts();
                break;
            case "2":
                System.out.println("Search concerts menu coming soon...");
                break;
            case "3":
                System.out.println("Artists menu coming soon...");
                break;
            case "4":
                System.out.println("Venues menu coming soon...");
                break;
            case "5":
                System.out.println("Promoters menu coming soon...");
                break;
            case "6":
                System.out.println("Reports menu coming soon...");
                break;
            case "0":
                running = false;
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    public void displayMenu() {
        System.out.println("\n==== Rolling Loud ====");
        System.out.println("1) Concerts");
        System.out.println("2) Search concerts");
        System.out.println("3) Artists");
        System.out.println("4) Venues");
        System.out.println("5) Promoters");
        System.out.println("6) Reports");
        System.out.println("0) Quit");
        System.out.print("Your Choice: ");
    }

    public void displayAllConcerts() {
        List<Concert> concerts = concertService.getAllConcerts();
        if (concerts.isEmpty()) {
            System.out.println("No concerts found.");
            return;
        }
        System.out.println("\n=== All Concerts ===");
        for (Concert concert : concerts) {
            System.out.println(
                    "ID: " + concert.getId() +
                            " | Artist: " + concert.getArtist().getName() +
                            " | Venue: " + concert.getVenue().getName() +
                            " | Year: " + concert.getConcertYear() +
                            " | Price: $" + concert.getTicketPrice() +
                            " | Tickets Sold: " + concert.getTicketSold()
            );
        }
    }


    public void seedData() {
        if (concertService.count() > 0) {
            return;
        }

        Artist drake = artistService.addArtist(new Artist("Drake", "Hip-Hop"));
        Artist sza = artistService.addArtist(new Artist("SZA", "R&B"));
        Artist kendrick = artistService.addArtist(new Artist("Kendrick Lamar", "Hip-Hop"));

        Venue msg = venueService.addVenue(new Venue("Madison Square Garden", "New York", 20000));
        Venue prudential = venueService.addVenue(new Venue("Prudential Center", "Newark", 16500));
        Venue barclays = venueService.addVenue(new Venue("Barclays Center", "Brooklyn", 19000));

        Promoter liveNation = promoterService.addPromoter(new Promoter("Live Nation"));
        Promoter aeg = promoterService.addPromoter(new Promoter("AEG Presents"));

        concertService.addConcert(new Concert(2026, 175.00, 18500, drake, msg, liveNation));
        concertService.addConcert(new Concert(2026, 140.00, 15000, sza, prudential, aeg));
        concertService.addConcert(new Concert(2025, 160.00, 16500, kendrick, barclays, liveNation));

        System.out.println("Seed data loaded successfully.");
    }

}
