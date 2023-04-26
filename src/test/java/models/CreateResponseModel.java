package models;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class CreateResponseModel {
    String name, job, id, createdAt;
}
