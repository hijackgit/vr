package br.com.vr;

import java.math.BigDecimal;

import java.util.Optional;

import br.com.vr.model.Cartao;
import br.com.vr.seguranca.SHA256;

public class CartaoProvider {
    public static Optional<Cartao> obterCartao() {
        final Cartao cartao = new Cartao();
        cartao.setNumero("11111");
        cartao.setSaldo(BigDecimal.valueOf(500));
        cartao.setSenha(SHA256.criptografar("senha"));
        return Optional.of(cartao);
    }

}
