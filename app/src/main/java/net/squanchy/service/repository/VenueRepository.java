package net.squanchy.service.repository;

import net.squanchy.service.firebase.FirebaseDbService;
import net.squanchy.venue.domain.view.Venue;

import io.reactivex.Observable;

public class VenueRepository {

    private final FirebaseDbService dbService;

    public VenueRepository(FirebaseDbService dbService) {
        this.dbService = dbService;
    }

    public Observable<Venue> venue() {
        return dbService.venueInfo()
                .map(firebaseVenue -> Venue.Companion.create(
                        firebaseVenue.getName(),
                        firebaseVenue.getAddress(),
                        firebaseVenue.getLat(),
                        firebaseVenue.getLon(),
                        firebaseVenue.getDescription(),
                        firebaseVenue.getMap_url(),
                        firebaseVenue.getTimezone()
                ));
    }
}
