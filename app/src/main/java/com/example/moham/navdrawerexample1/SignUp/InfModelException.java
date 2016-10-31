package com.example.moham.navdrawerexample1.SignUp;

/**
 * Created by moham on 10/23/2016.
 */

 class InfModelException extends Exception {

    String Message;

    @Override
    public String getMessage() {
        return Message;
    }

     InfModelException(String s) {
        this.Message = s;
    }
}
