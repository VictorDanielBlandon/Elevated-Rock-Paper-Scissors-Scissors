import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class EffectsManager {
    private Clip swordSound, shieldSound, gunSound;
    
    public EffectsManager() {
        try {
            swordSound = loadSound("C:\\Users\\88693\\Desktop\\rps\\resources\\sword.wav");
            shieldSound = loadSound("C:\\Users\\88693\\Desktop\\rps\\resources\\shield.wav");
            gunSound = loadSound("C:\\Users\\88693\\Desktop\\rps\\resources\\gun.wav");
        } catch (Exception e) {
            System.err.println("Error loading sound effects: " + e.getMessage());
        }
    }
    
    private Clip loadSound(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL url = getClass().getResource(filename);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        return clip;
    }
    
    public void playSwordEffect() {
        if (swordSound != null) {
            swordSound.setFramePosition(0);
            swordSound.start();
        }
    }
    
    public void playShieldEffect() {
        if (shieldSound != null) {
            shieldSound.setFramePosition(0);
            shieldSound.start();
        }
    }
    
    public void playGunEffect() {
        if (gunSound != null) {
            gunSound.setFramePosition(0);
            gunSound.start();
        }
    }
}