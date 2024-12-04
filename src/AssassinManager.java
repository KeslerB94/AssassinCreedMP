import java.util.List;

public class AssassinManager {
    AssassinNode killRingHead;
    AssassinNode graveyardHead;

    public AssassinManager(List<String> names) {
        if (!names.isEmpty()) {
            for (int i = names.size()-1; i >= 0; i--) {
                AssassinNode newNode = new AssassinNode(names.get(i));
                newNode.next = this.killRingHead;
                this.killRingHead = newNode;
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void printKillRing() {
        AssassinNode current = this.killRingHead;
        while (current.next != null) {
            System.out.println(current.name + " is stalking " + current.next.name);
            current = current.next;
        }

        System.out.println(current.name + " is stalking " + this.killRingHead.name);
    }

    public void printGraveyard() {
        AssassinNode current = this.graveyardHead;
        while (current != null) {
            System.out.println(current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    public boolean killRingContains(String name) {
        return listContains(name, this.killRingHead);
    }

    public boolean graveyardContains(String name) {
        return listContains(name, this.graveyardHead);
    }

    public boolean gameOver() {
        return this.killRingHead.next == null;
    }

    public String winner() {
        if (gameOver()) {
            return this.killRingHead.name;
        }
        else {
            return null;
        }
    }

    public void kill(String name) {
        if (!killRingContains(name)) throw new IllegalArgumentException();
        if (gameOver()) throw new IllegalStateException();

        AssassinNode currentNode = this.killRingHead;
        AssassinNode nextNode = this.killRingHead.next;

        if (currentNode.name.equals(name)) {
            this.killRingHead = nextNode;
            currentNode.next = this.graveyardHead;
            graveyardHead = currentNode;

            while (nextNode.next != null) {
                nextNode = nextNode.next;
            }
            currentNode.killer = nextNode.name;
        }
        else {
            while (nextNode != null) {
                if (nextNode.name.equals(name)) {
                    nextNode.killer = currentNode.name;
                    currentNode.next = nextNode.next;
                    nextNode.next = this.graveyardHead;
                    this.graveyardHead = nextNode;
                    return;
                }
                else {
                    nextNode = nextNode.next;
                    currentNode = currentNode.next;
                }
            }
        }
    }

    private boolean listContains(String name, AssassinNode headNode) {
        while (headNode != null) {
            if (headNode.name.equals(name)) return true;
            headNode = headNode.next;
        }
        return false;
    }

    private void addToGraveyard(AssassinNode node) {
        node.next =  this.graveyardHead;
        graveyardHead = node;
    }
}
