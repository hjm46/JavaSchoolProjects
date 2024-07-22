package cs1501_p1;

public class BST<T extends Comparable<T>> implements BST_Inter<T>
{
    private BTNode<T> root;

    public BST()
    {
        root = null;
    }

    public void put(T key)
    {
        BTNode<T> cur = root;
        if(cur == null)
        {
            root = new BTNode<T> (key);
            return;
        }
        if(contains(key) == true)
            return;

        root = put_rec(cur, key);
    }

    private BTNode<T> put_rec(BTNode<T> cur, T key)
    {
        if (cur == null)
            return new BTNode<T> (key);
        
        if(cur.getKey().compareTo(key) > 0)
            cur.setLeft(put_rec(cur.getLeft(), key));

        else if(cur.getKey().compareTo(key) < 0)
            cur.setRight(put_rec(cur.getRight() , key));

        return cur;

    }

    public boolean contains(T key)
    {
        BTNode<T> cur = root;
        if (cur == null)
            return false;
        return contains_rec(cur, key);
    }

    private boolean contains_rec(BTNode<T> cur, T key)
    {        
        if (cur == null)
            return false;

        if(cur.getKey().compareTo(key) > 0)
            return contains_rec(cur.getLeft(), key);
        
        else if(cur.getKey().compareTo(key) < 0)
            return contains_rec(cur.getRight(), key);

        else
            return true;
    }

    public void delete(T key)
    {
        if(root == null || key == null)
            return;
        
        if(contains(key) == false)
            return;

        delete_rec(root, key);
        return;
    }

    private BTNode<T> delete_rec(BTNode<T> cur, T key)
    {
        if (cur == null)
            return null;
        
        if(cur.getKey().compareTo(key) > 0)
            cur.setLeft(delete_rec(cur.getLeft(), key));

        else if(cur.getKey().compareTo(key) < 0)
            cur.setRight(delete_rec(cur.getRight(), key));

        else
        {
            //no children
            if(cur.getLeft() == null && cur.getRight() == null)
              return null;

            //one child
            if (cur.getLeft() == null)
                return cur.getRight();

            else if (cur.getRight() == null)
                return cur.getLeft();

            //both children
            else
            {
                BTNode<T> left_sub = cur.getLeft();
                BTNode<T> right_sub = cur.getRight();

                BTNode<T> temp = min(right_sub);
                temp.setLeft(left_sub);
                temp = right_sub;

                if(root.getKey().compareTo(key) > 0)
                    root.setLeft(temp);
                
                else if(root.getKey().compareTo(key) < 0)
                    root.setRight(temp);
                else
                    root = temp;

                return temp;
            }
        }
        return cur;
    }

    private BTNode<T> min(BTNode<T> min)
    {
        if(min.getLeft() == null)
            return min;
        else
            return min(min.getLeft());
    }

    public int height()
    {
        if (root == null)
            return 0;

        return height_rec(root);
    }

    private int height_rec(BTNode<T> cur)
    {
        if(cur == null)
            return 0;
        int left = height_rec(cur.getLeft());
        int right = height_rec(cur.getRight());

        return Math.max(left, right) + 1;
    }


    public boolean isBalanced()
    {
        if (root == null || (root.getLeft() == null && root.getRight() == null))
            return true;
        
        return isBal_rec(root);
    }

    private boolean isBal_rec(BTNode<T> cur)
    {
        int left = 0;
        int right = 0;
        if(cur.getLeft() != null)
            left = height_rec(cur.getLeft());

        if(cur.getRight() != null)
            right = height_rec(cur.getRight());

        if (Math.abs(left - right) > 1)
            return false;

        return true;
    }

    public String inOrderTraversal()
    {
        BTNode<T> cur = root;
        String elements = "";
        if (cur == null)
            return elements;
        elements = elements + traversal(cur, elements);
        return elements.substring(0, elements.length()-1);
    }

    private String traversal(BTNode<T> cur, String elements)
    {
        if(cur.getLeft() != null)
            elements = traversal(cur.getLeft(), elements) + "";
        
        elements = elements + cur.getKey() + ":";

        if(cur.getRight() != null)
            elements = traversal(cur.getRight(), elements) + "";
        
        return elements;
    }

    public String serialize()
    {
        BTNode<T> cur = root;
        String elements = "";
        if (cur == null)
            return elements;
        String start = "R(" + cur.getKey() + "),";
        String left = preorder(cur.getLeft(), elements);
        String right = preorder(cur.getRight(), elements);
        elements = start + left + right;
        return elements.substring(0, elements.length()-1);
    }

    private String preorder(BTNode<T> cur, String elements)
    {
        if (cur == null)
            return "";
            
        if (cur.getLeft() == null && cur.getRight() == null)
            elements = "L(" + cur.getKey() + "),";

        else if (cur.getLeft() == null && cur.getRight() != null)
            elements = "I(" + cur.getKey() + "),X(NULL),";

        else if (cur.getLeft() != null && cur.getRight() == null)
            elements = "I(" + cur.getKey() + "),X(NULL),";

        else
            elements = "I(" + cur.getKey() + "),";

        elements = elements + preorder(cur.getLeft(), elements);
        elements = elements + preorder(cur.getRight(), elements);
        
        return elements;
    }

    public BST_Inter<T> reverse()
    {
        BTNode<T> cur = root;
        if (cur == null)
            return null;

        BST<T> new_tree = new BST();
        deep_copy(cur, new_tree);
        return (BST_Inter<T>) new_tree;
    }

    private void deep_copy(BTNode<T> cur, BST<T> new_tree)
    {
        if (cur == null)
            return;
        new_tree.reverse_put(cur.getKey());
        deep_copy(cur.getLeft(), new_tree);
        deep_copy(cur.getRight(), new_tree);
        return;
    }

    private void reverse_put(T key)
    {
        BTNode<T> cur = root;
        if(cur == null)
        {
            root = new BTNode<T> (key);
            return;
        }

        root = reverse_rec(cur, key);
    }

    private BTNode<T> reverse_rec(BTNode<T> cur, T key)
    {
        if (cur == null)
            return new BTNode<T> (key);
        
        if(cur.getKey().compareTo(key) < 0)
            cur.setLeft(put_rec(cur.getLeft(), key));

        else if(cur.getKey().compareTo(key) > 0)
            cur.setRight(put_rec(cur.getRight(), key));

        return cur;
    }
}