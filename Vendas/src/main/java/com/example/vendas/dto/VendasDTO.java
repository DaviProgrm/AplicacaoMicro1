package com.example.vendas.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class VendasDTO {
    private String cpf;
    private String codigo;
    private int quantidade;
}
