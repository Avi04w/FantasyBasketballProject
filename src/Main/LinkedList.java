package Main;

import Objects.Player;

public class LinkedList {
    //Instance variables
    private Node head;

    //Constructor
    public LinkedList(){
        head = null;
    }

    //Methods
    /**
     * This method takes in a Player object and sets it as the first item in the list. It first creates a new
     * node, connects it to the current head and then changes the head to the new node.
     * @param p - Player object
     */
    public void addToFront(Player p){
        Node newNode = new Node(p);
        newNode.setNext(head);
        head = newNode;
    }

    /**
     * This method takes in 2 Strings which tells it which player to remove from the list. It searches through
     * the list looking for the Player and connects the node before it to the node after it, cutting it
     * out of the list.
     * @param fName - String of player first name
     * @param lName - String of player last name
     */
    public void remove(String fName, String lName){
        Node prev = null;
        Node curr = head;
        while(!curr.getData().getFirstName().equalsIgnoreCase(fName) &&
                !curr.getData().getLastName().equalsIgnoreCase(lName)){
            prev = curr;
            curr = curr.getNext();
        }
        if (prev == null){
            head = head.next;
        } else {
            prev.setNext(curr.getNext());
        }
    }

    /**
     * This method returns the size of the current list. There are no parameters.
     * @return an integer which represents the size of the list
     */
    public int size(){
        Node curr = head;
        int i = 1;
        while (!(curr.next == null)){
            curr = curr.next;
            i++;
        }
        return i;
    }

    /**
     * This method returns the value of a given index
     *
     * @param index - integer of index to be returned
     * @return a Player object
     */
    public Player get(int index){
        Node current = head;
        for (int i = 0; i < index; i++){
            current = current.next;
        }
        return current.getData();
    }

    /**
     * This method returns a String of the information of all the Employee objects in the list.
     * There are no parameters.
     * @return String of list information.
     */
    public String toString(){
        Node current = head;
        String info = "";
        while (current != null){
            info += current.getData() + "\n";
            current = current.getNext();
        }
        return info;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //Node Class
    private static class Node{
        //Instance variables
        private Player data;
        private Node next;

        //Constructor
        public Node(Player p){
            data = p;
            next = null;
        }

        //Methods
        /**
         * This method will return the next Node
         * No parameters
         * @return - Node which is set as the next value
         */
        public Node getNext() {
            return next;
        }

        /**
         * This method sets next value of a node
         * @param newNode - the Node which needs to be set as next.
         * Does not return anything
         */
        public void setNext(Node newNode) {
            next = newNode;
        }

        /**
         * This method returns the value of the data variable
         * No parameters
         * @return a Player object which is stored in the variable - 'data'.
         */
        public Player getData() {
            return data;
        }
    }
}
