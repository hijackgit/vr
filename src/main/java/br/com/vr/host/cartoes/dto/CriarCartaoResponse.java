package br.com.vr.host.cartoes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CriarCartaoResponse extends CriarCartaoRequest {
    @JsonIgnore
    private boolean cartaoExistente;
    
    public CriarCartaoResponse(String numeroCartao, String senha, boolean cartaoExistente) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.cartaoExistente = cartaoExistente;
    }

    public boolean isCartaoExistente() {
        return cartaoExistente;
    }

    public void setCartaoExistente(boolean cartaoExistente) {
        this.cartaoExistente = cartaoExistente;
    }

}
