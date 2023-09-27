package com.example.vendas.controller;

import com.example.vendas.domain.Vendas;
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

    private final RestTemplate restTemplate;

    @Autowired
    public VendasController(VendasService vendasService, RestTemplate restTemplate) {
        this.vendasService = vendasService;
        this.restTemplate = restTemplate;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        vendasService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/verificar-cliente")
    public ResponseEntity<String> verificarCliente() {
        String clienteServiceUrl = "http://cliente/teste"; // URL do serviço de cliente
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(clienteServiceUrl, String.class);

        // Você pode manipular a resposta aqui, se necessário
        String response = responseEntity.getBody();
        return ResponseEntity.ok(response);
    }
}
