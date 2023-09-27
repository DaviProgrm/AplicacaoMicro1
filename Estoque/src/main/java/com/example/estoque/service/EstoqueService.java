package com.example.estoque.service;

import com.example.estoque.domain.Estoque;
import com.example.estoque.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Optional<Estoque> buscarPorId(Long id) {
        return estoqueRepository.findById(String.valueOf(id));
    }

    public Estoque salvar(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public Estoque findByCodigoDeBarras(String codigo){
        return  estoqueRepository.findByCodBarras(codigo);
    }
    public void excluir(Long id) {
        estoqueRepository.deleteById(String.valueOf(id));
    }
}