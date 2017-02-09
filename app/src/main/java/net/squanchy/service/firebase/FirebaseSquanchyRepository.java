package net.squanchy.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import net.squanchy.service.firebase.model.FirebaseEvent;
import net.squanchy.service.firebase.model.FirebaseFloorPlan;
import net.squanchy.service.firebase.model.FirebaseInfoItem;
import net.squanchy.service.firebase.model.FirebaseLevel;
import net.squanchy.service.firebase.model.FirebaseLocation;
import net.squanchy.service.firebase.model.FirebasePoi;
import net.squanchy.service.firebase.model.FirebaseSettings;
import net.squanchy.service.firebase.model.FirebaseSpeaker;
import net.squanchy.service.firebase.model.FirebaseTrack;
import net.squanchy.service.firebase.model.FirebaseType;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.schedulers.Schedulers;

public final class FirebaseSquanchyRepository {

    private final DatabaseReference database;

    public FirebaseSquanchyRepository(DatabaseReference database) {
        this.database = database;
    }

    public Observable<FirebaseSettings.Holder> settings() {
        return observeChild("settings", FirebaseSettings.Holder.class);
    }

    public Observable<FirebaseFloorPlan.Holder> floorPlans() {
        return observeChild("floorPlans", FirebaseFloorPlan.Holder.class);
    }

    public Observable<FirebaseInfoItem.General> info() {
        return observeChild("info", FirebaseInfoItem.General.class);
    }

    public Observable<FirebaseLevel.Holder> levels() {
        return observeChild("levels", FirebaseLevel.Holder.class);
    }

    public Observable<FirebaseLocation.Holder> locations() {
        return observeChild("locations", FirebaseLocation.Holder.class);
    }

    public Observable<FirebasePoi.Holder> pois() {
        return observeChild("pois", FirebasePoi.Holder.class);
    }

    public Observable<FirebaseSpeaker.Holder> speakers() {
        return observeChild("speakers", FirebaseSpeaker.Holder.class);
    }

    public Observable<FirebaseTrack.Holder> tracks() {
        return observeChild("tracks", FirebaseTrack.Holder.class);
    }

    public Observable<FirebaseType.Holder> types() {
        return observeChild("types", FirebaseType.Holder.class);
    }

    public Observable<FirebaseEvent.Holder> socialEvents() {
        return observeChild("socialEvents", FirebaseEvent.Holder.class);
    }

    public Observable<FirebaseEvent.Holder> sessions() {
        return observeChild("sessions", FirebaseEvent.Holder.class);
    }

    public Observable<FirebaseEvent> event(int dayId, int eventId) {
        return Observable.create((ObservableEmitter<FirebaseEvent> e) -> {
            DatabaseReference day = database.child("sessions").child("days").child(String.valueOf(dayId));
            DatabaseReference event = day.child("events").child(String.valueOf(eventId));

            event.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseEvent value = dataSnapshot.getValue(FirebaseEvent.class);
                    e.onNext(value);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onError(databaseError.toException());
                }
            });
        }).observeOn(Schedulers.io());
    }

    private <T> Observable<T> observeChild(final String path, final Class<T> clazz) {
        return Observable.create((ObservableEmitter<T> e) -> {
            database.child(path).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    T value = dataSnapshot.getValue(clazz);
                    e.onNext(value);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onError(databaseError.toException());
                }
            });
        }).observeOn(Schedulers.io());
    }
}
