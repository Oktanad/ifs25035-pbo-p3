package usecase;

import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ActivityUseCase {
    private static final List<String> VALID_DAYS = Arrays.asList(
            "senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu");

    private final IActivityRepository activityRepository;

    public ActivityUseCase(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity addActivity(String title, String day, String time) throws ValidationException {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Judul kegiatan tidak boleh kosong");
        }
        if (title.trim().length() < 2) {
            throw new ValidationException("Judul kegiatan minimal 2 karakter");
        }
        if (day == null || day.trim().isEmpty()) {
            throw new ValidationException("Hari kegiatan tidak boleh kosong");
        }
        if (!isValidDay(day)) {
            throw new ValidationException("Hari kegiatan tidak valid (harus Senin-Minggu)");
        }
        if (time == null || time.trim().isEmpty()) {
            throw new ValidationException("Waktu kegiatan tidak boleh kosong");
        }
        if (!isValidTime(time)) {
            throw new ValidationException("Format waktu tidak valid (format: HH:mm, misal 08:00)");
        }
        return activityRepository.save(title.trim(), day.trim(), time.trim());
    }

    public void updateActivity(int id, String title, String day, String time)
            throws EntityNotFoundException, ValidationException {
        if (id <= 0) {
            throw new ValidationException("ID tidak valid");
        }
        Optional<Activity> found = activityRepository.findById(id);
        if (found.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        boolean hasTitle = title != null && !title.trim().isEmpty();
        boolean hasDay = day != null && !day.trim().isEmpty();
        boolean hasTime = time != null && !time.trim().isEmpty();

        if (!hasTitle && !hasDay && !hasTime) {
            throw new ValidationException("Minimal satu data harus diisi untuk perubahan");
        }

        Activity activity = found.get();
        if (hasTitle) {
            if (title.trim().length() < 2) {
                throw new ValidationException("Judul kegiatan minimal 2 karakter");
            }
            activity.changeTitle(title.trim());
        }
        if (hasDay) {
            if (!isValidDay(day)) {
                throw new ValidationException("Hari kegiatan tidak valid (harus Senin-Minggu)");
            }
            activity.changeDay(day.trim());
        }
        if (hasTime) {
            if (!isValidTime(time)) {
                throw new ValidationException("Format waktu tidak valid (format: HH:mm, misal 08:00)");
            }
            activity.changeTime(time.trim());
        }
        activityRepository.update(activity);
    }

    public void removeActivity(int id) throws EntityNotFoundException, ValidationException {
        if (id <= 0) {
            throw new ValidationException("ID tidak valid");
        }
        boolean deleted = activityRepository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Activity> searchActivities(String keyword) {
        if (keyword == null) {
            return List.of();
        }
        String lowerKeyword = keyword.toLowerCase();
        return activityRepository.findBy(a -> a.getTitle().toLowerCase().contains(lowerKeyword));
    }

    public List<Activity> sortActivities(SortOption option) {
        if (option == null) {
            return List.of();
        }
        return activityRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    private boolean isValidDay(String day) {
        return VALID_DAYS.contains(day.trim().toLowerCase());
    }

    private boolean isValidTime(String time) {
        return time.trim().matches("^([01]?\\d|2[0-3]):[0-5]\\d$");
    }
}
