package Controller.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LuceneRss {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@Column(length = 500)
	private String content;
	private String remard;

	public int getId() {
		return id;
	}

	public LuceneRss setId(int id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public LuceneRss setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public LuceneRss setContent(String content) {
		this.content = content;
		return this;
	}

	public String getRemard() {
		return remard;
	}

	public LuceneRss setRemard(String remard) {
		this.remard = remard;
		return this;
	}

	public LuceneRss() {
		super();
	}

	public LuceneRss(int id, String title, String content, String remard) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.remard = remard;
	}

}
