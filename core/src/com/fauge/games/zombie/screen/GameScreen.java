package com.fauge.games.zombie.screen;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fauge.games.zombie.entity.Direction;
import com.fauge.games.zombie.entity.Entity;
import com.fauge.games.zombie.entity.ListenerClass;
import com.fauge.games.zombie.entity.Player;
import com.fauge.games.zombie.entity.StreetLight;
import com.fauge.games.zombie.entity.WeaponState;
import com.fauge.games.zombie.entity.Zombie;
import com.fauge.games.zombie.weapon.Bicycle;
import com.fauge.games.zombie.weapon.Cone;
import com.fauge.games.zombie.weapon.Remote;
import com.fauge.games.zombie.weapon.TeddyBear;
import com.fauge.games.zombie.weapon.ThrowableWeapon;
import com.fauge.games.zombie.weapon.Tv;

public class GameScreen implements Screen {

	public static final float PIXELS_IN_A_METER = 50;
	public static float camX;
	public static float camY;
	public static FontUtility fontUtility;
	OrthographicCamera cam;
	Viewport view;
	SpriteBatch batch;
	Texture bg;
	World world;
	static Player player;
	Array<Body> bodyArray;
	Box2DDebugRenderer b2drend;
	TiledDrawable drawable;
	Array<ThrowableWeapon> throwableWeaponArray;
	Array<PointLight> pointLightArray;
	RayHandler rayHandler; 
	ConeLight light;
	Array<StreetLight> streetLightArray;
	Array<Zombie> ZombieArray;
	
	StreetLight streetLight;
	Zombie zombie;
	ListenerClass ContactListenerObject;
	@Override
	public void show() {
		// TODO Auto-generated method stub
		fontUtility = new FontUtility();
		fontUtility.setFont(Color.GRAY, 45);
		cam = new OrthographicCamera(720, 480);
		camX = 720/2;
		camY = 480/2;
		cam.position.set(camX, camY, 0);
		cam.update();
		view = new StretchViewport(720, 480,cam);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		bg = new Texture("background.png");
//		box2d stuff
		world = new World(new Vector2(0, -10.0f),true);
		rayHandler = new RayHandler(world);
		BodyDef Ground = new BodyDef();
		Ground.type = BodyType.StaticBody;
		Ground.position.set(0, 31.9f/2f);
		Body GroundBody = world.createBody(Ground);
		EdgeShape groundshape = new EdgeShape();
		groundshape.set(-100, -13, 100, -13);
		GroundBody.createFixture(groundshape, 1);
//		player stuff
		player = new Player(720/2, 155,rayHandler);
		player.initBox2D(world);
		bodyArray = new Array<Body>();
		b2drend = new Box2DDebugRenderer();
		drawable = new TiledDrawable(new TextureRegion(bg));
		throwableWeaponArray = new Array<ThrowableWeapon>();
		pointLightArray = new Array<PointLight>();
		ZombieArray = new Array<Zombie>();
		rayHandler.setAmbientLight(.15f);
		//placing street lights
		for(int i = -3000;i < 3000; i+=500){
			streetLight = new StreetLight(i, 165, false);
			streetLight.initBox2D(world);
			light = new ConeLight(rayHandler, 256, Color.YELLOW, 210, i, 200, 270, 45);
			light.setXray(true);
			light.setStaticLight(true);
		}
		for(int i = -5000;i < 5000; i+=350){
			Zombie toBeAdded = new Zombie(i, 155, player,rayHandler);
			toBeAdded.initBox2D(world);
			ZombieArray.add(toBeAdded);
		}
		ContactListenerObject = new ListenerClass();
		world.setContactListener(ContactListenerObject);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		rayHandler.setAmbientLight(.05f);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.update();
		camX = player.body.getPosition().x*PIXELS_IN_A_METER;
		camY = 480/2;
		cam.position.set(camX, camY,0);
		cam.update();
		if(Gdx.input.isKeyJustPressed(Keys.O)){
			Player.weaponState = WeaponState.REMOTE;
		}
		else if(Gdx.input.isKeyJustPressed(Keys.P)){
			Player.weaponState = WeaponState.TEDDYBEAR;
		}
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){	
			boolean skip = false;
			Entity tobeadded;
			switch (Player.getSelected()) {
			case 1:
				tobeadded = new TeddyBear(camX/PIXELS_IN_A_METER,480/2/PIXELS_IN_A_METER);
				break;
			case 2:
				tobeadded = new Remote(camX/PIXELS_IN_A_METER,480/2/PIXELS_IN_A_METER);
				break;
			case 3:
				tobeadded = new Bicycle(camX/PIXELS_IN_A_METER,480/2/PIXELS_IN_A_METER,2, 10);
				break;
			case 4:
				tobeadded = new Cone(camX/PIXELS_IN_A_METER,480/2/PIXELS_IN_A_METER,2, 20);
				break;
			case 5:
				tobeadded = new Tv(camX/PIXELS_IN_A_METER,480/2/PIXELS_IN_A_METER,2, 30);
				break;
			default:
				tobeadded = null;
				break;
			}
			if(Player.getSelected() == 1 || Player.getSelected() == 2){
				if(tobeadded != null){
					float throwX = 0;
					if(Player.directionState == Direction.LEFT){
						throwX = MathUtils.random(-5.0f, -3.0f);
						pointLightArray.add(new PointLight(rayHandler,64,Color.GRAY,10,camX,480/2));
					}
					else if(Player.directionState == Direction.RIGHT){
						throwX = MathUtils.random(3.0f, 5.0f);
						pointLightArray.add(new PointLight(rayHandler,64,Color.GRAY,10,camX,480/2));
					}
					else
						skip = true;
					
					if(!skip){
					tobeadded.initBox2D(world);
					tobeadded.body.setLinearVelocity(throwX, 5);
					tobeadded.body.setAngularVelocity(5f);
					throwableWeaponArray.add((ThrowableWeapon) tobeadded);
					}
			}
		}
		}
		batch.setProjectionMatrix(cam.combined);
//		start drawing
		world.step(delta, 10, 15);
		world.getBodies(bodyArray);
		batch.begin();
		drawable.draw(batch, -720*5, 0, 720*10, 480);
		rayHandler.setCombinedMatrix(cam.combined);
		
