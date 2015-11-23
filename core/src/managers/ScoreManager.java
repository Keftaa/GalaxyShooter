package managers;

import networking.MyClient;

public class ScoreManager {
	
	private int hitCount;
	private int oppScore;
	public int score;
	private MyClient client;
	
	public ScoreManager(MyClient client){
		this.client = client;
		hitCount = 0;
		oppScore = 0;
		score = 0;
	}
	
	public void update(){
		hitCount++;
		if(hitCount==1)
			oppScore+=100;
		else
			oppScore+=20;
		
		client.sendScore(oppScore);
	}
	
	public void render(){
		score = client.score;
	}
}
