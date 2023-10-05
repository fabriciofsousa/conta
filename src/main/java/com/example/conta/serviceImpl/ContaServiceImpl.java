package com.example.conta.serviceImpl;


import com.example.conta.model.Conta;
import com.example.conta.model.DTO.Client.ClientDTO;
import com.example.conta.model.DTO.Client.ResponseDTO;
import com.example.conta.model.DTO.ContaDTO;
import com.example.conta.model.DTO.TitularContaDTO;
import com.example.conta.model.TitularConta;
import com.example.conta.repository.ContaRepository;
import com.example.conta.service.ContaService;
import com.example.conta.service.TitularContaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Component
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;
    private final TitularContaServiceImpl titularContaServiceImpl;

    private final TitularContaClient titularContaClient;

    @Autowired
    public ContaServiceImpl(ContaRepository contaRepository, TitularContaServiceImpl titularContaServiceImpl, TitularContaClient titularContaClient) {
        this.contaRepository = contaRepository;
        this.titularContaServiceImpl = titularContaServiceImpl;
        this.titularContaClient = titularContaClient;
    }

    @Override
    public List<Conta> findAllContas() {
        return contaRepository.findAll();
    }

    @Override
    public Conta findContaById(Long id) {
        return contaRepository.findById(id).orElse(null);
    }

    @Override
    public Conta createConta(ContaDTO contaDTO) {

       Conta conta = new Conta();

       conta.setTipoConta(contaDTO.getTipoConta());
       conta.setNumeroConta(contaDTO.getNumeroConta());
       conta.setDataAberturaConta(contaDTO.getDataAberturaConta());
       conta.setSaldoDolar(contaDTO.getSaldoDolar());
       conta.setSaldoReal(contaDTO.getSaldoReal());
       conta.setStatus(contaDTO.getStatus());

       List<TitularConta> titularContas = new ArrayList<>();

       if (contaDTO.getTitulares() != null){
           for (TitularContaDTO titularContaDTO : contaDTO.getTitulares()){
               TitularConta titularConta = new TitularConta();
               // Adicione a lógica para buscar informações do TitularConta usando Feign
               ResponseDTO<ClientDTO> titularInfo = titularContaClient.findById(1L);
               titularConta.setNome(titularInfo.getData().getName());
               titularConta.setCpfCnpj(titularInfo.getData().getId());
               titularConta.setConta(conta);
               titularContas.add(titularConta);
           }
       }
        conta.setTitulares(new HashSet<>(titularContas));
        return contaRepository.save(conta);
    }

    @Override
    public void deleteConta(Long id) {
        contaRepository.deleteById(id);
    }
}
