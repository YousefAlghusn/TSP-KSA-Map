package test;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Stack;
import java.util.Comparator;
import java.math.*;
import org.jxmapviewer.viewer.GeoPosition;

class node {

    int index;
    node parent;
    double pathCost;
    double totalCost;
    int depth;
    GeoPosition position;
    Point point;
    ArrayList<Edge> adjacencies = new ArrayList<Edge>();

    public node(int index) {
        this.index = index;
    }

    public node(int index, GeoPosition p) {
        this.index = index;
        this.position = p;
    }

    public double getCostToParent() {
        if (parent == null) {
            return 0;
        }
        for (Edge e : adjacencies) {
            if (e.target.equals(parent)) {
                return e.cost;
            }
        }
        return 0;
    }

    public double getDistanceTo(node city) {
        TSP t = new TSP();
        return t.UCS(index, city.index);
    }

    public String getName() {
        return TSP.letters[index];
    }
}

class Edge {

    double cost;
    node target;

    public Edge(node target, double cost) {
        this.cost = cost;
        this.target = target;

    }
}

public class TSP {
    //cities

    static node nodes[] = new node[153];
    static String letters[] = {
        "ابها", "ابو عريش", "احد المسارحه", "احد رفيده", "الاحساء", "الأسياح", "أضم", "الافلاج", "ام حزم", "املج", "الباحة", "بارق",
        "بالقرن", "البتراء", "بحرة", "البدائع", "بدر", "بدر الجنوب", "البدع", "البرك", "بريده", "بقعاء", "بقيق", "البكيريه", "بلجرشي",
        "بني حسن", "بيش", "بيشه", "تبوك", "تثليث", "تربه", "تنومة", "تيماء", "ثادق", "ثار", "جازان", "الجبيل", "جدة", "الجموم", "الحائط",
        "حائل", "حبونا", "الحجرة", "الحرث", "الحرجه", "الحريق", "حريملاء", "حفر الباطن", "حقل", "الحناكيه", "حوطه بنى تميم", "خباش", "الخبر",
        "الخرج", "الخرمة", "الخفجي", "خليص", "خميس مشيط", "خيبر", "الدائر", "الدرب", "الدرعيه", "الدلم", "الدمام", "الدوادمى", "دومة الجندل",
        "رابغ", "رأس تنوره", "رجال المع", "الرس", "رفحاء", "رماح", "رنيه", "الرياض", "رياض الخبراء", "الريث", "الرين", "الزلفى", "سراة عبيده",
        "سكاكا", "السليل", "السليمي", "سميراء", "شروره", "شرى", "شقراء", "الشماسيه", "الشملى", "الشنان", "صامطة", "صبياء", "ضباء", "ضرما",
        "ضريه", "ضمد", "الطائف", "طبرجل", "طريب", "طريف", "الطوال", "ظهران الجنوب", "العارضه", "العرضيات", "عرعر", "عفيف", "العقلة", "العقيق",
        "العلا", "عنيزه", "العويقيله", "العيدابى", "العيص", "الغاط", "الغزاله", "فرسان", "فرعة غامد الزناد", "فيفا", "قبه", "القرعاء", "القرى",
        "القريات", "القطيف", "قلوه", "القنفذه", "القويعيه", "الكامل", "الليث", "المجارده", "المجمعه", "محايل", "المخرم", "المخواه", "المدينة المنورة",
        "المذنب", "مرات", "المزاحميه", "مكة المكرمة", "الملقاء", "المندق", "المهد", "موقق", "المويه", "ميسان", "نجران", "النعيريه", "النقره", "النماص",
        "هروب", "وادى الدواسر", "وادي الفرع", "الوجه", "يدمه", "ينبع",};
    static GeoPosition points[] = {
        new GeoPosition(18.24721570660114, 42.509624478994276),
        new GeoPosition(16.968541699581166, 42.84513522299233),
        new GeoPosition(16.71127943828121, 42.953632284156164),
        new GeoPosition(18.21231386688071, 42.843819233193386),
        new GeoPosition(25.318123670574522, 49.61880866688367),
        new GeoPosition(26.809443271083282, 44.20225795762448),
        new GeoPosition(20.422904851427944, 40.863003912272454),
        new GeoPosition(22.289804881157753, 46.718588000886875),
        new GeoPosition(25.834070896614147, 44.59141152652532),
        new GeoPosition(25.050014523995266, 37.265172838539556),
        new GeoPosition(20.021700711859047, 41.471825363965635),
        new GeoPosition(18.930718346019777, 41.929536319561464),
        new GeoPosition(19.598944427080813, 41.94855996942295),
        new GeoPosition(25.935358536246973, 42.946182889211734),
        new GeoPosition(21.40212884294121, 39.462867334724415),
        new GeoPosition(25.97974737991487, 43.733655806018604),
        new GeoPosition(26.12728076382373, 43.893570249350375),
        new GeoPosition(17.877779878153962, 43.71807088822875),
        new GeoPosition(28.47890327677589, 35.01900846960699),
        new GeoPosition(18.207479361232227, 41.536195415343194),
        new GeoPosition(26.359303294491408, 43.981111037724894),
        new GeoPosition(27.887242089459978, 42.41314935421456),
        new GeoPosition(25.916307711537684, 49.67099233104801),
        new GeoPosition(26.163028868170226, 43.65812482569721),
        new GeoPosition(19.86472308598488, 41.58230826249066),
        new GeoPosition(20.056252667710282, 41.366057414876515),
        new GeoPosition(17.373693751236427, 42.53625143919785),
        new GeoPosition(19.97562677388951, 42.59090615025508),
        new GeoPosition(28.386558246314898, 36.55909360913331),
        new GeoPosition(19.530142796470912, 43.50383695748853),
        new GeoPosition(21.206878504397423, 41.6207603896266),
        new GeoPosition(18.928657414687894, 42.17711489680979),
        new GeoPosition(27.61201699299375, 38.51660737961588),
        new GeoPosition(25.28027701477989, 45.86828122960088),
        new GeoPosition(17.980834323535834, 44.10549404287924),
        new GeoPosition(16.88829741642635, 42.567860598711164),
        new GeoPosition(26.95838670319874, 49.56671475260572),
        new GeoPosition(21.489032859216326, 39.20353092310054),
        new GeoPosition(21.613442345793125, 39.70115832283891),
        new GeoPosition(25.993535293065463, 40.46672349989525),
        new GeoPosition(27.510974253903377, 41.722297501969805),
        new GeoPosition(17.84111327380731, 44.025693066716386),
        new GeoPosition(20.233643812134616, 41.05690909417506),
        new GeoPosition(16.782091265068836, 43.21453273810856),
        new GeoPosition(17.92039095833972, 43.372950548249214),
        new GeoPosition(23.624705846053967, 46.51154288135993),
        new GeoPosition(25.115674268931606, 46.10412610765693),
        new GeoPosition(28.377775105535633, 45.962936276261665),
        new GeoPosition(29.28613038750774, 34.94544752936447),
        new GeoPosition(24.90840173848492, 40.53435847520195),
        new GeoPosition(23.524907478474532, 46.84394409351252),
        new GeoPosition(17.557748193888234, 44.74744331246753),
        new GeoPosition(26.216784013223734, 50.19252774695702),
        new GeoPosition(24.158052306541343, 47.32555207544258),
        new GeoPosition(21.923923132225937, 42.02892826021449),
        new GeoPosition(28.426175949099022, 48.48915739266418),
        new GeoPosition(22.15108293423882, 39.34148351383448),
        new GeoPosition(18.31127255012689, 42.76334585148749),
        new GeoPosition(25.6853283557294, 39.29202321848495),
        new GeoPosition(17.339117037769988, 43.13122999695791),
        new GeoPosition(17.733699204493444, 42.26509142976002),
        new GeoPosition(24.748182825171806, 46.536407019612035),
        new GeoPosition(23.977566879956015, 47.15572408551312),
        new GeoPosition(26.420061316751617, 50.089417530741045),
        new GeoPosition(24.51668508841227, 44.419184222073625),
        new GeoPosition(29.81075714332459, 39.88989875300155),
        new GeoPosition(22.790886730297913, 39.019758862396735),
        new GeoPosition(26.772130488185898, 49.99695966551408),
        new GeoPosition(18.23575728447516, 42.272115744740155),
        new GeoPosition(25.852047612362213, 43.52302732252547),
        new GeoPosition(29.62750048490289, 43.51934229417754),
        new GeoPosition(25.563763767275518, 47.16057307841214),
        new GeoPosition(21.26339284479099, 42.853608465278214),
        new GeoPosition(24.712456995845365, 46.687975637668615),
        new GeoPosition(26.053041286873636, 43.54292597534746),
        new GeoPosition(17.616865615446944, 42.8299876722455),
        //75
        new GeoPosition(23.5418584, 45.5157676),
        new GeoPosition(26.3098543, 44.8318340),
        new GeoPosition(18.0993174, 43.1165621),
        new GeoPosition(29.8780031, 40.1043057),
        new GeoPosition(20.4668679, 45.5629394),
        new GeoPosition(26.2896036, 41.3661242),
        new GeoPosition(26.4957432, 42.1251538),
        new GeoPosition(17.4850321, 47.1167237),
        new GeoPosition(27.2666757, 43.4163028),
        new GeoPosition(25.2476471, 45.2524792),
        new GeoPosition(26.3189107, 44.2573774),
        new GeoPosition(26.8578256, 40.3292795),
        new GeoPosition(27.1781377, 42.4429890),
        new GeoPosition(16.6018370, 42.9353694),
        new GeoPosition(17.1547981, 42.6268969),
        new GeoPosition(27.3457474, 35.7243073),
        new GeoPosition(24.6124375, 46.1517858),
        new GeoPosition(24.7219150, 42.9319657),
        new GeoPosition(17.1090117, 42.7759964),
        new GeoPosition(21.2840782, 40.4248192),
        new GeoPosition(30.5154780, 38.2216491),
        new GeoPosition(18.5587398, 43.2086085),
        new GeoPosition(31.6660775, 38.6634693),
        new GeoPosition(16.5279447, 42.9733304),
        new GeoPosition(17.6652253, 43.5174213),
        new GeoPosition(17.0378768, 43.0486309),
        new GeoPosition(19.3547808, 41.6809724),
        new GeoPosition(30.9599447, 41.0595636),
        new GeoPosition(23.9052203, 42.9124955),
        new GeoPosition(27.1035266, 41.2783479),
        new GeoPosition(20.2795444, 41.6510644),
        new GeoPosition(26.6031391, 37.9294820),
        new GeoPosition(26.0907310, 43.9875457),
        new GeoPosition(30.3548020, 42.2461069),
        new GeoPosition(17.2376555, 42.9392651),
        new GeoPosition(25.0599399, 38.1160213),
        new GeoPosition(26.0302278, 44.9470743),
        new GeoPosition(26.7921739, 41.3159832),
        new GeoPosition(16.7018950, 42.1209840),
        new GeoPosition(19.6033213, 41.4812771),
        new GeoPosition(17.2472916, 43.1068632),
        new GeoPosition(27.4023770, 44.3425754),
        new GeoPosition(26.4120791, 43.7573489),
        new GeoPosition(20.2427959, 41.3623652),
        new GeoPosition(31.3296352, 37.3613533),
        new GeoPosition(26.5764917, 49.9982360),
        new GeoPosition(19.9254477, 41.1602597),
        new GeoPosition(19.1281398, 41.0787392),
        new GeoPosition(24.0670560, 45.2806177),
        new GeoPosition(22.3650778, 39.8990473),
        new GeoPosition(20.1405331, 40.2782239),
        new GeoPosition(19.1284443, 41.9246083),
        new GeoPosition(25.8758866, 45.3730695),
        new GeoPosition(18.5473952, 42.0534398),
        new GeoPosition(26.7899674, 43.2303843),
        new GeoPosition(19.7254426, 41.3209993),
        new GeoPosition(24.5246542, 39.5691841),
        new GeoPosition(25.8568978, 44.2242106),
        new GeoPosition(25.0699284, 45.4639333),
        new GeoPosition(24.4713948, 46.2732342),
        new GeoPosition(21.5235584, 41.9196471),
        new GeoPosition(25.3800000, 43.9700000),
        new GeoPosition(20.1082678, 41.2858770),
        new GeoPosition(23.4841150, 41.1200028),
        new GeoPosition(27.3782439, 41.1803774),
        new GeoPosition(22.7417600, 41.5896910),
        new GeoPosition(20.7269641, 40.8298121),
        new GeoPosition(17.5656036, 44.2289441),
        new GeoPosition(27.4703685, 48.4855167),
        new GeoPosition(25.5808012, 41.4399998),
        new GeoPosition(19.1134285, 42.1671483),
        new GeoPosition(17.4350092, 42.8857118),
        new GeoPosition(20.4492858, 44.8501154),
        new GeoPosition(23.3119734, 39.7755571),
        new GeoPosition(26.2366058, 36.4688958),
        new GeoPosition(18.5315621, 44.2289441),
        new GeoPosition(24.0231757, 38.1899782),};

