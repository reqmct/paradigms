package queue;

import java.util.Objects;

public interface Queue {
    //Pred: element != null
    //Post: end' = end + 1 && a[end'] == element && immut(start, end)
    void enqueue(Object element);


    //Pred: n >= 1
    //Post: R==a[end] && immut(start, end) && n' == n
    Object element();


    //Pred: n>=1
    //Post: n'== n-1 && R == a[start] && a[start] = null && start' = start + 1 && immut(start', end)
    Object dequeue();


    //Pred: true
    //Post: n' == 0 && i = start...end a[i] == null
    void clear();


    //Pred: true
    //Post: R==n && n'==n && immut(sart, end)
    int size();

    //Pred: true
    //Post: R==(n>0) && n' == n && immut(start, end)
    boolean isEmpty();

    //Pred: element != null
    //Post: R = false: for i = start..end a[i] != element иначе R = false && immut(start, end)
    boolean contains(Object element);
    //Pred: element != null
    //Post R = false: for i = start... end a[i] != element && immut(start, end)
    // иначе R = true: ∃ ind: start <= ind <= end && a[ind] == element && a[start..ind-1] != element
    // && a'[0] = a[0] ... a'[ind] = a[ind+1] ... a'[end-1] = a[end]
    boolean removeFirstOccurrence(Object element);
}
