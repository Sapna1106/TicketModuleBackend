package com.tickets.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String name;

    @DBRef
    private List<CustomizedFiled> customizedFileds;
    public Project(String id){
        this.id=id;
    }

    public String getInitials() {
        if (name != null && !name.isEmpty()) {
            return Arrays.stream(this.name.split("\\s+"))
                    .filter(word -> !word.isEmpty())
                    .map(word -> String.valueOf(word.charAt(0)))
                    .collect(Collectors.joining());
        } else {
            return "";
        }
    }

}
