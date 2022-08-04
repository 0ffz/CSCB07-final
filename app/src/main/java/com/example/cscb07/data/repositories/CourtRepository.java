package com.example.cscb07.data.repositories;

import com.example.cscb07.data.RepositoryCallback;
import com.example.cscb07.data.Results.CourtResult;

import java.util.List;

public interface CourtRepository {
    void addCourt(String name, List<String> sports, RepositoryCallback<CourtResult> callback);
}
