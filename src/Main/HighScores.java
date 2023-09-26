package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScores extends Panel{
	
	private static final String SCORE_FILE_PATH = "C:\\Users\\Sadik\\eclipse-workspace\\Term Project\\bin\\scores.txt";
	
	
	class Score implements Comparable<Score> {
		private String username;
		private int score;
		
		public Score(String username, int score) {
			this.username = username;
			this.score = score;
		}
		
		public String getUsername() {
	        return username;
	    }

	    public int getScore() {
	        return score;
	    }
		
		@Override
		public int compareTo(HighScores.Score o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.score, o.score);
		}
		
	}
	
	private void display_high_scores() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(11, 2));
		
		
		JLabel header_label = new JLabel("HIGH SCORES");
		header_label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35)); 
		header_label.setForeground(Color.CYAN); 
		header_label.setHorizontalAlignment(JLabel.CENTER);
		
		panel.add(header_label);
		
		String scores = readFromFile(SCORE_FILE_PATH);
		String[] lines = scores.split("\n");
		int count = 0;
		
		Score[] score_array = new Score[lines.length];
		
		for(int i = 0; i < lines.length; i++) {
			String[] parts = lines[i].split(",");
			
			if(parts.length >= 2) {
				String username = parts[0];
				int score = Integer.parseInt(parts[1]);
				
				Score score_obj = new Score(username, score);
				score_array[i] = score_obj;
			}
			
		}
		
		Arrays.sort(score_array, (s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
		
		for (int i = 0; i < score_array.length && count < 20; i++) {
	        String username = score_array[i].getUsername();
	        int score = score_array[i].getScore();
	        
	        JLabel number_label = new JLabel(String.format("%-4d", i+1));
	        JLabel username_label = new JLabel(String.format("%-30s", username));
	        JLabel scor_label = new JLabel(String.format("%-6d", score));
	        
	        number_label.setForeground(Color.WHITE);
	        username_label.setForeground(Color.WHITE);
	        scor_label.setForeground(Color.WHITE);
	        
	        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	        username_label.setFont(font);
	        scor_label.setFont(font);
	        number_label.setFont(font);
	        
	        JPanel score_panel = new JPanel();
	        score_panel.setOpaque(false);
	        score_panel.add(number_label);
	        score_panel.add(username_label);
	        score_panel.add(scor_label);
	       
	        
	        panel.add(score_panel);
	        
	        count++;
	        
		}
		
		if(count == 0) {
			JLabel no_score_label = new JLabel("No high scores available.");
			panel.add(no_score_label);
		}
	      
		panel.setBackground(Color.BLACK);
		panel.requestFocus();
		panel.setVisible(true);
	
	}
}
