package chainOfResponsibility.ea;

import java.util.List;

public interface RequestValidator {
    List<String> validate(Request request);
}
