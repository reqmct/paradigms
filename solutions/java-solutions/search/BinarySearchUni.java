package search;

public class BinarySearchUni {

    //Pre: -1 < ind < a.length && a[-1] == -inf && a[a.length] == -inf
    //Post: R = (a[ind + 1] >= a[ind])
    private static boolean checkGoingUp(int[] a, int ind) {
        if (ind == a.length - 1) {
            //a[ind] > -inf = a.length
            //a[ind] > a[ind + 1]
            return false;
        }
        return a[ind + 1] >= a[ind];
        //a[ind + 1] >= a[ind]
    }

    //Pre: exist i: -inf < a[0] <= a[1] ... a[i-1] <= a[i] > a[i+1] >= ... >= a[n-1] > -inf && l = -1 && r = a.length
    //Post: R = i
    private static int iterativeUni(int[] a, int l, int r) {
        // Pre && ] a[-1] = -inf && a[a.length] = -inf
        //I: a[-1] <= a[0] <= a[1] ... a[i-1] <= a[i] > a[i+1] >= ... >= a[n-1] > a[a.length]
        //l = -1 && r = a.length
        // a[l] <= a[0] <= a[1] ... a[i-1] <= a[i] > a[i+1] >= ... >= a[n-1] >= a[r]
        while (r - l > 1) {
            //I && && r' - l' > 1
            // I && 1 + 2l' < l' + r' < 2r' - 1
            int centre = (r + l) / 2;
            // I && l' + 0.5 < centre < r' - 0.5
            // I && l'< centre < r' && a[-1] = -inf && a[a.size] = -inf
            // I && -1 < centre < a.length && a[-1] == -inf && a[a.length] == -inf
            if (checkGoingUp(a, centre)) {
                //a[centre] <= a[centre + 1]
                l = centre;
                //a[l'] <= a[l' + 1]
            } else {
                //a[centre] > a[centre + 1]
                r = centre;
                //a[r'] > a[r' + 1]
            }
        }
        // l = r - 1 && a[l] <= a[l + 1] > a[l + 2]
        // a[r-1] <= a[r] > a[r + 1]
        return r;
    }

    /*
    ] a[-1] = inf && a[a.length] = -inf
    Pre: exist i: a[l] <= a[l + 1] <= ... a[i-1] <= a[i] > a[i+1] >= ... >= a[r] >= a[r + 1] &&  -1 <= l < r <= a.length
    Post: a[l] <= a[l + 1] <= ... a[R-1] <= a[R] > a[R+1] >= ... >= a[r] >= a[r + 1] &&  -1 <= l < r <= a.length
     */
    private static int recursiveUni(int[] a, int l, int r) {
        //Pre
        if (r - l <= 1) {
            // l = r - 1 && a[l] <= a[l + 1] > a[l + 2]
            // a[r-1] <= a[r] > a[r + 1]
            return r;
        }
        //Pre && r - l > 1
        // Pre && 1 + 2l' < l' + r' < 2r' - 1
        int centre = (r + l) / 2;
        // Pre && l' + 0.5 < centre < r' - 0.5
        // Pre && l'< centre < r' && a[-1] = -inf && a[a.size] = -inf
        // Pre && -1 < centre < a.length && a[-1] == -inf && a[a.length] == -inf
        if (checkGoingUp(a, centre)) {
            //Pre && a[centre] <= a[centre + 1]
            //a[centre] <= a[centre + 1] <= ... a[i-1] <= a[i] > a[i+1] >= ... >= a[r] >= a[r + 1]
            // -1 <= l < centre < r <= a.size
            // -1 <= centre < r <= a.size
            return recursiveUni(a, centre, r);
        } else {
            //Pre && a[centre] > a[centre + 1]
            //a[l] <= a[l + 1] <= ... a[i-1] <= a[i] > a[i+1] >= ... >= a[centre] >= a[centre + 1]
            // -1 <= l < centre < r <= a.size
            // -1 <= centre < r <= a.size
            return recursiveUni(a, l, centre);
        }
    }

    //Pre: -inf < int(args[0]) <= int(args[1]) ... int(args[i-1]) <= int(args[i])
    // > int(args[i+1]) >= ... >= int(args[n-1]) > -inf
    //Post: R = i
    public static void main(String[] args) {
        // args.length
        int n = args.length;
        // Pre && n = args.length
        int[] a = new int[n];
        //Pre && n = args.length = a.length
        int isEven = 0;
        for (int i = 0; i < n; i++) {
            //int(args'[i])
            a[i] = Integer.parseInt(args[i]);
            //a'[i] = int(args'[i])
            // isEven' = (a[0] % 2 + ... + a[i-1] % 2) % 2
            isEven = (isEven + a[i] % 2) % 2;
            // isEven' = (a[0] % 2 + ... + a[i] % 2) % 2
        }
        // int(args) = a && Pre
        // isEven = (a[0] % 2 + ... + a[a.size-1] % 2) % 2
        // isEven = (a[0] + ... + a[a.size-1]) % 2
        if (isEven == 0) {
            //(a[0] + ... + a[a.size-1]) % 2 == 0
            // -inf < a[0] <= a[1] ... a[i-1] <= a[i] > a[i+1] >= ... >= a[n-1] > -inf
            // a[l] <= a[l + 1] <= ... a[i-1] <= a[i] > a[i+1] >= ... >= a[r] >= a[r + 1] &&  -1 <= l < r <= a.size
            System.out.println(recursiveUni(a, -1, a.length));
            // R = i
        } else {
            //(a[0] + ... + a[a.size-1]) % 2 == 1
            // -inf < a[0] <= a[1] ... a[i-1] <= a[i] > a[i+1] >= ... >= a[n-1] > -inf
            System.out.println(iterativeUni(a, -1, a.length));
            // R = i
        }
    }
}
