package com.example.vendas.service;

import com.example.vendas.domain.Vendas;
import com.example.vendas.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendasService {
    private final VendasRepository vendasRepository;

    @Autowired
    public VendasService(VendasRepository vendasRepository){
        this.vendasRepository = vendasRepository;
    }
    public List<Vendas> listarTodos(){
        return vendasRepository.findAll();
    }
    public Optional<Vendas> buscarPorId(Long id){
        return vendasRepository.findById(id);
    }
    public Vendas salvar(Vendas venda){
        return vendasRepository.save(venda);
    }
    public void excluir(Long id){
        vendasRepository.deleteById(id);
    }



    }

