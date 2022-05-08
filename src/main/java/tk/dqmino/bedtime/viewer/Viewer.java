package tk.dqmino.bedtime.viewer;

public interface Viewer<I, O> {
    O view(I i);
}
