package com.example.estoque.repository;

import com.example.estoque.domain.Estoque;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstoqueRepository extends MongoRepository<Estoque, String> {
    Estoque findByCodBarras(String codigo);
}

