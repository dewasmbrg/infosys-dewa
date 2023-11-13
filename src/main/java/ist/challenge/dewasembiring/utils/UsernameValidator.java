package ist.challenge.dewasembiring.utils;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class UsernameValidator implements Predicate<String> {
    @Override
    public boolean checkUsername(String u) {
        if(u.length() < 5){
            return false;
        }

        return true;
    }
}