package br.com.vr.host.cartoes.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.vr.host.cartoes.validators.CartaoConstraint;
//import br.com.vr.host.cartoes.validators.CartaoConstraint;
import io.swagger.annotations.ApiModelProperty;

public class CriarCartaoRequest {
    @Valid
    @NotNull
    @ApiModelProperty(required = true, notes = "Número do Cartão")
    @JsonProperty("numeroCartao")
    //validador customizado do cartão VR
    @CartaoConstraint
    protected String numeroCartao;
    
    @Valid
    @NotNull
    @ApiModelProperty(required = true, notes = "senha")
    @JsonProperty("senha")
    protected String senha;
    
    public String getNumeroCartao() {
        return numeroCartao;
    }
    
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    

}
