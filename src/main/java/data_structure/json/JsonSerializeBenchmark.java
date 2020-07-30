package data_structure.json;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.naming.PartialResultException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 利用基准测试，测试各个json的性能
 * Created by Niki on 2019/6/10 9:54
 */
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JsonSerializeBenchmark {

    @Param({"1000", "10000", "100000"})
    private int count;

    private Person person;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JsonSerializeBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(0)
                .build();
        Collection<RunResult> results = new Runner(options).run();
        if (results.iterator().hasNext()) {
            System.out.println("结果="+ results.iterator().next());
        }
    }

    @Benchmark
    public void Gson() {
        for (int i = 0; i < count; i++) {
            GsonUtil.bean2Json(person);
        }
    }

    @Benchmark
    public void FastJson() {
        for (int i = 0; i < count; i++) {
            FastJsonUtil.bean2Json(person);
        }
    }

    @Setup
    public void prepare() {
        List<Person> friends=new ArrayList<Person>();
        friends.add(createAPerson("小明",null));
        friends.add(createAPerson("Tony",null));
        friends.add(createAPerson("陈小二",null));
        person=createAPerson("邵同学",friends);
    }

    @TearDown
    public void shutdown() {
        System.out.println("shutdown");
    }
    private Person createAPerson(String name,List<Person> friends) {
        Person newPerson=new Person();
        newPerson.setName(name);
        newPerson.setFullName(new FullName("zjj_first", "zjj_middle", "zjj_last"));
        newPerson.setAge(24);
        List<String> hobbies=new ArrayList<String>();
        hobbies.add("篮球");
        hobbies.add("游泳");
        hobbies.add("coding");
        newPerson.setHobbies(hobbies);
        Map<String,String> clothes=new HashMap<String, String>();
        clothes.put("coat", "Nike");
        clothes.put("trousers", "adidas");
        clothes.put("shoes", "安踏");
        newPerson.setClothes(clothes);
        newPerson.setFriends(friends);
        return newPerson;
    }

}
