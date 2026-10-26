
public class GenericPractice {

    static class Box<T> {
        private T value;

        public void set(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }
    }

    static class Pair<A, B> {
        private A first;
        private B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }
    }

  
    public static <T> T first(T[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

   
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }

        double sum = 0;

        for (N value : values) {
            sum += value.doubleValue();
        }

        return sum / values.length;
    }

    public static void main(String[] args) {

        System.out.println("===== BOX TEST =====");
        Box<String> box = new Box<>();
        box.set("MiniBank");
        System.out.println("Box value: " + box.get());

        System.out.println("\n===== PAIR TEST =====");
        Pair<String, Integer> pair = new Pair<>("Angel", 5000);
        System.out.println("Name: " + pair.getFirst());
        System.out.println("Balance: " + pair.getSecond());

        System.out.println("\n===== FIRST ELEMENT TEST =====");
        Integer[] numbers = {10, 20, 30};
        System.out.println("First number: " + first(numbers));

        System.out.println("\n===== AVERAGE TEST =====");
        Integer[] a = {10, 20, 30};
        Double[] b = {2.5, 3.5, 4.0};
        Long[] c = {100L, 200L, 300L};

        System.out.println("Integer average: " + average(a));
        System.out.println("Double average: " + average(b));
        System.out.println("Long average: " + average(c));
    }
}
