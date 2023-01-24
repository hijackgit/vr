package br.com.vr.host.cartoes;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.host.cartoes.dto.CriarCartaoRequest;
import br.com.vr.host.cartoes.dto.CriarCartaoResponse;
import br.com.vr.host.cartoes.dto.ObterSaldoCartaoResponse;
import br.com.vr.host.cartoes.dto.RealizarTransacaoRequest;
import br.com.vr.host.cartoes.dto.StatusTransacaoEnum;
import br.com.vr.service.CartaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { br.com.vr.host.SwaggerConfig.TAG_CARTOES })
public class CartoesEndpoint {
    
    @Autowired
    private CartaoService cartaoService;
    
    private final Logger log = LoggerFactory.getLogger(CartoesEndpoint.class);
    
    @ApiOperation(value = "Criar um novo cartão")
    @PostMapping("cartoes")
    public ResponseEntity<CriarCartaoResponse> criarCartao(@Valid @RequestBody CriarCartaoRequest request) {  
        try {
            final CriarCartaoResponse response = cartaoService.criarCartao(request);
            return ResponseEntity.status(response.isCartaoExistente()? HttpStatus.UNPROCESSABLE_ENTITY:HttpStatus.CREATED).body(
                            response);
        }catch(Exception ex) {
            if (log.isErrorEnabled())
                log.error("Erro ao criar cartão ", ex);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @ApiOperation(value = "Consultar o saldo do cartão")
    @GetMapping("cartoes/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSadoDoCartao(@Valid @PathVariable String numeroCartao) {  
        try {
            final ObterSaldoCartaoResponse response = cartaoService.obterSaldoCartao(numeroCartao);
            return ResponseEntity.status(response.isCartaoExiste()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(response.getSaldo());
        }catch(Exception ex) {
            if (log.isErrorEnabled())
                log.error("Erro ao obter saldo do cartão ", ex);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @ApiOperation(value = "Realizar uma transação no cartão")
    @PostMapping("transacoes")
    public ResponseEntity<StatusTransacaoEnum> realizarTransacao(@Valid @RequestBody RealizarTransacaoRequest request) {  
        try {
           final StatusTransacaoEnum statusTransacao = cartaoService.realizarTransacao(request);
           return ResponseEntity.status(statusTransacao == StatusTransacaoEnum.OK? 
                           HttpStatus.CREATED: HttpStatus.UNPROCESSABLE_ENTITY ).body(statusTransacao);
        }catch(Exception ex) {
            if (log.isErrorEnabled())
                log.error("Erro ao realizar uma transação no cartão ", ex);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
