package br.com.vr.service;

import br.com.vr.host.cartoes.dto.CriarCartaoRequest;
import br.com.vr.host.cartoes.dto.CriarCartaoResponse;
import br.com.vr.host.cartoes.dto.ObterSaldoCartaoResponse;
import br.com.vr.host.cartoes.dto.RealizarTransacaoRequest;
import br.com.vr.host.cartoes.dto.StatusTransacaoEnum;

public interface CartaoService {
    CriarCartaoResponse criarCartao(CriarCartaoRequest criarCartaoRequest);
    ObterSaldoCartaoResponse obterSaldoCartao(String numeroCartao);
    StatusTransacaoEnum realizarTransacao(RealizarTransacaoRequest request);
}
