package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.weather.model.Book;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    Button button;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Single<Book> bookSingle = getSingleBook();

                bookSingle.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Book>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }

                            @Override
                            public void onSuccess(Book book) {
                                Log.d("Main", book.getBookname() + " " + book.getId());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

                /*

                Single<String> stringSingle = getSingleString();

                stringSingle.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("Main", s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

                Observable<String> observable = getObservable();
                Observer<String> observer = getObserver();
                observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(observer);
                        */
            }
        });
    }

    private Single<Book> getSingleBook() {
        return Single.just(new Book("1", "The Richest man in Babylon"));
    }

    private Single<String> getSingleString() {
        return Single.just("sarim");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d("Main", s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observable<String> getObservable() {
        return Observable.just("one", "two", "three", "four", "five", "six", "seven");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
