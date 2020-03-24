package toolbox;

public class DayTracker {
    private float time = 0f; // day = 60 seconds

    private float secondsPerDay;

    public DayTracker(float secondsPerDay) {
        this.secondsPerDay = secondsPerDay;
    }

    public void tick(){
        time += DisplayManager.getFrameTimeSeconds();
        time %= secondsPerDay;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTimeAsPercentage(){
        return time / secondsPerDay;
    }

    public float getSecondsPerDay() {
        return secondsPerDay;
    }
}
