package factory.ec;

// 3. The Abstract Factory Interface
// Unlike the previous examples, this factory creates *multiple* types of objects
// (Buttons AND Checkboxes) that belong to the same theme.
public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
