import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by r028367 on 08/06/2017.
 *
 * http://docs.oracle.com/javase/7/docs/api/java/util/IdentityHashMap.html
 * https://dzone.com/articles/difference-between-hashmap-and
 * https://stackoverflow.com/questions/838528/use-cases-for-identityhashmap
 * https://himanshugpt.wordpress.com/2010/03/24/difference-between-hashmap-and-identityhashmap/
 */
public class StudyIdentityHashMap {


    /**
     * Segundo a documentacao, a classe {@link java.util.IdentityHashMap} implementa a interface Map
     * porem quebra o contrado da implementacao do metodo equals. A proposta do metodo equals na maioria das implementacoes
     * de Map eh a comparacao de 2 elementos k1 e k2, respectivamente representado a chave do elemento 1
     * e 2 onde a comparacao eh feita da seguinte forma k1.equals(k2)
     *
     * Ja em IdentityHashMap a comparacao entre chaves ou valores eh feita da seguinte forma k1 == k2
     * ou seja, HasMap compara a nivel de Objeto(Valor) e o IdentityHashMap compara a nivel de Referencia.
     *
     * Para o IdentityHashMap, "a" eh diferentece de new String("a"), para o HashMap nao pois a comparacao
     * eh a nivel de valor
     * */


    public static void test() {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new IdentityHashMap<>();
        map1.put("a", 1);
        map1.put(new String("a"), 2);
        map1.put("a", 3);

        map2.put("a", 1);
        map2.put(new String("a"), 2);
        map2.put("a", 3);

        System.out.printf("%d %d\n", map1.size(), map2.size());
    }

    public static void main(String[] args) {
        test();
    }
}
