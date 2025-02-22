import java.util.Scanner;

public class MUDController {
    private Player player;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);
    public MUDController(Player player) {
        this.player = player;
    }
    public void runGameLoop() {
        System.out.println("MudController!");
        while (running) {
            handleInput(scanner.nextLine());
        }
    }
    private void handleInput(String input) {
        if (input.isEmpty()) {
            return;
        }
        String[] words = input.split(" ");
        String command = words[0];
        String argument = "";
        if (words.length > 1) {
            argument = input.substring(command.length());
        }
        if (command.equals("look")) {
            lookAround();
        } else if (command.equals("move")) {
            move(argument);
        } else if (command.equals("pick")) {
            if (argument.startsWith("up ")) {
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
    }
    private void move(String a) {
        Room nextRoom = player.getCurrentRoom().getConnectedRoom(a);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println(a);
            System.out.println(player.getCurrentRoom());
        } else {
            System.out.println();
        }
    }
    private void pickUp(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Item?");
            return;
        }
        Room room = player.getCurrentRoom();
        Item item = room.getItem(itemName);
        if (item != null) {
            player.addItem(item);
            room.removeItem(item);
            System.out.println("Item Name: " + itemName);
        } else {
            System.out.println("Unknown");
        }
    }
    private void showHelp() {
        System.out.println("Commands: look, move, pick up, inventory, help, quit");
    }
    private void exitGame() {
        running = false;
    }
}