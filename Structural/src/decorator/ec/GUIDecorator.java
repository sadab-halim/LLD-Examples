package decorator.ec;

// 5. Client Code
public class GUIDecorator {
    public static void main(String[] args) {
        // Create a simple button
        GUIComponent myButton = new Button("Click Me");
        myButton.display();
        System.out.println("--------------------");

        // Decorate the button with a thick border
        GUIComponent borderedButton = new BorderDecorator(myButton, "Thick");
        borderedButton.display();
        System.out.println("--------------------");

        // Create a panel and decorate it with a scrollbar AND a thin border
        GUIComponent myPanel = new Panel("Main Panel");
        GUIComponent scrollablePanel = new ScrollbarDecorator(myPanel);
        GUIComponent thinBorderedScrollablePanel = new BorderDecorator(scrollablePanel, "Thin");
        thinBorderedScrollablePanel.display();
        System.out.println("--------------------");

        // You can also chain them like this:
        GUIComponent complexButton = new ScrollbarDecorator(new BorderDecorator(new Button("Save"), "Dashed"));
        complexButton.display();
    }
}
