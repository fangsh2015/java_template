package data_structure.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Niki on 2019/6/10 10:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullName {
    private String firstName;
    private String middleName;
    private String lastName;
}
