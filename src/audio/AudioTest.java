package audio;


import java.io.IOException;

public class AudioTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        AudioMaster.init();
        AudioMaster.setListenerData(0 ,0, 0);

        int buffer = AudioMaster.loadSound("res/sounds/bounce.wav");
        Source source = new Source();

        source.setLooping(true);
        source.play(buffer);

        float xPos = 8;
        source.setPosition(xPos, 0, 2);

        char c = ' ';
        while (c != 'q'){
            xPos -= 0.03f;
            source.setPosition(xPos, 0, 2);
            Thread.sleep(10);
        }
        source.delete();
        AudioMaster.cleanUp();
    }
}
