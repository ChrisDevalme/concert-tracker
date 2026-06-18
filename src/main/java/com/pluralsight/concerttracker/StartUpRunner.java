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

        while (running) {
            displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    concertsMenu(scanner);
                    break;
                case "2":
                    searchConcertsMenu(scanner);
                    break;
                case "3":
                    artistsMenu(scanner);
                    break;
                case "4":
                    venuesMenu(scanner);
                    break;
                case "5":
                    promotersMenu(scanner);
                    break;
                case "6":
                    reportsMenu(scanner);
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
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

    public void concertsMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Concerts ====");
            System.out.println("1) List all concerts");
            System.out.println("2) View concert by id");
            System.out.println("3) Add concert");
            System.out.println("4) Update ticket price");
            System.out.println("5) Update tickets sold");
            System.out.println("6) Delete concert");
            System.out.println("0) Back");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllConcerts();
                    break;
                case "2":
                    viewConcertById(scanner);
                    break;
                case "3":
                    addConcert(scanner);
                    break;
                case "4":
                    updateConcertPrice(scanner);
                    break;
                case "5":
                    updateTicketsSold(scanner);
                    break;
                case "6":
                    deleteConcert(scanner);
                    break;
                case "0":
                    System.out.println("Leaving Concert Menu...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public void viewConcertById(Scanner scanner) {
        try {
            System.out.print("Enter concert id: ");
            Long id = Long.parseLong(scanner.nextLine());

            Concert concert = concertService.getConcertById(id);

            if (concert == null) {
                System.out.println("Concert not found.");
                return;
            }

            System.out.println("\n=== Concert Details ===");
            System.out.println(concert);

        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }
    public void addConcert(Scanner scanner) {
        try {
            System.out.println("\nChoose an artist:");
            artistService.getAllArtists().forEach(System.out::println);

            System.out.print("Artist id: ");
            Long artistId = Long.parseLong(scanner.nextLine());
            Artist artist = artistService.getArtistById(artistId);

            if (artist == null) {
                System.out.println("Artist not found.");
                return;
            }

            System.out.println("\nChoose a venue:");
            venueService.getAllVenues().forEach(System.out::println);

            System.out.print("Venue id: ");
            Long venueId = Long.parseLong(scanner.nextLine());
            Venue venue = venueService.getVenueById(venueId);

            if (venue == null) {
                System.out.println("Venue not found.");
                return;
            }

            System.out.println("\nChoose a promoter:");
            promoterService.getAllPromoters().forEach(System.out::println);

            System.out.print("Promoter id: ");
            Long promoterId = Long.parseLong(scanner.nextLine());
            Promoter promoter = promoterService.getPromoterById(promoterId);

            if (promoter == null) {
                System.out.println("Promoter not found.");
                return;
            }

            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Ticket price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Tickets sold: ");
            int ticketsSold = Integer.parseInt(scanner.nextLine());

            Concert concert = new Concert(year, price, ticketsSold, artist, venue, promoter);
            Concert savedConcert = concertService.addConcert(concert);

            System.out.println("Concert added successfully.");
            System.out.println(savedConcert);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateConcertPrice(Scanner scanner) {
        try {
            displayAllConcerts();

            System.out.print("Enter concert id: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Enter new ticket price: ");
            double newPrice = Double.parseDouble(scanner.nextLine());

            Concert updatedConcert = concertService.updateTicketPrice(id, newPrice);

            if (updatedConcert == null) {
                System.out.println("Concert not found.");
                return;
            }

            System.out.println("Ticket price updated successfully.");
            System.out.println(updatedConcert);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateTicketsSold(Scanner scanner) {
        try {
            displayAllConcerts();

            System.out.print("Enter concert id: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Enter new tickets sold: ");
            int ticketsSold = Integer.parseInt(scanner.nextLine());

            Concert updatedConcert = concertService.updateTicketsSold(id, ticketsSold);

            if (updatedConcert == null) {
                System.out.println("Concert not found.");
                return;
            }

            System.out.println("Tickets sold updated successfully.");
            System.out.println(updatedConcert);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteConcert(Scanner scanner) {
        try {
            displayAllConcerts();

            System.out.print("Enter concert id to delete: ");
            Long id = Long.parseLong(scanner.nextLine());

            boolean deleted = concertService.deleteConcert(id);

            if (deleted) {
                System.out.println("Concert deleted successfully.");
            } else {
                System.out.println("Concert not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    public void searchConcertsMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Search Concerts ====");
            System.out.println("1) Search by year");
            System.out.println("2) Search by artist");
            System.out.println("3) Search by venue");
            System.out.println("4) Search by city");
            System.out.println("5) Search by maximum price");
            System.out.println("6) Search by price range");
            System.out.println("7) Advanced search");
            System.out.println("0) Back");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchByYear(scanner);
                    break;
                case "2":
                    searchByArtist(scanner);
                    break;
                case "3":
                    searchByVenue(scanner);
                    break;
                case "4":
                    searchByCity(scanner);
                    break;
                case "5":
                    searchByMaxPrice(scanner);
                    break;
                case "6":
                    searchByPriceRange(scanner);
                    break;
                case "7":
                    advancedConcertSearch(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public void searchByYear(Scanner scanner) {
        try {
            System.out.print("Enter year: ");
            int year = Integer.parseInt(scanner.nextLine());

            printConcertResults(concertService.findConcertsByYear(year));

        } catch (NumberFormatException e) {
            System.out.println("Invalid year.");
        }
    }
    public void searchByArtist(Scanner scanner) {
        System.out.print("Enter artist name: ");
        String artistName = scanner.nextLine();

        printConcertResults(concertService.findConcertsByArtist(artistName));
    }
    public void searchByVenue(Scanner scanner) {
        System.out.print("Enter venue name: ");
        String venueName = scanner.nextLine();

        printConcertResults(concertService.findConcertsByVenue(venueName));
    }
    public void searchByCity(Scanner scanner) {
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        printConcertResults(concertService.findConcertsByCity(city));
    }
    public void searchByMaxPrice(Scanner scanner) {
        try {
            System.out.print("Enter maximum ticket price: ");
            double maxPrice = Double.parseDouble(scanner.nextLine());

            printConcertResults(concertService.findConcertsByMaxPrice(maxPrice));

        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
        }
    }
    public void searchByPriceRange(Scanner scanner) {
        try {
            System.out.print("Enter minimum price: ");
            double minPrice = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter maximum price: ");
            double maxPrice = Double.parseDouble(scanner.nextLine());

            printConcertResults(concertService.findConcertsByPriceRange(minPrice, maxPrice));

        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
        }
    }
    public void advancedConcertSearch(Scanner scanner) {
        try {
            System.out.print("Enter maximum ticket price: ");
            double maxPrice = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter earliest year: ");
            int earliestYear = Integer.parseInt(scanner.nextLine());

            printConcertResults(concertService.advancedSearch(maxPrice, earliestYear));

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    public void printConcertResults(List<Concert> concerts) {
        if (concerts.isEmpty()) {
            System.out.println("No concerts found.");
            return;
        }

        concerts.forEach(System.out::println);
    }

    public void artistsMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Artists ====");
            System.out.println("1) List artists");
            System.out.println("2) Add artist");
            System.out.println("3) Find artists by genre");
            System.out.println("4) Find artists by name");
            System.out.println("5) Update artist genre");
            System.out.println("6) Delete artist");
            System.out.println("0) Back");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listArtists();
                    break;
                case "2":
                    addArtist(scanner);
                    break;
                case "3":
                    findArtistsByGenre(scanner);
                    break;
                case "4":
                    findArtistsByName(scanner);
                    break;
                case "5":
                    updateArtistGenre(scanner);
                    break;
                case "6":
                    deleteArtist(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public void listArtists() {
        List<Artist> artists = artistService.getAllArtists();

        if (artists.isEmpty()) {
            System.out.println("No artists found.");
            return;
        }

        System.out.println("\n=== Artists ===");
        artists.forEach(System.out::println);
    }
    public void addArtist(Scanner scanner) {
        System.out.print("Artist name: ");
        String name = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        Artist savedArtist = artistService.addArtist(new Artist(name, genre));

        System.out.println("Artist added successfully.");
        System.out.println(savedArtist);
    }
    public void findArtistsByGenre(Scanner scanner) {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        List<Artist> artists = artistService.findArtistsByGenre(genre);

        if (artists.isEmpty()) {
            System.out.println("No artists found with that genre.");
            return;
        }

        artists.forEach(System.out::println);
    }
    public void findArtistsByName(Scanner scanner) {
        System.out.print("Enter artist name search: ");
        String name = scanner.nextLine();

        List<Artist> artists = artistService.findArtistsByName(name);

        if (artists.isEmpty()) {
            System.out.println("No artists found with that name.");
            return;
        }

        artists.forEach(System.out::println);
    }
    public void updateArtistGenre(Scanner scanner) {
        try {
            listArtists();

            System.out.print("Enter artist id: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Enter new genre: ");
            String genre = scanner.nextLine();

            Artist updatedArtist = artistService.updateGenre(id, genre);

            if (updatedArtist == null) {
                System.out.println("Artist not found.");
                return;
            }

            System.out.println("Artist genre updated successfully.");
            System.out.println(updatedArtist);

        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }
    public void deleteArtist(Scanner scanner) {
        try {
            listArtists();

            System.out.print("Enter artist id to delete: ");
            Long id = Long.parseLong(scanner.nextLine());

            boolean deleted = artistService.deleteArtist(id);

            if (deleted) {
                System.out.println("Artist deleted successfully.");
            } else {
                System.out.println("Artist not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    public void venuesMenu(Scanner scanner) {
        boolean running = true;

    while (running) {
        System.out.println("\n==== Venues ====");
        System.out.println("1) List venues");
        System.out.println("2) Add venue");
        System.out.println("3) Find venues by city");
        System.out.println("4) Find venues by name");
        System.out.println("5) Find venues by minimum capacity");
        System.out.println("6) Update venue capacity");
        System.out.println("7) Delete venue");
        System.out.println("0) Back");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                listVenues();
                break;
            case "2":
                addVenue(scanner);
                break;
            case "3":
                findVenuesByCity(scanner);
                break;
            case "4":
                findVenuesByName(scanner);
                break;
            case "5":
                findVenuesByMinimumCapacity(scanner);
                break;
            case "6":
                updateVenueCapacity(scanner);
                break;
            case "7":
                deleteVenue(scanner);
                break;
            case "0":
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }
    }
    public void listVenues() {
        List<Venue> venues = venueService.getAllVenues();

        if (venues.isEmpty()) {
            System.out.println("No venues found.");
            return;
        }

        System.out.println("\n=== Venues ===");
        venues.forEach(System.out::println);
    }
    public void addVenue(Scanner scanner) {
        try {
            System.out.print("Venue name: ");
            String name = scanner.nextLine();

            System.out.print("City: ");
            String city = scanner.nextLine();

            System.out.print("Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            Venue venue = new Venue(name, city, capacity);
            Venue savedVenue = venueService.addVenue(venue);

            System.out.println("Venue added successfully.");
            System.out.println(savedVenue);

        } catch (NumberFormatException e) {
            System.out.println("Invalid capacity.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void findVenuesByCity(Scanner scanner) {
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        List<Venue> venues = venueService.findVenuesByCity(city);

        if (venues.isEmpty()) {
            System.out.println("No venues found in that city.");
            return;
        }

        venues.forEach(System.out::println);
    }
    public void findVenuesByName(Scanner scanner) {
        System.out.print("Enter venue name search: ");
        String name = scanner.nextLine();

        List<Venue> venues = venueService.findVenuesByName(name);

        if (venues.isEmpty()) {
            System.out.println("No venues found with that name.");
            return;
        }

        venues.forEach(System.out::println);
    }
    public void findVenuesByMinimumCapacity(Scanner scanner) {
        try {
            System.out.print("Enter minimum capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            List<Venue> venues = venueService.findVenuesByMinimumCapacity(capacity);

            if (venues.isEmpty()) {
                System.out.println("No venues found with that capacity.");
                return;
            }

            venues.forEach(System.out::println);

        } catch (NumberFormatException e) {
            System.out.println("Invalid capacity.");
        }
    }
    public void updateVenueCapacity(Scanner scanner) {
        try {
            listVenues();

            System.out.print("Enter venue id: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Enter new capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            Venue updatedVenue = venueService.updateCapacity(id, capacity);

            if (updatedVenue == null) {
                System.out.println("Venue not found.");
                return;
            }

            System.out.println("Venue capacity updated successfully.");
            System.out.println(updatedVenue);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteVenue(Scanner scanner) {
        try {
            listVenues();

            System.out.print("Enter venue id to delete: ");
            Long id = Long.parseLong(scanner.nextLine());

            boolean deleted = venueService.deleteVenue(id);

            if (deleted) {
                System.out.println("Venue deleted successfully.");
            } else {
                System.out.println("Venue not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    public void promotersMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Promoters ====");
            System.out.println("1) List promoters");
            System.out.println("2) Add promoter");
            System.out.println("3) Find promoters by name");
            System.out.println("4) Delete promoter");
            System.out.println("0) Back");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listPromoters();
                    break;
                case "2":
                    addPromoter(scanner);
                    break;
                case "3":
                    findPromotersByName(scanner);
                    break;
                case "4":
                    deletePromoter(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public void listPromoters() {
        List<Promoter> promoters = promoterService.getAllPromoters();

        if (promoters.isEmpty()) {
            System.out.println("No promoters found.");
            return;
        }

        System.out.println("\n=== Promoters ===");
        promoters.forEach(System.out::println);
    }
    public void addPromoter(Scanner scanner) {
        System.out.print("Promoter name: ");
        String name = scanner.nextLine();

        Promoter savedPromoter = promoterService.addPromoter(new Promoter(name));

        System.out.println("Promoter added successfully.");
        System.out.println(savedPromoter);
    }
    public void findPromotersByName(Scanner scanner) {
        System.out.print("Enter promoter name search: ");
        String name = scanner.nextLine();

        List<Promoter> promoters = promoterService.findPromotersByName(name);

        if (promoters.isEmpty()) {
            System.out.println("No promoters found with that name.");
            return;
        }

        promoters.forEach(System.out::println);
    }
    public void deletePromoter(Scanner scanner) {
        try {
            listPromoters();

            System.out.print("Enter promoter id to delete: ");
            Long id = Long.parseLong(scanner.nextLine());

            boolean deleted = promoterService.deletePromoter(id);

            if (deleted) {
                System.out.println("Promoter deleted successfully.");
            } else {
                System.out.println("Promoter not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    public void reportsMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Reports ====");
            System.out.println("1) Revenue per venue");
            System.out.println("2) Busiest venue and artist");
            System.out.println("3) Average ticket price by year");
            System.out.println("4) Capacity report");
            System.out.println("0) Back");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    revenuePerVenueReport();
                    break;
                case "2":
                    busiestVenueAndArtistReport();
                    break;
                case "3":
                    averagePriceByYearReport();
                    break;
                case "4":
                    capacityReport();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public void revenuePerVenueReport() {
        List<Object[]> results = concertService.revenuePerVenue();

        if (results.isEmpty()) {
            System.out.println("No revenue data found.");
            return;
        }

        System.out.println("\n=== Revenue Per Venue ===");

        for (Object[] row : results) {
            String venueName = (String) row[0];
            Double revenue = (Double) row[1];

            System.out.printf("%s | Revenue: $%.2f%n", venueName, revenue);
        }
    }
    public void busiestVenueAndArtistReport() {
        List<Object[]> venues = concertService.busiestVenues();
        List<Object[]> artists = concertService.busiestArtists();

        if (venues.isEmpty() || artists.isEmpty()) {
            System.out.println("No concert data found.");
            return;
        }

        Object[] busiestVenue = venues.get(0);
        Object[] busiestArtist = artists.get(0);

        System.out.println("\n=== Busiest Venue and Artist ===");
        System.out.println("Busiest Venue: " + busiestVenue[0] + " | Concerts: " + busiestVenue[1]);
        System.out.println("Busiest Artist: " + busiestArtist[0] + " | Concerts: " + busiestArtist[1]);
    }
    public void averagePriceByYearReport() {
        List<Object[]> results = concertService.averagePriceByYear();

        if (results.isEmpty()) {
            System.out.println("No average price data found.");
            return;
        }

        System.out.println("\n=== Average Ticket Price By Year ===");

        for (Object[] row : results) {
            Integer year = (Integer) row[0];
            Double averagePrice = (Double) row[1];

            System.out.printf("%d | Average Price: $%.2f%n", year, averagePrice);
        }
    }
    public void capacityReport() {
        List<Concert> concerts = concertService.getAllConcerts();

        if (concerts.isEmpty()) {
            System.out.println("No concerts found.");
            return;
        }

        System.out.println("\n=== Capacity Report ===");

        for (Concert concert : concerts) {
            double percentFull =
                    (concert.getTicketsSold() * 100.0) / concert.getVenue().getCapacity();

            String status = percentFull >= 100 ? "SOLD OUT" : "";

            System.out.printf(
                    "%s at %s | Tickets Sold: %,d / %,d | %.2f%% full %s%n",
                    concert.getArtist().getName(),
                    concert.getVenue().getName(),
                    concert.getTicketsSold(),
                    concert.getVenue().getCapacity(),
                    percentFull,
                    status
            );
        }
    }

    public void displayAllConcerts() {
        List<Concert> concerts = concertService.getAllConcerts();
        if (concerts.isEmpty()) {
            System.out.println("No concerts found.");
            return;
        }
        System.out.println("\n=== All Concerts ===");
        concerts.forEach(System.out::println);
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
