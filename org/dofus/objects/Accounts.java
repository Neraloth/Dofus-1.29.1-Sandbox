package org.dofus.objects;

import org.apache.mina.core.session.IoSession;
import org.dofus.utils.Cipher;

public class Accounts {

	private int id;
	private String username;
	private String password;
	
	private String question;
	private String answer;
	private String nickname;
	
	private boolean banned = false;
	
	private IoSession session;
	
	public Accounts(int id, String username, String password, String question, String answer, String nickname, boolean banned) {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setQuestion(question);
		this.setAnswer(answer);
		this.setNickname(nickname);
		this.setBanned(banned);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	//TODO: Check si la uncrypt est plus rapide
	public boolean valid(String password, String key) {
		return password.equals(Cipher.encode(this.password, key));
	}
}
