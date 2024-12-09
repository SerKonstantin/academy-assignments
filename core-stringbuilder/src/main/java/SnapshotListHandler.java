import java.util.ArrayDeque;
import java.util.Deque;

public class SnapshotListHandler {
    private final Deque<Snapshot> snapshots = new ArrayDeque<>();
    private final int maxSnapshotCount;

    public SnapshotListHandler(int maxSnapshotCount) {
        this.maxSnapshotCount = maxSnapshotCount;
    }

    public void save(String state) {
        if (snapshots.size() == maxSnapshotCount) {
            snapshots.removeFirst();
        }
        snapshots.addLast(new Snapshot(state));
    }

    public Snapshot undo() {
        if (snapshots.isEmpty()) {
            return null;
        } else {
            snapshots.removeLast();
            return snapshots.peekLast();
        }
    }
}
