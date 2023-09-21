package com.example.estoque.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "estoque")
public class Estoque {
    @Id
    private String id;
    private String codBarras;
    private int quantidade;
}
