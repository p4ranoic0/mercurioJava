package pe.isil.mercurio.repository;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MiAlgoritmoPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword+"ISIL";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
