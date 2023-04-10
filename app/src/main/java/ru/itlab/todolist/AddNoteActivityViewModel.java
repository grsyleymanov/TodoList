package ru.itlab.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteActivityViewModel extends AndroidViewModel {
    private NotesDatabase notesDatabase;
    private MutableLiveData<Boolean> addNoteLiveData;
    private CompositeDisposable compositeDisposable;

    public AddNoteActivityViewModel(@NonNull Application application) {
        super(application);
        notesDatabase = NotesDatabase.getInstance(application);
        addNoteLiveData = new MutableLiveData<>(false);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public LiveData<Boolean> getAddNoteLiveData(){
        return addNoteLiveData;
    }

    public void add(Note note){
        Disposable disposable = notesDatabase
                .notesDAO()
                .add(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        addNoteLiveData.setValue(true);
                    }
                });

        compositeDisposable.add(disposable);
    }
}
