package Controller.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import Controller.entity.LuceneRss;
import Controller.entity.service.LuceneRssService;

@Component
public class LuceneUtil {

	@Autowired
	private LuceneRssService luceneRssService;

	private String path = "D:/Lucene";

	public LuceneUtil() {
		super();
		System.out.println("123");
	}

	private Analyzer analyzer = new IKAnalyzer(true);

	private Directory directory = new RAMDirectory();

	@PostConstruct
	public void create() throws Throwable {
		// create(luceneRssService.getList(null, false, 0, 0), getPath());
	}

	// private Analyzer analyzer = new IKAnalyzer();

	public static void main(String[] args) throws IOException, ParseException {
		List<LuceneRss> list = new ArrayList<LuceneRss>();
		list.add(new LuceneRss(1, "番禺日报", "你好好好限限好好中国人在这里广州", "咖啡因"));
		LuceneUtil u = new LuceneUtil();
		u.create(list, "D:/Lucene");
		u.getSeach("中国人", "D:/Lucene");
	}

	public void create(List<LuceneRss> list, String path) throws IOException {
		// Directory directory = FSDirectory.open(Paths.get(path));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);// 分词器
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
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
			doc.add(new StringField("id", Integer.toString(rss.getId()), Store.YES));
			doc.add(new TextField("title", rss.getTitle(), Store.YES));
			doc.add(new TextField("content", rss.getContent(), Store.YES));
			doc.add(new TextField("remard", rss.getRemard(), Store.NO));
			writer.addDocument(doc);
		}
		writer.commit();
		writer.close();
	}

	public List<LuceneRss> getSeach(String search) throws IOException, ParseException {
		return getSeach(search, getPath());
	}

	public List<LuceneRss> getSeach(String search, String path) throws IOException, ParseException {
		// Directory directory = FSDirectory.open(Paths.get(path));
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser("content", analyzer);

		// QueryParser parser = new MultiFieldQueryParser(new String[] { "content" }, analyzer);

		Query query = parser.parse(search);

		TopDocs topDocs = searcher.search(query, 1000);

		Document doc;
		List<LuceneRss> rss = new ArrayList<LuceneRss>();
		for (ScoreDoc sd : topDocs.scoreDocs) {
			doc = searcher.doc(sd.doc);
			rss.add(new LuceneRss(Integer.valueOf(doc.get("id")), doc.get("title"), doc.get("content"), doc.get("remard")));
		}
		System.out.println(rss.size());
		return rss;
	}

	public String getPath() {
		return path;
	}

	public LuceneUtil setPath(String path) {
		this.path = path;
		return this;
	}

	// public static void main(String[] args) {
	// // Lucene Document的域名
	// String fieldName = "content";
	// // 检索内容
	// // String text = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";
	// String text = "你好好好限限好好中国人在这里广州";
	//
	// // 实例化IKAnalyzer分词器
	// Analyzer analyzer = new IKAnalyzer(true);
	//
	// Directory directory = null;
	// IndexWriter iwriter = null;
	// IndexReader ireader = null;
	// IndexSearcher isearcher = null;
	// try {
	// // 建立内存索引对象
	// directory = FSDirectory.open(Paths.get("D:/Lucene"));
	//
	// // 配置IndexWriterConfig
	// IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
	// iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
	// iwriter = new IndexWriter(directory, iwConfig);
	//
	// iwriter.deleteAll();
	// Document doc;
	// List<LuceneRss> list = new ArrayList<LuceneRss>();
	// list.add(new LuceneRss(1, "番禺日报", "你好好好限限好好中国人在这里广州", "咖啡因"));
	// for (LuceneRss rss : list) {
	// doc = new Document();
	// // doc.add(new StringField("id", rss.getId(), Store.YES));
	// doc.add(new StringField("id", Integer.toString(rss.getId()), Field.Store.YES));
	// doc.add(new TextField("title", rss.getTitle(), Field.Store.YES));
	// doc.add(new TextField(fieldName, rss.getContent(), Field.Store.YES));
	// doc.add(new TextField("remard", rss.getRemard(), Field.Store.YES));
	// iwriter.addDocument(doc);
	// }
	//
	// // 写入索引
	// // Document doc = new Document();
	// // doc = new Document();
	// // doc.add(new StringField("ID", "10000", Field.Store.YES));
	// // doc.add(new TextField(fieldName, text, Field.Store.YES));
	// // iwriter.addDocument(doc);
	// iwriter.close();
	//
	// // 搜索过程**********************************
	// // 实例化搜索器
	// ireader = DirectoryReader.open(directory);
	// isearcher = new IndexSearcher(ireader);
	//
	// String keyword = "你好";
	// // 使用QueryParser查询分析器构造Query对象
	// QueryParser qp = new QueryParser(fieldName, analyzer);
	// qp.setDefaultOperator(QueryParser.AND_OPERATOR);
	// Query query = qp.parse(keyword);
	// System.out.println("Query = " + query);
	//
	// // 搜索相似度最高的5条记录
	// TopDocs topDocs = isearcher.search(query, 5);
	// System.out.println("命中：" + topDocs.totalHits);
	// // 输出结果
	// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	// for (int i = 0; i < topDocs.totalHits; i++) {
	// Document targetDoc = isearcher.doc(scoreDocs[i].doc);
	// System.out.println("内容：" + targetDoc.toString());
	// }
	//
	// } catch (CorruptIndexException e) {
	// e.printStackTrace();
	// } catch (LockObtainFailedException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (ParseException e) {
	// e.printStackTrace();
	// } finally {
	// if (ireader != null) {
	// try {
	// ireader.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// if (directory != null) {
	// try {
	// directory.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

}
