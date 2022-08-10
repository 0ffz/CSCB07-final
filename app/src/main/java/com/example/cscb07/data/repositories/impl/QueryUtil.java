package com.example.cscb07.data.repositories.impl;

import com.example.cscb07.data.models.EventModel;
import com.example.cscb07.data.results.EventId;
import com.example.cscb07.data.results.WithId;
import com.example.cscb07.data.util.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class QueryUtil {

    public static void getEvents(Query eventsListQuery, Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        eventsListQuery.get().addOnSuccessListener(snapshot -> {
            callback.accept(Try.success(Stream
                    .ofAll(snapshot.getChildren())
                    .map(DataSnapshot::getKey)
                    .map(key -> WithId.of(new EventId(key), snapshot.child(key).getValue(EventModel.class)))
//                    .filter(withId -> withId.model != null));
                    .toJavaList()));
        }).addOnFailureListener(e -> callback.accept(Try.failure(e)));
    }

    public static void getEventsForKeys(Query keysQuery, Query eventModels, Consumer<Try<List<WithId<EventId, EventModel>>>> callback) {
        readEventKeys(keysQuery, (snapshot, eventIds) -> {
            eventModels.get().addOnSuccessListener(eventsSnapshot -> callback.accept(Try.success(
                    eventIds.map(key -> WithId.of(key, eventsSnapshot.child(key.key).getValue(EventModel.class)))
                            .filter(withId -> withId.model != null)
                            .toJavaList())
            )).addOnFailureListener(e -> callback.accept(Try.failure(e)));
        });
    }

    public static void readEventKeys(Query ref, BiConsumer<DataSnapshot, Stream<EventId>> callback) {
        ref.get().addOnSuccessListener(snapshot -> callback.accept(snapshot, Stream
                .ofAll(snapshot.getChildren())
                .map(DataSnapshot::getKey)
                .map(EventId::new)
        ));
    }
}
