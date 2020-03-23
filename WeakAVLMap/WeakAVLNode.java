package col106.assignment4.WeakAVLMap;

public class WeakAVLNode<K extends Comparable, V> {

    K key;
    V value;
    WeakAVLNode parent;
    WeakAVLNode leftChild;
    WeakAVLNode rightChild;
    int rank;

    public WeakAVLNode(K key, V value, WeakAVLNode parent){
        this.parent = parent;
        this.leftChild = null;
        this.rightChild = null;
        this.key = key;
        this.value = value;
        this.rank = 0;
    }

}
