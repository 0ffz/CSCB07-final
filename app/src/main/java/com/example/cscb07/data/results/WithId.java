package com.example.cscb07.data.results;

public class WithId<I, M> {
    public final M model;
    public final I id;

    public WithId(I id, M model) {
        this.id = id;
        this.model = model;
    }

}
