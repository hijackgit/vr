package br.com.vr;

import br.com.vr.host.cartoes.dto.CriarCartaoRequest;

public class CriarCartaoRequestProvider {
    public static CriarCartaoRequest getCriarCartaoRequest() {
        final CriarCartaoRequest request = new CriarCartaoRequest();
        request.setNumeroCartao("11111");
        request.setSenha("senha");
        return request;        
    }

}
