package mynotes.mynotes.domain;

public interface Callback<T> {

    void onResult(T value);
}
