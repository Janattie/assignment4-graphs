package util;

public class Metrics {
    private long dfsVisits = 0, dfsEdges = 0, kahnPushes = 0, kahnPops = 0, relaxations = 0;
    private long timeSccNs = 0;

    public void incDfsVisits() { dfsVisits++; }
    public void incDfsEdges() { dfsEdges++; }
    public void incKahnPushes() { kahnPushes++; }
    public void incKahnPops() { kahnPops++; }
    public void incRelaxations() { relaxations++; }
    public void addTimeScc(long ns) { timeSccNs += ns; }

    public long getDfsVisits() { return dfsVisits; }
    public long getDfsEdges() { return dfsEdges; }
    public long getKahnPushes() { return kahnPushes; }
    public long getKahnPops() { return kahnPops; }
    public long getRelaxations() { return relaxations; }
    public long getTimeSccNs() { return timeSccNs; }
}
