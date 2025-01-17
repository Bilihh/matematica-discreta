import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * Cada tema té el mateix pes, i l'avaluació consistirà en:
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estàndar que heu de seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No és
 *   necessari seguir-la estrictament, però ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Gabriel Gonzalez Pibernat
 * - Nom 2: Antoni Riutort Company
 * - Nom 3: Ruben Ballesteros Jimenez
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més fàcilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
    /*
     * Aquí teniu els exercicis del Tema 1 (Lògica).
     *
     * Els mètodes reben de paràmetre l'univers (representat com un array) i els
     * predicats adients
     * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és
     * un element de
     * l'univers, podeu fer-ho com `p.test(x)`, té com resultat un booleà. Els
     * predicats de dues
     * variables són de tipus `BiPredicate<Integer, Integer>` i similarment
     * s'avaluen com
     * `p.test(x, y)`.
     *
     * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats
     * retorneu `true`
     * o `false` segons si la proposició donada és certa (suposau que l'univers és
     * suficientment
     * petit com per utilitzar la força bruta)
     */
    static class Tema1 {
        /*
         * És cert que ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
         */
        static boolean exercici1(
                int[] universe,
                BiPredicate<Integer, Integer> p,
                Predicate<Integer> q,
                Predicate<Integer> r) {
            boolean result = true;
            for (int x : universe) {
                for (int y : universe) {
                    if (!(Implicacio(p.test(x, y), (q.test(x) && r.test(y))))) {
                        result = false;
                    }
                }
            }

            return result;
        }

        /*
         * És cert que ∃!x. ∀y. Q(y) -> P(x) ?
         */
        static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {

            for (int y : universe) {
                boolean xTrobada = false;
                for (int x : universe) {
                    if (Implicacio(q.test(y), p.test(x))) {
                        if (!xTrobada) {
                            xTrobada = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        /*
         * És cert que ¬(∃x. ∀y. y ⊆ x) ?
         *
         * Observau que els membres de l'univers són arrays, tractau-los com conjunts i
         * podeu suposar
         * que cada un d'ells està ordenat de menor a major.
         */
        static boolean exercici3(int[][] universe) {
            boolean resultat = false;

            ArrayList<ArrayList<Integer>> universList = new ArrayList<ArrayList<Integer>>();
            for (int[] element : universe) {
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                for (int element2 : element) {
                    tmp.add(element2);
                }
                universList.add(tmp);
            }

            boolean are_all_subsets = true;
            for (ArrayList<Integer> x : universList) {
                are_all_subsets = true;
                for (ArrayList<Integer> y : universList) {
                    boolean is_subset = true;
                    for (Integer num : y) {
                        if (!x.contains(num)) {
                            is_subset = false;
                            break;
                        }
                    }

                    if (!is_subset) {
                        are_all_subsets = false;
                        continue;
                    }
                }
            }

            resultat = are_all_subsets;
            resultat = !resultat;
            return resultat; // TO DO
        }

        /*
         * És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?
         */
        static boolean exercici4(int[] universe, int n) {
            int count = 0;
            for (int x : universe) {

                boolean yvalinicializada = false;

                for (int y : universe) {

                    if ((x * y) % n == 1 % n) {

                        if (!yvalinicializada) {
                            yvalinicializada = true;
                            count++;
                        } else {
                            return false;
                        }

                    }

                }
            }

            if (count == universe.length) {
                return true;
            } else {
                return false;
            }

        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // ∀x,y. P(x,y) -> Q(x) ^ R(y)

            assertThat(
                    exercici1(
                            new int[] { 2, 3, 5, 6 },
                            (x, y) -> x * y <= 4,
                            x -> x <= 3,
                            x -> x <= 3));

            assertThat(
                    !exercici1(
                            new int[] { -2, -1, 0, 1, 2, 3 },
                            (x, y) -> x * y >= 0,
                            x -> x >= 0,
                            x -> x >= 0));

            // Exercici 2
            // ∃!x. ∀y. Q(y) -> P(x) ?

            assertThat(
                    exercici2(
                            new int[] { -1, 1, 2, 3, 4 },
                            x -> x < 0,
                            x -> true));

            assertThat(
                    !exercici2(
                            new int[] { 1, 2, 3, 4, 5, 6 },
                            x -> x % 2 == 0, // x és múltiple de 2
                            x -> x % 4 == 0 // x és múltiple de 4
                    ));

            // Exercici 3
            // ¬(∃x. ∀y. y ⊆ x) ?

            assertThat(
                    exercici3(new int[][] { { 1, 2 }, { 0, 3 }, { 1, 2, 3 }, {} }));

            assertThat(
                    !exercici3(new int[][] { { 1, 2 }, { 0, 3 }, { 1, 2, 3 }, {}, { 0, 1, 2, 3 } }));

            // Exercici 4
            // És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?

            assertThat(
                    !exercici4(
                            new int[] { 0, 5, 7 },
                            13));

            assertThat(
                    exercici4(
                            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
                            11));

        }
    }

    /*
     * Aquí teniu els exercicis del Tema 2 (Conjunts).
     *
     * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com
     * arrays (sense
     * elements repetits). Per tant, un conjunt de conjunts d'enters tendrà tipus
     * int[][].
     *
     * Les relacions també les representarem com arrays de dues dimensions, on la
     * segona dimensió
     * només té dos elements. Per exemple
     * int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
     * i també donarem el conjunt on està definida, per exemple
     * int[] a = {0,1,2};
     *
     * Les funcions f : A -> B (on A i B son subconjunts dels enters) les
     * representam donant int[] a,
     * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar
     * com f.apply(x) (on
     * x és un enter d'a i el resultat f.apply(x) és un enter de b).
     */
    static class Tema2 {
        /*
         * És `p` una partició d'`a`?
         *
         * `p` és un array de conjunts, haureu de comprovar que siguin elements d'`a`.
         * Podeu suposar que
         * tant `a` com cada un dels elements de `p` està ordenat de menor a major.
         */
        static boolean exercici1(int[] a, int[][] p) {

            ArrayList<Integer> pjunt = new ArrayList<Integer>();
            for (int[] x : p) {
                for (int y : x) {
                    pjunt.add(y);
                }
            }

            Object[] tmp = pjunt.toArray();
            int[] pjuntarr = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                pjuntarr[i] = (int) tmp[i];
            }

            Arrays.sort(pjuntarr);
            Arrays.sort(a);
            return Arrays.equals(a, pjuntarr);
        }

        /*
         * Comprovau si la relació `rel` definida sobre `a` és un ordre parcial i que
         * `x` n'és el mínim.
         *
         * Podeu soposar que `x` pertany a `a` i que `a` està ordenat de menor a major.
         */
        static boolean exercici2(int[] a, int[][] rel, int x) {

            // Check Reflexividad
            for (int i = 0; i < a.length; i++) {
                boolean trobat = false;
                for (int j = 0; j < rel.length; j++) {
                    if (rel[j][0] == a[i] && rel[j][1] == a[i]) {
                        trobat = true;
                        break;
                    }
                }
                if (!trobat) {
                    return false;
                }
            }

            // Check transitividad
            ArrayList<Boolean> transitivos = new ArrayList<>();
            for (int i = 0; i < rel.length; i++) {

                int posicioa = rel[i][0];
                int posiciob = rel[i][1];

                for (int j = 0; j < rel.length; j++) {
                    int posicioc = rel[j][0];
                    int posiciod = rel[j][1];

                    if (posiciob == posicioc) {
                        transitivos.add(false);
                        for (int k = 0; k < rel.length; k++) {
                            int posicioe = rel[k][0];
                            int posiciof = rel[k][1];

                            if (posicioa == posicioe && posiciod == posiciof) {
                                transitivos.set(transitivos.size() - 1, true);
                                break;
                            }
                        }
                    }
                }
            }

            for (Boolean trans : transitivos) {
                if (!trans) {
                    return false;
                }
            }

            // Check Antisimetria
            ArrayList<Boolean> antisimetricos = new ArrayList<>();
            for (int i = 0; i < rel.length; i++) {

                int posicioa = rel[i][0];
                int posiciob = rel[i][1];

                for (int j = 0; j < rel.length; j++) {
                    int posicioc = rel[j][0];
                    int posiciod = rel[j][1];

                    if (posiciob == posicioc && posicioa == posiciod) {
                        antisimetricos.add(false);
                        if (posicioa == posiciob) {
                            antisimetricos.set(antisimetricos.size() - 1, true);
                        }
                    }
                }
            }

            for (Boolean antis : antisimetricos) {
                if (!antis) {
                    return false;
                }
            }

            // Check minim
            ArrayList<Integer> minimals = new ArrayList<Integer>();
            for (int i = 0; i < rel.length; i++) {
                int posicioa = rel[i][0];

                boolean minimal = true;
                for (int j = 0; j < rel.length; j++) {
                    int posiciob = rel[j][1];

                    if (posicioa == posiciob && i != j) {
                        minimal = false;
                        break;
                    }
                }

                if (minimal) {
                    if (!minimals.contains(posicioa)) {
                        minimals.add(posicioa);
                    }
                }

            }

            if (minimals.size() != 1 || minimals.get(0) != x) {
                return false;
            }

            return true;
        }

        /*
         * Suposau que `f` és una funció amb domini `dom` i codomini `codom`. Trobau
         * l'antiimatge de
         * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`).
         * Podeu suposar
         * que `y` pertany a `codom` i que tant `dom` com `codom` també estàn ordenats
         * de menor a major.
         */
        static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
            ArrayList<Integer> antiimagenes = new ArrayList<Integer>();
            for (int i : dom) {
                if (f.apply(i) == y) {
                    antiimagenes.add(i);
                }
            }

            Object[] tmp = antiimagenes.toArray();
            int[] pjuntarr = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                pjuntarr[i] = (int) tmp[i];
            }

            Arrays.sort(pjuntarr);

            return pjuntarr;
        }

        /*
         * Suposau que `f` és una funció amb domini `dom` i codomini `codom`. Retornau:
         * - 3 si `f` és bijectiva
         * - 2 si `f` només és exhaustiva
         * - 1 si `f` només és injectiva
         * - 0 en qualsevol altre cas
         *
         * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major. Per
         * comoditat, podeu
         * utilitzar les constants definides a continuació:
         */
        static final int NOTHING_SPECIAL = 0;
        static final int INJECTIVE = 1;
        static final int SURJECTIVE = 2;
        static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

        static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
            boolean inyectiva = true;
            boolean subyectiva = false;

            ArrayList<Integer> AntiImgs = new ArrayList<Integer>();
            ArrayList<Integer> aux = new ArrayList<Integer>();
            ArrayList<Integer> Imgs = new ArrayList<Integer>();

            for (int i : dom) {
                AntiImgs.add(i);
                aux.add(i);
            }

            for (int i : codom) {
                Imgs.add(i);
            }

            ArrayList<Integer> TrobatAntiImgs = new ArrayList<Integer>();
            ArrayList<Integer> TrobatImgs = new ArrayList<Integer>();

            for (int i : aux) {
                TrobatAntiImgs.add(i);
                AntiImgs.remove((Object) i);

                if (TrobatImgs.contains(f.apply(i))) {
                    inyectiva = false;
                }
                TrobatImgs.add(f.apply(i));
                Imgs.remove((Object) f.apply(i));

            }

            subyectiva = AntiImgs.size() == 0 && Imgs.size() == 0;

            if (inyectiva && subyectiva) {
                return BIJECTIVE;
            }
            if (inyectiva) {
                return INJECTIVE;
            }
            if (subyectiva) {
                return SURJECTIVE;
            }
            return NOTHING_SPECIAL;
        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // `p` és una partició d'`a`?

            assertThat(
                    exercici1(
                            new int[] { 1, 2, 3, 4, 5 },
                            new int[][] { { 1, 2 }, { 3, 5 }, { 4 } }));

            assertThat(
                    !exercici1(
                            new int[] { 1, 2, 3, 4, 5 },
                            new int[][] { { 1, 2 }, { 5 }, { 1, 4 } }));

            // Exercici 2
            // és `rel` definida sobre `a` d'ordre parcial i `x` n'és el mínim?

            ArrayList<int[]> divisibility = new ArrayList<int[]>();

            for (int i = 1; i < 8; i++) {
                for (int j = 1; j <= i; j++) {
                    if (i % j == 0) {
                        // i és múltiple de j, és a dir, j|i
                        divisibility.add(new int[] { j, i });
                    }
                }
            }

            assertThat(
                    exercici2(
                            new int[] { 1, 2, 3, 4, 5, 6, 7 },
                            divisibility.toArray(new int[][] {}),
                            1));

            assertThat(
                    !exercici2(
                            new int[] { 1, 2, 3 },
                            new int[][] { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 1, 2 }, { 2, 3 } },
                            1));

            assertThat(
                    !exercici2(
                            new int[] { 1, 2, 3, 4, 5, 6, 7 },
                            divisibility.toArray(new int[][] {}),
                            2));

            // Exercici 3
            // calcular l'antiimatge de `y`

            assertThat(
                    Arrays.equals(
                            new int[] { 0, 2 },
                            exercici3(
                                    new int[] { 0, 1, 2, 3 },
                                    new int[] { 0, 1 },
                                    x -> x % 2, // residu de dividir entre 2
                                    0)));

            assertThat(
                    Arrays.equals(
                            new int[] {},
                            exercici3(
                                    new int[] { 0, 1, 2, 3 },
                                    new int[] { 0, 1, 2, 3, 4 },
                                    x -> x + 1,
                                    0)));

            // Exercici 4
            // classificar la funció en res/injectiva/exhaustiva/bijectiva

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1, 2, 3 },
                            x -> (x + 1) % 4) == BIJECTIVE);

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1, 2, 3, 4 },
                            x -> x + 1) == INJECTIVE);

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1 },
                            x -> x / 2) == SURJECTIVE);

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3 },
                            new int[] { 0, 1, 2, 3 },
                            x -> x <= 1 ? x + 1 : x - 1) == NOTHING_SPECIAL);
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 3 (Aritmètica).
     *
     */
    static class Tema3 {
        /*
         * Donat `a`, `b` retornau el màxim comú divisor entre `a` i `b`.
         *
         * Podeu suposar que `a` i `b` són positius.
         */
        static int exercici1(int a, int b) {
            int tmp;
            while (b != 0) {
                tmp = b;
                b = a % b;
                a = tmp;
            }
            return a; // TO DO
        }

        /*
         * Es cert que `a``x` + `b``y` = `c` té solució?.
         *
         * Podeu suposar que `a`, `b` i `c` són positius.
         */
        static boolean exercici2(int a, int b, int c) {
            boolean solucion;
            int mcd;
            mcd = exercici1(a, b);
            if (c % mcd == 0) {
                solucion = true;
            } else {
                solucion = false;
            }

            return solucion; // TO DO
        }

        /*
         * Quin es l'invers de `a` mòdul `n`?
         *
         * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
         */
        static int exercici3(int a, int n) {
            int mcd = exercici1(a, n);
            int resultado = 0;
            if (mcd != 1) {
                resultado = -1;
                return resultado;
            }
            for (int i = 0; i < n; i++) {
                if ((a * i - 1) % n == 0) {
                    return resultado = i;
                }
            }
            return resultado;
        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // `mcd(a,b)`

            assertThat(
                    exercici1(2, 4) == 2);

            assertThat(
                    exercici1(1236, 984) == 12);

            // Exercici 2
            // `a``x` + `b``y` = `c` té solució?

            assertThat(
                    exercici2(4, 2, 2));
            assertThat(
                    !exercici2(6, 2, 1));
            // Exercici 3
            // invers de `a` mòdul `n`
            assertThat(exercici3(2, 5) == 3);
            assertThat(exercici3(2, 6) == -1);
        }
    }

    static class Tema4 {
        /*
         * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i
         * la mida del graf.
         */
        static int[] exercici1(int[][] A) {

            int ordre = A.length;
            int mida = 0;

            for (int[] i : A) {
                for (int j : i) {
                    if (j == 1) {
                        mida++;
                    }
                }
            }
            mida = mida / 2;

            return new int[] { ordre, mida }; // TO DO
        }

        /*
         * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es
         * eulerià.
         */
        static boolean exercici2(int[][] A) {

            boolean esConexo = DFS_Ex2(A, new ArrayList<Integer>(), 0).size() == A.length;

            for (int[] i : A) {
                int count = 0;
                for (int j : i) {
                    count += j;
                }
                if (!(esConexo && count % 2 == 0)) {
                    return false;
                }
            }

            return true;
        }

        /*
         * Donat `n` el número de fulles d'un arbre arrelat i `d` el nombre de fills
         * dels nodes interiors i de l'arrel,
         * retornau el nombre total de vèrtexos de l'arbre
         *
         */
        static int exercici3(int n, int d) {
            ArrayList<int[]> relacio = new ArrayList<int[]>();
            relacio = addChildren(relacio, 0, d);

            while (getLeafCount(relacio) != n) {
                relacio = addChildren(relacio, getMax(relacio) - (d - 1), d);
            }
            return getMax(relacio) + 1;
        }

        private static int getLeafCount(ArrayList<int[]> relacio) {
            int count = 0;
            for (int[] i : relacio) {
                boolean maximal = true;
                for (int[] j : relacio) {
                    if (i[1] == j[0]) {
                        maximal = false;
                    }
                }

                if (maximal){
                    count++;
                }
            }
            return count;
        }

        private static ArrayList<int[]> addChildren(ArrayList<int[]> relacio, int node, int quantitat) {
            for (int i = 0; i < quantitat; i++) {
                relacio.add(new int[] {node, getMax(relacio) + 1});
            }
            return relacio;
        }

        private static int getMax(ArrayList<int[]> relacio) {
            int result = 0;
            for (int[] i : relacio) {
                result = Math.max(result, i[1]);
            }
            return result;
        }

        /*
         * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el
         * graf conté algún cicle.
         */
        static boolean exercici4(int[][] A) {
            if (DFS_Ex4(A, new ArrayList<Integer>(), 0, 0) == null){
                return true;
            }else{
                return false;
            }
        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
         * `main`)
         */
        static void tests() {
            // Exercici 1
            // `ordre i mida`

            assertThat(
                    Arrays.equals(exercici1(new int[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } }),
                            new int[] { 3, 2 }));

            assertThat(
                    Arrays.equals(
                            exercici1(new int[][] { { 0, 1, 0, 1 }, { 1, 0, 1, 1 }, { 0, 1, 0, 1 }, { 1, 1, 1, 0 } }),
                            new int[] { 4, 5 }));

            // Exercici 2
            // `Es eulerià?`

            assertThat(
                    exercici2(new int[][] { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } }));
            assertThat(
                    !exercici2(new int[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } }));
            // Exercici 3
            // `Quants de nodes té l'arbre?`
            assertThat(exercici3(5, 2) == 9);
            assertThat(exercici3(7, 3) == 10);

            // Exercici 4
            // `Conté algún cicle?`
            assertThat(
                    exercici4(new int[][] { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } }));
            assertThat(
                    !exercici4(new int[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } }));

        }
    }

    /*
     * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que
     * haurien de donar
     * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres
     * (no els tendrem en
     * compte, però és molt recomanable).
     *
     * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor
     * sigui `true`.
     */
    public static void main(String[] args) {
        Tema1.tests();
        Tema2.tests();
        Tema3.tests();
        Tema4.tests();
    }

    static void assertThat(boolean b) {
        if (!b)
            throw new AssertionError();
    }

    static boolean Implicacio(boolean a, boolean b) {
        if (!a) {
            if (!b) {
                return true;
            } else {
                return true;
            }
        } else {
            if (!b) {
                return false;
            } else {
                return true;
            }
        }
    }

    static ArrayList<Integer> DFS_Ex2(int[][] matrix, ArrayList<Integer> visited, int node) {
        if (!visited.contains(node)) {
            visited.add(node);
        }

        for (int i = 0; i < matrix[node].length; i++) {
            Integer connectionNode = matrix[node][i];
            if (connectionNode == 1 && !visited.contains(i)) {
                visited = DFS_Ex2(matrix, visited, i);
            }
        }

        return visited;
    }


    static ArrayList<Integer> DFS_Ex4(int[][] matrix, ArrayList<Integer> visited, int node, int parent) {
        if (!visited.contains(node)) {
            visited.add(node);
        }

        for (int i = 0; i < matrix[node].length; i++) {
            Integer connectionNode = matrix[node][i];
            
            if (connectionNode == 1 && visited.contains(i) && i != parent) {
                return null;
            }

            if (connectionNode == 1 && !visited.contains(i)) {
                visited = DFS_Ex4(matrix, visited, i, node);
                if (visited == null){
                    return null;
                }
            }
        }

        return visited;
    }

}

// vim: set textwidth=100 shiftwidth=2 expandtab :
