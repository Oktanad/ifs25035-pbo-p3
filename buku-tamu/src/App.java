import adapter.presenter.GuestPresenter;
import adapter.repository.GuestRepository;
import domain.repository.IGuestRepository;
import framework.view.GuestView;
import usecase.GuestUseCase;

/**
 * Titik masuk aplikasi (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        IGuestRepository guestRepository = new GuestRepository();
        GuestUseCase guestUseCase = new GuestUseCase(guestRepository);
        GuestPresenter guestPresenter = new GuestPresenter();
        GuestView guestView = new GuestView(guestUseCase, guestPresenter);

        guestView.show();
    }
}
