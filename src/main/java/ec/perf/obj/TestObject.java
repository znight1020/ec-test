package ec.perf.obj;

import java.util.Arrays;
import java.util.Objects;

public class TestObject {
  private final int id;
  private final String name;
  private final long timestamp;
  private final double value1;
  private final double value2;
  private final String category;
  private final byte[] data1;
  private final byte[] data2;
  private final boolean flag1;
  private final boolean flag2;

  private final int collisionFactor;

  public TestObject(int id, int collisionFactor) {
    this.id = id;
    this.collisionFactor = collisionFactor;
    this.name = "key-name-for-id-" + id;
    this.timestamp = System.nanoTime() + id;
    this.value1 = id * 1.618;
    this.value2 = id * 3.141;
    this.category = "category-" + (id % 500);
    this.data1 = new byte[]{(byte) id, (byte) (id >> 8)};
    this.data2 = new byte[]{(byte) (id >> 16), (byte) (id >> 24)};
    this.flag1 = (id % 2 == 0);
    this.flag2 = (id % 3 == 0);
  }

  @Override
  public int hashCode() {
    return id % this.collisionFactor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestObject that = (TestObject) o;
    return id == that.id &&
        timestamp == that.timestamp &&
        Double.compare(that.value1, value1) == 0 &&
        Double.compare(that.value2, value2) == 0 &&
        flag1 == that.flag1 &&
        flag2 == that.flag2 &&
        Objects.equals(name, that.name) &&
        Objects.equals(category, that.category) &&
        Arrays.equals(data1, that.data1) &&
        Arrays.equals(data2, that.data2);
  }
}
