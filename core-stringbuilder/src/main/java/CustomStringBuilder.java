
public class CustomStringBuilder {
    private final StringBuilder sb;
    private final SnapshotListHandler snapshotListHandler;

    public CustomStringBuilder() {
        var defaultSnapshotCount = 3;
        this.sb = new StringBuilder();
        this.snapshotListHandler = new SnapshotListHandler(defaultSnapshotCount);
        save();
    }

    public CustomStringBuilder(int maxSnapshotCount) {
        this.sb = new StringBuilder();
        this.snapshotListHandler = new SnapshotListHandler(maxSnapshotCount);
        save();
    }

    private void save() {
        snapshotListHandler.save(sb.toString());
    }

    public CustomStringBuilder undo() {
        var snapshot = snapshotListHandler.undo();
        if (snapshot == null) {
            System.out.println("No more snapshots"); // Or log, throw ex, or whatever
            sb.delete(0, sb.length());
            return this;
        } else {
            sb.delete(0, sb.length());
            sb.append(snapshot.getValue());
            return this;
        }
    }


    /*
    Теоретически можно попробовать использовать паттерн Delegate и сделать обобщенный вызов с methodName и
    Object[] args, но боюсь закопаюсь, а задача вроде про другое.
    Поэтому просто несколько методов из StringBuilder для примера.
    */
    public String toString() {
        return sb.toString();
    }

    public CustomStringBuilder append(String str) {
        sb.append(str);
        save();
        return this;
    }

    public CustomStringBuilder delete(int start, int end) {
        sb.delete(start, end);
        save();
        return this;
    }

    public CustomStringBuilder replace(int start, int end, String str) {
        sb.replace(start, end, str);
        save();
        return this;
    }

    public CustomStringBuilder insert(int index, char[] str, int offset, int len) {
        sb.insert(index, str, offset, len);
        save();
        return this;
    }
}
