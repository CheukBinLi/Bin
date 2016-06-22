package Controller.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import Controller.entity.LuceneRss;

public class LuceneUtil {

	public void create(List<LuceneRss> list, String path) throws IOException {
		Directory directory = FSDirectory.open(Paths.get(path));
		IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());// 分词器
		IndexWriter writer = new IndexWriter(directory, config);
		Document doc;

		FieldType type = new FieldType(TextField.TYPE_STORED);
		type.setStoreTermVectorOffsets(true);// 记录相对增量
		type.setStoreTermVectorPositions(true);// 记录位置信息
		type.setStoreTermVectors(true);// 存储向量信息

		writer.deleteAll();

		for (LuceneRss rss : list) {
			doc = new Document();
			// doc.add(new StringField("id", rss.getId(), Store.YES));
			doc.add(new StringField("id", Integer.toString(rss.getId()), Store.NO));
			doc.add(new TextField("content", rss.getContent(), Store.YES));
			doc.add(new TextField("title", rss.getTitle(), Store.YES));
			doc.add(new TextField("remard", rss.getRemard(), Store.NO));
			writer.addDocument(doc);
		}
		writer.commit();
		writer.close();
	}

	public List<LuceneRss> getSeach() {

		return null;
	}

}
