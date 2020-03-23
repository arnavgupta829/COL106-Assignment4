package col106.assignment4.WeakAVLMap;
import java.util.Vector;
import java.util.ArrayList;

public class WeakAVLMap<K extends Comparable,V> implements WeakAVLMapInterface<K,V>{

    private WeakAVLNode<K, V> root;
    private int rotationsPerformed;

    public WeakAVLMap(){
        // write your code here
        root = new WeakAVLNode<>(null, null, null);
        rotationsPerformed = 0;
    }

    private WeakAVLNode<K, V> returnOtherChild(WeakAVLNode<K, V> node){
        if(node.parent.rightChild.key != null && node.parent.rightChild.key.compareTo(node.key) == 0)
            return node.parent.leftChild;
        return node.parent.rightChild;
    }

    private WeakAVLNode<K, V> returnSameChild(WeakAVLNode<K, V> node){
        if(node.parent.rightChild.key != null && node.parent.rightChild.key.compareTo(node.key) == 0)
            return node.parent.rightChild;
        return node.parent.leftChild;
    }

    private String returnWhichChild(WeakAVLNode<K, V> node){
        if(node.parent.rightChild.key != null && node.parent.rightChild.key.compareTo(node.key) == 0)
            return "right";
        return "left";
    }

    private boolean keyExists(K key){
        WeakAVLNode<K, V> curr = null;
        if(root.key == null)
            return false;
        else if(root.key.compareTo(key) < 0){
            curr = root.rightChild;
        }
        else if(root.key.compareTo(key) > 0){
            curr = root.leftChild;
        }
        else
            return true;
        while(curr.key != null){
            if(curr.key != null && curr.key.compareTo(key) == 0)
                return true;
            else if(curr.key != null && curr.key.compareTo(key) < 0)
                curr = curr.rightChild;
            else if(curr.key != null)
                curr = curr.leftChild;
        }
        return false;
    }

