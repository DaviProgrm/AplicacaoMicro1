package com.example.vendas.controller;

import com.example.vendas.domain.Cliente;
import com.example.vendas.domain.Estoque;
import com.example.vendas.domain.Vendas;
import com.example.vendas.domain.reponse.ErrorResponse;
import com.example.vendas.dto.VendasDTO;
import com.example.vendas.service.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendasController {
    private final VendasService vendasService;


    @Autowired
    public VendasController(VendasService vendasService, RestTemplate restTemplate) {
        this.vendasService = vendasService;

    }

    @GetMapping
    public ResponseEntity<List<Vendas>> listarTodos() {
        List<Vendas> clientes = vendasService.listarTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendas> buscarPorId(@PathVariable Long id) {
        Optional<Vendas> vendas = vendasService.buscarPorId(id);
        return vendas.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Vendas> salvar(@RequestBody Vendas vendas) {
        Vendas vendaSalva = vendasService.salvar(vendas);
        return new ResponseEntity<>(vendaSalva, HttpStatus.CREATED);
    }

    @PostMapping("realizar-venda")
    public ResponseEntity<?> realizarVenda(@RequestBody VendasDTO vendasDTO){
        try {
            //return vendasService.verificarExistenciaCliente(vendasDTO.getCpf());
            return vendasService.realizarVenda(vendasDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        vendasService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    }


