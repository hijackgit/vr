package br.com.vr.host.cartoes.validators;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidator;


public class CartaoValidator implements ConstraintValidator<CartaoConstraint, String> {
    
    @Override
    public void initialize(CartaoConstraint arg0) {
        
    }
    
@Override
    public boolean isValid(String cartao, ConstraintValidatorContext arg1) {
        //TODO immplementar validacao do cartão VR
        return true;
    }
}