package entities.particles;


import entities.Camera;
import entities.Player;
import maths.Vector2f;
import maths.Vector3f;
import renderEngine.NewDisplayManager;

public class Particle {

    private Vector3f position;
    private Vector3f velocity;
    private float gravityEffect;
    private float lifeLength;
    private float rotation;
    private float scale;

    private ParticleTexture texture;

    private Vector2f texOffset1 = new Vector2f();
    private Vector2f texOffset2 = new Vector2f();
    private float blendFactor;

    private float elapsedTime;
    private float distance;

    public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale, ParticleTexture texture) {
        this.texture = texture;
        this.position = position;
        this.velocity = velocity;
        this.gravityEffect = gravityEffect;
        this.lifeLength = lifeLength;
        this.rotation = rotation;
        this.scale = scale;
        ParticleMaster.addParticle(this);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public ParticleTexture getTexture() {
        return texture;
    }

    public Vector2f getTexOffset1() {
        return texOffset1;
    }

    public Vector2f getTexOffset2() {
        return texOffset2;
    }

    public float getBlendFactor() {
        return blendFactor;
    }

    public float getDistance() {
        return distance;
    }

    public boolean update(Camera camera){
        velocity.y -= Player.GRAVITY * gravityEffect * NewDisplayManager.getFrameTimeSeconds();
        Vector3f change = new Vector3f(velocity);
        change.scale(NewDisplayManager.getFrameTimeSeconds());
        Vector3f.add(change, position, position);
        distance = Vector3f.sub(camera.getPosition(), position).lengthSquared();
        updateTextureCoordInfo();
        elapsedTime += NewDisplayManager.getFrameTimeSeconds();
        return elapsedTime < lifeLength;
    }

    private void updateTextureCoordInfo(){
        float lifeFactor = elapsedTime / lifeLength;
        int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
        float atlasProgression = lifeFactor * stageCount;
        int currentIndex = (int) Math.floor(atlasProgression);
        int nextIndex = currentIndex < stageCount - 1 ? currentIndex + 1: currentIndex;
        this.blendFactor = atlasProgression % 1;
        setTextureOffset(texOffset1, currentIndex);
        setTextureOffset(texOffset2, nextIndex);
    }

    private void setTextureOffset(Vector2f offset, int index){
        int column = index % texture.getNumberOfRows();
        int row = index / texture.getNumberOfRows();
        offset.x = (float)column / (float)texture.getNumberOfRows();
        offset.y = (float)row / (float)texture.getNumberOfRows();
    }
}
