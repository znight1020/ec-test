package ec.perf;

import ec.perf.obj.TestObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
public class UnifiedSetRehashBenchMark {

  @Param({"1000000"})
  public int setSize;

  // This parameter controls the hash collision rate. A lower value leads to more collisions.
  // We test with different collision rates to see how the optimization behaves under varying load.
  @Param({"100", "500", "1000"})
  public int collisionFactor;

  private List<TestObject> objects;

  /**
   * This method runs once per trial to prepare the test data.
   * The time taken here is *not* included in the benchmark measurement,
   * ensuring we only time the 'measureRehash' method.
   */
  @Setup(Level.Trial)
  public void setup() {
    objects = new ArrayList<>(setSize);
    for (int i = 0; i < setSize; i++) {
      objects.add(new TestObject(i, this.collisionFactor));
    }
  }

  @Benchmark
  public UnifiedSet<TestObject> measureRehash() {
    UnifiedSet<TestObject> set = new UnifiedSet<>(16);
    for (TestObject key : objects) {
      set.add(key);
    }
    return set;
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(UnifiedSetRehashBenchMark.class.getSimpleName())
        .build();

    new Runner(opt).run();
  }
}