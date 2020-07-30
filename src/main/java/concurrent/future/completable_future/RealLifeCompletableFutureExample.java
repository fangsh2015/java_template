package concurrent.future.completable_future;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created by Niki on 2018/12/26 19:05
 *
 * @see{}
 */
@Slf4j
public class RealLifeCompletableFutureExample {

    public static void syncRating() {
        long start = System.currentTimeMillis();
        List<Car> cars = cars();
        cars.forEach(car -> {
            float rating = rating(car.manufacturerId);
            car.setRating(rating);
        });

        cars.forEach(System.out::println);

        log.info("同步获取汽车评分={}", System.currentTimeMillis() - start);
    }

    public static void main(String[] args) {
        carsCompletableFuture().thenCompose(cars -> {
            List<CompletionStage<Car>> updatedCars = cars.stream()
                    .map(car -> ratingAsync(car.manufacturerId).thenApply(r -> {
                        car.setRating(r);
                        return car;
                    })).collect(Collectors.toList());

            CompletableFuture<Void> done = CompletableFuture
                    .allOf(updatedCars.toArray(new CompletableFuture[updatedCars.size()]));
            return done.thenApply(v -> updatedCars.stream().map(CompletionStage::toCompletableFuture)
                    .map(CompletableFuture::join).collect(Collectors.toList()));
        }).whenComplete((cars, th) -> {
            if (th == null) {
                cars.forEach(System.out::println);
            } else {
                throw new RuntimeException(th);
            }
        }).toCompletableFuture().join();
    }

    public static void asyncRating() {
        long start = System.currentTimeMillis();
        List<Car> carList = cars();

        CompletableFuture<List<Car>> cars = CompletableFuture.supplyAsync(() -> carList);

        cars.thenCompose(cars_ -> {
            List<CompletionStage<Car>> updateCars = cars_.stream()
                    .map(car -> ratingAsync(car.getManufacturerId()).thenApply(r -> {
                        car.setRating(r);
                        return car;
                    })).collect(Collectors.toList());

            CompletableFuture<Void> done = CompletableFuture.allOf(updateCars.toArray(new CompletableFuture[updateCars.size()]));

            return done.thenApply(v -> updateCars.stream().map(CompletionStage::toCompletableFuture)
                    .map(CompletableFuture::join).collect(Collectors.toList()));
        }).whenComplete((cars__, th) -> {
            if (th == null) {
                cars__.forEach(System.out::println);
            } else {
                throw new RuntimeException(th);
            }
        }).toCompletableFuture().join();


    }

    static List<Car> cars() {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(1, 3, "Fiesta", 2017));
        carList.add(new Car(2, 7, "Camry", 2014));
        carList.add(new Car(3, 2, "M2", 2008));
        return carList;
    }

    static CompletableFuture<List<Car>> carsCompletableFuture() {
        List<Car> carList = cars();
        return CompletableFuture.completedFuture(carList);
    }

    private static void simulateDelay() throws InterruptedException {
        Thread.sleep(5000);
    }

    static float rating(int manufacturer) {
        try {
            simulateDelay();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        switch (manufacturer) {
            case 2:
                return 4f;
            case 3:
                return 4.1f;
            case 7:
                return 4.2f;
            default:
                return 5f;
        }
    }

    static CompletionStage<Float> ratingAsync(int manufacturer) {
        return CompletableFuture.supplyAsync(() -> {
            return rating(manufacturer);
        }).exceptionally(th -> -1f);
    }
}
