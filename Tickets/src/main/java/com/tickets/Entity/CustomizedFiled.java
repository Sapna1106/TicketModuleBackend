package com.tickets.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Data
@AllArgsConstructor
@Document(collection = "customfields")
public class CustomizedFiled {
    @Id
    private String id;
    private String name;
    private String dataType;
    private String description;

}
