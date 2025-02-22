import java.util.Scanner;

public class MUDController {
    private Player player;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);


    /**
     * Constructs the controller with a reference to the current player.
     */
    public MUDController(Player player) {
        this.player = player;     // Initialize fields here (if needed)
    }
    public void runGameLoop() {
        System.out.println("MudController!");
        System.out.print("> "); //1
        while (running) { //2
            handleInput(scanner.nextLine()); //3
        }
    }
    private void handleInput(String input) {
        if (input.isEmpty()) {
            return;
        }
        String[] words = input.split(" "); //1
        String command = words[0];
        String argument = "";
        if (words.length > 1) {  //2
            argument = input.substring(command.length());
        }
        if (command.equals("look")) {
            lookAround();
        } else if (command.equals("move")) {
            move(argument);
        } else if (command.equals("pick")) {
            if (argument.startsWith("up ")) {    // 1) Parse out the item name if 'arg' starts with "up "
                pickUp(argument.substring(3));
            } else {
                System.out.println("Unknpwn");
            }
        } else if (command.equals("inventory")) {
            player.showInventory();
        } else if (command.equals("help")) {
            showHelp();
        } else if (command.equals("exit")) {
            exitGame();
        } else {
            System.out.println("Unknown command.");
        }
    }
    private void lookAround() {
        System.out.println(player.getCurrentRoom());
    }   // TODO: Print information about the player's current room

    private void move(String a) {
        Room nextRoom = player.getCurrentRoom().getConnectedRoom(a);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);         // TODO: Attempt to move to the next room in the given direction
            System.out.println(a);
            System.out.println(player.getCurrentRoom());          //       If successfully moved, describe the new room
        } else {
            System.out.println("Error");   //       If there's no room in that direction, print an error message
        }
    }
    private void pickUp(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Item?");
            return;
        }
        Room room = player.getCurrentRoom();
        Item item = room.getItem(itemName);
        if (item != null) {           // 2) Check if that item exists in the current room
            player.addItem(item);
            room.removeItem(item);            // 3) Remove from room, add to player's inventory
            System.out.println("Item Name: " + itemName);
        } else {
            System.out.println("Unknown");
        }
    }
    private void showHelp() {
        System.out.println("Commands: look, move, pick up, inventory, help, quit");
    }         // TODO: Print a list of available commands and brief instructions
    private void exitGame() {
        running = false;
    }
}