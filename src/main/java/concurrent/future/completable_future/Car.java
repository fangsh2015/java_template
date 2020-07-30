package concurrent.future.completable_future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by Niki on 2018/12/26 19:03
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Car {
    int id;
    int manufacturerId;
    String model;
    int year;
    float rating;

    public Car(int id, int manufacturerId, String model, int year) {
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.model = model;
        this.year = year;
    }
}
