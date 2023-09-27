package com.example.vendas.service;

import com.example.vendas.domain.Cliente;
import com.example.vendas.domain.Estoque;
import com.example.vendas.domain.Vendas;
import com.example.vendas.domain.reponse.ErrorResponse;
import com.example.vendas.domain.reponse.EstoqueResponse;
import com.example.vendas.dto.VendasDTO;
import com.example.vendas.exception.ClienteNaoEncontradoException;
import com.example.vendas.exception.ProdutoNaoEncontradoException;
import com.example.vendas.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
public class VendasService {
    private final VendasRepository vendasRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public VendasService(VendasRepository vendasRepository, RestTemplate restTemplate){
        this.vendasRepository = vendasRepository;
        this.restTemplate = restTemplate;
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


    public ResponseEntity<?> realizarVenda(VendasDTO vendasDTO) {
        try {
            ResponseEntity<?> clienteResponse = verificarExistenciaCliente(vendasDTO.getCpf());

            ResponseEntity<?> estoqueResponse = validarDisponibilidadeProduto(vendasDTO.getCodigo(), vendasDTO.getQuantidade());

            if (clienteResponse.getStatusCode() == HttpStatus.OK && estoqueResponse.getStatusCode() == HttpStatus.OK) {
                Vendas vendas = new Vendas();
                vendas.setCpf(vendasDTO.getCpf());
                vendas.setCodigoBarras(vendasDTO.getCodigo());
                vendas.setQuantidade(vendasDTO.getQuantidade());
                salvar(vendas);
                return ResponseEntity.status(HttpStatus.OK).body(vendas);
            } else {
                ErrorResponse errorResponse = new ErrorResponse("Erro nas verificações");
                if (clienteResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                    errorResponse.setMensagem("Cliente nao encontrado, por favor verifique o CPF");
                } else if (estoqueResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                    errorResponse.setMensagem("Produto nao encontrado no estoque");
                } else if (estoqueResponse.getStatusCode() == HttpStatus.NO_CONTENT) {
                    errorResponse.setMensagem("Quantidade de produto insuficiente para atender a sua venda");
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        } catch (ClienteNaoEncontradoException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao verificar o cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (ProdutoNaoEncontradoException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao verificar o estoque: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao criar a venda");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }







    public ResponseEntity<?>  verificarExistenciaCliente(String cpf) {
        try {
            String clienteServiceUrl = "http://CLIENTE/cliente/verificar-cpf";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("cpf", cpf);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Cliente> responseEntity = restTemplate.exchange(clienteServiceUrl, HttpMethod.POST, request, Cliente.class);

            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("cliente não encotrado");
            }

            Cliente cliente = responseEntity.getBody();

            return ResponseEntity.status(HttpStatus.OK).body(cliente);


        } catch (HttpClientErrorException.NotFound e) {
            ErrorResponse errorResponse = new ErrorResponse("Cliente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    public ResponseEntity<?>  validarDisponibilidadeProduto(String codigoBarras, int quantidade) {
        try {
            String estoqueServiceUrl = "http://ESTOQUE/estoque/verificar-produto";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("codigo", codigoBarras);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<EstoqueResponse> responseEntity = restTemplate.exchange(estoqueServiceUrl, HttpMethod.POST, request, EstoqueResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                ErrorResponse errorResponse = new ErrorResponse("produto nao encontrado");

                return    ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

            }

            if (Objects.requireNonNull(responseEntity.getBody()).getQuantidade() < quantidade ){
                ErrorResponse errorResponse = new ErrorResponse("quantidade de produto insuficiente");
              return   ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);

            }

            EstoqueResponse estoque = responseEntity.getBody();

            return ResponseEntity.status(HttpStatus.OK).body(estoque);

        }catch (HttpClientErrorException.NotFound e){
            ErrorResponse errorResponse = new ErrorResponse("produto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }
}