    public V put(K key, V value){
        // write your code here
        V oldValue = null;
        if(keyExists(key)){
            if(root.key.compareTo(key) == 0){
                oldValue = root.value;
                root.value = value;
            }
            else{
                WeakAVLNode<K, V> curr;
                if(root.key.compareTo(key) < 0)
                    curr = root.rightChild;
                else
                    curr = root.leftChild;
                while(true){
                    if(curr.key.compareTo(key) < 0)
                        curr = curr.rightChild;
                    else if(curr.key.compareTo(key) > 0)
                        curr = curr.leftChild;
                    else{
                        oldValue = curr.value;
                        curr.value = value;
                        break;
                    }
                }
            }
        }
        else{
            if(root.key == null){
                root.key = key;
                root.value = value;
                root.rank = 1;
                root.leftChild = new WeakAVLNode<>(null, null, root);
                root.rightChild = new WeakAVLNode<>(null, null, root);
                root.rightChild.parent = root;
                root.leftChild.parent = root;
            }
            else{
                WeakAVLNode<K, V> curr;
                if(root.key.compareTo(key) < 0)
                    curr = root.rightChild;
                else
                    curr = root.leftChild;
                while(curr.key != null){
                    if(curr.key.compareTo(key) < 0)
                        curr = curr.rightChild;
                    else
                        curr = curr.leftChild;
                }
                curr.key = key;
                curr.value = value;
                curr.rank = 1;
                curr.leftChild = new WeakAVLNode<>(null, null, curr);
                curr.rightChild = new WeakAVLNode<>(null, null, curr);
                curr.leftChild.parent = curr;
                curr.rightChild.parent = curr;
                WeakAVLNode<K, V> traverse = curr;
                while(traverse.parent != null && (traverse.parent.rank - traverse.rank == 0)) {
                    WeakAVLNode<K, V> s = null;
                    if(traverse.parent.rank - traverse.parent.rightChild.rank == 0)
                        s = traverse.parent.leftChild;
                    else
                        s = traverse.parent.rightChild;
                    if(traverse.parent.rank - s.rank == 1){
                        traverse.parent.rank += 1;
                        traverse = traverse.parent;
                    }
                    else{
                        if(traverse.parent.rank - traverse.parent.leftChild.rank == 0){
                            if(traverse.rank - traverse.leftChild.rank == 1){
                                if(traverse.parent.parent != null) {
                                    if (returnWhichChild(traverse.parent).equals("left"))
                                        traverse.parent.parent.leftChild = traverse;
                                    else
                                        traverse.parent.parent.rightChild = traverse;
                                    WeakAVLNode<K, V> temp = traverse.rightChild;
                                    traverse.rightChild = traverse.parent;
                                    traverse.rightChild.leftChild = temp;
                                    traverse.parent = traverse.parent.parent;
                                    traverse.rightChild.parent = traverse;
                                    temp.parent = traverse.rightChild;
                                    traverse.rightChild.rank -= 1;
                                    rotationsPerformed += 1;
                                }
                                else{
                                    WeakAVLNode<K, V> temp = traverse.rightChild;
                                    traverse.rightChild = traverse.parent;
                                    traverse.rightChild.leftChild = temp;
                                    traverse.parent = traverse.parent.parent;
                                    traverse.rightChild.parent = traverse;
                                    temp.parent = traverse.rightChild;
                                    traverse.rightChild.rank -= 1;
                                    root = traverse;
                                    rotationsPerformed += 1;
                                }
                            }
                            else{
                                WeakAVLNode<K, V> temp = traverse.rightChild;
                                traverse.rightChild = temp.leftChild;
                                traverse.parent.leftChild = temp.rightChild;
                                if(traverse.parent.parent != null) {
                                    if (returnWhichChild(traverse.parent).equals("left"))
                                        traverse.parent.parent.leftChild = temp;
                                    else
                                        traverse.parent.parent.rightChild = temp;
                                    temp.rightChild = traverse.parent;
                                    temp.leftChild = traverse;
                                    temp.parent = traverse.parent.parent;
                                    temp.rightChild.parent = temp;
                                    temp.leftChild.parent = temp;
                                    temp.leftChild.rightChild.parent = temp.leftChild;
                                    temp.rightChild.leftChild.parent = temp.rightChild;
                                    temp.rank += 1;
                                    temp.leftChild.rank -= 1;
                                    temp.rightChild.rank -= 1;
                                    rotationsPerformed += 2;
                                }
                                else{
                                    temp.rightChild = traverse.parent;
                                    temp.leftChild = traverse;
                                    temp.parent = traverse.parent.parent;
                                    temp.rightChild.parent = temp;
                                    temp.leftChild.parent = temp;
                                    temp.leftChild.rightChild.parent = temp.leftChild;
                                    temp.rightChild.leftChild.parent = temp.rightChild;
                                    temp.rank += 1;
                                    temp.leftChild.rank -= 1;
                                    temp.rightChild.rank -= 1;
                                    rotationsPerformed += 2;
                                    root = temp;
                                }
                            }
                        }
                        else{
                            if(traverse.rank - traverse.rightChild.rank == 1){
                                //perform right right
                                if(traverse.parent.parent != null) {
                                    if (returnWhichChild(traverse.parent).equals("left"))
                                        traverse.parent.parent.leftChild = traverse;
                                    else
                                        traverse.parent.parent.rightChild = traverse;
                                    WeakAVLNode<K, V> temp = traverse.leftChild;
                                    traverse.leftChild = traverse.parent;
                                    traverse.leftChild.rightChild = temp;
                                    traverse.parent = traverse.parent.parent;
                                    traverse.leftChild.parent = traverse;
                                    temp.parent = traverse.leftChild;
                                    traverse.leftChild.rank -= 1;
                                    rotationsPerformed += 1;
                                }
                                else{
                                    WeakAVLNode<K, V> temp = traverse.leftChild;
                                    traverse.leftChild = traverse.parent;
                                    traverse.leftChild.rightChild = temp;
                                    traverse.parent = traverse.parent.parent;
                                    traverse.leftChild.parent = traverse;
                                    temp.parent = traverse.leftChild;
                                    traverse.leftChild.rank -= 1;
                                    root = traverse;
                                    rotationsPerformed += 1;
                                }
                            }
                            else{
                                //perform right left
                                WeakAVLNode<K, V> temp = traverse.leftChild;
                                traverse.leftChild = temp.rightChild;
                                traverse.parent.rightChild = temp.leftChild;
                                if(traverse.parent.parent != null) {
                                    if (returnWhichChild(traverse.parent).equals("left"))
                                        traverse.parent.parent.leftChild = temp;
                                    else
                                        traverse.parent.parent.rightChild = temp;
                                    temp.leftChild = traverse.parent;
                                    temp.rightChild = traverse;
                                    temp.parent = traverse.parent.parent;
                                    temp.leftChild.parent = temp;
                                    temp.rightChild.parent = temp;
                                    temp.rightChild.leftChild.parent = temp.rightChild;
                                    temp.leftChild.rightChild.parent = temp.leftChild;
                                    temp.rank += 1;
                                    temp.rightChild.rank -= 1;
                                    temp.leftChild.rank -= 1;
                                    rotationsPerformed += 2;
                                }
                                else{
                                    temp.leftChild = traverse.parent;
                                    temp.rightChild = traverse;
                                    temp.parent = traverse.parent.parent;
                                    temp.leftChild.parent = temp;
                                    temp.rightChild.parent = temp;
                                    temp.rightChild.leftChild.parent = temp.rightChild;
                                    temp.leftChild.rightChild.parent = temp.leftChild;
                                    temp.rank += 1;
                                    temp.rightChild.rank -= 1;
                                    temp.leftChild.rank -= 1;
                                    rotationsPerformed += 2;
                                    root = temp;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return oldValue;
    }

    private void startBalance(WeakAVLNode<K, V> node){
        WeakAVLNode<K, V> q = node;
        if(q.parent != null && (q.parent.rank - q.parent.rightChild.rank == 2 && q.parent.rightChild.key == null) && (q.parent.rank - q.parent.leftChild.rank == 2 && q.parent.leftChild.key == null)){
            q.parent.rank -= 1;
            q = q.parent;
            if(q != null)
                startBalance(q);
        }
        while(q.parent != null && (q.parent.rank - q.rank == 3)){
            WeakAVLNode<K, V> s = null;
            if(q.parent.rank - q.parent.rightChild.rank == 3)
                s = q.parent.leftChild;
            else
                s = q.parent.rightChild;
            if(q.parent.rank - s.rank == 2){
                q.parent.rank -= 1;
                q = q.parent;
            }
            else{
                if(s.rank - s.rightChild.rank == 2 && s.rank - s.leftChild.rank == 2){
                    q.parent.rank -= 1;
                    s.rank -= 1;
                    q = q.parent;
                }
                else{
                    if(q.parent.rank - q.parent.rightChild.rank == 3){
                        //left
                        WeakAVLNode<K, V> temp = s.rightChild;
                        if(s.rank - s.leftChild.rank == 1){
                            //left left
                            rotationsPerformed += 1;
                            if(q.parent.parent != null){
                                if(returnWhichChild(q.parent).equals("left"))
                                    q.parent.parent.leftChild = s;
                                else
                                    q.parent.parent.rightChild = s;
                                s.rightChild = q.parent;
                                s.parent = q.parent.parent;
                                q.parent.parent = s;
                                s.rightChild.leftChild = temp;
                                temp.parent = q.parent;
                                s.rank += 1;
                                q.parent.rank -= 1;
                            }
                            else{
                                s.rightChild = q.parent;
                                s.parent = q.parent.parent;
                                q.parent.parent = s;
                                s.rightChild.leftChild = temp;
                                temp.parent = q.parent;
                                s.rank += 1;
                                q.parent.rank -= 1;
                                root = s;
                            }
                        }
                        else if(s.rank - s.rightChild.rank == 1){
                            //left right
                            rotationsPerformed += 2;
                            q.parent.leftChild = temp.rightChild;
                            s.rightChild = temp.leftChild;
                            if(q.parent.parent != null){
                                if(returnWhichChild(q.parent).equals("left"))
                                    q.parent.parent.leftChild = temp;
                                else
                                    q.parent.parent.rightChild = temp;
                                temp.rightChild = q.parent;
                                temp.leftChild = s;
                                temp.parent = q.parent.parent;
                                q.parent.parent = temp;
                                temp.rightChild.leftChild.parent = q.parent;
                                temp.leftChild.rightChild.parent = s;
                                s.parent = temp;
                                temp.rank += 2;
                                temp.leftChild.rank -= 1;
                                temp.rightChild.rank -= 2;
                            }
                            else{
                                temp.rightChild = q.parent;
                                temp.leftChild = s;
                                temp.parent = q.parent.parent;
                                q.parent.parent = temp;
                                temp.rightChild.leftChild.parent = q.parent;
                                temp.leftChild.rightChild.parent = s;
                                s.parent = temp;
                                temp.rank += 2;
                                temp.leftChild.rank -= 1;
                                temp.rightChild.rank -= 2;
                                root = temp;
                            }
                        }
                    }
                    else if(q.parent.rank - q.parent.leftChild.rank == 3){
                        //right
                        WeakAVLNode<K, V> temp = s.leftChild;
                        if(s.rank - s.rightChild.rank == 1){
                            //right right
                            rotationsPerformed += 1;
                            if(q.parent.parent != null){
                                if(returnWhichChild(q.parent).equals("left"))
                                    q.parent.parent.leftChild = s;
                                else
                                    q.parent.parent.rightChild = s;
                                s.leftChild = q.parent;
                                s.parent = q.parent.parent;
                                q.parent.parent = s;
                                s.leftChild.rightChild = temp;
                                temp.parent = q.parent;
                                s.rank += 1;
                                q.parent.rank -= 1;
                            }
                            else{
                                s.leftChild = q.parent;
                                s.parent = q.parent.parent;
                                q.parent.parent = s;
                                s.leftChild.rightChild = temp;
                                temp.parent = q.parent;
                                s.rank += 1;
                                q.parent.rank -= 1;
                                root = s;
                            }
                        }
                        else if(s.rank - s.leftChild.rank == 1){
                            //right left
                            rotationsPerformed += 2;
                            q.parent.rightChild = temp.leftChild;
                            s.leftChild = temp.rightChild;
                            if(q.parent.parent != null){
                                if(returnWhichChild(q.parent).equals("left"))
                                    q.parent.parent.leftChild = temp;
                                else
                                    q.parent.parent.rightChild = temp;
                                temp.leftChild = q.parent;
                                temp.rightChild = s;
                                temp.parent = q.parent.parent;
                                q.parent.parent = temp;
                                temp.leftChild.rightChild.parent = q.parent;
                                temp.rightChild.leftChild.parent = s;
                                s.parent = temp;
                                temp.rank += 2;
                                temp.rightChild.rank -= 1;
                                temp.leftChild.rank -= 2;
                            }
                            else{
                                temp.leftChild = q.parent;
                                temp.rightChild = s;
                                temp.parent = q.parent.parent;
                                q.parent.parent = temp;
                                temp.leftChild.rightChild.parent = q.parent;
                                temp.rightChild.leftChild.parent = s;
                                s.parent = temp;
                                temp.rank += 2;
                                temp.rightChild.rank -= 1;
                                temp.leftChild.rank -= 2;
                                root = temp;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private WeakAVLNode<K, V> findNextSuccessor(WeakAVLNode<K, V> node){
        WeakAVLNode<K, V> temp = node;
        while(node.leftChild.key != null){
            temp = node.leftChild;
            node = node.leftChild;
        }
        return temp;
    }

    private void removeAndReplace(K key, WeakAVLNode<K, V> node){
        if(node.key.compareTo(key) < 0)
            removeAndReplace(key, node.rightChild);
        else if(node.key.compareTo(key) > 0)
            removeAndReplace(key, node.leftChild);
        else{
            if(node.leftChild.key == null && node.rightChild.key == null){
                node.rightChild = null;
                node.leftChild = null;
                node.key = null;
                node.value = null;
                node.rank -= 1;
                if(node != null && node.parent != null)
                    startBalance(node);
            }
            else if(node.leftChild.key != null && node.rightChild.key == null){
                if(node.parent == null){
                    node.leftChild.parent = null;
                    root = node.leftChild;
                }
                else{
                    node.leftChild.parent = node.parent;
                    if(returnWhichChild(node).equals("left"))
                        node.parent.leftChild = node.leftChild;
                    else
                        node.parent.rightChild = node.leftChild;
                    startBalance(node.leftChild);
                }
            }
            else if(node.leftChild.key == null && node.rightChild.key != null){
                if(node.parent == null){
                    node.rightChild.parent = null;
                    root = node.rightChild;
                }
                else{
                    node.rightChild.parent = node.parent;
                    if(returnWhichChild(node).equals("left"))
                        node.parent.leftChild = node.rightChild;
                    else
                        node.parent.rightChild = node.rightChild;
                    startBalance(node.rightChild);
                }
            }
            else{
                WeakAVLNode<K, V> succ = findNextSuccessor(node.rightChild);
                node.key = succ.key;
                node.value = succ.value;
                removeAndReplace(succ.key, node.rightChild);
            }
        }
    }

    public V remove(K key){
        // write your code here
        if(keyExists(key)){
            V removedValue = get(key);
            removeAndReplace(key, root);
            return removedValue;
        }
        return null;
    }

    public V get(K key){
        // write your code here
        if(keyExists(key)) {
            if(root.key.compareTo(key) == 0)
                return root.value;
            else{
                WeakAVLNode<K, V> curr;
                if(root.key.compareTo(key) < 0)
                    curr = root.rightChild;
                else
                    curr = root.leftChild;
                while(curr.key != null){
                    if(curr.key.compareTo(key) < 0)
                        curr = curr.rightChild;
                    else if(curr.key.compareTo(key) > 0)
                        curr = curr.leftChild;
                    else
                        return curr.value;
                }
            }
        }
        return null;
    }

    private void addBetweenKeys(Vector<V> values,WeakAVLNode<K, V> node, K key1, K key2){
        if(node.key == null)
            return;
        else{
            addBetweenKeys(values, node.leftChild, key1, key2);
            if(node.key.compareTo(key1) >= 0 && node.key.compareTo(key2) <= 0)
                values.add(node.value);
            addBetweenKeys(values, node.rightChild, key1, key2);
        }
    }

    public Vector<V> searchRange(K key1, K key2){
        // write your code here
        Vector<V> values = new Vector<>();
        addBetweenKeys(values, root, key1, key2);
        return values;
    }

    public int rotateCount(){
        // write your code here
        return rotationsPerformed;
    }

    private int computeHeight(WeakAVLNode<K, V> node){
        if(node.key == null)
            return 0;
        int lheight = computeHeight(node.leftChild);
        int rheight = computeHeight(node.rightChild);
        return Math.max(lheight, rheight) + 1;
    }

    public int getHeight(){
        // write your code here
        return computeHeight(root);
    }

    private void addCurrentLevel(Vector<K> keys, WeakAVLNode<K, V> node, int level){
        if(node.key == null)
            return;
        else if(level == 1) {
            keys.add(node.key);
        }
        else{
            addCurrentLevel(keys, node.leftChild, level-1);
            addCurrentLevel(keys, node.rightChild, level-1);
        }
    }

    public Vector<K> BFS(){
        // write your code here
        Vector<K> keys = new Vector<>();
        int height = getHeight();
        for(int i = 1; i <= height; i++){
            addCurrentLevel(keys, root, i);
        }
        return keys;
    }

    private void printAll(WeakAVLNode<K, V> node){
        if(node.key != null) {
            System.out.print("key: " + node.key + ", value: " + node.value + ", rank: " + node.rank + ", node.left: " + node.leftChild.key + ", node.right: " + node.rightChild.key);
            if(node.parent != null)
                System.out.print(", node.parent: "+node.parent.key);
            System.out.println();
            printAll(node.leftChild);
            printAll(node.rightChild);
        }
    }

}