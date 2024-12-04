import javax.xml.namespace.QName;
import java.nio.file.Files;
import java.util.List;

/*
NOTE: To complete this assignment this is the ONLY file you need
      to make changes to. Also note, you do not need to add
      any additional class fields.

Instructions on how each method should work are written above each method.
The game should end when the kill ring only contains one player.


 */

public class AssassinManager {
    AssassinNode killRingHead;
    AssassinNode graveyardHead;

    /*
    This is your method for constructing an assassin manager
    object. It should add the names from the list into the kill ring in
    the same order in which they appear in the list. For example, if
    the list contains {“John”, “Sally”, “Fred”}, then in the initial
    kill ring we should see that John is stalking Sally who is
    stalking Fred who is stalking John (reported in that order). You
    may assume that the names are nonempty strings and that there
    are no duplicate names (ignoring case). Your method should
    throw an IllegalArgumentException if the list is empty.
    */
    public AssassinManager(List<String> names) {
        System.out.println(names);
        if (names == null) {
            throw new NullPointerException("names is null");
        }
        killRingHead = new AssassinNode(names.get(0));
        AssassinNode currentHead = killRingHead;

        for (int i = 1; i < names.size(); i++) {
            AssassinNode newAssassin = new AssassinNode(names.get(i));
            currentHead.next = newAssassin;
            currentHead = newAssassin;
        }
        currentHead.next = killRingHead;

    }


    /*
    This method should print the names of the people in the kill
    ring, one per line, indented four spaces, with output of the form
    “<name> is stalking <name>”. If there is only one person in
    the ring, it should report that the person is stalking themselves
    (e.g., “John is stalking John”).
    */
    public void printKillRing() {
        if (killRingHead == null) {
            System.out.println("kill ring is empty");
            return;
        }
        AssassinNode currentHead = killRingHead;
        if (currentHead.next == killRingHead) {
            System.out.println(currentHead.name + " is stalking " + currentHead.name);
        }
        do {
            System.out.println(currentHead.name + " is stalking " + currentHead.name);
            currentHead = currentHead.next;
        }
        while (currentHead.next != killRingHead);


    }


    /*
    This method should print the names of the people in the
    graveyard, one per line, indented four spaces, with output of the
    form “<name> was killed by <name>”. It should print the
    names in reverse kill order (most recently killed first, then next
    more recently killed, and so on). It should produce no output if
    the graveyard is empty.
    */
    public void printGraveyard() {
        if (graveyardHead == null) {
            System.out.println("graveyard is empty");
        } else {
            AssassinNode current = graveyardHead;
            while (current != null) {
                System.out.println("    " + current.name + " was killed by " + current.killer);
                current = current.next;
            }
        }
    }

    /*
    This should return true if the given name is in the current kill
    ring and should return false otherwise. It should ignore case in
    comparing names.
    */
    public boolean killRingContains(String name) {
        AssassinNode current = killRingHead;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }



    /*
    This should return true if the given name is in the current
    graveyard and should return false otherwise. It should ignore
    case in comparing names.
    */
    public boolean graveyardContains(String name) {
        AssassinNode current = graveyardHead;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /*
    This should return true if the game is over (i.e., if the kill ring
    has just one person in it) and should return false otherwise.
    */
    public boolean gameOver() {
        return  killRingHead != null && killRingHead.next == killRingHead;

    }

    /*
    This should return the mame of the winner of the game. It
    should return null if the game is not over
    */
    public String winner() {
        if (gameOver()){
            return killRingHead.name + " won";
        }
        return null;
    }

    /*
    This method records the killing of the person with the given
    name, transferring the person from the kill ring to the
    graveyard. This operation should not change the kill ring order
    of printKillRing (i.e., whoever used to be printed first should
    still be printed first unless that’s the person who was killed, in
    which case the person who used to be printed second should
    now be printed first). It should throw an
    IllegalArgumentException if the given name is not part of the
    current kill ring and it should throw an IllegalStateException if
    the game is over (it doesn’t matter which it throws if both are
    true). It should ignore case in comparing names.
    */
    public void kill(String name) {
        if (gameOver()) {
            throw new IllegalStateException("The game is over.");
        }

        AssassinNode current = killRingHead;
        AssassinNode previous = null;

        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                break;
            }
            previous = current;
            current = current.next;
        }

        if (current == null) {
            throw new IllegalArgumentException("Name not found in kill ring.");
        }

        // Kill the person
        if (previous != null) {
            previous.next = current.next;
        } else {
            killRingHead = current.next;
        }

        // Add to graveyard
        AssassinNode killedNode = new AssassinNode(current.name);
        killedNode.killer = previous != null ? previous.name : killRingHead.name;
        killedNode.next = graveyardHead;
        graveyardHead = killedNode;
    }

}