		for(Zombie z : ZombieArray){
			z.update(player);
		}
		for(Body b : bodyArray){
			if(b.getFixtureList().get(0).getUserData() != null){
				Entity ent = (Entity) b.getFixtureList().get(0).getUserData();
				ent.update();
				ent.spr.setRotation(ent.body.getAngle()*MathUtils.radiansToDegrees);
				ent.spr.setPosition((PIXELS_IN_A_METER * ent.posX)-ent.spr.getWidth()/2, 
									(PIXELS_IN_A_METER * ent.posY)-ent.spr.getHeight()/2);
				if(ent.Alive)
					ent.spr.draw(batch);
				else
					world.destroyBody(b);
				continue;
			}
		}
		for(int i = 0;i < pointLightArray.size; i++){
			pointLightArray.get(i).setPosition(throwableWeaponArray.get(i).posX * PIXELS_IN_A_METER,throwableWeaponArray.get(i).posY * PIXELS_IN_A_METER);
			pointLightArray.get(i).update();
		}	
		batch.end();
		bodyArray.clear();
		rayHandler.updateAndRender();
		//drawing text
		batch.begin();
		if(!player.Alive){
			fontUtility.setColor(Color.RED);
			fontUtility.draw(batch, "Game Over", camX-fontUtility.getBounds("Game Over").width/2, view.getWorldHeight()/2);
			fontUtility.setColor(Color.WHITE);
			fontUtility.draw(batch, "Final score: " + Player.score, camX-fontUtility.getBounds("Final score: " + Player.score).width/2, view.getWorldHeight()/2-45);
		}
		else{
			fontUtility.setColor(Color.WHITE);
			fontUtility.draw(batch, "Score: " + Player.score, camX-view.getWorldWidth()/2+20, view.getWorldHeight()-20);
			fontUtility.draw(batch, "HP:" + player.HP, camX+view.getWorldWidth()/2-20-fontUtility.getBounds("HP:" + player.HP).width, view.getWorldHeight()-20);
		}
		batch.end();
//		b2drend.render(world, cam.combined);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		view.update(width, height,true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
