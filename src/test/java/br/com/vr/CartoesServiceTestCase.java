package br.com.vr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.vr.service.impl.CartaoServiceImpl;
import br.com.vr.host.cartoes.dto.ObterSaldoCartaoResponse;
import br.com.vr.host.cartoes.dto.RealizarTransacaoRequest;
import br.com.vr.host.cartoes.dto.StatusTransacaoEnum;
import br.com.vr.model.Cartao;
import br.com.vr.model.CartaoRepository;
import br.com.vr.service.CartaoService;

@TestInstance(Lifecycle.PER_CLASS)
public class CartoesServiceTestCase extends ServiceUnitTestBase {
    
    @InjectMocks
    private CartaoService cartaoService = new CartaoServiceImpl();
    
    @Mock
    private CartaoRepository cartaoRepository;
    
    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testarCriacaoCartaoNovo() {
        final Optional<Cartao> cartao = CartaoProvider.obterCartao();        
        when(cartaoRepository.findById(any(String.class))).thenReturn(Optional.empty());
        when(cartaoRepository.save( any())).thenReturn(cartao.get());
        assertFalse(cartaoService.criarCartao(CriarCartaoRequestProvider.getCriarCartaoRequest()).isCartaoExistente());
    }
    
    @Test
    public void testarCriacaoCartaoExistente() {
        final Optional<Cartao> cartao = CartaoProvider.obterCartao();
        when(cartaoRepository.save( any())).thenReturn(cartao.get());
        when(cartaoRepository.findById(any(String.class))).thenReturn(cartao);      
        assertTrue(cartaoService.criarCartao(CriarCartaoRequestProvider.getCriarCartaoRequest()).isCartaoExistente());
    }
    
    
    @Test
    public void testObterSaldoCartaoExistente() {
        final Optional<Cartao> cartao = CartaoProvider.obterCartao();        
        when(cartaoRepository.findById(any(String.class))).thenReturn(cartao);
        final ObterSaldoCartaoResponse response = cartaoService.obterSaldoCartao("111111");
        assertTrue(response.isCartaoExiste());
    }
    
    @Test
    public void testObterSaldoCartaoInexistente() {                
        when(cartaoRepository.findById(any(String.class))).thenReturn(Optional.empty());
        final ObterSaldoCartaoResponse response = cartaoService.obterSaldoCartao("111111");
        assertFalse(response.isCartaoExiste());
    }
    
    @Test
    public void testRealizarTransacaoOK() {
        final RealizarTransacaoRequest request = new RealizarTransacaoRequest();
        request.setNumeroCartao("11111");
        request.setSenha("senha");
        request.setValor(BigDecimal.valueOf(100));
        
        when(cartaoRepository.findById(any(String.class))).thenReturn(CartaoProvider.obterCartao());
        final StatusTransacaoEnum status =  cartaoService.realizarTransacao(request);
        assertEquals(status, StatusTransacaoEnum.OK);
    }
    
    @Test
    public void testRealizarTransacaoSALDO_INSUFICIENTE() {
        final RealizarTransacaoRequest request = new RealizarTransacaoRequest();
        request.setNumeroCartao("11111");
        request.setSenha("senha");
        request.setValor(BigDecimal.valueOf(600));
        
        when(cartaoRepository.findById(any(String.class))).thenReturn(CartaoProvider.obterCartao());
        final StatusTransacaoEnum status =  cartaoService.realizarTransacao(request);
        assertEquals(status, StatusTransacaoEnum.SALDO_INSUFICIENTE);
    }
    
    @Test    
    public void testRealizarTransacaoSENHA_INVALIDA() {
        final RealizarTransacaoRequest request = new RealizarTransacaoRequest();
        request.setNumeroCartao("11111");
        request.setSenha("SENHA_DIFERENTE");
        request.setValor(BigDecimal.valueOf(500));
        
        when(cartaoRepository.findById(any(String.class))).thenReturn(CartaoProvider.obterCartao());
        final StatusTransacaoEnum status =  cartaoService.realizarTransacao(request);
        assertEquals(status, StatusTransacaoEnum.SENHA_INVALIDA);
    }
    
    @Test
    public void testRealizarTransacaoCARTAO_INEXISTENTE() {
        final RealizarTransacaoRequest request = new RealizarTransacaoRequest();
        request.setNumeroCartao("11111");
        request.setSenha("senha");
        request.setValor(BigDecimal.valueOf(600));
        
        when(cartaoRepository.findById(any(String.class))).thenReturn(Optional.empty());
        final StatusTransacaoEnum status =  cartaoService.realizarTransacao(request);
        assertEquals(status, StatusTransacaoEnum.CARTAO_INEXISTENTE);
    }
    

}
