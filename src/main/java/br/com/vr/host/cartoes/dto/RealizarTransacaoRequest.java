package br.com.vr.host.cartoes.dto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class RealizarTransacaoRequest extends CriarCartaoRequest {
    
    @Valid
    @NotNull
    @ApiModelProperty(required = true, notes = "valor")
    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