    public void declare() {
        for (int i = 0; i < 153; i++) {
            nodes[i] = new node(i, points[i]);
        }
        fillKSAGraph();
    }

    static void addEdge(int from, int to, double weight) {
        nodes[from].adjacencies.add(new Edge(nodes[to], weight));
        nodes[to].adjacencies.add(new Edge(nodes[from], weight));
    }

    double disCal(node target) {
        double distance = 0;
        for (node node = target; node != null; node = node.parent) {
            distance += node.getCostToParent();
        }
        return distance;
    }

    double UCS(int start, int end) {
        for (int i = 0; i < 153; i++) {
            nodes[i] = new node(i);
        }
        fillKSAGraph();
        PriorityQueue<node> queue = new PriorityQueue<node>(
                new Comparator<>() {

            //override compare method
            public int compare(node i, node j) {
                if (i.pathCost > j.pathCost) {
                    return 1;
                } else if (i.pathCost < j.pathCost) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
        );

        HashSet<node> visited = new HashSet<>();
        node temp = nodes[start];
        temp.pathCost = 0;
        queue.add(temp);

        int tc = 1;
        int maxsize = 1;

        double distance = 0;

        while (!queue.isEmpty()) {
            node target = queue.poll();
            visited.add(target);

            //goal test
            if (target.equals(nodes[end])) {
                distance = disCal(target);
                distance = ((int) (distance * 100)) / 100.0;
                List<String> path = new ArrayList<String>();
                for (node node = target; node != null; node = node.parent) {
                    path.add(letters[node.index]);
                }

                return distance;
            }

            for (Edge e : target.adjacencies) {
                node child = e.target;
                double cost = e.cost;

                if (!visited.contains(child) && !queue.contains(child)) {
                    child.pathCost = target.pathCost + cost;
                    child.parent = target;
                    queue.add(child);

                    tc++;
                    if (queue.size() > maxsize) {
                        maxsize = queue.size();
                    }
                } //current path is shorter than previous path found
                else if ((queue.contains(child)) && (child.pathCost > (target.pathCost + cost))) {
                    child.parent = target;
                    child.pathCost = target.pathCost + cost;
                    queue.remove(child);
                    queue.add(child);

                    tc++;
                    if (queue.size() > maxsize) {
                        maxsize = queue.size();
                    }
                }
            }
        }
        return 0;
    }

    static void fillKSAGraph() {
        addEdge(0, 57, 36.6);
        addEdge(0, 31, 116);
        addEdge(0, 129, 80);
        addEdge(0, 68, 89.8);
        addEdge(0, 60, 89);
        addEdge(0, 3, 44);
        addEdge(0, 75, 169);

        // 1
        addEdge(1, 35, 33.7);
        addEdge(1, 90, 53.3);
        addEdge(1, 2, 36.9);
        addEdge(1, 94, 28.5);
        addEdge(1, 101, 62);
        // 2
        addEdge(2, 1, 35.1);
        addEdge(2, 89, 15.8);
        addEdge(2, 35, 49.7);
        addEdge(2, 43, 41);

        // 3
        addEdge(3, 0, 43.6);
        addEdge(3, 57, 13.6);
        addEdge(3, 78, 44.5);
        // 4
        addEdge(4, 22, 76.5);
        addEdge(4, 144, 301);
        addEdge(4, 73, 328);
        addEdge(4, 71, 352);
        addEdge(4, 53, 355);
        // 5
        addEdge(5, 47, 321);
        addEdge(5, 20, 64.7);
        addEdge(5, 21, 280);
        addEdge(5, 88, 214);
        addEdge(5, 77, 127);
        addEdge(5, 8, 137);
        addEdge(5, 112, 145);
        // 6
        addEdge(6, 42, 40.2);
        addEdge(6, 142, 90.3);
        addEdge(6, 30, 195);
        addEdge(6, 126, 114);
        addEdge(6, 123, 201);
        // 7
        addEdge(7, 50, 151);
        addEdge(7, 76, 265);
        addEdge(7, 80, 245);

        // 8
        addEdge(8, 5, 139);
        addEdge(8, 112, 49);
        addEdge(8, 85, 101);
        addEdge(8, 133, 43.2);
        addEdge(8, 64, 219);
        addEdge(8, 105, 67.6);
        addEdge(8, 77, 86.4);

        // 9
        addEdge(9, 111, 100);
        addEdge(9, 152, 169);
        addEdge(9, 150, 166);

        // 10
        addEdge(10, 131, 53.2);
        addEdge(10, 24, 32.6);
        addEdge(10, 122, 76.7);
        addEdge(10, 25, 22.9);
        addEdge(10, 119, 33.2);
        addEdge(10, 106, 43.5);

        // 11
        addEdge(11, 31, 44.5);
        addEdge(11, 129, 49.9);
        addEdge(11, 123, 139);
        addEdge(11, 127, 32.5);
        addEdge(11, 146, 72.5);
        addEdge(11, 131, 141);
        addEdge(11, 12, 115);

        // 12
        addEdge(12, 27, 109);
        addEdge(12, 146, 79.1);
        addEdge(12, 127, 90.6);
        addEdge(12, 24, 91.3);

        // 13
        addEdge(13, 93, 163);
        addEdge(13, 49, 283);
        addEdge(13, 69, 73.1);
        addEdge(13, 74, 64.4);
        addEdge(13, 23, 81.7);
        addEdge(13, 88, 212);
        addEdge(13, 145, 162);
        addEdge(13, 93, 163);

        // 14
        addEdge(14, 136, 54.2);
        addEdge(14, 37, 38);
        addEdge(14, 38, 40.6);
        addEdge(14, 56, 111);
        addEdge(14, 126, 212);

        // 15
        addEdge(15, 20, 59.5);
        addEdge(15, 108, 33);
        addEdge(15, 69, 29.8);
        addEdge(15, 74, 25.3);

        // 16
        addEdge(16, 152, 81.7);
        addEdge(16, 66, 124);
        addEdge(16, 132, 156);
        addEdge(16, 149, 215);
        addEdge(16, 139, 73.7);

        // 17
        addEdge(17, 44, 46.9);
        addEdge(17, 78, 87.7);
        addEdge(17, 41, 69.2);
        addEdge(17, 143, 116);
        addEdge(17, 100, 48);

        // 18
        addEdge(18, 91, 167);
        addEdge(18, 48, 117);
        addEdge(18, 28, 234);

        // 19
        addEdge(19, 60, 112);
        addEdge(19, 129, 89.6);
        addEdge(19, 123, 129);

        // 20
        addEdge(20, 23, 42.3);
        addEdge(20, 108, 39.6);
        addEdge(20, 84, 127);
        addEdge(20, 77, 113);
        addEdge(20, 112, 119);
        addEdge(20, 86, 34.1);
        addEdge(20, 118, 32.8);
        addEdge(20, 130, 99.7);

        // 21
        addEdge(21, 40, 107);
        addEdge(21, 88, 102);
        addEdge(21, 70, 321);
        addEdge(21, 47, 518);
        addEdge(21, 109, 464);

        // 22
        addEdge(22, 4, 77.8);
        addEdge(22, 144, 264);
        addEdge(22, 121, 104);
        addEdge(22, 63, 84.2);
        addEdge(22, 52, 87.6);

        // 23
        addEdge(23, 20, 41.2);
        addEdge(23, 108, 41.4);
        addEdge(23, 118, 38);
        addEdge(23, 13, 84.3);
        addEdge(23, 74, 21.7);

        // 24
        addEdge(24, 131, 40.6);
        addEdge(24, 10, 31.4);
        addEdge(24, 27, 160);
        addEdge(24, 146, 164);

        // 25
        addEdge(25, 10, 27.9);
        addEdge(25, 138, 12.2);

        // 26
        addEdge(26, 90, 25);
        addEdge(26, 60, 54);
        addEdge(26, 147, 62);
        addEdge(26, 75, 67.5);
        addEdge(26, 110, 47);

        // 27
        addEdge(27, 24, 155);
        addEdge(27, 106, 138);
        addEdge(27, 30, 216);
        addEdge(27, 29, 129);
        addEdge(27, 72, 167);
        addEdge(27, 146, 180);
        addEdge(27, 57, 225);

        // 28
        addEdge(28, 48, 228);
        addEdge(28, 18, 241);
        addEdge(28, 91, 196);
        addEdge(28, 32, 261);
        addEdge(28, 96, 469);
        addEdge(28, 65, 451);

        // 29
        addEdge(29, 97, 132);
        addEdge(29, 27, 128);
        addEdge(29, 148, 199);

        // 30
        addEdge(30, 6, 196);
        addEdge(30, 106, 146);
        addEdge(30, 95, 161);
        addEdge(30, 10, 180);
        addEdge(30, 27, 139);
        addEdge(30, 72, 163);
        addEdge(30, 104, 351);
        addEdge(30, 54, 101);
        addEdge(30, 142, 141);

        // 31
        addEdge(31, 0, 117);
        addEdge(31, 129, 62);
        addEdge(31, 146, 34);
        addEdge(31, 11, 44.5);

        // 32
        addEdge(32, 58, 256);
        addEdge(32, 87, 264);
        addEdge(32, 140, 354);
        addEdge(32, 96, 466);
        addEdge(32, 65, 447);
        addEdge(32, 28, 261);
        addEdge(32, 107, 194);

        // 33
        addEdge(33, 128, 106);
        addEdge(33, 46, 53);
        addEdge(33, 85, 75);
        addEdge(33, 134, 70);

        // 34
        addEdge(34, 151, 74);
        addEdge(34, 41, 33.5);
        addEdge(34, 143, 67);

        // 35
        addEdge(35, 2, 51);
        addEdge(35, 1, 33.5);
        addEdge(35, 90, 50);
        addEdge(35, 94, 42);
        addEdge(35, 114, 60);

        // 36
        addEdge(36, 55, 226);
        addEdge(36, 144, 139);
        addEdge(36, 67, 80);
        addEdge(36, 121, 82);

        // 37
        addEdge(37, 136, 86);
        addEdge(37, 14, 41);
        addEdge(37, 126, 212);
        addEdge(37, 56, 98);
        addEdge(37, 125, 144);

        // 38
        addEdge(38, 14, 37);
        addEdge(38, 56, 89);
        addEdge(38, 136, 39);
        addEdge(38, 125, 125);

        // 39
        addEdge(39, 49, 134);
        addEdge(39, 132, 215);
        addEdge(39, 87, 110);
        addEdge(39, 113, 147);
        addEdge(39, 145, 151);

        // 40
        addEdge(40, 82, 139);
        addEdge(40, 88, 95);
        addEdge(40, 113, 105);
        addEdge(40, 140, 69);
        addEdge(40, 21, 98.5);
        addEdge(40, 65, 358);

        // 41
        addEdge(41, 143, 69.5);
        addEdge(41, 17, 67);

        // 42
        addEdge(42, 6, 40);
        addEdge(42, 122, 44);
        addEdge(42, 126, 124);

        // 43
        addEdge(43, 89, 40);
        addEdge(43, 2, 38);
        addEdge(43, 1, 53);
        addEdge(43, 101, 43.5);

        // 44
        addEdge(44, 17, 47);
        addEdge(44, 78, 41);
        addEdge(44, 75, 128);
        addEdge(44, 101, 188);
        addEdge(44, 100, 38);

        //45
        addEdge(45, 50, 48);
        addEdge(45, 76, 117);

        // 46
        addEdge(46, 33, 38);
        addEdge(46, 134, 98);
        addEdge(46, 85, 103);
        addEdge(46, 128, 130);
        addEdge(46, 61, 80.5);
        addEdge(46, 73, 86);
        addEdge(46, 71, 162);

        // 47
        addEdge(47, 70, 277);
        addEdge(47, 144, 271);
        addEdge(47, 55, 288);
        addEdge(47, 84, 385);
        addEdge(47, 128, 306);

        // 48
        addEdge(48, 18, 116);
        addEdge(48, 28, 228);

        // 49
        addEdge(49, 113, 248);
        addEdge(49, 39, 135);
        addEdge(49, 132, 117);
        addEdge(49, 139, 219);
        addEdge(49, 13, 283);
        addEdge(49, 145, 130);

        // 50
        addEdge(50, 45, 47);
        addEdge(50, 62, 74);
        addEdge(50, 7, 150);

        // 51
        addEdge(51, 83, 288);
        addEdge(51, 143, 67);

        // 52
        addEdge(52, 63, 29);
        addEdge(52, 22, 96);

        // 53
        addEdge(53, 73, 103);
        addEdge(53, 62, 30);
        addEdge(53, 135, 141);
        addEdge(53, 4, 357);

        // 54
        addEdge(54, 30, 100);
        addEdge(54, 72, 146);
        addEdge(54, 124, 470);
        addEdge(54, 104, 253);
        addEdge(54, 141, 150);

        // 55
        addEdge(55, 71, 400);
        addEdge(55, 47, 293);
        addEdge(55, 144, 132);
        addEdge(55, 36, 225);

        // 56
        addEdge(56, 139, 319);
        addEdge(56, 66, 89);
        addEdge(56, 37, 110);
        addEdge(56, 125, 67);
        addEdge(56, 38, 97);

        // 57
        addEdge(57, 3, 19.5);
        addEdge(57, 0, 35);
        addEdge(57, 97, 68);
        addEdge(57, 27, 226);

        // 58
        addEdge(58, 32, 263);
        addEdge(58, 107, 222);
        addEdge(58, 132, 169);
        addEdge(58, 87, 269);

        // 59
        addEdge(59, 75, 75);

        addEdge(59, 100, 131);
        addEdge(59, 110, 33);
        addEdge(59, 101, 51);
        addEdge(59, 116, 19.5);

        // 60
        addEdge(60, 26, 59);
        addEdge(60, 0, 93);
        addEdge(60, 129, 134);
        addEdge(60, 19, 107);

        // 61
        addEdge(61, 73, 20);
        addEdge(61, 135, 59);
        addEdge(61, 92, 76);
        addEdge(61, 46, 82);

        // 62
        addEdge(62, 53, 30);
        addEdge(62, 50, 75.5);

        // 63
        addEdge(63, 73, 410);
        addEdge(63, 52, 39);
        addEdge(63, 121, 27);
        addEdge(63, 22, 86);
        addEdge(63, 144, 210);

        // 64
        addEdge(64, 85, 123);
        addEdge(64, 124, 131);
        addEdge(64, 104, 175);
        addEdge(64, 69, 198);
        addEdge(64, 133, 192);

        // 65
        addEdge(65, 28, 424);
        addEdge(65, 40, 364);
        addEdge(65, 120, 324);
        addEdge(65, 21, 416);
        addEdge(65, 79, 48);

        // 66
        addEdge(66, 149, 116);
        addEdge(66, 37, 164);
        addEdge(66, 16, 124);
        addEdge(66, 152, 181);
        addEdge(66, 56, 85);

        // 67
        addEdge(67, 121, 28);

        // 68
        addEdge(68, 129, 63);
        addEdge(68, 0, 112);

        // 69
        addEdge(69, 15, 29);
        addEdge(69, 74, 27);
        addEdge(69, 64, 198);
        addEdge(69, 104, 264);
        addEdge(69, 13, 76);

        // 70
        addEdge(70, 109, 149);
        addEdge(70, 47, 279);
        addEdge(70, 21, 318);

        // 71
        addEdge(71, 128, 224);
        addEdge(71, 46, 156);
        addEdge(71, 47, 385);
        addEdge(71, 22, 403);
        addEdge(71, 73, 126);

        // 72
        addEdge(72, 76, 486);
        addEdge(72, 148, 246);
        addEdge(72, 27, 168);
        addEdge(72, 54, 143);

        // 73
        addEdge(73, 61, 19);
        addEdge(73, 135, 53.5);
        addEdge(73, 46, 86);
        addEdge(73, 71, 126);
        addEdge(73, 4, 326);
        addEdge(73, 63, 409);
        addEdge(73, 128, 190);

        // 74
        addEdge(74, 23, 22);
        addEdge(74, 15, 26);
        addEdge(74, 69, 26);
        addEdge(74, 13, 68);

        // 75
        addEdge(75, 60, 97.5);
        addEdge(75, 59, 75);
        addEdge(75, 0, 150);
        addEdge(75, 90, 68);
        addEdge(75, 26, 62);
        addEdge(75, 100, 120);
        addEdge(75, 147, 62);
        addEdge(75, 68, 128);
        addEdge(75, 78, 163);

        // 125
        addEdge(125, 149, 202);
        addEdge(125, 56, 65);
        addEdge(125, 37, 145);
        addEdge(125, 38, 125);

        // 126
        addEdge(126, 95, 279);
        addEdge(126, 136, 274);
        addEdge(126, 14, 288);
        addEdge(126, 37, 294);
        addEdge(126, 142, 184);
        addEdge(126, 6, 110);
        addEdge(126, 123, 162);
        addEdge(126, 42, 121);
        addEdge(126, 122, 144);
        addEdge(126, 131, 170);

        // 127
        addEdge(127, 146, 48);
        addEdge(127, 11, 33);
        addEdge(127, 115, 121);
        addEdge(127, 27, 189);
        addEdge(127, 31, 64);
        addEdge(127, 131, 129);
        addEdge(127, 102, 79);
        // 128
        addEdge(128, 33, 99);
        addEdge(128, 85, 89.5);
        addEdge(128, 73, 190);
        addEdge(128, 112, 51);
        addEdge(128, 47, 303);
        addEdge(128, 77, 80);

        // 129
        addEdge(129, 0, 83);
        addEdge(129, 11, 50);
        addEdge(129, 60, 127);
        addEdge(129, 31, 63);
        addEdge(129, 123, 157);

        // 130
        addEdge(130, 47, 447);
        addEdge(130, 88, 100);
        addEdge(130, 84, 102);
        addEdge(130, 20, 99);

        // 131
        addEdge(131, 115, 44);
        addEdge(131, 11, 142);
        addEdge(131, 127, 130);
        addEdge(131, 24, 36);
        addEdge(131, 10, 50);
        addEdge(131, 126, 174);
        addEdge(131, 122, 34.5);
        addEdge(131, 123, 105);

        // 132
        addEdge(132, 16, 159);
        addEdge(132, 149, 165);
        addEdge(132, 139, 232);
        addEdge(132, 49, 118);
        addEdge(132, 58, 173);
        addEdge(132, 152, 212);
        addEdge(132, 111, 204);
        addEdge(132, 39, 217);

        // 133
        addEdge(133, 108, 37);
        addEdge(133, 85, 144);
        addEdge(133, 64, 191);
        addEdge(133, 112, 92.5);
        addEdge(133, 137, 80);

        // 134
        addEdge(134, 92, 92);
        addEdge(134, 85, 36.5);
        addEdge(134, 46, 98);
        addEdge(134, 33, 79);

        // 135
        addEdge(135, 61, 62);
        addEdge(135, 73, 53);
        addEdge(135, 124, 115);
        addEdge(135, 76, 140);
        addEdge(135, 92, 26);

        // 136
        addEdge(136, 95, 86);
        addEdge(136, 126, 192);
        addEdge(136, 37, 89);
        addEdge(136, 38, 44);
        addEdge(136, 14, 54);

        // 137
        addEdge(137, 133, 80);
        addEdge(137, 64, 149);
        addEdge(137, 69, 109);
        addEdge(137, 15, 92.5);

        // 138
        addEdge(138, 42, 70);
        addEdge(138, 142, 116);
        addEdge(138, 25, 26);

        // 139
        addEdge(139, 54, 263);
        addEdge(139, 104, 226);
        addEdge(139, 49, 188);
        addEdge(139, 132, 199);
        addEdge(139, 149, 142);

        // 140 
        addEdge(140, 40, 70);
        addEdge(140, 113, 79.5);
        addEdge(140, 87, 115);

        // 141
        addEdge(141, 139, 262);
        addEdge(141, 95, 232);
        addEdge(141, 54, 150);
        addEdge(141, 104, 247);
        addEdge(141, 124, 464);

        // 142
        addEdge(142, 30, 140);
        addEdge(142, 106, 187);
        addEdge(142, 138, 114);
        addEdge(142, 6, 88);
        addEdge(142, 95, 106);

        // 143
        addEdge(143, 51, 71.5);
        addEdge(143, 100, 102);
        addEdge(143, 17, 110);
        addEdge(143, 41, 72);
        addEdge(143, 34, 69);

        // 144
        addEdge(144, 36, 138);
        addEdge(144, 121, 200);
        addEdge(144, 55, 132);
        addEdge(144, 47, 271);
        addEdge(144, 71, 354);

        // 145
        addEdge(145, 39, 126);
        addEdge(145, 81, 94);
        addEdge(145, 49, 139);
        addEdge(145, 13, 169);
        addEdge(145, 82, 167);

        // 146 
        addEdge(146, 127, 39);
        addEdge(146, 27, 179);
        addEdge(146, 31, 35);

        // 147
        addEdge(147, 26, 59);
        addEdge(147, 110, 30);
        addEdge(147, 59, 53);
        addEdge(147, 116, 46.5);
        addEdge(147, 90, 56);
        addEdge(147, 0, 163);

        // 148
        addEdge(148, 29, 192);
        addEdge(148, 72, 237);
        addEdge(148, 80, 79);
        addEdge(148, 151, 228);

        // 149
        addEdge(149, 139, 178);
        addEdge(149, 132, 168);
        addEdge(149, 125, 204);
        addEdge(149, 56, 181);

        // 150 
        addEdge(150, 9, 169);
        addEdge(150, 107, 245);
        addEdge(150, 9, 152);

        // 151
        addEdge(151, 83, 427);
        addEdge(151, 80, 336);
        addEdge(151, 148, 314);
        addEdge(151, 97, 160);
        addEdge(151, 34, 75);

        // 152
        addEdge(152, 16, 82.5);
        addEdge(152, 132, 214);
        addEdge(152, 9, 169);
        addEdge(152, 66, 181);
        addEdge(152, 111, 157);

        addEdge(75, 0, 150);
        addEdge(75, 26, 62);
        addEdge(75, 78, 163);
        addEdge(75, 59, 75);
        addEdge(75, 90, 78);
        addEdge(75, 100, 120);

// 76
        addEdge(76, 135, 145);
        addEdge(76, 124, 81);
        addEdge(76, 45, 120);
        addEdge(76, 72, 443);
        addEdge(76, 80, 486);
        addEdge(76, 92, 150);
// 77
        addEdge(77, 112, 36);
        addEdge(77, 86, 77);
        addEdge(77, 47, 282);
        addEdge(77, 108, 116);
        addEdge(77, 20, 117);
// 78
        addEdge(78, 97, 68);
        addEdge(78, 44, 41);
        addEdge(78, 3, 45);
        addEdge(78, 75, 131);
// 79
        addEdge(79, 103, 185);
        addEdge(79, 65, 42);
        addEdge(79, 40, 379);
// 80
        addEdge(80, 7, 248);
        addEdge(80, 148, 80);
        addEdge(80, 151, 334);
        addEdge(80, 143, 410);
        addEdge(80, 83, 528);
// 81
        addEdge(81, 113, 74);
        addEdge(81, 39, 140);
        addEdge(81, 132, 327);
        addEdge(81, 145, 88);
        addEdge(81, 13, 197);
// 82
        addEdge(82, 88, 103);
        addEdge(82, 40, 139);
        addEdge(82, 13, 113);
        addEdge(82, 145, 160);
        addEdge(82, 113, 115);
// 83
        addEdge(83, 80, 527);
        addEdge(83, 148, 528);
        addEdge(83, 51, 280);
// 84
        addEdge(84, 15, 126);
        addEdge(84, 40, 178);
        addEdge(84, 21, 168);
        addEdge(84, 70, 408);
        addEdge(84, 117, 143);
        addEdge(84, 20, 126);
        addEdge(84, 88, 103);
        addEdge(84, 118, 121);

// 85
        addEdge(85, 64, 124);
        addEdge(85, 8, 100);
        addEdge(85, 86, 169);
        addEdge(85, 20, 198);
        addEdge(85, 128, 94);
        addEdge(85, 134, 37);
        addEdge(85, 33, 84);
        addEdge(85, 46, 103);
// 86
        addEdge(86, 20, 34);
        addEdge(86, 117, 142);
        addEdge(86, 133, 71);
        addEdge(86, 77, 78);
        addEdge(86, 112, 84);
// 87
        addEdge(87, 32, 269);
        addEdge(87, 58, 310);
        addEdge(87, 107, 284);
        addEdge(87, 39, 109);
        addEdge(87, 113, 187);
        addEdge(87, 40, 174);
// 88
        addEdge(88, 82, 103);
        addEdge(88, 40, 91);
        addEdge(88, 20, 192);
        addEdge(88, 84, 103);
        addEdge(88, 118, 166);

// 89
        addEdge(89, 2, 14);
        addEdge(89, 99, 13);

// 90
        addEdge(90, 26, 34);
        addEdge(90, 75, 80);
        addEdge(90, 110, 38);
        addEdge(90, 1, 37);
        addEdge(90, 35, 38);
        addEdge(90, 94, 25);
// 91
        addEdge(91, 150, 150);
        addEdge(91, 18, 175);
        addEdge(91, 28, 198);
        addEdge(91, 107, 310);
// 92
        addEdge(92, 134, 92);
        addEdge(92, 33, 101);
        addEdge(92, 46, 120);
        addEdge(92, 135, 24);
        addEdge(92, 124, 120);
        addEdge(92, 76, 145);
        addEdge(92, 45, 171);
        addEdge(92, 73, 70);
// 93
        addEdge(93, 104, 102);
        addEdge(93, 64, 179);
        addEdge(93, 69, 165);
        addEdge(93, 13, 165);
        addEdge(93, 145, 264);
        addEdge(93, 132, 441);
// 94
        addEdge(94, 90, 25);
        addEdge(94, 110, 33);
        addEdge(94, 35, 45);
        addEdge(94, 1, 24);
        addEdge(94, 101, 47);
// 95
        addEdge(95, 141, 231);
        addEdge(95, 136, 90);
        addEdge(95, 142, 102);
        addEdge(95, 10, 218);
        addEdge(95, 30, 161);
        addEdge(95, 106, 225);
        addEdge(95, 54, 230);
// 96
        addEdge(96, 120, 136);
        addEdge(96, 98, 252);
        addEdge(96, 65, 193);
        addEdge(96, 28, 428);
// 97
        addEdge(97, 29, 141);
        addEdge(97, 78, 73);
        addEdge(97, 57, 75);
        addEdge(97, 27, 228);
// 98
        addEdge(98, 103, 249);
        addEdge(98, 120, 157);
// 99
        addEdge(99, 89, 12);
// 100
        addEdge(100, 17, 50);
        addEdge(100, 143, 101);
        addEdge(100, 59, 132);
        addEdge(100, 75, 120);
        addEdge(100, 44, 37);
// 101
        addEdge(101, 116, 33);
        addEdge(101, 110, 35);
        addEdge(101, 1, 50);
// 102
        addEdge(102, 131, 59);
        addEdge(102, 123, 117);
        addEdge(102, 146, 114);
        addEdge(102, 11, 82);
// 103
        addEdge(103, 98, 249);
        addEdge(103, 79, 185);
        addEdge(103, 109, 137);
// 104
        addEdge(104, 93, 100);
        addEdge(104, 64, 175);
        addEdge(104, 141, 246);
// 105
        addEdge(105, 40, 68);
        addEdge(105, 113, 37);
// 106
        addEdge(106, 10, 43);
        addEdge(106, 30, 145);
        addEdge(106, 95, 223);
        addEdge(106, 27, 139);
// 107
        addEdge(107, 32, 229);
        addEdge(107, 58, 217);
        addEdge(107, 111, 210);
        addEdge(107, 150, 245);
        addEdge(107, 28, 363);
// 108
        addEdge(108, 133, 38);
        addEdge(108, 20, 36);
        addEdge(108, 77, 110);
        addEdge(108, 15, 34);
        addEdge(108, 23, 41);
// 109
        addEdge(109, 103, 135);
        addEdge(109, 70, 153);

//110
        addEdge(110, 94, 59);
        addEdge(110, 90, 38);
        addEdge(110, 116, 32);
// 111
        addEdge(111, 107, 211);
        addEdge(111, 132, 217);
        addEdge(111, 58, 278);
        addEdge(111, 9, 101);
        addEdge(111, 152, 157);
// 112
        addEdge(112, 77, 44);
        addEdge(112, 86, 85);
        addEdge(112, 108, 113);
        addEdge(112, 8, 49);
        addEdge(112, 128, 53);
// 113
        addEdge(113, 145, 160);
        addEdge(113, 39, 160);
        addEdge(113, 40, 104);
        addEdge(113, 87, 187);
// 114
        addEdge(114, 35, 60);
// 115
        addEdge(115, 131, 71);
        addEdge(115, 123, 143);
        addEdge(115, 146, 165);
        addEdge(115, 27, 208);
// 116
        addEdge(116, 59, 22);
        addEdge(116, 101, 38);
        addEdge(116, 110, 29);
// 117
        addEdge(117, 47, 241);
        addEdge(117, 70, 359);
        addEdge(117, 84, 143);
        addEdge(117, 20, 145);
// 118
        addEdge(118, 20, 36);
        addEdge(118, 23, 39);
        addEdge(118, 88, 167);
        addEdge(118, 84, 121);
// 119 
        addEdge(119, 10, 74);
        addEdge(119, 25, 69);
        addEdge(119, 30, 146);
        addEdge(119, 95, 185);
// 120
        addEdge(120, 98, 160);
        addEdge(120, 96, 132);
// 121 
        addEdge(121, 63, 48);
        addEdge(121, 67, 35);
        addEdge(121, 36, 79);
// 122
        addEdge(122, 25, 43);
        addEdge(122, 131, 37);
        addEdge(122, 10, 47);
        addEdge(122, 42, 45);
// 123
        addEdge(123, 126, 164);
        addEdge(123, 131, 103);
        addEdge(123, 19, 130);
        addEdge(123, 11, 139);
// 124 
        addEdge(124, 135, 137);
        addEdge(124, 76, 80);
        addEdge(124, 64, 188);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        /*for(int i = 0; i<153;i++)
			nodes[i] = new node(i);
		
		fillKSAGraph();
		System.out.println("(BFS)");
		System.out.println(BFS(0,102));
		System.out.println("---\n(UCS)");
		System.out.println(UCS(0,102));
		System.out.println("---\n(IDS)");
		System.out.println(IDS(0,102));*/
    }
}
