package com.example.cliente.controller;

import com.example.cliente.dto.CpfDTO;
import com.example.cliente.exeption.ClienteNaoEncontradoException;
import com.example.cliente.service.ClienteService;
import com.example.cliente.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/teste")
    public String teste(){
        return "dawdad";
    }

    @PostMapping("/verificar-cpf")
    public ResponseEntity<?> verificarCpf(@RequestBody CpfDTO cpfDTO) {
        String cpf = cpfDTO.getCpf();
        try {
            System.out.println("entrou no try");
            Cliente cliente = clienteService.findByCpf(cpf);
            if (cliente == null) {
                throw new ClienteNaoEncontradoException("Cliente não encontrado");
            }
            return ResponseEntity.ok(cliente);
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("entrou no catch");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }





}

