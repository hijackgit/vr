package br.com.vr.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vr.host.cartoes.dto.CriarCartaoRequest;
import br.com.vr.host.cartoes.dto.CriarCartaoResponse;
import br.com.vr.host.cartoes.dto.ObterSaldoCartaoResponse;
import br.com.vr.host.cartoes.dto.RealizarTransacaoRequest;
import br.com.vr.host.cartoes.dto.StatusTransacaoEnum;
import br.com.vr.model.Cartao;
import br.com.vr.model.CartaoRepository;
import br.com.vr.seguranca.SHA256;
import br.com.vr.service.CartaoService;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:bootstrap.yml")
public class CartaoServiceImpl implements CartaoService {
    
    @Autowired
    private CartaoRepository cartaoRepository;
    
    @Value("${cartoes.saldoInicial}")    
    private BigDecimal saldoInicialCartaoDefault;

    @Override
    public CriarCartaoResponse criarCartao(CriarCartaoRequest criarCartaoRequest) {
        final Optional<Cartao> optionalCartao =  cartaoRepository.findById(criarCartaoRequest.getNumeroCartao());
        boolean cartaoExistente = optionalCartao.isPresent();
        
        //Para evitar o if, poderia inserir e capturar o erro de chave duplicada(caso ocorresse) 
        //e disparar o erro de negócio de cartão existente
        if (!cartaoExistente) {
            Cartao cartao = new Cartao();
            cartao.setNumero(criarCartaoRequest.getNumeroCartao());
            cartao.setSenha(SHA256.criptografar(criarCartaoRequest.getSenha()));
            cartao.setSaldo(saldoInicialCartaoDefault);
            cartao = cartaoRepository.save(cartao);            
        }
        
        return new CriarCartaoResponse(criarCartaoRequest.getNumeroCartao(), criarCartaoRequest.getSenha(), 
                        cartaoExistente);
    }

    @Override
    public ObterSaldoCartaoResponse obterSaldoCartao(String numeroCartao) {
        final Optional<Cartao> optionalCartao =  cartaoRepository.findById(numeroCartao);        
        final boolean cartaoExiste = optionalCartao.isPresent();        
        final ObterSaldoCartaoResponse response = new ObterSaldoCartaoResponse();
        response.setCartaoExiste(cartaoExiste);
        response.setSaldo( cartaoExiste? optionalCartao.get().getSaldo(): null);
        return response;
    }

    //Com a transação repeatable read temos boa performance,
    //mas dois usuários não podem atualizar o mesmo registro ao mesmo tempo
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public StatusTransacaoEnum realizarTransacao(RealizarTransacaoRequest request) {
        final Optional<Cartao> optionalCartao =  cartaoRepository.findById(request.getNumeroCartao());
        
        if (optionalCartao.isPresent()) {
            final String senhaCriptografada = SHA256.criptografar(request.getSenha());
            
            if (  ! senhaCriptografada.equals( optionalCartao.get().getSenha() )) {
                return StatusTransacaoEnum.SENHA_INVALIDA;
            }else if ( request.getValor().compareTo(optionalCartao.get().getSaldo()) > 0  ) {
                return StatusTransacaoEnum.SALDO_INSUFICIENTE;
            }else {
                optionalCartao.get().realizarTransacao(request.getValor());                 
                cartaoRepository.save(optionalCartao.get());
                return StatusTransacaoEnum.OK;
            }   
            
        }else {
            return StatusTransacaoEnum.CARTAO_INEXISTENTE;
        }
      
    }

}
