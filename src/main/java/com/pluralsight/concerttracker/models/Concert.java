package com.pluralsight.concerttracker.models;

import jakarta.persistence.*;

@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int concertYear;
    private double ticketPrice;
    private int ticketsSold;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promoter_id", nullable = false)
    private Promoter promoter;

    public Concert() {
    }

    public Concert(int concertYear, double ticketPrice, int ticketSold, Artist artist, Venue venue, Promoter promoter) {
        this.concertYear = concertYear;
        this.ticketPrice = ticketPrice;
        this.ticketsSold = ticketSold;
        this.artist = artist;
        this.venue = venue;
        this.promoter = promoter;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConcertYear() {
        return concertYear;
    }

    public void setConcertYear(int concertYear) {
        this.concertYear = concertYear;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Promoter getPromoter() {
        return promoter;
    }

    public void setPromoter(Promoter promoter) {
        this.promoter = promoter;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        --------------------------------------------------
                        Concert ID: %d
                        Artist: %s
                        Venue: %s
                        Promoter: %s
                        Year: %d
                        Ticket Price: $%.2f
                        Tickets Sold: %,d
                        --------------------------------------------------
                        """,
                id,
                artist.getName(),
                venue.getName(),
                promoter.getName(),
                concertYear,
                ticketPrice,
                ticketsSold
        );
    }
}