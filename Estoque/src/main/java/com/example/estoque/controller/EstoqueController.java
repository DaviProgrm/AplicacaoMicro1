package com.example.estoque.controller;

import com.example.estoque.domain.Estoque;
import com.example.estoque.dto.CodigoDTO;
import com.example.estoque.exception.ProdutoNaoEncontradoException;
import com.example.estoque.service.EstoqueService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() {
        List<Estoque> estoques = estoqueService.listarTodos();
        return new ResponseEntity<>(estoques, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        Optional<Estoque> estoque = estoqueService.buscarPorId(id);
        return estoque.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/verificar-produto")
    public ResponseEntity<?> verificarCodigoDeBarras(@RequestBody CodigoDTO codigoDTO) {
        String codigoDeBarras = codigoDTO.getCodigo();
        try {
            System.out.println("entrou no try");
            Estoque estoque = estoqueService.findByCodigoDeBarras(codigoDeBarras);
            if (estoque == null) {
                throw new ProdutoNaoEncontradoException("produto não encontrado");
            }
            return ResponseEntity.ok(estoque);
        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("entrou no catch");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<Estoque> salvar(@RequestBody Estoque estoque) {
        Estoque e = new Estoque(new ObjectId(),"awdawd",10);
        Estoque estoqueSalvo = estoqueService.salvar(e);
        return new ResponseEntity<>(estoqueSalvo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        estoqueService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
