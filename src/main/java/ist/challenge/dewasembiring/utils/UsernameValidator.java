package ist.challenge.dewasembiring.utils;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class UsernameValidator implements Predicate<String> {

    @Override
    public boolean test(String u) {
        return u.length() >= 5;
    }
}
