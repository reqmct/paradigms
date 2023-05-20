package search;

public class BinarySearch {

    /*
    Pre a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]
    Post a[R-1] > x >= a[R]
     */
    private static int iterativeBinarySearch(int[] a, int x) {
        // ] a[-1] = inf && a[a.size] = -inf
        // I: a[-1] > x >= a[a.size]
        int l = -1;
        //a[l] > x >= a[a.size]
        int r = a.length;
        //a[l] > x >= a[r]
        while (r - l > 1) {
            // a[l'] > x >= a[r'] && r' - l' > 1
            // I && 1 + 2l' < l' + r' < 2r' - 1
            // I && 0.5 + l' < (l' + r') / 2 < r' - 0.5
            int centre = (r + l) / 2;
            // I && l' + 0.5 < centre < r' - 0.5
            // I && l' < centre < r'
            if (a[centre] <= x) {
                // a[l'] > x >= a[r'] && x >= a[centre]
                r = centre;
                // a[l'] > x >= a[r']
            } else {
                // a[l'] > x >= a[r'] && a[centre] > x
                l = centre;
                // a[l'] > x >= a[r']
            }
            // a[l'] > x >= a[r']
        }
        //a[l] > x >= a[r] && r <= l + 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]
        //a[l] > x >= a[r] && r - 1 <= l && i = 1 ... a.size - 1 a[i] <= a[i-1]
        // a[r-1] >= a[l] > x >= a[r]
        // a[r-1] > x >= a[r]
        return r;
    }


    /*
    // ] a[-1] = inf && a[a.size] = -inf
    Pre a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[l] > x >= a[r] &&  -1 <= l < r <= a.size
    Post a[R-1] > x >= a[R]
     */
    private static int recursiveBinarySearch(int[] a, int l, int r, int x) {
        // a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[l] > x >= a[r] &&  -1 <= l < r <= a.size
        if (r - l > 1) {
            // I && a[l] > x >= a[r] && r - l > 1
            // I && 1 + 2l < l + r < 2r - 1
            int centre = (r + l) / 2;
            // I && l + 0.5 < centre < r - 0.5
            // I && l < centre < r
            if (a[centre] <= x) {
                // I && a[l] > x >= a[r] && x >= a[centre] && l < centre < r
                /*
                a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[l] > x >= a[centre] => a[r]
                &&  -1 <= l < centre < r <= a.size
                */
                /*
                a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[l] > x >= a[centre]
                 &&  -1 <= l < centre <= a.size
                */
                return recursiveBinarySearch(a, l, centre, x);
                // a[R-1] > x >= a[R]
            } else {
                // I && a[centre] > x && l < centre < r
                /*
                a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[l] > x >= a[r] &&  -1 <= l < r <= a.size
                && a[centre] > x && l < centre < r
                */
                /*
                a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[centre] > x >= a[r] &&  -1 <= centre < r <= a.size
                */
                return recursiveBinarySearch(a, centre, r, x);
                // a[R-1] > x >= a[R]
            }
        }
        //a[l] > x >= a[r] && r <= l + 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]
        //a[l] > x >= a[r] && r - 1 <= l && i = 1 ... a.size - 1 a[i] <= a[i-1]
        // a[r-1] >= a[l] > x >= a[r]
        // a[r-1] > x >= a[r]
        return r;
    }

    /*
    args.size >= 2 && i = 2 ... args.size - 1 int(args[i]) <= int(args[i-1])
     */
    public static void main(String[] args) {
        //I: args.size >= 2 && i = 2 ... args.size - 1 int(args[i]) <= int(args[i-1])
        int x = Integer.parseInt(args[0]);
        // args[0] % 2
        int isEven = x % 2;
        // isEven = args[0] % 2
        // I
        int n = args.length - 1;
        // n >= 1 && i = 2 ... a.size - 1 int(args[i]) <= int(args[i-1)
        int[] a = new int[n];
        // n = a.size >= 1  && i = 2 ... a.size - 1 int(args[i]) <= int(args[i-1])
        for (int i = 0; i < n; i++) {
            //n = a.size >= 1 && int(args[j]) <= (args[j-1]) при j>=2
            a[i] = Integer.parseInt(args[i + 1]);
            // isEven' = (args[0] % 2 +...+ args[i] % 2) % 2
            isEven = (isEven + (a[i] % 2)) % 2;
            // isEven' = (args[0] % 2 + ... + args[i + 1] % 2) % 2
            //n = a.size >= 1 && a[i] <= a[i-1] при i >= 1
        }
        // n = a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]
        // isEven = (args[0] % 2 + ... + a[args.size - 1] % 2) % 2
        /* n = a.size >= 1 && i = 1 ... a.size - 1 a[i] <= a[i-1]  && a[-1] > x >= a[a.size]
         &&  -1 <= -1 < n <= a.size
        */
        if (isEven == 0) {
            //(args[0] % 2 + ... + a[args.size - 1] % 2) % 2 == 0
            //(args[0]  + ... + a[args.size - 1] ) % 2 == 0
            System.out.println(recursiveBinarySearch(a, -1, n, x));
        } else {
            //(args[0] % 2 + ... + a[args.size - 1] % 2) % 2 == 1
            //(args[0]  + ... + a[args.size - 1] ) % 2 == 1
            System.out.println(iterativeBinarySearch(a, x));
        }
    }
}