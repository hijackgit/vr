package br.com.vr.host.cartoes.dto;

import java.math.BigDecimal;

public class ObterSaldoCartaoResponse {
    private BigDecimal saldo;
    private boolean cartaoExiste;
    
    public BigDecimal getSaldo() {
        return saldo;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    public boolean isCartaoExiste() {
        return cartaoExiste;
    }
    
    public void setCartaoExiste(boolean cartaoExiste) {
        this.cartaoExiste = cartaoExiste;
    }
}
