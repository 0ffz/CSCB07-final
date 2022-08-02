package com.example.cscb07.data;

/**
 * This class is used by repositories to inform ViewModels that some delayed work has been complete.
 * <p>
 * ViewModels can then process whatever they need to and use postValue on some LiveData and let the UI observe those changes.
 */
@FunctionalInterface
public interface RepositoryCallback<T> {
    void onComplete(T result);
}
