package audio;


import java.io.IOException;

public class AudioTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        AudioMaster.init();
        AudioMaster.setListenerData(0 ,0, 0);
        //AL10.alDistanceModel(AL10.AL_INVERSE_DISTANCE_CLAMPED);

        int buffer = AudioMaster.loadSound("res/sounds/jj-omg.ogg");
        Source source = new Source();

        source.setLooping(true);
        source.play(buffer);

        float xPos = 10;
        source.setPosition(xPos, 0, 0.1f);

        while (true){
            xPos -= 0.01f;
            source.setPosition(xPos, 0, 0.1f);
            System.out.println(xPos);
            Thread.sleep(10);

        }
        //source.delete();
        //AudioMaster.cleanUp();
    }
}
