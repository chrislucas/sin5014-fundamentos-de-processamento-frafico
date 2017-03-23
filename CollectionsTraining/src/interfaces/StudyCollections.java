package interfaces;

import javafx.print.Collation;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by r028367 on 10/03/2017.
 */
public class StudyCollections {

    /**
     *
     * Sobre generics
     * Type parameter <T>
     * Formal paramatger (String, Integer, Float, int, float, double ...)
     * "The difference is that the inputs to formal parameters are values, while the inputs to type parameters are types."
     * */

    private static <T> void convert(Collection<T> collection) {
        List<T> list = (List) collection;
        ArrayList<T> arrayList = new ArrayList(collection);


        for(T t : arrayList) {
            System.out.println(t.toString());
        }

        list = new ArrayList<T>(collection);
        Stream<T> ssl = list.stream();
    }

    private static void convert(ArrayList arrayList) {
        Collection collection = arrayList;
    }

/**
 * http://stackoverflow.com/questions/31044041/how-do-i-iterate-over-a-stream-in-java-using-for
 * */
    public static void testStreamAPI8(Collection<Integer> collection) {
        Stream<Integer> ssc = collection.stream();
        /*
        java.lang.IllegalStateException: stream has already been operated upon or closed
        Nao funciona
        ssc.filter( o -> o.intValue() % 2 == 0 );
        for(Object i : ssc.toArray()) {
            System.out.println(i);
        }
        */
        /*
        Object [] filtered = ssc.filter( o -> o.intValue() % 2 == 0 ).toArray();
        for(Object f : filtered) {
            System.out.println(f);
        }
        */
/*
        for(Object o : ssc.filter( o -> o.intValue() % 2 == 0 ).toArray(Object[]::new)) {
            System.out.println(o);
        }
*/
        //ssc.filter( o -> o.intValue() % 2 == 0 ).forEach(e -> System.out.println(e));
        //ssc.filter( o -> o.intValue() % 2 == 0 ).forEach(System.out::println);
        //ssc.filter( o -> o.intValue() % 2 == 0 ).forEach(StudyCollections::process);

        // o metodo to Array faz uma copia do vetor
            ssc
                .filter( o -> o.intValue() % 2 == 0 )
                    .forEach( StudyCollections::multiply);
        //.toArray();


        /*
        * IntFunction
        * */
    }

    private static <T> void multiply(T data) {
        return;
    }


    private static void multiply(int n, int m) {
        return;
    }

    public static <T> void process(T data) {
        System.out.println(data);
        return;
    }

    static <T> Stream<T> iteratorToFiniteStream(final Iterator<T> iterator) {
        final Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    static <T> Stream<T> iteratorToInfiniteStream(final Iterator<T> iterator) {
        return Stream.generate(iterator::next);
    }


    private static <T> void convert(Iterator<T> iterator) {
        Collection<T> collection = (Collection) iterator;
        for(T t : collection) {
            System.out.println(t.toString());
        }
    }

    private static <T> boolean containsAll(Collection<T> A, Collection<T> B) {
        return A.containsAll(B);
    }

    private static void testConvert() {
        Collection<String> collection = new ArrayList<>();
        for(int i=0; i<50; i++) {
            collection.add(String.valueOf(i));
        }
        convert(collection);
        Collection<String> collection1 = new ArrayList<>();
        collection1.add("1");
        collection1.add("2");
        collection1.add("3");
        System.out.println(containsAll(collection1, collection));
        System.out.println(containsAll(collection, collection1));
        Collection<Integer> collection2 = new ArrayList<>();
        for(int i=0; i<50; i++)
            collection2.add(i);
        // Gera um erro, a menos que eu remova o Tipo Generico <T>
        // System.out.println(containsAll(collection, collection2));

    }

    private static void testStreamAPI8 () {
        Collection<Integer> collection2 = new ArrayList<>();
        for(int i=0; i<50; i++)
            collection2.add(i);
        testStreamAPI8(collection2);
    }

    public static void main(String[] args) {
        //testConvert();
        testStreamAPI8();
    }

}
