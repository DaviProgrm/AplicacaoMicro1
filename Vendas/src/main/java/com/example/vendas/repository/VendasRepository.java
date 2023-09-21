package com.example.vendas.repository;

import com.example.vendas.domain.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendasRepository extends JpaRepository<Vendas, Long>{
}
