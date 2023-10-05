package com.example.conta.controller;

import com.example.conta.model.Conta;
import com.example.conta.model.DTO.ContaDTO;
import com.example.conta.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/")
    public ResponseEntity<Conta> createConta(@RequestBody ContaDTO contaDTO) {
            Conta novaConta = contaService.createConta(contaDTO);
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<Conta> getAllContas() {
        return contaService.findAllContas();
    }

    @GetMapping("/{id}")
    public Conta getContaById(@PathVariable Long id) {
        return contaService.findContaById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteConta(@PathVariable Long id) {
        contaService.deleteConta(id);
    }
}