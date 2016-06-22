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
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
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

	public List<LuceneRss> getSeach(String search, String path) throws IOException, ParseException {

		Directory directory = FSDirectory.open(Paths.get(path));
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		// QueryParser parser=new QueryParser(, new SimpleAnalyzer());
		QueryParser parser = new MultiFieldQueryParser(new String[] { "" }, new SimpleAnalyzer());
		Query query = parser.parse(search);
		TopDocs topDocs = searcher.search(query, 1000);

		return null;
	}

}
